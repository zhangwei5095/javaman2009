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
package org.jbpm.pvm.internal.svc;

import java.util.List;

import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.WireObject;


/** sets up an environment around the execution of the command.
 * 
 * @author Tom Baeyens
 */
public class EnvironmentInterceptor extends Interceptor {

  protected EnvironmentFactory environmentFactory;

  public <T> T execute(Command<T> command) {
    Environment environment;
    
    if (command instanceof AbstractCommand) {
      AbstractCommand abstractCommand = (AbstractCommand) command;
      List<WireObject> txWireObjects = abstractCommand.getTxWireObjects();
      environment = environmentFactory.openEnvironment(txWireObjects);

    } else {
      environment = environmentFactory.openEnvironment();
    }
    
    try {
      return next.execute(command);
      
    } finally {
      environment.close();
    }
  }

  public EnvironmentFactory getEnvironmentFactory() {
    return environmentFactory;
  }
  public void setEnvironmentFactory(EnvironmentFactory environmentFactory) {
    this.environmentFactory = environmentFactory;
  }
}
