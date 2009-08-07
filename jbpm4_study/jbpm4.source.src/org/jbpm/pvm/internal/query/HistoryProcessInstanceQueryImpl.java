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
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryProcessInstanceQuery;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;

/**
 * @author Tom Baeyens
 */
public class HistoryProcessInstanceQueryImpl extends AbstractQuery implements HistoryProcessInstanceQuery {

  private static final long serialVersionUID = 1L;
  
  protected String processDefinitionId;
  protected String state;
  protected String processInstanceId;

  public String hql() {
  	StringBuilder hql = new StringBuilder();
    hql.append("select hpi ");
    hql.append("from ");
    hql.append(HistoryProcessInstanceImpl.class.getName());
    hql.append(" as hpi ");
    
    if (processInstanceId!=null) {
      appendWhereClause(" hpi.processInstanceId = '"+processInstanceId+"' ", hql);
    }
    
    if (processDefinitionId!=null) {
      appendWhereClause(" hpi.processDefinitionId = '"+processDefinitionId+"' ", hql);
    }
    
    if (state!=null) {
      appendWhereClause(" hpi.state = '"+state+"' ", hql);
    }
    
    return hql.toString();
  }

  protected void applyParameters(Query query) {
  }

  public List<HistoryProcessInstance> list() {
    return (List) untypedList();
  }
  
  public HistoryProcessInstance uniqueResult() {
    return (HistoryProcessInstance)untypedUniqueResult();
  }

  public HistoryProcessInstanceQuery processInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
    return this;
  }

  public HistoryProcessInstanceQuery orderAsc(String property) {
    addOrderByClause("hpi."+property+" asc");
    return this;
  }

  public HistoryProcessInstanceQuery orderDesc(String property) {
    addOrderByClause("hpi."+property+" asc");
    return this;
  }

  public HistoryProcessInstanceQuery page(int firstResult, int maxResults) {
    this.page = new Page(firstResult, maxResults);
    return this;
  }

  public HistoryProcessInstanceQuery processDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
    return this;
  }

  public HistoryProcessInstanceQuery state(String state) {
    this.state = state;
    return this;
  }
}
