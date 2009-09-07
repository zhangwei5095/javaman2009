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
package org.jbpm.pvm.internal.cmd;

import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ActivityBehaviour;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.session.RepositorySession;
import org.jbpm.pvm.internal.task.FormBehaviour;


/**
 * @author Tom Baeyens
 */
public class GetStartFormResourceNameCmd implements Command<String> {

  private static final long serialVersionUID = 1L;
  
  String processDefinitionId;
  String activityName;
  
  public GetStartFormResourceNameCmd(String processDefinitionId, String activityName) {
    if (processDefinitionId==null) {
      throw new JbpmException("processDefinitionId is null");
    }
    this.processDefinitionId = processDefinitionId;
    this.activityName = activityName;
  }

  public String execute(Environment environment) {
    RepositorySession repositorySession = Environment.getFromCurrent(RepositorySession.class);
    ProcessDefinitionImpl processDefinition = repositorySession.findProcessDefinitionById(processDefinitionId);
    
    ActivityImpl activity = processDefinition.getActivity(activityName);
    
    ActivityBehaviour behaviour = activity.getBehaviour();
    if (behaviour instanceof FormBehaviour) {
      return ((FormBehaviour)behaviour).getFormResourceName();
    }

    return null;
  }
}