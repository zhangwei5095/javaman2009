package org.ndot.ips.comm;

import org.ndot.ips.constant.IPSConstantConfig;
import org.ndot.ips.db.DBJdbcTool;
import org.ndot.ips.db.services.BusinessDBServices;
import org.ndot.ips.db.services.SecurityDBService;
import org.ndot.ips.log.IPSLogger;
import org.ndot.ips.util.GenJNL;

import com.nasoft.IPSReport;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSReportProcess.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-20
 * 
 */
public abstract class IPSReportProcesser extends IPSLogger {
	BusinessDBServices businessDBServices;
	SecurityDBService securityDBService;
	DBJdbcTool dbJdbcTool;
	IPSConstantConfig ipsConstantConfig;
	GenJNL seq;

	public GenJNL getSeq() {
		return seq;
	}

	public void setSeq(GenJNL seq) {
		this.seq = seq;
	}

	public DBJdbcTool getDbJdbcTool() {
		return dbJdbcTool;
	}

	public void setDbJdbcTool(DBJdbcTool dbJdbcTool) {
		this.dbJdbcTool = dbJdbcTool;
	}

	public BusinessDBServices getBusinessDBServices() {
		return businessDBServices;
	}

	public void setBusinessDBServices(BusinessDBServices businessDBServices) {
		this.businessDBServices = businessDBServices;
	}

	/**
	 * 
	 * @param channelId
	 *            ��������
	 * @param reqReportObj
	 *            ������
	 * @param devIP
	 *            ���ͽ��׵��豸IP
	 * @return �����Ѿ�����ҵ�����Ӧ����
	 */
	public abstract IPSReport processer(IPSReportChannel channel,
			IPSReport reqReportObj, String devIP);

	public SecurityDBService getSecurityDBService() {
		return securityDBService;
	}

	public void setSecurityDBService(SecurityDBService securityDBService) {
		this.securityDBService = securityDBService;
	}

	public IPSConstantConfig getIpsConstantConfig() {
		return ipsConstantConfig;
	}

	public void setIpsConstantConfig(IPSConstantConfig ipsConstantConfig) {
		this.ipsConstantConfig = ipsConstantConfig;
	}

}
