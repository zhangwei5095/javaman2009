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
 * 文件名： IPSReportDecoder.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-20
 * 
 */
public abstract class IPSReportDecoder extends IPSLogger {

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
	 *            渠道编码
	 * @param reportContent
	 *            接收到得报文字节信息
	 * @return 返回拆包后的 报文对象 IPSReport
	 * @throws ISOException
	 */
	public abstract IPSReport decoder(String channelId, byte[] reportContent)
			throws ISOException;
}
