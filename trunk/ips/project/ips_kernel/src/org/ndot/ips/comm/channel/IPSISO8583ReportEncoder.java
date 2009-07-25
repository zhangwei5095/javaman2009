package org.ndot.ips.comm.channel;

import org.apache.log4j.Logger;
import org.ndot.ips.comm.IPSReportEncoder;

import com.nasoft.IPSReport;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.header.BaseHeader.HeaderField;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSISO8583ReportEncoder.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-21
 * 
 */
public class IPSISO8583ReportEncoder extends IPSReportEncoder {

	public IPSISO8583ReportEncoder() {
		setLog(Logger.getLogger(IPSISO8583ReportEncoder.class));
	}

	public byte[] encoder(String channelId, IPSReport reqReportObj)
			throws ISOException {
		// 如果对指定的渠道报文信息进行了配置
		if (getIpsReportConfigFactory().getIsoPackagerBySysID(channelId) != null) {
			// 获得报文头的域类型配置信息
			BaseHeader header = getIpsReportConfigFactory()
					.getHeaderPackagerBySysID(channelId);
			String headerXML = reqReportObj.formatToXml();
			// 获取报文格式定义的相关信息
			NodeForm node = getIpsReportConfigFactory().getNodeInfo(channelId,
					"req");
			String mtiFlag = "1"; // 是否包含报文类型0：不包含报文类型 1：包含报文类型
			String headLenField = "N"; // 是否计算报文头长度，如果计算存放在哪个域 type=1
			String bodyLenField = "N"; // 是否计算报文体长度，如果计算存放在哪个域 type=2
			String msgLenField = "N"; // 是否计算报文总长度，如果计算存放在哪个域 type=3
			String msgLen1Field = "N";// 是否计算报文总长度去掉表示报文长度的域的长度，如果计算存放在哪个域type=4

			if (node != null) {
				mtiFlag = node.getMsgType();
				headLenField = node.getHeadLenField();
				bodyLenField = node.getBodyLenField();
				msgLenField = node.getMsgLenField();
				msgLen1Field = node.getMsgLen1Field();
			}
			ISOMsg m = reqReportObj.getBody();
			m.setMtiFlag(mtiFlag);
			// 获得报文体的域类型配置信息
			ISOPackager packager = getIpsReportConfigFactory()
					.getIsoPackagerBySysID(channelId);
			m.setPackager(packager);
			byte[] body = m.pack();
			int len = body.length;

			// 组报文头长度
			if (!"N".equals(headLenField)) {
				headerXML = calcuLen(header, headerXML, body.length,
						headLenField, 1);
			}

			// 组报文体长度
			if (!"N".equals(bodyLenField)) {
				headerXML = calcuLen(header, headerXML, body.length,
						bodyLenField, 2);
			}

			// 组报文总长度
			if (!"N".equals(msgLenField)) {
				headerXML = calcuLen(header, headerXML, body.length,
						msgLenField, 3);

			}

			// 组报文总长度
			if (!"N".equals(msgLen1Field)) {
				headerXML = calcuLen(header, headerXML, body.length,
						msgLen1Field, 4);
			}

			for (int i = 0; i < 10; i++) {

				String fieldName = "HF" + (i + 1);
				if (headerXML.indexOf(fieldName) == -1) {

					StringBuilder sb = new StringBuilder();
					sb.append(headerXML.substring(0, 8));
					sb.append("<" + fieldName + ">" + " " + "</" + fieldName
							+ ">");
					sb.append(headerXML.substring(8));
					headerXML = sb.toString();
				}

			}

			if (header != null) {
				byte[] h = new byte[0];
				h = header.xmlPack(headerXML);
				len += h.length;
				byte[] data = new byte[len];
				System.arraycopy(h, 0, data, 0, header.getLength());
				System.arraycopy(body, 0, data, h.length, body.length);
				return data;
			}

			return body;

		} else {
			throw new ISOException("编号为： " + channelId + " 渠道信息没有进行配置......");
		}

	}

	/**
	 * 
	 * @param header
	 * @param headerXML
	 * @param bodyLen
	 * @param fieldName
	 * @param type
	 * @return
	 */
	private static String calcuLen(BaseHeader header, String headerXML,
			int bodyLen, String fieldName, int type) {
		fieldName = "HF" + fieldName;
		HeaderField f = header.getFieldByName(fieldName);
		int fieldLen = f.Len;
		int len = getLen(bodyLen, header.getHLen(), fieldLen, type);
		String serach = fieldName;
		if (headerXML.indexOf(serach) != -1) {
			String tmpHeaderXML = "";
			tmpHeaderXML += headerXML.substring(0, headerXML.indexOf("<"
					+ serach));
			tmpHeaderXML += headerXML.substring(headerXML.indexOf("</" + serach
					+ ">") + 6);
			headerXML = tmpHeaderXML;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(headerXML.substring(0, 8));
		sb.append("<" + serach + ">" + len + "</" + serach + ">");
		sb.append(headerXML.substring(8));
		headerXML = sb.toString();
		return headerXML;

	}

	/**
	 * 
	 * @param bodyLen
	 * @param headerLen
	 * @param fieldLen
	 * @param type
	 * @return
	 */
	private static int getLen(int bodyLen, int headerLen, int fieldLen, int type) {
		int len = 0;
		switch (type) {
		case 1:
			len = headerLen;
			break;
		case 2:
			len = bodyLen;
			break;
		case 3:
			len = bodyLen + headerLen;
			break;
		case 4:
			len = bodyLen + headerLen - fieldLen;
			break;
		}
		return len;
	}

}
