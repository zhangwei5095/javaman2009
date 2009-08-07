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
package org.jbpm.test.enterprise;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServerConnection;

import junit.extensions.TestSetup;
import junit.framework.TestSuite;

import org.jbpm.internal.log.Log;

/**
 * A test setup that deploys/undeploys archives
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 14-Oct-2004
 */
public class IntegrationTestSetup extends TestSetup {
  private IntegrationTestHelper delegate = new IntegrationTestHelper();
  private String[] archives;
  private ClassLoader originalClassLoader;

  private static final Log log = Log.getLog(IntegrationTestSetup.class
      .getName());

  public IntegrationTestSetup(Class<?> testClass, String... archiveList) {
    super(new TestSuite(testClass));
    archives = archiveList != null ? archiveList : new String[0];
  }

  public File getArchiveFile(String archive) {
    return delegate.getTestArchiveFile(archive);
  }

  public URL getArchiveURL(String archive) throws MalformedURLException {
    return delegate.getTestArchiveFile(archive).toURI().toURL();
  }

  public boolean isTargetJBoss500() {
    return delegate.isTargetJBoss500();
  }

  public boolean isTargetJBoss423() {
    return delegate.isTargetJBoss423();
  }

  public boolean isTargetJBoss422() {
    return delegate.isTargetJBoss422();
  }

  public MBeanServerConnection getServer() {
    return delegate.getServer();
  }

  @Override
  protected void setUp() throws Exception {
    log.debug("### START SETUP " + getTest());

    List<URL> clientJars = new ArrayList<URL>();
    for (int i = 0; i < archives.length; i++) {
      String archive = archives[i];
      try {
        delegate.deploy(archive);
      } catch (Exception ex) {
        ex.printStackTrace();
        delegate.undeploy(archive);
      }

      if (archive.endsWith("-client.jar")) {
        URL archiveURL = getArchiveURL(archive);
        clientJars.add(archiveURL);
      }
    }

    ClassLoader parent = Thread.currentThread().getContextClassLoader();
    originalClassLoader = parent;

    // add client jars to the class loader
    if (!clientJars.isEmpty()) {
      URL[] urls = new URL[clientJars.size()];
      for (int i = 0; i < clientJars.size(); i++) {
        urls[i] = clientJars.get(i);
      }
      URLClassLoader cl = new URLClassLoader(urls, parent);
      Thread.currentThread().setContextClassLoader(cl);
    }
  }

  @Override
  protected void tearDown() throws Exception {
    try {
      for (int i = 0; i < archives.length; i++) {
        String archive = archives[archives.length - i - 1];
        delegate.undeploy(archive);
      }
    } finally {
      Thread.currentThread().setContextClassLoader(originalClassLoader);
    }
    log.debug("### END SETUP " + getTest());
  }
}
