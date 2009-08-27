package com.nasoft;

import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.packager.XMLPackager;
import com.nasoft.util.TestLog;
import com.nasoft.xml.IsoMsg2PlatMsg;
import com.nasoft.xml.PlatMsg2IsoMsg;

public class PackUnpackHelper {

	public static byte[] pack(String nodeId, String xml) throws Exception {
		TestLog log = TestLog.getNewInstance();

		log.writeLog("pack start---------------------------------------");
		log.writeLog("nodeId=" + nodeId);
		log.writeLog("PackUnpackHelper-23 - "
				+ Thread.currentThread().getName() + " : xml=" + xml);

		if (xml == null || "".equals(xml)) {
			log.writeLog("end pack--------------------------");
			throw new Exception("xml is null");
		}
		if (PackagerFactory.getIsoPackagerBySysID(nodeId) != null) {
			PlatMsg2IsoMsg convert = new PlatMsg2IsoMsg();
			//			
			BaseHeader header = PackagerFactory
					.getHeaderPackagerBySysID(nodeId);
			convert.setHeader(header);
			xml = convert.transform(xml);
			int start = xml.indexOf("<isomsg>");
			int end = xml.indexOf("</isomsg>") + 9;
			String bodyXML = xml.substring(start, end);
			log.writeLog("bodyXML=" + bodyXML);
			String headerXML = "";
			if (header != null) {
				start = xml.indexOf("<header>");
				end = xml.indexOf("</header>") + 9;
				headerXML = xml.substring(start, end);
			}
			NodeForm node = PackagerFactory.getNodeInfo(nodeId, "req");
			String mtiFlag = "1";
			String headLenField = "N";
			String bodyLenField = "N";
			String msgLenField = "N";
			String msgLen1Field = "N";
			if (node != null) {
				mtiFlag = node.getMsgType();
				headLenField = node.getHeadLenField();
				bodyLenField = node.getBodyLenField();
				msgLenField = node.getMsgLenField();
				msgLen1Field = node.getMsgLen1Field();
			}

			ISOMsg m = new ISOMsg();
			m.setMtiFlag(mtiFlag);

			XMLPackager xmlPackager = new XMLPackager();
			m.setPackager(xmlPackager);
			// 将所有的域设置到ISOMsg的fields中，也是将
			m.unpack(bodyXML.getBytes("utf-8"));

			ISOPackager packager = PackagerFactory
					.getIsoPackagerBySysID(nodeId);
			m.setPackager(packager);
			byte[] body = m.pack();
			int len = body.length;

			log.writeLog("body=" + ISOUtil.byte2HexStr(body, body.length));
			// 组报文头长度
			if (!"N".equals(headLenField)) {
				String fieldName = "HF" + headLenField;
				if (headerXML.indexOf(fieldName) != -1) {
					String tmpHeaderXML = "";
					tmpHeaderXML += headerXML.substring(0, headerXML
							.indexOf("<" + fieldName));
					tmpHeaderXML += headerXML.substring(headerXML.indexOf("</"
							+ fieldName + ">") + 6);
					headerXML = tmpHeaderXML;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(headerXML.substring(0, 8));
				sb.append("<" + fieldName + ">" + (header.getHLen()) + "</"
						+ fieldName + ">");
				sb.append(headerXML.substring(8));
				headerXML = sb.toString();

			}

			// 组报文体长度
			if (!"N".equals(bodyLenField)) {
				String fieldName = "HF" + bodyLenField;
				if (headerXML.indexOf(fieldName) != -1) {

					String tmpHeaderXML = "";
					tmpHeaderXML += headerXML.substring(0, headerXML
							.indexOf("<" + fieldName));
					tmpHeaderXML += headerXML.substring(headerXML.indexOf("</"
							+ fieldName + ">") + 6);
					headerXML = tmpHeaderXML;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(headerXML.substring(0, 8));
				sb.append("<" + fieldName + ">" + (body.length) + "</"
						+ fieldName + ">");
				sb.append(headerXML.substring(8));
				headerXML = sb.toString();

			}

			// 组报文总长度
			if (!"N".equals(msgLenField)) {
				String fieldName = "HF" + msgLenField;
				if (headerXML.indexOf(fieldName) != -1) {

					String tmpHeaderXML = "";
					tmpHeaderXML += headerXML.substring(0, headerXML
							.indexOf("<" + fieldName));
					tmpHeaderXML += headerXML.substring(headerXML.indexOf("</"
							+ fieldName + ">") + 6);
					headerXML = tmpHeaderXML;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(headerXML.substring(0, 8));
				sb.append("<" + fieldName + ">"
						+ (body.length + header.getHLen()) + "</" + fieldName
						+ ">");
				sb.append(headerXML.substring(8));
				headerXML = sb.toString();

			}

			// 组报文总长度
			if (!"N".equals(msgLen1Field)) {
				String fieldName = "HF" + msgLen1Field;
				int fieldLen = header.getFieldByName(fieldName).Len;
				if (headerXML.indexOf(fieldName) != -1) {

					String tmpHeaderXML = "";
					tmpHeaderXML += headerXML.substring(0, headerXML
							.indexOf("<" + fieldName));
					tmpHeaderXML += headerXML.substring(headerXML.indexOf("</"
							+ fieldName + ">") + 6);
					headerXML = tmpHeaderXML;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(headerXML.substring(0, 8));
				sb.append("<" + fieldName + ">"
						+ (body.length + header.getHLen() - fieldLen) + "</"
						+ fieldName + ">");
				sb.append(headerXML.substring(8));
				headerXML = sb.toString();

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

			log.writeLog("headerXML=" + headerXML);
			if (header != null) {
				byte[] h = new byte[0];
				h = header.xmlPack(headerXML);
				len += h.length;
				byte[] data = new byte[len];
				System.arraycopy(h, 0, data, 0, header.getLength());
				System.arraycopy(body, 0, data, h.length, body.length);
				log.writeLog("data=" + ISOUtil.byte2HexStr(data, data.length));
				log.writeLog("end pack--------------------------");
				return data;
			}

			// log.writeLog("data="+ISOUtil.byte2HexStr(body,body.length));
			// log.writeLog("end pack--------------------------");
			return body;

		}
		// else if(PackagerFactory.getIsoPackagerBySysID(nodeId)!=null)
		// {
		// IFixedMsgPackager fmp =
		// PackagerFactory.getFixedPackagerBySysID(nodeId);
		// FixedMsg msg = new FixedMsg();
		// msg.setPackager(fmp);
		// msg.Xml2Msg(xml);
		// return msg.pack();
		// }
		else {
			// log.writeLog("end pack--------------------------");
			throw new Exception("node not found:" + nodeId);
		}
	}

	public static String unpack(String nodeId, byte[] buf) throws Exception {
		TestLog log = TestLog.getNewInstance();
		// log.writeLog("unpack
		// start-----------------------------------------------");
		// log.writeLog("nodeId="+nodeId);
		// log.writeLog("buf(hex)="+ISOUtil.byte2HexStr(buf,buf.length));

		if (buf == null || buf.length == 0) {
			// log.writeLog("unpack end---------------------------------");
			throw new Exception("buf is null or buf length is 0");
		}

		if (PackagerFactory.getIsoPackagerBySysID(nodeId) != null) {
			IsoMsg2PlatMsg convert = new IsoMsg2PlatMsg();
			BaseHeader header = PackagerFactory
					.getHeaderPackagerBySysID(nodeId);

			// BaseHeader header=null;
			// if("C001".equals(nodeId)){
			// header=new com.nasoft.iso.header.CUPSHeader();
			// }else if("C002".equals(nodeId)){
			// header =new com.nasoft.iso.header.ATMPHeader();
			// }

			convert.setHeader(header);
			NodeForm node = PackagerFactory.getNodeInfo(nodeId, "req");
			String mtiFlag = "1";
			if (node != null) {
				mtiFlag = node.getMsgType();
			}
			XMLPackager xmlPackager = new XMLPackager();
			ISOMsg m = new ISOMsg();
			m.setMtiFlag(mtiFlag);
			ISOPackager packager = PackagerFactory
					.getIsoPackagerBySysID(nodeId);

			int hlen = 0;
			String headerXML = "";

			if (header != null) {
				header.unpack(buf);
				headerXML = header.xmlUnpack();
				hlen = header.getHLen();
			}
			// log.writeLog("hlen="+hlen);
			log.writeLog("headerXML=" + headerXML);
			m.setPackager(packager);
			byte[] nhData = new byte[buf.length - hlen];
			System.arraycopy(buf, hlen, nhData, 0, nhData.length);
			log
					.writeLog("nhData="
							+ ISOUtil.byte2HexStr(nhData, nhData.length));
			m.unpack(nhData);
			m.setPackager(xmlPackager);

			byte[] xmlBytes = m.pack();
			StringBuffer sb = new StringBuffer();
			sb.append("<root>");
			sb.append(headerXML);
			sb.append(new String(xmlBytes));
			sb.append("</root>");
			System.out.println("xmlBytes==" + new String(xmlBytes));

			String xml = convert.transform(sb.toString());
			// log.writeLog("PackUnpackHellper-258"+Thread.currentThread().getName()+"
			// : 111xml=" + xml);
			// log.writeLog("unpack end---------------------------------");
			return xml;
			// return sb.toString();

		} else {
			// log.writeLog("unpack end---------------------------------");
			throw new Exception("node not found:" + nodeId);
		}
	}
}
