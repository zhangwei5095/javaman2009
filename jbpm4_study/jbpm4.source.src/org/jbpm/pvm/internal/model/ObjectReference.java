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
package org.jbpm.pvm.internal.model;

import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.wire.Descriptor;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;

public class ObjectReference<T> extends ProcessElementImpl {
  
  private static final long serialVersionUID = 1L;
  // private static final Log log = Log.getLog(ObjectReference.class.getName());

  protected String expression;
  protected String expressionLanguage;
  protected Descriptor descriptor;
  protected T object;

  public ObjectReference() {
  }

  public ObjectReference(String expression) {
    this.expression = expression;
  }

  public ObjectReference(Descriptor descriptor) {
    this.descriptor = descriptor;
  }

  public ObjectReference(T object) {
    this.object = object;
  }
  
  public String toString() {
    if (object!=null) {
      return object.toString();
    }
    if (expression!=null) {
      return expression;
    }
    if (descriptor!=null) {
      return descriptor.toString();
    }
    return null;
  }

  public T get() {
    if (object!=null) {
      return object;
    }
    if (expression!=null) {
      return resolveValueExpression();
    }
    if (descriptor!=null) {
      return constructFromDescriptor();
    }
    return null;
  }

  public void set(T object) {
    this.object = object;
  }
  
  // object builders //////////////////////////////////////////////////////////

  protected T constructFromDescriptor() {
    return (T) WireContext.create(descriptor);
  }

  protected T resolveValueExpression() {
    Environment environment = Environment.getCurrent();
    return null;
  }
  
  protected T resolveMethodExpression(Environment environment) {
    return null;
  }
  
  public void setDescriptor(Descriptor descriptor) {
    this.descriptor = descriptor;
  }
  public Descriptor getDescriptor() {
    return descriptor;
  }
  public void setExpression(String expression) {
    this.expression = expression;
  }
  public T getObject() {
    return object;
  }
  public void setObject(T object) {
    this.object = object;
  }
  public String getExpression() {
    return expression;
  }
}
