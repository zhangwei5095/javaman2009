package org.ndot.ips.comm;

import org.ndot.ips.log.IPSLogger;

import com.nasoft.IPSReport;
import com.nasoft.IPSReportConfigFactory;
import com.nasoft.iso.ISOException;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSReportEncoder.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-20
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
	 *            ����ID �� C003��ʾATM
	 * @param report
	 *            ���Ķ���
	 * @return
	 * @throws ISOException
	 */
	public abstract byte[] encoder(String channelId, IPSReport report)
			throws ISOException;
}
