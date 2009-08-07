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

import org.jbpm.api.activity.ActivityBehaviour;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.binding.ObjectBinding;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;


/**
 * @author Tom Baeyens
 */
public class CustomBinding extends JpdlBinding {

  static ObjectBinding objectBinding = new ObjectBinding();

  public CustomBinding() {
    super("custom");
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    CustomActivity customActivity = new CustomActivity();
    ObjectDescriptor descriptor = (ObjectDescriptor) 
        objectBinding.parse(element, parse, wireParser);
    ActivityBehaviour customActivityBehaviour = (ActivityBehaviour) WireContext.create(descriptor);
    customActivity.setCustomBehaviour(customActivityBehaviour);
    return customActivity;
  }
}
