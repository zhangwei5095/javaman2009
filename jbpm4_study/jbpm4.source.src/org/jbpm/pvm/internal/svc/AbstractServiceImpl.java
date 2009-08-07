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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.cmd.CommandService;
import org.jbpm.pvm.internal.env.ProvidedAuthentication;
import org.jbpm.pvm.internal.env.WireObject;


/**
 * @author Tom Baeyens
 */
public class AbstractServiceImpl {
  
  protected CommandService commandService;
  protected ThreadLocal<List<WireObject>> contextThreadLocal;

  public CommandService getCommandService() {
    return commandService;
  }

  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }

  public void setUserId(String userId) {
    addTxWireObject(new WireObject(new ProvidedAuthentication(userId)));
  }

  public void setConnection(Connection connection) {
    addTxWireObject(new WireObject(connection));
  }

  protected synchronized void addTxWireObject(WireObject wireObject) {
    if (contextThreadLocal==null) {
      contextThreadLocal = new ThreadLocal<List<WireObject>>();
    }
    List<WireObject> txWireObjects = contextThreadLocal.get();
    if (txWireObjects==null) {
      txWireObjects = new ArrayList<WireObject>();
      contextThreadLocal.set(txWireObjects);
    }
    txWireObjects.add(wireObject);
  }
  
  protected void addTxWireObjects(AbstractCommand cmd) {
    if (contextThreadLocal!=null) {
      cmd.setTxWireObjects(contextThreadLocal.get());
      contextThreadLocal.set(null);
    }
  }
}
