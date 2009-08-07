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
package org.jbpm.jpdl.internal.activity;

import java.util.List;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.binding.ObjectBinding;
import org.jbpm.pvm.internal.wire.descriptor.ExpressionEvaluatorDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.ReferenceDescriptor;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;


/**
 * @author Tom Baeyens
 */
public class DecisionBinding extends JpdlBinding {

  static ObjectBinding objectBinding = new ObjectBinding();
  static WireParser wireParser = WireParser.getInstance();

  public DecisionBinding() {
    super("decision");
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    if (element.hasAttribute("expr")) {
      DecisionExpressionActivity decisionExpressionActivity = new DecisionExpressionActivity();
      String expr = element.getAttribute("expr");
      decisionExpressionActivity.setExpr(expr);
      return decisionExpressionActivity;
    }

    if (element.hasAttribute("handler-ref")) {
      String decisionHandlerName = element.getAttribute("handler-ref");
      DecisionHandlerActivity decisionHandlerActivity = new DecisionHandlerActivity();
      decisionHandlerActivity.setDecisionHandlerName(decisionHandlerName);
      return decisionHandlerActivity;
    }

    Element handlerElement = XmlUtil.element(element, "handler");
    if (handlerElement!=null) {
      DecisionHandlerActivity decisionHandlerActivity = new DecisionHandlerActivity();
      ObjectDescriptor decisionHandlerDescriptor = (ObjectDescriptor) 
          objectBinding.parse(handlerElement, parse, wireParser);
      DecisionHandler decisionHandler = (DecisionHandler) WireContext.create(decisionHandlerDescriptor);
      decisionHandlerActivity.setDecisionHandler(decisionHandler);
      return decisionHandlerActivity;
    }
    
    boolean hasConditions = false;
    List<Element> transitionElements = XmlUtil.elements(element, "transition");
    ActivityImpl activity = parse.findObject(ActivityImpl.class);
    List<TransitionImpl> transitions = (List) activity.getOutgoingTransitions();
    
    for (int i=0; i<transitionElements.size(); i++) {
      TransitionImpl transition = transitions.get(i);
      Element transitionElement = transitionElements.get(i);

      Element conditionElement = XmlUtil.element(transitionElement, "condition");
      if (conditionElement!=null) {
        hasConditions = true;
        
        if (conditionElement.hasAttribute("expr")) {
          String expr = conditionElement.getAttribute("expr");
          String lang = XmlUtil.attribute(conditionElement, "expr-lang");
          ExpressionEvaluatorDescriptor expressionDescriptor = new ExpressionEvaluatorDescriptor(expr, lang);
          transition.setConditionDescriptor(expressionDescriptor);
          
        } else if (conditionElement.hasAttribute("ref")) {
          String expr = conditionElement.getAttribute("ref");
          ReferenceDescriptor refDescriptor = new ReferenceDescriptor(expr);
          transition.setConditionDescriptor(refDescriptor);
          
        } else if (ObjectBinding.isObjectDescriptor(conditionElement)) {
          ObjectDescriptor conditionDescriptor = (ObjectDescriptor) objectBinding.parse(conditionElement, parse, parser);
          transition.setConditionDescriptor(conditionDescriptor);
        }
      }
    }
    
    if (hasConditions) {
      return new DecisionConditionActivity();
    } else {
      parse.addProblem("decision '"+element.getAttribute("name")+"' must have one of: expr attribute, handler attribute, handler element or condition expressions", element);
    }
    
    return null;
  }
}
