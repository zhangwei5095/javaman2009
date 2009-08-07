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
package org.jbpm.pvm.internal.wire.binding;

import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.Descriptor;
import org.jbpm.pvm.internal.wire.operation.FieldOperation;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

/** parses a field injection operation.
 * 
 * See schema docs for more details.
 *
 * @author Tom Baeyens
 * @author Guillaume Porcher (documentation)
 */
public class FieldBinding extends WireOperationBinding {

  public FieldBinding() {
    super("field");
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    FieldOperation fieldOperation = new FieldOperation();
    if (element.hasAttribute("name")) {
      fieldOperation.setFieldName(element.getAttribute("name"));
    } else {
      parse.addProblem("field must have name : "+XmlUtil.toString(element), element);
    }
    Element descriptorElement = XmlUtil.element(element);
    if (descriptorElement!=null) {
      Descriptor descriptor = (Descriptor) parser.parseElement(descriptorElement, parse, WireParser.CATEGORY_DESCRIPTOR);
      if (descriptor!=null) {
        fieldOperation.setDescriptor(descriptor);
      } else {
        parse.addProblem("unknown descriptor element "+descriptorElement.getTagName()+" inside field operation: "+XmlUtil.toString(element), element);
      }
    } else {
      parse.addProblem("field must have 1 descriptor element out of "+parser.getBindings().getTagNames(WireParser.CATEGORY_DESCRIPTOR)+" as content: "+XmlUtil.toString(element), element);
    }
    return fieldOperation;
  }

}