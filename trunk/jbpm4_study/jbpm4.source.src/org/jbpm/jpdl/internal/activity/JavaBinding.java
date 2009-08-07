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

import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.descriptor.ArgDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.wire.operation.InvokeOperation;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;


/**
 * @author Tom Baeyens
 */
public class JavaBinding extends JpdlBinding {

  public static final String TAG = "java";
  
  public JavaBinding() {
    super(TAG);
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    JavaActivity javaActivity = new JavaActivity();

    String methodName = XmlUtil.attribute(element, "method", true, parse, null);
    javaActivity.setMethodName(methodName);

    String variableName = XmlUtil.attribute(element, "var");
    javaActivity.setVariableName(variableName);

    List<Element> argElements = XmlUtil.elements(element, "arg");
    if (!argElements.isEmpty()) {
      List<ArgDescriptor> argDescriptors = wireParser.parseArgs(argElements, parse);
      InvokeOperation invokeOperation = new InvokeOperation();
      invokeOperation.setArgDescriptors(argDescriptors);
      javaActivity.setInvokeOperation(invokeOperation);
    }

    if (element.hasAttribute("class")) {
      ObjectDescriptor objectDescriptor = JpdlParser.parseObjectDescriptor(element, parse);
      Object target = WireContext.create(objectDescriptor);
      javaActivity.setTarget(target);

    } else if (element.hasAttribute("expr")) {
      String expression = element.getAttribute("expr");
      javaActivity.setTargetExpression(expression);
      javaActivity.setTargetLanguage(XmlUtil.attribute(element, "lang"));
    
    } else {
      // parse.addProblem("no target specified in "+TAG+": must specify attribute 'class' or 'expr'", element);
    }
     
    return javaActivity;
  }
}
