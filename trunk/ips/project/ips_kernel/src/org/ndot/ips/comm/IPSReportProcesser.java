package org.ndot.ips.comm;

import org.ndot.ips.constant.IPSConstantConfig;
import org.ndot.ips.db.DBJdbcTool;
import org.ndot.ips.db.services.BusinessDBServices;
import org.ndot.ips.db.services.SecurityDBService;
import org.ndot.ips.log.IPSLogger;
import org.ndot.ips.util.GenJNL;

import com.nasoft.IPSReport;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSReportProcess.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-20
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
	 *            渠道代码
	 * @param reqReportObj
	 *            请求报文
	 * @param devIP
	 *            上送交易的设备IP
	 * @return 返回已经进行业务处理的应答报文
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
