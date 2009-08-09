package org.ndot.jbpm4.log;

import org.apache.log4j.Logger;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSLoger.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-20
 * 
 */
public class Jbpm4Logger {

	Logger log = null;
	Boolean open = Boolean.FALSE;

	public Jbpm4Logger() {
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Boolean isOpen() {
		return open;
	}

	public void writeLog(int level, String message) {
		// levels: debug, info, warn, error, fatal
		if (isOpen().booleanValue()) {
			switch (level) {
			case 0:
				log.debug(message);
				break;
			case 1:
				log.info(message);
				break;
			case 2:
				log.warn(message);
				break;
			case 3:
				log.error(message);
				break;
			case 4:
				log.fatal(message);
				break;
			default:
				break;
			}

		}

	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

}
