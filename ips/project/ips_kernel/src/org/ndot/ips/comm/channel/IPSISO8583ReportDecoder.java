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
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSISO8583ReportDecoder.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-21
 * 
 */
public class IPSISO8583ReportDecoder extends IPSReportDecoder {

	public IPSISO8583ReportDecoder() {
		setLog(Logger.getLogger(IPSISO8583ReportDecoder.class));
	}

	public IPSReport decoder(String channelId, byte[] reportContent) {
		IPSReport reqReportObj = new IPSReport();
		try {
			// 如果对指定的渠道报文信息进行了配置
			if (getIpsReportConfigFactory().getIsoPackagerBySysID(channelId) != null) {
				// 获得报文头的域类型配置信息
				BaseHeader header = getIpsReportConfigFactory()
						.getHeaderPackagerBySysID(channelId);
				// 获取报文格式定义的相关信息
				NodeForm node = getIpsReportConfigFactory().getNodeInfo(
						channelId, "req");
				// 有没有消息类型域，1表示有，2表示没有
				String mtiFlag = "1";
				if (node != null) {
					mtiFlag = node.getMsgType();
				}
				ISOMsg m = new ISOMsg();
				m.setMtiFlag(mtiFlag);
				// 获得报文体的域类型配置信息
				ISOPackager packager = getIpsReportConfigFactory()
						.getIsoPackagerBySysID(channelId);

				int hlen = 0;
				Map<String, IPSHeaderField> headers = null;
				if (header != null) {
					// 将接收到的header信息（二进制）赋值到ISOMsg对象（m）的header属性中
					// 并且将接收到的报文头，形成<key,IPSHeaderField>形式，以便应用处理
					headers = header.mapUnpack(reportContent);
					hlen = header.getHLen();
				}
				m.setPackager(packager);
				byte[] nhData = new byte[reportContent.length - hlen];
				System.arraycopy(reportContent, hlen, nhData, 0, nhData.length);
				// 根据配置的各个域的packager类（如：字符串类型的域，配置ISOStringFieldPackager，则将接收到的该域信息用ISOStringFieldPackager的uppack方法进行解包，然后将值赋值到各個Field对象的value中
				m.unpack(nhData);
				reqReportObj.setHeader(headers);
				reqReportObj.setBody(m);
			} else {
				throw new ISOException("编号为： " + channelId
						+ " 渠道信息没有进行配置......");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqReportObj;
	}

}
