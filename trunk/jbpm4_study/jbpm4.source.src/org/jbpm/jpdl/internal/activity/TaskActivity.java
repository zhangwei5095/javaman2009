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

import java.util.List;
import java.util.Map;

import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.task.Task;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.model.Activity;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.Transition;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.SwimlaneDefinitionImpl;
import org.jbpm.pvm.internal.task.SwimlaneImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;


/**
 * @author Tom Baeyens
 */
public class TaskActivity extends JpdlExternalActivity {

  private static final long serialVersionUID = 1L;
  
  private static final Log log = Log.getLog(TaskActivity.class.getName());

  protected TaskDefinitionImpl taskDefinition;
  
  public void execute(ActivityExecution execution) {
    execute((ExecutionImpl)execution);
  }

  public void execute(ExecutionImpl execution) {
    DbSession dbSession = Environment.getFromCurrent(DbSession.class);
    TaskImpl task = (TaskImpl) dbSession.createTask();
    task.setTaskDefinition(taskDefinition);
    task.setExecution(execution);
    task.setProcessInstance(execution.getProcessInstance());
    task.setSignalling(true);
    
    // initialize the name
    if (taskDefinition.getName()!=null) {
      task.setName(taskDefinition.getName());
    } else {
      task.setName(execution.getActivityName());
    }

    task.setDescription(taskDefinition.getDescription());
    task.setPriority(taskDefinition.getPriority());
    task.setFormResourceName(taskDefinition.getFormResourceName());
    
    // save task so that TaskDbSession.findTaskByExecution works for assign event listeners
    dbSession.save(task);

    SwimlaneDefinitionImpl swimlaneDefinition = taskDefinition.getSwimlaneDefinition();
    if (swimlaneDefinition!=null) {
      SwimlaneImpl swimlane = execution.getInitializedSwimlane(swimlaneDefinition);
      task.setSwimlane(swimlane);
      
      // copy the swimlane assignments to the task
      task.setAssignee(swimlane.getAssignee());
      for (ParticipationImpl participant: swimlane.getParticipations()) {
        task.addParticipation(participant.getUserId(), participant.getGroupId(), participant.getType());
      }
    }

    execution.initializeAssignments(taskDefinition, task);
    
    HistoryEvent.fire(new TaskActivityStart(task), execution);

    execution.waitForSignal();
  }
  
  public void signal(ActivityExecution execution, String signalName, Map<String, ?> parameters) throws Exception {
    signal((ExecutionImpl)execution, signalName, parameters);
  }

  public void signal(ExecutionImpl execution, String signalName, Map<String, ?> parameters) throws Exception {
    Activity activity = execution.getActivity();
    
    if (parameters!=null) {
      execution.setVariables(parameters);
    }
    
    execution.fire(signalName, activity);

    DbSession taskDbSession = Environment
        .getFromCurrent(DbSession.class);
    TaskImpl task = (TaskImpl) taskDbSession.findTaskByExecution(execution);
    task.setSignalling(false);
    
    Transition transition = null;
    List<Transition> outgoingTransitions = activity.getOutgoingTransitions();
    if ( (outgoingTransitions!=null)
         && (!outgoingTransitions.isEmpty())
       ) {
      transition = activity.findOutgoingTransition(signalName);
      if (transition==null) {
        if (Task.STATE_COMPLETED.equals(signalName)) {
          if (outgoingTransitions.size()==1) {
            transition = outgoingTransitions.get(0);
          } else {
            transition = activity.getDefaultOutgoingTransition();
          }
        } else {
          // if a user specified outcome was provided and it doesn't
          // match with an outgoing transition name, then the process 
          // instance is suspended.  parked for admin intervention.
          log.info("No outcome named '" + signalName + "' was found." 
                  + "The process instance is now suspended.");
          ((ExecutionImpl)execution.getProcessInstance()).suspend();
        }
      }
      if (transition!=null) {
        execution.take(transition);
      }
    }
  }

  public TaskDefinitionImpl getTaskDefinition() {
    return taskDefinition;
  }
  public void setTaskDefinition(TaskDefinitionImpl taskDefinition) {
    this.taskDefinition = taskDefinition;
  }
}
