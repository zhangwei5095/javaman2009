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
package org.jbpm.pvm.internal.type;

import java.io.Serializable;

import org.jbpm.api.JbpmException;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.HistorySession;
import org.jbpm.pvm.internal.history.events.VariableUpdate;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * is a jbpm-internal class that serves as a base class for classes 
 * that store variable values in the database.
 */
public abstract class Variable implements Serializable {

  private static final long serialVersionUID = 1L;
  
  protected long dbid = -1;
  protected int dbversion = 0;
  
  protected String key = null;
  protected Converter converter = null;
  protected String textValue = null;
  protected boolean isHistoryEnabled = false;
  
  protected ExecutionImpl execution;
  protected TaskImpl task;
  
  // constructors /////////////////////////////////////////////////////////////
  
  // abstract methods /////////////////////////////////////////////////////////

  /**
   * is true if this variable-instance supports the given value, false otherwise.
   */
  public abstract boolean isStorable(Object value);
  /**
   * is the value, stored by this variable instance.
   */
  protected abstract Object getObject();
  /**
   * stores the value in this variable instance.
   */
  protected abstract void setObject(Object value);

  // variable management //////////////////////////////////////////////////////

  public boolean supports(Object value) {
    if (converter!=null) {
      return converter.supports(value);
    }
    return isStorable(value);
  }

  public void setValue(Object value) {
    if (converter!=null) {
      if (! converter.supports(value)) {
        throw new JbpmException("the converter '"+converter.getClass().getName()+"' in variable instance '"+this.getClass().getName()+"' does not support values of type '"+value.getClass().getName()+"'.  to change the type of a variable, you have to delete it first");
      }
      value = converter.convert(value);
    }
    if ( (value!=null)
         && (! this.isStorable(value)) ) {
      throw new JbpmException("variable instance '"+this.getClass().getName()+"' does not support values of type '"+value.getClass().getName()+"'.  to change the type of a variable, you have to delete it first");
    }
    setObject(value);
    
    HistorySession historySession = Environment.getFromCurrent(HistorySession.class, false);
    if ( isHistoryEnabled 
         && (historySession!=null)
       ) {
      HistoryEvent.fire(new VariableUpdate(this));
    }
  }

  public Object getValue() {
    Object value = getObject();
    if ( (value!=null)
         && (converter!=null) ) {
      value = converter.revert(value);
    }
    return value;
  }
  
  // utility methods /////////////////////////////////////////////////////////

  public String toString() {
    return "${"+key+"}";
  }

  public Type getType() {
    Type type = new Type();
    type.setConverter(converter);
    type.setVariableClass(getClass());
    return type;
  }
  
  public ExecutionImpl getProcessInstance() {
    return (execution!=null ? execution.getProcessInstance() : null);
  }

  // getters and setters //////////////////////////////////////////////////////

  public String getKey() {
    return key;
  }
  public long getDbid() {
    return dbid;
  }
  public Converter getConverter() {
    return converter;
  }
  public void setConverter(Converter converter) {
    this.converter = converter;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getTextValue() {
    return textValue;
  }
  public ExecutionImpl getExecution() {
    return execution;
  }
  public void setExecution(ExecutionImpl execution) {
    this.execution = execution;
  }
  public TaskImpl getTask() {
    return task;
  }
  public void setTask(TaskImpl task) {
    this.task = task;
  }
  public boolean isHistoryEnabled() {
    return isHistoryEnabled;
  }
  public void setHistoryEnabled(boolean isHistoryEnabled) {
    this.isHistoryEnabled = isHistoryEnabled;
  }
}
