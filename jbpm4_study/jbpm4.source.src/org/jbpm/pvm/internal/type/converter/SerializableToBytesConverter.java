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
package org.jbpm.pvm.internal.type.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jbpm.api.JbpmException;
import org.jbpm.pvm.internal.type.Converter;

public class SerializableToBytesConverter implements Converter {

  private static final long serialVersionUID = 1L;
  
  public boolean supports(Object value) {
    if (value==null) return true;
    return Serializable.class.isAssignableFrom(value.getClass());
  }

  public Object convert(Object o) {
    byte[] bytes = null;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(o);
      oos.flush();
      bytes = baos.toByteArray();
    } catch (IOException e) {
      throw new JbpmException("couldn't serialize '"+o+"'", e);
    }
    
    return bytes;
  }

  public Object revert(Object o) {
    byte[] bytes = (byte[]) o;
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      return ois.readObject();
    } catch (Exception e) {
      throw new JbpmException("couldn't deserialize object", e);
    }
  }
}
