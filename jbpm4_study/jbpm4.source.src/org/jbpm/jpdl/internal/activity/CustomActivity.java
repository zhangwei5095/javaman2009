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

import java.util.Map;

import org.jbpm.api.activity.ActivityBehaviour;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;


/**
 * @author Tom Baeyens
 */
public class CustomActivity extends JpdlExternalActivity {

  private static final long serialVersionUID = 1L;

  protected ActivityBehaviour customBehaviour;

  public void signal(ActivityExecution execution, String signalName, Map<String, ? > parameters) throws Exception {
    ExternalActivityBehaviour externalActivityBehaviour = (ExternalActivityBehaviour)customBehaviour;
    externalActivityBehaviour.signal(execution, signalName, parameters);
  }

  public void execute(ActivityExecution execution) throws Exception {
    customBehaviour.execute(execution);
  }
  
  public ActivityBehaviour getCustomBehaviour() {
    return customBehaviour;
  }
  public void setCustomBehaviour(ActivityBehaviour customBehaviour) {
    this.customBehaviour = customBehaviour;
  }
}
