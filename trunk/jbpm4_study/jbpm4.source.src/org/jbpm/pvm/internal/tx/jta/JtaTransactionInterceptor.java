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
package org.jbpm.pvm.internal.tx.jta;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;

import org.jbpm.api.JbpmException;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.svc.Interceptor;


/**
 * @author Tom Baeyens
 */
public class JtaTransactionInterceptor extends Interceptor {
  
  private static Log log = Log.getLog(JtaTransactionInterceptor.class.getName());

  public <T> T execute(Command<T> command) {
    JtaTransaction jtaTransaction = Environment.getFromCurrent(JtaTransaction.class);
    
    UserTransaction userTransaction = jtaTransaction.lookupJeeUserTransaction();

    int status = JtaTransaction.getUserTransactionStatus(userTransaction);
    
    if (status == Status.STATUS_ACTIVE) {
      return executeInExistingTx(command);
    }
    
    if ( (status != Status.STATUS_NO_TRANSACTION) 
         && (status != Status.STATUS_COMMITTED) 
         && (status != Status.STATUS_ROLLEDBACK)
       ) {
      throw new JbpmException("invalid transaction state: "+JtaStatusHelper.toString(status));
    }

    return executeInNewTx(command, jtaTransaction, status);
  }

  protected <T> T executeInExistingTx(Command<T> command) {
    return next.execute(command);
  }

  protected <T> T executeInNewTx(Command<T> command, JtaTransaction jtaTransaction, int status) {
    Transaction suspendedTransaction = null;
    if ((status == Status.STATUS_COMMITTED) || (status == Status.STATUS_ROLLEDBACK)) {
      suspendedTransaction = jtaTransaction.suspend();
    }
    
    Object returnValue = null;
    
    try {
      jtaTransaction.begin();
      
      returnValue = next.execute(command);

      jtaTransaction.commit();

    } catch (RuntimeException e) {
      jtaTransaction.rollback();
      throw e;
      
    } finally {
      
      if (suspendedTransaction!=null) {
        jtaTransaction.resume(suspendedTransaction);
      }
    }
    
    return (T) returnValue;
  }
  
}
