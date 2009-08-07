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

import java.io.IOException;
import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.jbpm.pvm.internal.env.Context;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.PvmEnvironment;
import org.jbpm.pvm.internal.env.SpringContext;
import org.jbpm.pvm.internal.env.WireObject;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * this environment factory will see only the singleton beans.
 * 
 * The created {@link SpringEnvironment}s will see the prototype beans and it
 * will cache them.
 * 
 * @author Andries Inze
 */
public class SpringConfiguration extends JbpmConfiguration implements
        EnvironmentFactory, ProcessEngine, ApplicationContextAware {

    private static final long serialVersionUID = 1L;

    private ApplicationContext applicationContext;

    private String jbpmConfigurationLocation;

    /**
     * Instantiates a new spring configuration.
     */
    public SpringConfiguration(String jbpmConfigurationLocation) {
        try {
            super.setInputStream(new ClassPathResource(
                    jbpmConfigurationLocation).getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SpringConfiguration() {
        // By jbpmTestCase
    }

    @Override
    public ProcessEngine buildProcessEngine() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(System
                    .getProperty("jbpm.test.cfg.applicationContext"));
        }

        return super.buildProcessEngine();
    }

    /**
     * {@inheritDoc)

     */
    @Override
    public Environment openEnvironment(List<WireObject> txWireObjects) {

        PvmEnvironment environment = new PvmEnvironment(this);

        // FIXME: All beneath should be a super call

        // set the classloader
        ClassLoader classLoader = processEngineWireContext.getClassLoader();
        if (classLoader != null) {
            environment.setClassLoader(classLoader);
        }

        // add the process-engine context
        environment.setContext(new SpringContext(applicationContext));
        environment.setContext(processEngineWireContext);

        // add the transaction context
        WireDefinition usedWireDefinition = transactionWireDefinition;
        if (txWireObjects != null) {
            usedWireDefinition = new WireDefinition(transactionWireDefinition,
                    txWireObjects);
        }

        WireContext transactionContext = new WireContext(usedWireDefinition,
                Context.CONTEXTNAME_TRANSACTION, environment, true);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> type) {
        String[] names = applicationContext.getBeanNamesForType(type);
        if (names.length == 1) {
            return (T) applicationContext.getBean(names[0]);
        }

        return super.get(type);
    }

    @Override
    public Object get(String key) {
        if (applicationContext.containsBean(key)) {
            return applicationContext.getBean(key);
        }

        return super.get(key);
    }

    /**
     * {@inheritDoc)

     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Gets the application context.
     * 
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
