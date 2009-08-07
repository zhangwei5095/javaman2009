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
package org.jbpm.pvm.internal.tx;

import org.hibernate.Session;
import org.jbpm.api.JbpmException;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.spring.CommandTransactionCallback;
import org.jbpm.pvm.internal.svc.Interceptor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * calls setRollbackOnly on the transaction in the environment in case an
 * exception occurs during execution of the command.
 * 
 * @author Andries Inze
 */
public class SpringTransactionInterceptor extends Interceptor {

  private static final Log log = Log.getLog(SpringTransactionInterceptor.class.getName());

  private boolean useCurrent;

  @SuppressWarnings("unchecked")
  public <T> T execute(Command<T> command) {
    Environment environment = Environment.getCurrent();
    if (environment == null) {
      throw new JbpmException("no environment for managing hibernate transaction");
    }

    StandardTransaction standardTransaction = environment.get(StandardTransaction.class);

    PlatformTransactionManager platformTransactionManager = environment.get(PlatformTransactionManager.class);
    if (platformTransactionManager == null) {
      throw new JbpmException("No platformTransaction manager defined.");
    }

    if (standardTransaction != null) {
      standardTransaction.begin();
    }

    try {
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();


      if (useCurrent) {
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_MANDATORY);
      } else {
    	  definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      }

      TransactionTemplate template = new TransactionTemplate(platformTransactionManager, definition);
      TransactionCallback transactionCallback = new CommandTransactionCallback<T>(command, next, platformTransactionManager);
      T t = (T) template.execute(transactionCallback);

      Session session = environment.get(Session.class);
      
      if (session.isOpen()) {
    	  session.flush();    	  
      }
      return t;
    } catch (RuntimeException e) {
      if (standardTransaction != null) {
        standardTransaction.setRollbackOnly();
      }
      throw e;

    } finally {
      if (standardTransaction != null) {
        standardTransaction.complete();
      }
    }
  }

  public void setUseCurrent(Boolean useCurrent) {
    this.useCurrent = useCurrent;
  }
}
