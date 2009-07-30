package org.ndot.struts.action;

import org.apache.struts.action.Action;
import org.ndot.ips.log.IPSLogger;

public abstract class IPSAction extends Action {
	IPSLogger log;
	Boolean isOpen;

	public IPSAction() {
		log = new IPSLogger();
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
		this.log.setOpen(isOpen);
	}

	public IPSLogger getLog() {
		return log;
	}

	public void setLog(IPSLogger log) {
		this.log = log;
	}

}
