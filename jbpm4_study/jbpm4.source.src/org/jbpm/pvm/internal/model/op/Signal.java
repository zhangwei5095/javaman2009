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
package org.jbpm.pvm.internal.model.op;

import java.util.Map;

import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.job.MessageImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl.Propagation;

/**
 * @author Tom Baeyens
 */
public class Signal extends AtomicOperation {

  private static final long serialVersionUID = 1L;

  private static final Log log = Log.getLog(Signal.class.getName());

  String signalName;
  Map<String, ?> parameters;

  public Signal(String signalName, Map<String, ?> parameters) {
    this.signalName = signalName;
    this.parameters = parameters;
  }

  public boolean isAsync(ExecutionImpl execution) {
    return false;
  }

  public void perform(ExecutionImpl execution) {
    ActivityImpl activity = execution.getActivity();
    
    if (execution.getName()!=null) {
      log.debug(execution.toString()+" signals "+activity);
    } else {
      log.debug("signalling "+activity+", signalName="+signalName);
    }

    ExternalActivityBehaviour externalActivityBehaviour = (ExternalActivityBehaviour) activity.getBehaviour();
    
    try {
      execution.setPropagation(Propagation.UNSPECIFIED);
      externalActivityBehaviour.signal(execution, signalName, parameters);

    } catch (RuntimeException e) {
      throw e;
      
    } catch (Exception e) {
      throw new JbpmException("couldn't signal "+activity+": "+e.getMessage(), e);
    }

    if (execution.getPropagation() == Propagation.UNSPECIFIED) {
      execution.proceed();
    }
  }
  
  public String toString() {
    return "Signal";
  }

  public MessageImpl<?> createAsyncMessage(ExecutionImpl execution) {
    return null;
  }
}
