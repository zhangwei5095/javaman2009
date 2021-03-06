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

import java.util.Collection;

import javax.mail.Message;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.jbpm.pvm.internal.email.spi.MailProducer;
import org.jbpm.pvm.internal.email.spi.MailSession;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.TaskContext;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * @author Alejandro Guizar
 */
public class MailListener implements EventListener {

  protected transient MailProducer mailProducer;

  private static final long serialVersionUID = 1L;

  public void notify(EventListenerExecution execution) throws Exception {
    // find current task
    Environment environment = Environment.getCurrent();
    DbSession dbSession = environment.get(DbSession.class);
    TaskImpl task = dbSession.findTaskByExecution(execution);

    // make task available to mail templates through task context
    TaskContext taskContext = new TaskContext(task);
    environment.setContext(taskContext);
    try {
      Collection<Message> messages = mailProducer.produce(execution);
      environment.get(MailSession.class).send(messages);
    } finally {
      environment.removeContext(taskContext);
    }
  }

  public void setMailProducer(MailProducer mailProducer) {
    this.mailProducer = mailProducer;
  }

}
