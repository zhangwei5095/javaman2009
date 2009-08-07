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

import org.jbpm.pvm.internal.cfg.JbpmConfiguration;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** parses the <code>jbpm-configuration</code>, which is assumed the document element
 * and can contain the {@link EnvironmentFactoryXmlParser process-engine}
 * context and the {@link EnvironmentXmlParser environment} context.
 * 
 * See {@link Parser} for usage instructions.
 *   
 * @author Tom Baeyens
 */
public class JbpmConfigurationParser extends Parser {

  private static final long serialVersionUID = 1L;
  
  // private static Log log = Log.getLog(JbpmConfigurationParser.class.getName());

  Parser processEngineContextParser = new WireParser();
  Parser transactionContextParser = new WireParser();

  protected static JbpmConfigurationParser INSTANCE = new JbpmConfigurationParser();
  
  public static JbpmConfigurationParser getInstance() {
    return INSTANCE;
  }

  public Object parseDocument(Document document, Parse parse) {
    Element documentElement = document.getDocumentElement();
    
    // if the default environment factory was already set in the parse
    JbpmConfiguration jbpmConfiguration = (JbpmConfiguration) parse.findObject(JbpmConfiguration.class);
    if (jbpmConfiguration==null) {
      jbpmConfiguration = new JbpmConfiguration();
    }
    
    for (Element importElement : XmlUtil.elements(documentElement, "import")) {
      if (importElement.hasAttribute("resource")) {
        String resource = importElement.getAttribute("resource");
        Parse importParse = createParse()
          .setResource(resource)
          .pushObject(jbpmConfiguration)
          .execute();
        
        parse.addProblems(importParse.getProblems());
      }
    }

    Element processEngineElement = XmlUtil.element(documentElement, "process-engine-context");
    if (processEngineElement != null) {
      WireDefinition processEngineContextDefinition = jbpmConfiguration.getProcessEngineWireContext().getWireDefinition();
      parse.pushObject(processEngineContextDefinition);
      try {
        processEngineContextParser.parseDocumentElement(processEngineElement, parse);
      } finally {
        parse.popObject();
      }
    }

    Element txCtxElement = XmlUtil.element(documentElement, "transaction-context");
    if (txCtxElement != null) {
      WireDefinition transactionContextDefinition = jbpmConfiguration.getTransactionWireDefinition();
      parse.pushObject(transactionContextDefinition);
      try {
        transactionContextParser.parseDocumentElement(txCtxElement, parse);
      } finally {
        parse.popObject();
      }
    }

    parse.setDocumentObject(jbpmConfiguration);
    
    return jbpmConfiguration;
  }

  public Parser getProcessEngineContextParser() {
    return processEngineContextParser;
  }
  public void setProcessEngineContextParser(Parser applicationWireXmlParser) {
    this.processEngineContextParser = applicationWireXmlParser;
  }
  public Parser getTransactionContextParser() {
    return transactionContextParser;
  }
  public void setTransactionContextParser(Parser blockWireXmlParser) {
    this.transactionContextParser = blockWireXmlParser;
  }
}
