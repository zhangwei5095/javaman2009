/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.jpdl.internal.xml;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.jbpm.api.activity.ActivityBehaviour;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.model.Event;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.internal.log.Log;
import org.jbpm.jpdl.internal.activity.JpdlBinding;
import org.jbpm.jpdl.internal.activity.MailListener;
import org.jbpm.jpdl.internal.model.JpdlProcessDefinition;
import org.jbpm.pvm.internal.email.impl.MailProducerImpl;
import org.jbpm.pvm.internal.email.impl.MailTemplate;
import org.jbpm.pvm.internal.email.impl.MailTemplateRegistry;
import org.jbpm.pvm.internal.email.spi.MailProducer;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.model.ActivityCoordinatesImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.CompositeElementImpl;
import org.jbpm.pvm.internal.model.Continuation;
import org.jbpm.pvm.internal.model.EventImpl;
import org.jbpm.pvm.internal.model.EventListenerReference;
import org.jbpm.pvm.internal.model.ObservableElementImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.ScopeElementImpl;
import org.jbpm.pvm.internal.model.TimerDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.model.VariableDefinitionImpl;
import org.jbpm.pvm.internal.model.VariableOutDefinitionImpl;
import org.jbpm.pvm.internal.model.VariableOutDefinitionSet;
import org.jbpm.pvm.internal.task.AssignableDefinitionImpl;
import org.jbpm.pvm.internal.task.SwimlaneDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.util.ReflectUtil;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.Descriptor;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.binding.MailTemplateBinding;
import org.jbpm.pvm.internal.wire.binding.ObjectBinding;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.wire.operation.Operation;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Bindings;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.jbpm.pvm.internal.xml.ProblemImpl;
import org.w3c.dom.Element;

/**
 * @author Tom Baeyens
 */
public class JpdlParser extends Parser {
  
  private static final Log log = Log.getLog(JpdlParser.class.getName());

  public static final String JPDL_NAMESPACE = "http://jbpm.org/4.0/jpdl";

  public static final WireParser wireParser = WireParser.getInstance();

  // array elements are mutable, even when final
  // never make a static array public
  static final String[] DEFAULT_ACTIVITIES_RESOURCES = {
    "jbpm.jpdl.activities.xml",
    "jbpm.user.activities.xml"
  }; 

  static final String[] DEFAULT_EVENTLISTENERS_RESOURCES = {
    "jbpm.jpdl.eventlisteners.xml",
    "jbpm.user.eventlisteners.xml"
  }; 

  static BindingsParser bindingsParser = new BindingsParser();
  static final String CATEGORY_ACTIVITY = "activity";
  static final String CATEGORY_EVENT_LISTENER = "eventlistener";

  public JpdlParser() {
    initialize(); 
    
    parseBindings();
    
    List<String> schemaResources =  new ArrayList<String>();
    schemaResources.add("jpdl-4.0.xsd");
    setSchemaResources(schemaResources);
  }

  protected void parseBindings() {
    Bindings bindings = new Bindings();
    setBindings(bindings);

    for (String activityResource: DEFAULT_ACTIVITIES_RESOURCES) {
      Enumeration<URL> resourceUrls = ReflectUtil.getResources(null, activityResource);
      if (resourceUrls.hasMoreElements()) {
        while (resourceUrls.hasMoreElements()) {
          URL resourceUrl = resourceUrls.nextElement();
          log.trace("loading jpdl activities from resource: "+resourceUrl);
          List<JpdlBinding> activityBindings = (List<JpdlBinding>) bindingsParser.createParse()
            .setUrl(resourceUrl)
            .execute()
            .checkErrors("jpdl activities from "+resourceUrl.toString())
            .getDocumentObject();
          
          for (JpdlBinding binding: activityBindings) {
            binding.setCategory(CATEGORY_ACTIVITY);
            bindings.addBinding(binding);
          }
        }
      } else {
        log.trace("skipping unavailable jpdl activities resource: "+activityResource);
      }
    }

    for (String eventListenerResource: DEFAULT_EVENTLISTENERS_RESOURCES) {
      Enumeration<URL> resourceUrls = ReflectUtil.getResources(null, eventListenerResource);
      if (resourceUrls.hasMoreElements()) {
        while (resourceUrls.hasMoreElements()) {
          URL resourceUrl = resourceUrls.nextElement();
          log.trace("loading jpdl eventlisteners from resource: "+resourceUrl);
          List<JpdlBinding> activityBindings = (List<JpdlBinding>) bindingsParser.createParse()
            .setUrl(resourceUrl)
            .execute()
            .checkErrors("jpdl eventlisteners from "+resourceUrl.toString())
            .getDocumentObject();
          
          for (JpdlBinding binding: activityBindings) {
            binding.setCategory(CATEGORY_EVENT_LISTENER);
            bindings.addBinding(binding);
          }
        }
      } else {
        log.trace("skipping unavailable jpdl eventlistener resource: "+eventListenerResource);
      }
    }
  }

  public Object parseDocumentElement(Element documentElement, Parse parse) {
    JpdlProcessDefinition processDefinition = (JpdlProcessDefinition) parse.getDocumentObject();
    if (processDefinition==null) {
      processDefinition = new JpdlProcessDefinition();
      parse.setDocumentObject(processDefinition);
    }
    
    parse.pushObject(processDefinition);
    try {
      // process attribues
      String name = XmlUtil.attribute(documentElement, "name", true, parse);
      processDefinition.setName(name);
      
      String packageName = XmlUtil.attribute(documentElement, "package");
      processDefinition.setPackageName(packageName);

      Integer version = XmlUtil.attributeInteger(documentElement, "version", false, parse);
      if (version!=null) {
        processDefinition.setVersion(version);
      }

      String key = XmlUtil.attribute(documentElement, "key", false, parse);
      if (key!=null) {
        processDefinition.setKey(key);
      }

      Element descriptionElement = XmlUtil.element(documentElement, "description");
      if (descriptionElement!=null) {
        String description = XmlUtil.getContentText(descriptionElement);
        processDefinition.setDescription(description);
      }
      
      UnresolvedTransitions unresolvedTransitions = new UnresolvedTransitions();
      parse.pushObject(unresolvedTransitions);
      
      // swimlanes
      List<Element> swimlaneElements = XmlUtil.elements(documentElement, "swimlane");
      for (Element swimlaneElement: swimlaneElements) {
        String swimlaneName = XmlUtil.attribute(swimlaneElement, "name", true, parse);
        if (swimlaneName!=null) {
          SwimlaneDefinitionImpl swimlaneDefinition = 
              processDefinition.createSwimlaneDefinition(swimlaneName);
          JpdlParser.parseAssignmentAttributes(swimlaneElement, swimlaneDefinition, parse);
        }
      }

      // on events
      parseOnEvents(documentElement, parse, processDefinition);
      
      // activities
      parseActivities(documentElement, parse, processDefinition);

      // bind activities to their destinations
      resolveTransitionDestinations(parse, processDefinition, unresolvedTransitions);

    } finally {
      parse.popObject();
    }

    if (processDefinition.getInitial()==null) {
      parse.addProblem("no start activity in process", documentElement);
    }
    
    return processDefinition;
  }

  protected void resolveTransitionDestinations(Parse parse, JpdlProcessDefinition processDefinition, UnresolvedTransitions unresolvedTransitions) {
    for (UnresolvedTransition unresolvedTransition: unresolvedTransitions.list) {
      unresolvedTransition.resolve(processDefinition, parse);
    }
  }

  public void parseActivities(Element documentElement, Parse parse, CompositeElementImpl compositeElement) {
    List<Element> elements = XmlUtil.elements(documentElement);
    for (Element nestedElement : elements) {
      String tagName = XmlUtil.getTagLocalName(nestedElement);
      if ("on".equals(tagName) 
          || "timer".equals(tagName)
          || "swimlane".equals(tagName)) continue;

      JpdlBinding activityBinding = (JpdlBinding) getBinding(nestedElement, CATEGORY_ACTIVITY);
      if (activityBinding == null) {
        log.debug("unrecognized activity: " + tagName);
        continue;
      }

      ActivityImpl activity = compositeElement.createActivity();
      parse.pushObject(activity);
      try {
        activity.setType(activityBinding.getTagName());
        activityBinding.parseName(nestedElement, activity, parse);
        parseTransitions(nestedElement, activity, parse);

        Element descriptionElement = XmlUtil.element(documentElement, "description");
        if (descriptionElement!=null) {
          String description = XmlUtil.getContentText(descriptionElement);
          activity.setDescription(description);
        }

        String continuationText = XmlUtil.attribute(nestedElement, "continue");
        if (continuationText!=null) {
          if ("async".equals(continuationText)) {
            activity.setContinuation(Continuation.ASYNCHRONOUS);
          } else if ("exclusive".equals(continuationText)) {
            activity.setContinuation(Continuation.EXCLUSIVE);
          }
        }

        ActivityBehaviour activityBehaviour = (ActivityBehaviour) activityBinding.parse(nestedElement, parse, this);
        activity.setBehaviour(activityBehaviour);

        parseOnEvents(nestedElement, parse, activity);

        String g = XmlUtil.attribute(nestedElement, "g");
        if (g == null) continue;

        StringTokenizer stringTokenizer = new StringTokenizer(g, ",");
        ActivityCoordinatesImpl coordinates = null;
        if (stringTokenizer.countTokens() == 4) {
          try {
            int x = Integer.parseInt(stringTokenizer.nextToken());
            int y = Integer.parseInt(stringTokenizer.nextToken());
            int width = Integer.parseInt(stringTokenizer.nextToken());
            int height = Integer.parseInt(stringTokenizer.nextToken());
            coordinates = new ActivityCoordinatesImpl(x, y, width, height);
          } catch (NumberFormatException e) {
            coordinates = null;
          }
        }
        if (coordinates != null) {
          activity.setCoordinates(coordinates);
        } else {
          parse.addProblem("invalid coordinates g=\"" + g + "\" in " + activity, nestedElement);
        }
      } finally {
        parse.popObject();
      }
    }
  }
  
  public static TimerDefinitionImpl parseTimerDefinition(Element timerElement, Parse parse, ScopeElementImpl scopeElement) {
    TimerDefinitionImpl timerDefinition = scopeElement.createTimerDefinition();

    String duedate = XmlUtil.attribute(timerElement, "duedate");
    String duedatetime = XmlUtil.attribute(timerElement, "duedatetime");

    if (duedate!=null) {
      timerDefinition.setDueDateDescription(duedate);
      
    } else if (duedatetime!=null) {
      String dueDateTimeFormatText = (String) Environment.getFromCurrent("jbpm.duedatetime.format");
      if (dueDateTimeFormatText==null) {
        dueDateTimeFormatText = "HH:mm dd/MM/yyyy";
      }
      SimpleDateFormat dateFormat = new SimpleDateFormat(dueDateTimeFormatText);
      try {
        Date duedatetimeDate = dateFormat.parse(duedatetime);
        timerDefinition.setDueDate(duedatetimeDate);
      } catch (ParseException e) {
        parse.addProblem("couldn't parse duedatetime "+duedatetime, e);
      }
    } else {
      parse.addProblem("either duedate or duedatetime is required in timer", timerElement);
    }
    
    String repeat = XmlUtil.attribute(timerElement, "repeat");
    timerDefinition.setRepeat(repeat);
    
    return timerDefinition;
  }

  public void parseOnEvents(Element element, Parse parse, ScopeElementImpl scopeElement) {
    // event listeners
    List<Element> onElements = XmlUtil.elements(element, "on");
    for (Element onElement: onElements) {
      String eventName = XmlUtil.attribute(onElement, "event", true, parse);
      parseOnEvent(onElement, parse, scopeElement, eventName);

      Element timerElement = XmlUtil.element(onElement, "timer");
      if (timerElement!=null) {
        TimerDefinitionImpl timerDefinitionImpl = parseTimerDefinition(timerElement, parse, scopeElement);
        timerDefinitionImpl.setEventName(eventName);
      }
    }
  }

  public void parseOnEvent(Element element, Parse parse, ObservableElementImpl observableElement, String eventName) {
    if (eventName!=null) {
      EventImpl event = observableElement.getEvent(eventName);
      if (event==null) {
        event = observableElement.createEvent(eventName);
      }
      
      String continuationText = XmlUtil.attribute(element, "continue");
      if (continuationText!=null) {
        if ("async".equals(continuationText)) {
          event.setContinuation(Continuation.ASYNCHRONOUS);
        } else if ("exclusive".equals(continuationText)) {
          event.setContinuation(Continuation.EXCLUSIVE);
        }
      }

      for (Element eventListenerElement: XmlUtil.elements(element)) {
        JpdlBinding eventBinding = (JpdlBinding) getBinding(eventListenerElement, CATEGORY_EVENT_LISTENER);
        if (eventBinding!=null) {
          EventListener eventListener = (EventListener) eventBinding.parse(eventListenerElement, parse, this);
          EventListenerReference eventListenerReference = event.createEventListenerReference(eventListener);
          
          if (XmlUtil.attributeBoolean(eventListenerElement, "propagation", false, parse, false)) {
            eventListenerReference.setPropagationEnabled(true);
          }
          
          continuationText = XmlUtil.attribute(eventListenerElement, "continue");
          if (continuationText!=null) {
            if (observableElement instanceof ActivityImpl) {
              if (observableElement.getName()==null) {
                parse.addProblem("async continuation on event listener requires activity name", eventListenerElement);
              }
            } else if (observableElement instanceof TransitionImpl) {
              TransitionImpl transition = (TransitionImpl) observableElement;
              if (transition.getSource().getName()==null) {
                parse.addProblem("async continuation on event listener requires name in the transition source activity", eventListenerElement);
              }
            }
            if ("async".equals(continuationText)) {
              eventListenerReference.setContinuation(Continuation.ASYNCHRONOUS);
            } else if ("exclusive".equals(continuationText)) {
              eventListenerReference.setContinuation(Continuation.EXCLUSIVE);
            }
          }

        } else {
          String tagName = XmlUtil.getTagLocalName(eventListenerElement);
          if ( ! ( (observableElement instanceof TransitionImpl)
                   && ( "condition".equals(tagName)
                        || "timer".equals(tagName)
                      )
                 )
             ) {
            parse.addProblem("unrecognized event listener: "+tagName, null, ProblemImpl.TYPE_WARNING, eventListenerElement);
          }
        }
      }
    }
  }

  public void parseTransitions(Element element, ActivityImpl activity, Parse parse) {
    List<Element> transitionElements = XmlUtil.elements(element, "transition");
    UnresolvedTransitions unresolvedTransitions = parse.findObject(UnresolvedTransitions.class);
    for (Element transitionElement: transitionElements) {
      String transitionName = XmlUtil.attribute(transitionElement, "name", false, parse);

      Element timerElement = XmlUtil.element(transitionElement, "timer");
      if (timerElement!=null) {
        TimerDefinitionImpl timerDefinitionImpl = parseTimerDefinition(timerElement, parse, activity);
        timerDefinitionImpl.setSignalName(transitionName);
      }
  
      TransitionImpl transition = activity.createOutgoingTransition();
      transition.setName(transitionName);
  
      unresolvedTransitions.add(transition, transitionElement);
      
      parseOnEvent(transitionElement, parse, transition, Event.TAKE);
    }
  }

  public static void parseAssignmentAttributes(Element element, AssignableDefinitionImpl assignableDefinition, Parse parse) {
    Element descriptionElement = XmlUtil.element(element, "description");
    if (descriptionElement!=null) {
      String description = XmlUtil.getContentText(descriptionElement);
      assignableDefinition.setDescription(description);
    }
  
    Element assignmentHandlerElement = XmlUtil.element(element, "assignment-handler");
    if (assignmentHandlerElement!=null) {
      ObjectDescriptor objectDescriptor = parseObjectDescriptor(assignmentHandlerElement, parse);
      AssignmentHandler assignmentHandler = (AssignmentHandler) WireContext.create(objectDescriptor);
      assignableDefinition.setAssignmentHandler(assignmentHandler);
    }
  
    String assigneeExpression = XmlUtil.attribute(element, "assignee");
    assignableDefinition.setAssigneeExpression(assigneeExpression);
    
    String assigneeExpressionLanguage = XmlUtil.attribute(element, "assignee-lang");
    assignableDefinition.setAssigneeExpressionLanguage(assigneeExpressionLanguage);
    
    String candidateUsersExpression = XmlUtil.attribute(element, "candidate-users");
    assignableDefinition.setCandidateUsersExpression(candidateUsersExpression);
    
    String candidateUsersExpressionLanguage = XmlUtil.attribute(element, "candidate-users-lang");
    assignableDefinition.setCandidateUsersExpressionLanguage(candidateUsersExpressionLanguage);
    
    String candidateGroupsExpression = XmlUtil.attribute(element, "candidate-groups");
    assignableDefinition.setCandidateGroupsExpression(candidateGroupsExpression);
    
    String candidateGroupsExpressionLanguage = XmlUtil.attribute(element, "candidate-groups-lang");
    assignableDefinition.setCandidateGroupsExpressionLanguage(candidateGroupsExpressionLanguage);
  }

  public static TaskDefinitionImpl parseTaskDefinition(Element element, Parse parse, ScopeElementImpl scopeElement) {
    TaskDefinitionImpl taskDefinition = new TaskDefinitionImpl();
  
    String taskName = XmlUtil.attribute(element, "name");
    taskDefinition.setName(taskName);

    String form = XmlUtil.attribute(element, "form");
    taskDefinition.setFormResourceName(form);
    
    ProcessDefinitionImpl processDefinition = parse.findObject(ProcessDefinitionImpl.class);
    if (processDefinition.getTaskDefinition(taskName)!=null) {
      parse.addProblem("duplicate task name "+taskName, element);
    } else {
      processDefinition.addTaskDefinitionImpl(taskDefinition);
    }

    String swimlaneName = XmlUtil.attribute(element, "swimlane");
    if (swimlaneName!=null) {
      JpdlProcessDefinition jpdlProcessDefinition = parse.findObject(JpdlProcessDefinition.class);
      SwimlaneDefinitionImpl swimlaneDefinition = jpdlProcessDefinition.getSwimlaneDefinition(swimlaneName);
      if (swimlaneDefinition!=null) {
        taskDefinition.setSwimlaneDefinition(swimlaneDefinition);
      } else {
        parse.addProblem("swimlane "+swimlaneName+" not declared", element);
      }
    }
    
    JpdlParser.parseAssignmentAttributes(element, taskDefinition, parse);
    
    // parse notification mail producer
    Element notificationElement = XmlUtil.element(element, "notification");
    if (notificationElement != null) {
      parseMailEvent(notificationElement, parse, scopeElement, Event.ASSIGN);
    }

    Element reminderElement = XmlUtil.element(element, "reminder");
    if (reminderElement != null) {
      parseMailEvent(reminderElement, parse, scopeElement, Event.REMIND);
      // associate timer to event
      TimerDefinitionImpl timerDefinition = parseTimerDefinition(reminderElement, parse, scopeElement);
      timerDefinition.setEventName(Event.REMIND);
    }

    return taskDefinition;
  }

  public static ObjectDescriptor parseObjectDescriptor(Element element, Parse parse) {
    ObjectDescriptor objectDescriptor = new ObjectDescriptor();
  
    String className = XmlUtil.attribute(element, "class");
    if (className!=null) {
      objectDescriptor.setClassName(className);
  
      // read the operations elements
      List<Operation> operations = null;
      List<Element> elements = XmlUtil.elements(element);
      
      Set<String> operationTagNames = wireParser.getBindings().getTagNames(WireParser.CATEGORY_OPERATION);
      for (Element childElement: elements) {
        if (operationTagNames.contains(childElement.getTagName())) {
          Operation operation = (Operation) wireParser.parseElement(childElement, parse, WireParser.CATEGORY_OPERATION);
          if (operations==null) {
            operations = new ArrayList<Operation>();
          }
          operations.add(operation);
        }
      }
      objectDescriptor.setOperations(operations);
  
      // autowiring
      Boolean isAutoWireEnabled = XmlUtil.attributeBoolean(element, "auto-wire", false, parse);
      if (isAutoWireEnabled!=null) {
        objectDescriptor.setAutoWireEnabled(isAutoWireEnabled.booleanValue());
      }
    }
    return objectDescriptor;
  }
  

  public static List<VariableDefinitionImpl> parseVariableDefinitions(Element element, Parse parse, boolean initRequired) {
    List<VariableDefinitionImpl> variableDefinitions = new ArrayList<VariableDefinitionImpl>();
    
    for (Element inElement: XmlUtil.elements(element, "variable")) {
      VariableDefinitionImpl variableDefinition = new VariableDefinitionImpl();

      String name = XmlUtil.attribute(inElement, "name", true, parse);
      variableDefinition.setName(name);
      
      int sources = 0;
      
      String initExpr = XmlUtil.attribute(inElement, "init");
      if (initExpr!=null) {
        variableDefinition.setInitExpression(initExpr);
        sources++;
      }
      
      Element valueElement = XmlUtil.element(inElement);
      if (valueElement!=null) {
        Descriptor initValueDescriptor = (Descriptor) WireParser.getInstance().parseElement(valueElement, parse);
        variableDefinition.setInitDescriptor(initValueDescriptor);
        sources++;
      }

      if (initRequired && sources==0) {
        parse.addProblem("no init specified", inElement);
      }
      if (sources>1) {
        parse.addProblem("init attribute and init element are mutually exclusive on element variable", inElement);
      }
      
      variableDefinitions.add(variableDefinition);
    }

    return variableDefinitions;
  }

  public static VariableOutDefinitionSet parseVariableOutDefinitionSet(Element element, Parse parse) {
    VariableOutDefinitionSet variableOutDefinitionSet = new VariableOutDefinitionSet();
    
    for (Element inElement: XmlUtil.elements(element, "out-variable")) {
      VariableOutDefinitionImpl variableOutDefinition = variableOutDefinitionSet.createVariableOutDefinition();

      String name = XmlUtil.attribute(inElement, "name", true, parse);
      variableOutDefinition.setName(name);
      
      String expression = XmlUtil.attribute(inElement, "init");
      if (expression!=null) {
        variableOutDefinition.setExpression(expression);
      }
    }

    return variableOutDefinitionSet;
  }

  public static void parseMailEvent(Element element, Parse parse,
      ObservableElementImpl observableElement, String eventName) {
    // obtain assign event
    EventImpl event = observableElement.getEvent(eventName);
    if (event == null) {
      event = observableElement.createEvent(eventName);
    }
    // register event listener
    MailListener eventListener = new MailListener();
    EventListenerReference eventListenerRef = event.createEventListenerReference(eventListener);
    // set continuation mode
    String continuationText = XmlUtil.attribute(element, "continue");
    if ("async".equals(continuationText)) {
      eventListenerRef.setContinuation(Continuation.ASYNCHRONOUS);
    }
    else if ("exclusive".equals(continuationText)) {
      eventListenerRef.setContinuation(Continuation.EXCLUSIVE);
    }
    // associate mail producer to event listener
    MailProducer mailProducer = parseMailProducer(element, parse, "task-notification");
    eventListener.setMailProducer(mailProducer);
  }

  public static MailProducer parseMailProducer(Element element, Parse parse, String defaultTemplateName) {
    // check whether the element is a generic object descriptor
    if (ObjectBinding.isObjectDescriptor(element)) {
      // TODO test custom mail producer
      ObjectDescriptor objectDescriptor = parseObjectDescriptor(element, parse);
      return (MailProducer) WireContext.create(objectDescriptor);
    }

    // parse the default producer
    MailProducerImpl mailProducer = new MailProducerImpl();
    mailProducer.setTemplate(parseMailTemplate(element, parse, defaultTemplateName));
    return mailProducer;
  }

  private static MailTemplate parseMailTemplate(Element element, Parse parse,
      String defaultTemplateName) {
    if (element.hasAttribute("template")) {
      // fetch template from configuration
      return findTemplate(element, parse, element.getAttribute("template"));
    }
    if (!XmlUtil.isTextOnly(element)) {
      // parse inline template
      return MailTemplateBinding.parseMailTemplate(element, parse);
    }
    if (defaultTemplateName != null) {
      // fetch default template
      return findTemplate(element, parse, defaultTemplateName);
    }
    parse.addProblem("mail template must be referenced in the 'template' attribute "
        + "or specified inline", element);
    return null;
  }

  private static MailTemplate findTemplate(Element element, Parse parse, String templateName) {
    MailTemplateRegistry templateRegistry = Environment.getFromCurrent(MailTemplateRegistry.class);
    if (templateRegistry != null) {
      MailTemplate template = templateRegistry.getTemplate(templateName);
      if (template != null) return template;
    }
    parse.addProblem("mail template not found: " + templateName, element);
    return null;
  }
  
}
