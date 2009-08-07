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
package org.jbpm.jpdl.internal.repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.internal.log.Log;
import org.jbpm.jpdl.internal.model.JpdlProcessDefinition;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.repository.Deployer;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.session.RepositorySession;
import org.jbpm.pvm.internal.xml.Parse;


/**
 * @author Tom Baeyens
 */
public class JpdlDeployer implements Deployer {
  
  private static Log log = Log.getLog(JpdlDeployer.class.getName());
  
  static JpdlParser jpdlParser = new JpdlParser();

  public void deploy(DeploymentImpl deployment) {
    
    for (String resourceName: deployment.getResourceNames()) {
      
      if (resourceName.endsWith(".jpdl.xml")) {
        byte[] bytes = deployment.getBytes(resourceName);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Parse parse = jpdlParser.createParse();
        parse.setProblems(deployment.getProblems());
        parse.setInputStream(inputStream);
        parse.execute();
        JpdlProcessDefinition processDefinition = (JpdlProcessDefinition) parse.getDocumentObject();
        if ((processDefinition != null) && (processDefinition.getName() != null)) {
          String processDefinitionName = processDefinition.getName();
          
          processDefinition.setSuspended(deployment.isSuspended());
          
          String imageResourceName = resourceName.substring(0, resourceName.lastIndexOf(".jpdl.xml"))+".png";
          if (deployment.getResourceNames().contains(imageResourceName)) {
            processDefinition.setImageResourceName(imageResourceName);
          }

          processDefinition.setDeploymentDbid(deployment.getDbid());

          if (deployment.hasObjectProperties(processDefinitionName)) {
            String key = deployment.getProcessDefinitionKey(processDefinitionName);
            String id = deployment.getProcessDefinitionId(processDefinitionName);
            Long version = deployment.getProcessDefinitionVersion(processDefinitionName);
            processDefinition.setId(id);
            processDefinition.setKey(key);
            processDefinition.setVersion(version.intValue());

          } else {
            checkKey(processDefinition, deployment);
            checkVersion(processDefinition, deployment);
            checkId(processDefinition, deployment);

            deployment.setProcessDefinitionId(processDefinitionName, processDefinition.getId());
            deployment.setProcessDefinitionKey(processDefinitionName, processDefinition.getKey());
            deployment.setProcessDefinitionVersion(processDefinitionName, new Long(processDefinition.getVersion()));
          }

          deployment.addObject(processDefinitionName, processDefinition);
        }
      }
    }
  }
  
  protected void checkKey(ProcessDefinitionImpl processDefinition, DeploymentImpl deployment) {
    String processDefinitionName = processDefinition.getName();
    String processDefinitionKey = processDefinition.getKey();

    // if no key was specified in the jpdl process file
    if (processDefinitionKey==null) {
      // derive the key from the name
      // replace any non-word character with an underscore
      processDefinitionKey = processDefinitionName.replaceAll("\\W", "_");
      processDefinition.setKey(processDefinitionKey);
    }
    
    RepositorySession repositorySession = Environment.getFromCurrent(RepositorySession.class);

    List<ProcessDefinition> existingProcesses = repositorySession.createProcessDefinitionQuery()
        .processDefinitionName(processDefinitionName)
        .list();
    
    for (ProcessDefinition existingProcess: existingProcesses) {
      if (!processDefinitionKey.equals(existingProcess.getKey())) {
        deployment.addProblem("invalid key '"+processDefinitionKey+"' in process "+processDefinition.getName()+".  Existing process has name '"+processDefinitionName+"' and key '"+processDefinitionKey+"'");
      }
    }

    existingProcesses = repositorySession.createProcessDefinitionQuery()
        .processDefinitionKey(processDefinitionKey)
        .list();
    
    for (ProcessDefinition existingProcess: existingProcesses) {
      if (!processDefinitionName.equals(existingProcess.getName())) {
        deployment.addProblem("invalid name '"+processDefinitionName+"' in process "+processDefinition.getName()+".  Existing process has name '"+processDefinitionName+"' and key '"+processDefinitionKey+"'");
      }
    }
  }

  protected void checkId(ProcessDefinitionImpl processDefinition, DeploymentImpl deployment) {
    String id = processDefinition.getId();
    if (id==null) {
      id = processDefinition.getKey()+"-"+processDefinition.getVersion();
      if (log.isTraceEnabled()) log.trace("created id '"+id+"' for "+processDefinition);
      processDefinition.setId(id);
    }
    
    RepositorySession repositorySession = Environment.getFromCurrent(RepositorySession.class);
    ProcessDefinition existingProcessDefinition = repositorySession.createProcessDefinitionQuery()
        .processDefinitionId(id)
        .uniqueResult();
    if (existingProcessDefinition != null) {
      deployment.addProblem("process '" + id + "' already exists");
    }
  }
  
  protected void checkVersion(ProcessDefinitionImpl processDefinition, DeploymentImpl deployment) {
    int version = processDefinition.getVersion();
    String key = processDefinition.getKey();
    if (version==ProcessDefinitionImpl.UNASSIGNED_VERSION) {
      RepositorySession repositorySession = Environment.getFromCurrent(RepositorySession.class);
      
      ProcessDefinition latestDeployedVersion = repositorySession
          .createProcessDefinitionQuery()
          .processDefinitionKey(key)
          .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION)
          .page(0, 1)
          .uniqueResult();

      if (latestDeployedVersion!=null) {
        version = latestDeployedVersion.getVersion() + 1;
      } else {
        version = 1;
      }
      if (log.isTraceEnabled()) log.trace("assigning version "+version+" to process definition "+key);
      processDefinition.setVersion(version);
    }
  }
}
