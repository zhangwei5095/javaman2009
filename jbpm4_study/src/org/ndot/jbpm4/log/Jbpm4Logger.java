package org.ndot.jbpm4.log;

import org.apache.log4j.Logger;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSLoger.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-20
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
