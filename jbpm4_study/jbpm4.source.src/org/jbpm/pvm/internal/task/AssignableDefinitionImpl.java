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
package org.jbpm.pvm.internal.task;

import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.pvm.internal.model.ProcessElementImpl;
import org.jbpm.pvm.internal.util.EqualsUtil;

/**
 * @author Tom Baeyens
 */
public class AssignableDefinitionImpl extends ProcessElementImpl {

  private static final long serialVersionUID = 1L;

  protected String name;
  protected String description;

  protected String assigneeExpression;
  protected String assigneeExpressionLanguage;
  protected String candidateUsersExpression;
  protected String candidateUsersExpressionLanguage;
  protected String candidateGroupsExpression;
  protected String candidateGroupsExpressionLanguage;
  protected AssignmentHandler assignmentHandler;

  // equals ///////////////////////////////////////////////////////////////////
  // hack to support comparing hibernate proxies against the real objects
  // since this always falls back to ==, we don't need to overwrite the hashcode
  public boolean equals(Object o) {
    return EqualsUtil.equals(this, o);
  }

  // getters and setters //////////////////////////////////////////////////////

  public String getAssigneeExpression() {
    return assigneeExpression;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public void setAssigneeExpression(String assigneeExpression) {
    this.assigneeExpression = assigneeExpression;
  }
  public AssignmentHandler getAssignmentHandler() {
    return assignmentHandler;
  }
  public void setAssignmentHandler(AssignmentHandler assignmentHandler) {
    this.assignmentHandler = assignmentHandler;
  }
  public String getAssigneeExpressionLanguage() {
    return assigneeExpressionLanguage;
  }
  public void setAssigneeExpressionLanguage(String assigneeExpressionLanguage) {
    this.assigneeExpressionLanguage = assigneeExpressionLanguage;
  }
  public String getCandidateUsersExpression() {
    return candidateUsersExpression;
  }
  public void setCandidateUsersExpression(String candidateUsersExpression) {
    this.candidateUsersExpression = candidateUsersExpression;
  }
  public String getCandidateUsersExpressionLanguage() {
    return candidateUsersExpressionLanguage;
  }
  public void setCandidateUsersExpressionLanguage(String candidateUsersExpressionLanguage) {
    this.candidateUsersExpressionLanguage = candidateUsersExpressionLanguage;
  }
  public String getCandidateGroupsExpression() {
    return candidateGroupsExpression;
  }
  public void setCandidateGroupsExpression(String candidateGroupsExpression) {
    this.candidateGroupsExpression = candidateGroupsExpression;
  }
  public String getCandidateGroupsExpressionLanguage() {
    return candidateGroupsExpressionLanguage;
  }
  public void setCandidateGroupsExpressionLanguage(String candidateGroupsExpressionLanguage) {
    this.candidateGroupsExpressionLanguage = candidateGroupsExpressionLanguage;
  }

}
