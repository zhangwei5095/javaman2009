package org.ndot.ips.comm.channel;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ndot.ips.comm.IPSReportDecoder;

import com.nasoft.IPSReport;
import com.nasoft.IPSReportConfigFactory;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.header.IPSHeaderField;
import com.nasoft.iso.packager.XMLPackager;
import com.nasoft.log4j.ISOMsgRenderer;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSISO8583ReportDecoder.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-21
 * 
 */
public class IPSISO8583ReportDecoder extends IPSReportDecoder {

	public IPSISO8583ReportDecoder() {
		setLog(Logger.getLogger(IPSISO8583ReportDecoder.class));
	}

	public IPSReport decoder(String channelId, byte[] reportContent) {
		IPSReport reqReportObj = new IPSReport();
		try {
			// �����ָ��������������Ϣ����������
			if (getIpsReportConfigFactory().getIsoPackagerBySysID(channelId) != null) {
				// ��ñ���ͷ��������������Ϣ
				BaseHeader header = getIpsReportConfigFactory()
						.getHeaderPackagerBySysID(channelId);
				// ��ȡ���ĸ�ʽ����������Ϣ
				NodeForm node = getIpsReportConfigFactory().getNodeInfo(
						channelId, "req");
				// ��û����Ϣ������1��ʾ�У�2��ʾû��
				String mtiFlag = "1";
				if (node != null) {
					mtiFlag = node.getMsgType();
				}
				ISOMsg m = new ISOMsg();
				m.setMtiFlag(mtiFlag);
				// ��ñ������������������Ϣ
				ISOPackager packager = getIpsReportConfigFactory()
						.getIsoPackagerBySysID(channelId);

				int hlen = 0;
				Map<String, IPSHeaderField> headers = null;
				if (header != null) {
					// �����յ���header��Ϣ�������ƣ���ֵ��ISOMsg����m����header������
					// ���ҽ����յ��ı���ͷ���γ�<key,IPSHeaderField>��ʽ���Ա�Ӧ�ô���
					headers = header.mapUnpack(reportContent);
					hlen = header.getHLen();
				}
				m.setPackager(packager);
				byte[] nhData = new byte[reportContent.length - hlen];
				System.arraycopy(reportContent, hlen, nhData, 0, nhData.length);
				// �������õĸ������packager�ࣨ�磺�ַ������͵�������ISOStringFieldPackager���򽫽��յ��ĸ�����Ϣ��ISOStringFieldPackager��uppack�������н����Ȼ��ֵ��ֵ������Field�����value��
				m.unpack(nhData);
				reqReportObj.setHeader(headers);
				reqReportObj.setBody(m);
			} else {
				throw new ISOException("���Ϊ�� " + channelId
						+ " ������Ϣû�н�������......");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqReportObj;
	}

}
