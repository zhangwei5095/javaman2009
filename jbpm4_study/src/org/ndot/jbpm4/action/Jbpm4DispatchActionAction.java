/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package org.ndot.jbpm4.action;

import org.apache.struts.actions.DispatchAction;
import org.ndot.jbpm4.Jbpm4Controller;
import org.ndot.jbpm4.log.Jbpm4Logger;

/**
 * MyEclipse Struts Creation date: 08-08-2009
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public abstract class Jbpm4DispatchActionAction extends DispatchAction {
	private Jbpm4Controller controller;
	private Jbpm4Logger logger;

	public Jbpm4Logger getLogger() {
		return logger;
	}

	public void setLogger(Jbpm4Logger logger) {
		this.logger = logger;
	}

	public Jbpm4Controller getController() {
		return controller;
	}

	public void setController(Jbpm4Controller controller) {
		this.controller = controller;
	}
}