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
package org.jbpm.pvm.internal.script;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jbpm.api.Execution;
import org.jbpm.api.JbpmException;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.model.ScopeInstanceImpl;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.xml.WireParser;

/**
 * @author Tom Baeyens
 */
public class ScriptManager {

  private static Log log = Log.getLog(ScriptManager.class.getName());

  private static ScriptManager defaultScriptManager = null;

  protected String defaultExpressionLanguage;
  protected String defaultScriptLanguage;
  protected ScriptEngineManager scriptEngineManager;
  protected String[] readContextNames = null;
  protected String writeContextName;
  
  public static synchronized ScriptManager getDefaultScriptManager() {
    if (defaultScriptManager==null) {
      WireDefinition wireDefinition = (WireDefinition) new WireParser()
        .createParse()
        .setString(
          "<objects>" +
          "  <script-manager default-expression-language='juel'" +
          "                  default-script-language='beanshell'>" +
          "    <script-language name='juel' factory='org.jbpm.pvm.internal.script.JuelScriptEngineFactory' />" +
          "  </script-manager>" +
          "</objects>"
        )
        .execute()
        .getDocumentObject();
  
      WireContext wireContext = new WireContext(wireDefinition);
      defaultScriptManager = wireContext.get(ScriptManager.class);
    }
    return defaultScriptManager;
  }

  /** {@link #evaluate(String, Execution, String) evaluates} the expression 
   * with the given language or with the defaultExpressionLanguage if the 
   * given language is null. */
  public Object evaluateExpression(String expression, String language) {
    return evaluate(expression, (language!=null ? language : defaultExpressionLanguage));
  }

  /** {@link #evaluate(String, Execution, String) evaluates} the script 
   * with the given language or with the defaultScriptLanguage if the 
   * given language is null. */
  public Object evaluateScript(String script, String language) {
    return evaluate(script, (language!=null ? language : defaultScriptLanguage));
  }

  /** evaluates the script with the given language.
   * If script is null, then this method will return null.
   * @throws JbpmException if language is null.
   */
  public Object evaluate(String script, String language) {
    if (script==null) {
      return null;
    }
    if (language==null) {
      throw new JbpmException("no language specified");
    }
    ScriptEngine scriptEngine = scriptEngineManager.getEngineByName(language);
    if (scriptEngine==null) {
      throw new JbpmException("no scripting engine configured for language "+language);
    }
    
    if (log.isTraceEnabled()) log.trace("evaluating "+language+" script "+script);
    
    return evaluate(scriptEngine, script);
  }
  
  protected Object evaluate(ScriptEngine scriptEngine, String script) {
    Bindings bindings = new EnvironmentBindings(readContextNames, writeContextName);
    scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
    
    try {
      Object result = scriptEngine.eval(script);
      if (log.isTraceEnabled()) log.trace("script evaluated to "+result);
      return result;
    } catch (ScriptException e) {
      throw new JbpmException("script evaluation error: "+e.getMessage(), e);
    }
  }

  public String getDefaultExpressionLanguage() {
    return defaultExpressionLanguage;
  }
  public String getDefaultScriptLanguage() {
    return defaultScriptLanguage;
  }
}
