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
import java.util.Map;

import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.pvm.internal.client.ClientProcessDefinition;
import org.jbpm.pvm.internal.env.Context;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.ExecutionContext;
import org.jbpm.pvm.internal.model.Activity;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.script.ScriptManager;
import org.jbpm.pvm.internal.session.RepositorySession;
import org.jbpm.pvm.internal.task.SwimlaneImpl;


/**
 * @author Tom Baeyens
 */
public class SubProcessActivity extends JpdlExternalActivity {

  private static final long serialVersionUID = 1L;
  
  protected String subProcessKey;
  protected String subProcessId;
  protected Map<String, String> swimlaneMappings;

  protected List<SubProcessInParameterImpl> inParameters;
  protected List<SubProcessOutParameterImpl> outParameters;
  
  protected String outcomeExpression;
  protected Map<Object, String> outcomeVariableMappings;

  public void execute(ActivityExecution execution) {
    ExecutionImpl executionImpl = (ExecutionImpl) execution;
    
    RepositorySession repositorySession = Environment.getFromCurrent(RepositorySession.class);
    
    ClientProcessDefinition processDefinition = null;
    
    if (subProcessId!=null) {
      processDefinition = repositorySession.findProcessDefinitionById(subProcessId);
    } else {
      processDefinition = repositorySession.findProcessDefinitionByKey(subProcessKey);
    }
    
    ExecutionImpl subProcessInstance = (ExecutionImpl) processDefinition.createProcessInstance(null, execution);
    
    for (String swimlaneName: swimlaneMappings.keySet()) {
      String subSwimlaneName = swimlaneMappings.get(swimlaneName);
      SwimlaneImpl subSwimlane = subProcessInstance.createSwimlane(subSwimlaneName);
      SwimlaneImpl swimlane = executionImpl.getSwimlane(swimlaneName);
      if (swimlane!=null) {
        subSwimlane.initialize(swimlane);
      }
    }
    
    for (SubProcessInParameterImpl inParameter: inParameters) {
      inParameter.produce(executionImpl, subProcessInstance);
    }

    executionImpl.historyActivityStart();
    
    subProcessInstance.start();
    execution.waitForSignal();
  }

  public void signal(ActivityExecution execution, String signalName, Map<String, ?> parameters) throws Exception {
    signal((ExecutionImpl)execution, signalName, parameters);
  }

  public void signal(ExecutionImpl execution, String signalName, Map<String, ?> parameters) throws Exception {
    ExecutionImpl executionImpl = (ExecutionImpl) execution;

    ExecutionImpl subProcessInstance = executionImpl.getSubProcessInstance();

    String transitionName = null;

    ExecutionContext originalExecutionContext = null;
    ExecutionContext subProcessExecutionContext = null;
    Environment environment = Environment.getCurrent();
    if (environment!=null) {
      originalExecutionContext = (ExecutionContext) environment.removeContext(Context.CONTEXTNAME_EXECUTION);
      subProcessExecutionContext = new ExecutionContext((ExecutionImpl) subProcessInstance);
      environment.setContext(subProcessExecutionContext);
    }

    try {
      subProcessInstance.setSuperProcessExecution(null);
      executionImpl.setSubProcessInstance(null);
      

      for (SubProcessOutParameterImpl outParameter: outParameters) {
        outParameter.consume(executionImpl, subProcessInstance);
      }
      
      Activity activity = execution.getActivity();
      String subProcessActivityName = subProcessInstance.getActivityName();
      
      if (outcomeExpression!=null) {
        ScriptManager scriptManager = Environment.getFromCurrent(ScriptManager.class);
        Object value = scriptManager.evaluateExpression(outcomeExpression, null);
        // if the value is a String and matches the name of an outgoing transition
        if ( (value instanceof String)
             && (activity.hasOutgoingTransition(((String) value)))
           ) {
          // then take that one
          transitionName = (String) value; 
        } else {
          // else see if there is a value mapping
          transitionName = outcomeVariableMappings.get(value);
        }

      } else if (activity.hasOutgoingTransition(subProcessActivityName)) {
        transitionName = subProcessActivityName;
      }

    } finally {
      if (subProcessExecutionContext!=null) {
        environment.removeContext(subProcessExecutionContext);
      }
      if (originalExecutionContext!=null) {
        environment.setContext(originalExecutionContext);
      }
    }
    
    executionImpl.historyActivityEnd();

    if (transitionName!=null) {
      execution.take(transitionName);
    } else {
      execution.takeDefaultTransition();
    }
  }

  public void setSwimlaneMappings(Map<String, String> swimlaneMappings) {
    this.swimlaneMappings = swimlaneMappings;
  }
  public void setOutcomeVariableMappings(Map<Object, String> outcomeVariableMappings) {
    this.outcomeVariableMappings = outcomeVariableMappings;
  }
  public void setSubProcessKey(String subProcessKey) {
    this.subProcessKey = subProcessKey;
  }
  public void setSubProcessId(String subProcessId) {
    this.subProcessId = subProcessId;
  }
  public void setOutcomeExpression(String outcomeExpression) {
    this.outcomeExpression = outcomeExpression;
  }
  public List<SubProcessInParameterImpl> getInParameters() {
    return inParameters;
  }
  public void setInParameters(List<SubProcessInParameterImpl> inParameters) {
    this.inParameters = inParameters;
  }
  public List<SubProcessOutParameterImpl> getOutParameters() {
    return outParameters;
  }
  public void setOutParameters(List<SubProcessOutParameterImpl> outParameters) {
    this.outParameters = outParameters;
  }
}
