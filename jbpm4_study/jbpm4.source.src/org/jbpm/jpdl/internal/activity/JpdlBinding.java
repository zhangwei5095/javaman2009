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

import org.jbpm.api.model.Event;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.jpdl.internal.xml.UnresolvedTransitions;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.TimerDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.util.TagBinding;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;


/**
 * @author Tom Baeyens
 */
public abstract class JpdlBinding extends TagBinding {
  
  protected static final WireParser wireParser = JpdlParser.wireParser;

  public JpdlBinding(String tagName) {
    super(tagName, JpdlParser.JPDL_NAMESPACE, null);
  }

  public void parseName(Element element, ActivityImpl activity, Parse parse) {
    String name = XmlUtil.attribute(element, "name", isNameRequired(), parse);
    
    if (name!=null) {
      // basic name validation
      if ("".equals(name)) {
        parse.addProblem(XmlUtil.errorMessageAttribute(element, "name", name, "is empty"), element);
      } else if (name.indexOf('/')!=-1) {
        parse.addProblem(XmlUtil.errorMessageAttribute(element, "name", name, "contains slash (/)"), element);
      }
      activity.setName(name);
    }
  }

  public boolean isNameRequired() {
    return true;
  }

  public void parseTransitions(Element element, ActivityImpl activity, Parse parse, JpdlParser jpdlParser) {
    List<Element> transitionElements = XmlUtil.elements(element, "transition");
    UnresolvedTransitions unresolvedTransitions = parse.findObject(UnresolvedTransitions.class);
    for (Element transitionElement: transitionElements) {
      String transitionName = XmlUtil.attribute(transitionElement, "name", false, parse);

      Element timerElement = XmlUtil.element(transitionElement, "timer");
      if (timerElement!=null) {
        if (transitionName!=null) {
          TimerDefinitionImpl timerDefinitionImpl = jpdlParser.parseTimerDefinition(timerElement, parse, activity);
          timerDefinitionImpl.setSignalName(transitionName);
        } else {
          parse.addProblem("a transition name is required when a timer is placed on a transition", element);
        }
      }

      TransitionImpl transition = activity.createOutgoingTransition();
      transition.setName(transitionName);

      unresolvedTransitions.add(transition, transitionElement);
      
      jpdlParser.parseOnEvent(transitionElement, parse, transition, Event.TAKE);
    }
  }
}
