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
package org.jbpm.pvm.internal.cfg;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.env.Context;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.JbpmConfigurationParser;
import org.jbpm.pvm.internal.env.PvmEnvironment;
import org.jbpm.pvm.internal.env.WireObject;
import org.jbpm.pvm.internal.stream.FileStreamInput;
import org.jbpm.pvm.internal.stream.InputStreamInput;
import org.jbpm.pvm.internal.stream.ResourceStreamInput;
import org.jbpm.pvm.internal.stream.StreamInput;
import org.jbpm.pvm.internal.stream.StringStreamInput;
import org.jbpm.pvm.internal.stream.UrlStreamInput;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.descriptor.ProvidedObjectDescriptor;

/**
 * an environment factory that also is the process-engine context.
 * 
 * <p>
 * This environment factory will produce environments with 2 contexts: the
 * process-engine context and the block context.
 * </p>
 * 
 * <p>
 * An process-engine context is build from two wire definitions: the
 * process-engine wire definition and the environment wire definition.
 * </p>
 * 
 * <p>
 * The process-engine context itself is build from the process-engine
 * wire definition. So all objects that are created in this context remain
 * cached for the lifetime of this process-engine context object.
 * </p>
 * 
 * <p>
 * This process-engine context is also a environment factory. The produced
 * environments contain 2 contexts: the process-engine context itself and a
 * new environment context, build from the environment wire definition. For each
 * created environment, a new environment context will be created from the same
 * environment wire definition. Objects in the environment context will live for
 * as long as the environment.
 * </p>
 * 
 * @author Tom Baeyens
 */
public class JbpmConfiguration extends Configuration implements Context, ProcessEngine, EnvironmentFactory {

  private static final long serialVersionUID = 1L;
  private static final Log log = Log.getLog(JbpmConfiguration.class.getName());

  protected boolean isConfigured = false;
  protected WireContext processEngineWireContext = new WireContext(new WireDefinition(), Context.CONTEXTNAME_PROCESS_ENGINE);
  protected WireDefinition transactionWireDefinition = new WireDefinition();

  public JbpmConfiguration() {
    super((Configuration)null);
  }

  public ProcessEngine buildProcessEngine() {
    if (!isConfigured) {
      setResource("jbpm.cfg.xml");
    }
    if (log.isTraceEnabled()) {
      log.trace("created ProcessEngine "+System.identityHashCode(this));
      if ( (processEngineWireContext!=null)
           && (processEngineWireContext.getWireDefinition()!=null)
           && (processEngineWireContext.getWireDefinition().getDescriptorTypes()!=null)
         ) {
        log.trace("  process-engine-context "+System.identityHashCode(processEngineWireContext));
        for (Class<?> descriptorType: processEngineWireContext.getWireDefinition().getDescriptorTypes()) {
          log.trace("    "+descriptorType.getName());
        }
      }
      if ( (transactionWireDefinition!=null)
           && (transactionWireDefinition.getDescriptorTypes()!=null)
         ) {
        log.trace("  transaction-context:");
        for (Class<?> descriptorType: transactionWireDefinition.getDescriptorTypes()) {
          log.trace("    "+descriptorType.getName());
        }
      }
    }
    return this;
  }

  public void setSessionFactory(Object sessionFactory) {
    processEngineWireContext
        .getWireDefinition()
        .addDescriptor(new ProvidedObjectDescriptor(sessionFactory, true));
  }

  public Configuration setInputStream(InputStream inputStream) {
    parse(new InputStreamInput(inputStream));
    return this;
  }

  public Configuration setResource(String resource) {
    parse(new ResourceStreamInput(resource, getClassLoader()));
    return this;
  }

  public Configuration setUrl(URL url) {
    parse(new UrlStreamInput(url));
    return this;
  }

  public Configuration setFile(File file) {
    parse(new FileStreamInput(file));
    return this;
  }

  public Configuration setXmlString(String xmlString) {
    parse(new StringStreamInput(xmlString));
    return this;
  }


  public static EnvironmentFactory parseXmlString(String xmlString) {
    JbpmConfiguration jbpmConfiguration = new JbpmConfiguration();
    jbpmConfiguration.setXmlString(xmlString);
    return jbpmConfiguration;
  }

  protected void parse(StreamInput streamSource) {
    isConfigured = true;
    JbpmConfigurationParser.getInstance()
      .createParse()
      .pushObject(this)
      .setStreamSource(streamSource)
      .execute()
      .checkErrors("jbpm configuration " + streamSource);
  }
  
  public ExecutionService getExecutionService() {
    return processEngineWireContext.get(ExecutionService.class);
  }
  public HistoryService getHistoryService() {
    return processEngineWireContext.get(HistoryService.class);
  }
  public ManagementService getManagementService() {
    return processEngineWireContext.get(ManagementService.class);
  }
  public TaskService getTaskService() {
    return processEngineWireContext.get(TaskService.class);
  }
  public IdentityService getIdentityService() {
    return processEngineWireContext.get(IdentityService.class);
  }
  public RepositoryService getRepositoryService() {
    return processEngineWireContext.get(RepositoryService.class);
  }


  public Environment openEnvironment() {
    return openEnvironment(null);
  }
  
  public Environment openEnvironment(List<WireObject> txWireObjects) {
    PvmEnvironment environment = new PvmEnvironment(this);

    if (log.isTraceEnabled()) log.trace("opening " + environment);

    // set the classloader
    ClassLoader classLoader = processEngineWireContext.getClassLoader();
    if (classLoader != null) {
      environment.setClassLoader(classLoader);
    }

    // add the process-engine context
    environment.setContext(processEngineWireContext);

    // add the transaction context
    WireDefinition usedWireDefinition = transactionWireDefinition;
    if (txWireObjects!=null) {
      usedWireDefinition = new WireDefinition(transactionWireDefinition, txWireObjects);
    }
    
    WireContext transactionContext = new WireContext(usedWireDefinition, Context.CONTEXTNAME_TRANSACTION, environment, true);
    // add the environment block context to the environment
    environment.setContext(transactionContext);

    Environment.pushEnvironment(environment);
    try {
      // finish the creation of the environment wire context
      transactionContext.create();

    } catch (RuntimeException e) {
      Environment.popEnvironment();
      throw e;
    }

    // if all went well, return the created environment
    return environment;
  }

  public void close() {
    processEngineWireContext.fire(WireContext.EVENT_CLOSE, null);
  }

  // process-engine context delegation methods
  // ///////////////////////////////////

  public Object get(String key) {
    return processEngineWireContext.get(key);
  }

  public <T> T get(Class<T> type) {
    return processEngineWireContext.get(type);
  }

  public String getName() {
    return processEngineWireContext.getName();
  }

  public boolean has(String key) {
    return processEngineWireContext.has(key);
  }

  public Set<String> keys() {
    return processEngineWireContext.keys();
  }

  public Object set(String key, Object value) {
    return processEngineWireContext.set(key, value);
  }
  
  public void addProcessEngineWireDefinition(WireDefinition wireDefinition) {
    processEngineWireContext.getWireDefinition().addWireDefinition(wireDefinition);
  }

  public void addTransactionWireDefinition(WireDefinition wireDefinition) {
    transactionWireDefinition.addWireDefinition(wireDefinition);
  }

  // getters and setters //////////////////////////////////////////////////////

  public void setTransactionWireDefinition(WireDefinition transactionWireDefinition) {
    this.transactionWireDefinition = transactionWireDefinition;
  }
  public WireContext getProcessEngineWireContext() {
    return processEngineWireContext;
  }
  public void setProcessEngineWireContext(WireContext processEngineWireContext) {
    this.processEngineWireContext = processEngineWireContext;
  }
  public WireDefinition getTransactionWireDefinition() {
    return transactionWireDefinition;
  }
}
