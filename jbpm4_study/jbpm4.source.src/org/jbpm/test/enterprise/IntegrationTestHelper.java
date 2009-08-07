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
import java.util.Hashtable;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * An integration test helper that deals with test deployment/undeployment, etc.
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 14-Oct-2004
 */
public class IntegrationTestHelper {
  protected static final String SYSPROP_TEST_RESOURCES_DIRECTORY = "test.resources.directory";
  protected static final String SYSPROP_TEST_ARCHIVE_DIRECTORY = "test.archive.directory";

  protected static String testResourcesDir;
  protected static String testArchiveDir;

  private static MBeanServerConnection server;
  private String integrationTarget;

  public void deploy(String[] archives) throws Exception {
    for(String archive : archives)
    {
      deploy(archive);
    }
  }

  public void deploy(String archive) throws Exception {
    URL url = getTestArchiveFile(archive).toURI().toURL();
    deploy(url);
  }

  public void deploy(URL archive) throws Exception {
    getDeployer().deploy(archive);
  }

  public void undeploy(String[] archives) throws Exception
  {
    for(String archive : archives)
    {
      undeploy(archive);
    }
  }

  public void undeploy(String archive) throws Exception {
    File testArchiveFile = getTestArchiveFile(archive);
    URL url = testArchiveFile.toURI().toURL();    
    undeploy(url);
  }

  public void undeploy(URL archive) throws Exception {
    File testArchiveFile = getTestArchiveFile(archive.getPath());
    File testArchiveFileTmp = new File(testArchiveFile.getAbsolutePath()+ ".tmp");

    // if not moved the JBPMDeployer retains the process definition ;)
    testArchiveFile.renameTo(testArchiveFileTmp);

    // invoke MainDeployer
    getDeployer().undeploy(archive);

    // restore orig file for subsequent tests
    testArchiveFileTmp.renameTo(testArchiveFile);
  }

  public boolean isTargetJBoss500() {
    String target = getIntegrationTarget();
    return target.startsWith("jboss500");
  }

  public boolean isTargetJBoss423() {
    String target = getIntegrationTarget();
    return target.startsWith("jboss423");
  }

  public boolean isTargetJBoss422() {
    String target = getIntegrationTarget();
    return target.startsWith("jboss422");
  }

  private String getIntegrationTarget() {
    if (integrationTarget == null) {
      String jbossVersion;
      try {
        ObjectName oname = ObjectNameFactory
            .create("jboss.system:type=ServerConfig");
        jbossVersion = (String) getServer().getAttribute(oname,
            "SpecificationVersion");
      } catch (Exception ex) {
        throw new IllegalStateException("Cannot obtain jboss version", ex);
      }

      if (jbossVersion.startsWith("5.0.0"))
        integrationTarget = "jboss500";
      else if (jbossVersion.startsWith("4.2.3"))
        integrationTarget = "jboss423";
      else if (jbossVersion.startsWith("4.2.2"))
        integrationTarget = "jboss422";
      else
        throw new IllegalStateException("Unsupported jboss version: "
            + jbossVersion);
    }
    return integrationTarget;
  }

  public MBeanServerConnection getServer() {
    if (server == null) {
      Hashtable jndiEnv = null;
      try {
        InitialContext iniCtx = new InitialContext();
        jndiEnv = iniCtx.getEnvironment();
        server = (MBeanServerConnection) iniCtx
            .lookup("jmx/invoker/RMIAdaptor");
      } catch (NamingException ex) {
        throw new RuntimeException(
            "Cannot obtain MBeanServerConnection using jndi props: " + jndiEnv,
            ex);
      }
    }
    return server;
  }

  private ArchiveDeployer getDeployer() {
    return new JBossArchiveDeployer(getServer());
  }

  /** Try to discover the URL for the test resource */
  public URL getResourceURL(String resource) {
    URL resURL = null;
    try {
      File resourceFile = getResourceFile(resource);
      resURL = resourceFile.toURI().toURL();
    } catch (MalformedURLException e) {
      // ignore
    }
    return resURL;
  }

  /** Try to discover the File for the test resource */
  public File getResourceFile(String resource) {
    File file = new File(resource);
    if (file.exists())
      return file;

    file = new File(getTestResourcesDir() + "/" + resource);
    if (file.exists())
      return file;

    throw new IllegalArgumentException("Cannot obtain '"
        + getTestResourcesDir() + "/" + resource + "'");
  }

  public String getTestResourcesDir() {
    if (testResourcesDir == null) {
      testResourcesDir = System.getProperty(SYSPROP_TEST_RESOURCES_DIRECTORY,
          "target/test-classes");
    }
    return testResourcesDir;
  }

  /** Try to discover the URL for the deployment archive */
  public static URL getTestArchiveURL(String archive) {
    try
    {
      return getTestArchiveFile(archive).toURI().toURL();
    }
    catch (MalformedURLException e)
    {
      throw new RuntimeException("Failed to getTestArchiveURL", e);
    }
  }

  /** Try to discover the File for the deployment archive */
  public static File getTestArchiveFile(String archive) {
    File file = new File(archive);
    if (file.exists())
      return file;

    file = new File(getTestArchiveDir() + "/" + archive);
    if (file.exists())
      return file;

    String notSet = getTestArchiveDir() == null ? " System property '"
        + SYSPROP_TEST_ARCHIVE_DIRECTORY + "' not set." : "";
    throw new IllegalArgumentException("Cannot obtain '" + getTestArchiveDir()
        + "/" + archive + "'." + notSet);
  }

  public static String getTestArchiveDir() {
    if (testArchiveDir == null) {
      testArchiveDir = System.getProperty(SYSPROP_TEST_ARCHIVE_DIRECTORY,
          "target/test-libs");
    }
    return testArchiveDir;
  }
}
