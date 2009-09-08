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

import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.job.MessageImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;

/**
 * @author Tom Baeyens
 */
public class TransitionStartActivityMessage extends MessageImpl<Object> {

  private static final long serialVersionUID = 1L;

  public TransitionStartActivityMessage() {
  }

  public TransitionStartActivityMessage(ExecutionImpl execution) {
    super(execution);
  }

  public Object execute(Environment environment) throws Exception {
    AsyncContinuations.restoreState(execution);

    execution.performAtomicOperationSync(AtomicOperation.TRANSITION_START_ACTIVITY);
    
    DbSession dbSession = environment.get(DbSession.class);
    dbSession.delete(this);

    return null;
  }
  
  public String toString() {
    return "ProceedToDestinationMessage["+dbid+"]";
  }
}