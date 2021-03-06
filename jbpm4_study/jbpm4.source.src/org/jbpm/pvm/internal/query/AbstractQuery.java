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

import java.io.ObjectStreamException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.api.JbpmException;
import org.jbpm.pvm.internal.cmd.Command;
import org.jbpm.pvm.internal.cmd.CommandService;
import org.jbpm.pvm.internal.env.Environment;


/**
 * @author Tom Baeyens
 */
public abstract class AbstractQuery implements Command<Object> {

  private static final long serialVersionUID = 1L;
  
  protected CommandService commandService;
  protected String orderByClause = null;
  protected Page page = null;
  protected boolean isWhereAdded = false;
  
  protected abstract void applyParameters(Query query);

  public abstract String hql();

  /* reuse by copy and paste:
   * (return type can't be changed)
  public ConcreteQuery page(int firstResult, int maxResults) {
    this.page = new Page(firstResult, maxResults);
    return this;
  } 
  */

  public List untypedList() {
    if (commandService!=null) {
      return (List) commandService.execute(this);
    }
    Session session = Environment.getFromCurrent(Session.class);
    return (List) execute(session); 
  }

  protected Object untypedUniqueResult() {
    List list = untypedList();
    if (list.isEmpty()) {
      return null;
    }
    if (list.size()>1) {
      throw new JbpmException("result not unique: "+list.size());
    }
    return list.get(0);
  }

  public Object execute(Environment environment) throws Exception {
    Session session = environment.get(Session.class);
    return execute(session);
  }
  
  public Object execute(Session session) {
    String hql = hql();
    Query query = session.createQuery(hql);
    applyParameters(query);
    applyPage(query);
    return query.list();
  }

  protected void appendWhereClause(String whereClause, StringBuilder hql) {
    if (isWhereAdded) {
      hql.append("  and ");
    } else {
      isWhereAdded = true;
      hql.append("where ");
    }
    hql.append(whereClause);
  }

  protected void appendOrderByClause(StringBuilder hql) {
    if (orderByClause!=null) {
      hql.append("order by ");
      hql.append(orderByClause);
    }
  }

  protected void applyPage(Query query) {
    if (page!=null) {
      query.setFirstResult(page.firstResult);
      query.setMaxResults(page.maxResults);
    }
  }

  protected void addOrderByClause(String clause) {
    if (orderByClause==null) {
      orderByClause = clause;
    } else {
      orderByClause += ", " + clause;
    }
  }
  
  protected Object writeReplace() throws ObjectStreamException {
    this.commandService = null;
    return this;
  }

  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }
}
