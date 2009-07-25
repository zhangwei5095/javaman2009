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
 * �ļ����� IPSReportDecoder.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-20
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
	 *            ��������
	 * @param reportContent
	 *            ���յ��ñ����ֽ���Ϣ
	 * @return ���ز����� ���Ķ��� IPSReport
	 * @throws ISOException
	 */
	public abstract IPSReport decoder(String channelId, byte[] reportContent)
			throws ISOException;
}
