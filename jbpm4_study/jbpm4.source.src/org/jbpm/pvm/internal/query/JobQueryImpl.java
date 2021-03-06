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
package org.jbpm.pvm.internal.query;

import java.util.List;

import org.hibernate.Query;
import org.jbpm.api.JobQuery;
import org.jbpm.api.job.Job;
import org.jbpm.pvm.internal.job.JobImpl;
import org.jbpm.pvm.internal.job.MessageImpl;
import org.jbpm.pvm.internal.job.TimerImpl;


/**
 * @author Tom Baeyens
 */
public class JobQueryImpl extends AbstractQuery implements JobQuery {
  
  private static final long serialVersionUID = 1L;

  protected boolean messagesOnly = false; 
  protected boolean timersOnly = false;
  protected String processInstanceId = null;
  protected Boolean exception;

  public String hql() {
  	StringBuilder hql = new StringBuilder();
    hql.append("select j ");
    hql.append("from ");
    if (messagesOnly) {
      hql.append(MessageImpl.class.getName());
    } else if (timersOnly) {
      hql.append(TimerImpl.class.getName());
    } else {
      hql.append(JobImpl.class.getName());
    }
    hql.append(" j ");

    if (processInstanceId!=null) {
      appendWhereClause("j.processInstance.id = '"+processInstanceId+"' ", hql);
    }
    
    if (exception!=null) {
      if (exception) {
        appendWhereClause("j.exception is not null ", hql);
      } else {
        appendWhereClause("j.exception is null ", hql);
      }
    }

    return hql.toString();
  }

  protected void applyParameters(Query query) {
  }

  public List<Job> list() {
    return (List<Job>) untypedList();
  }
  
  public Job uniqueResult() {
    return (Job)untypedUniqueResult();
  }

  public JobQuery messages() {
    this.messagesOnly = true;
    return this;
  }

  public JobQuery timers() {
    this.timersOnly = true;
    return this;
  }

  public JobQuery exception(boolean hasException) {
    this.exception = hasException;
    return this;
  }

  public JobQuery orderAsc(String property) {
    addOrderByClause("j."+property+" asc");
    return this;
  }

  public JobQuery orderDesc(String property) {
    addOrderByClause("j."+property+" desc");
    return this;
  }

  public JobQuery page(int firstResult, int maxResults) {
    this.page = new Page(firstResult, maxResults);
    return this;
  }

  public JobQuery processInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
    return this;
  }
}
