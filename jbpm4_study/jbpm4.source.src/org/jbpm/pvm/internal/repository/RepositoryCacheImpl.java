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
package org.jbpm.pvm.internal.repository;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Tom Baeyens
 */
public class RepositoryCacheImpl implements RepositoryCache {
  
  Map<String, Map<String, Object>> deployments = new HashMap<String, Map<String,Object>>();

  public Object get(String deploymentId, String objectName) {
    Map<String, Object> deploymentObjects = deployments.get(deploymentId);
    if (deploymentObjects==null) {
      return null;
    }
    return deploymentObjects.get(objectName);
  }

  public void set(String deploymentId, Map<String, Object> objects) {
    if (objects==null) {
      deployments.put(deploymentId, null);
    } else {
      Map<String, Object> deploymentObjects = deployments.get(deploymentId);
      if (deploymentObjects==null) {
        deploymentObjects = new HashMap<String, Object>();
        deployments.put(deploymentId, deploymentObjects);
      }
      for (String objectName: objects.keySet()) {
        Object object = objects.get(objectName);
        deploymentObjects.put(objectName, object);
      }
    }
  }

  public void remove(String deploymentDbid) {
    if (deployments!=null) {
      deployments.remove(deploymentDbid);
    }
  }

  public void clear() {
    deployments = new HashMap<String, Map<String,Object>>();
  }
}
