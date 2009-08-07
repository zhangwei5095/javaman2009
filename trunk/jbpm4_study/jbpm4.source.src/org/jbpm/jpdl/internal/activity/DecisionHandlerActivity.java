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

import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.model.Activity;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.Transition;
import org.jbpm.pvm.internal.wire.Descriptor;
import org.jbpm.pvm.internal.wire.WireContext;

/**
 * @author Tom Baeyens
 */
public class DecisionHandlerActivity extends JpdlActivity {

  private static final long serialVersionUID = 1L;
  
  protected String decisionHandlerName;
  protected DecisionHandler decisionHandler;

  public void execute(ActivityExecution execution) {
    execute((ExecutionImpl) execution); 
  }
  
  public void execute(ExecutionImpl execution) {
    Activity activity = execution.getActivity();
    
    String transitionName = null;

    DecisionHandler usedDecisionHandler = null;
    if (decisionHandler!=null) {
      usedDecisionHandler = decisionHandler;
      
    } else if (decisionHandlerName!=null) {
      Environment environment = Environment.getCurrent();
      Object object = environment.get(decisionHandlerName);
      if (object==null) {
        throw new JbpmException("decision handler for "+activity+" is null");
      }
      if (! (object instanceof DecisionHandler)) {
        throw new JbpmException("handler for decision is not a "+DecisionHandler.class.getName()+": "+object.getClass().getName());
      }
      usedDecisionHandler = (DecisionHandler) object;
    } else {
      throw new JbpmException("no decision handler specified");
    }
    
    transitionName = usedDecisionHandler.decide(execution);

    Transition transition = activity.getOutgoingTransition(transitionName);
    if (transition==null) {
      throw new JbpmException("handler in decision '"+activity.getName()+"' returned unexisting outgoing transition name: "+transitionName);
    }
    
    execution.historyDecision(transitionName);

    execution.take(transition);
  }

  public void setDecisionHandlerName(String decisionHandlerName) {
    this.decisionHandlerName = decisionHandlerName;
  }
  public void setDecisionHandler(DecisionHandler decisionHandler) {
    this.decisionHandler = decisionHandler;
  }
}
