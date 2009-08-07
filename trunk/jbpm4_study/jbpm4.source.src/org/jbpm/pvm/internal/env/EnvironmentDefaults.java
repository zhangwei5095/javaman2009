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
package org.jbpm.pvm.internal.env;

import org.jbpm.pvm.internal.script.ScriptManager;


/** central location for configuration objects that also 
 * have a default so that they can be obtained without a 
 * configuration and an environment block.
 * 
 * for every object, first, the current environment will 
 * be searched if there is one,  if not, the default object 
 * is returned.
 *  
 * @author Tom Baeyens
 */
public abstract class EnvironmentDefaults {

  public static ScriptManager getScriptManager() {
    ScriptManager scriptManager = Environment.getFromCurrent(ScriptManager.class, false);
    if (scriptManager!=null) {
      return scriptManager;
    }
    return ScriptManager.getDefaultScriptManager();
  }

}
