package org.ndot.ips.comm;

import org.ndot.ips.log.IPSLogger;

import com.nasoft.IPSReport;
import com.nasoft.IPSReportConfigFactory;
import com.nasoft.iso.ISOException;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSReportEncoder.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-20
 * 
 */
public abstract class IPSReportEncoder extends IPSLogger {
	IPSReportConfigFactory ipsReportConfigFactory;

	public IPSReportConfigFactory getIpsReportConfigFactory() {
		return ipsReportConfigFactory;
	}

	public void setIpsReportConfigFactory(
			IPSReportConfigFactory ipsReportConfigFactory) {
		this.ipsReportConfigFactory = ipsReportConfigFactory;
	}

	/**
	 * 
	 * @param channelId
	 *            渠道ID 如 C003表示ATM
	 * @param report
	 *            报文对象
	 * @return
	 * @throws ISOException
	 */
	public abstract byte[] encoder(String channelId, IPSReport report)
			throws ISOException;
}
