package com.nasoft;

import com.nasoft.fixed.C021Head;
import com.nasoft.fixed.C021Packager;
import com.nasoft.fixed.C028Head;
import com.nasoft.fixed.C028Packager;
import com.nasoft.fixed.FixedTransBitMap;
import com.nasoft.iso.FixedISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.packager.XMLPackager;
import com.nasoft.util.EBCaASCtransfer;
import com.nasoft.xml.IsoMsg2PlatMsg;

public class UnPack {
	public synchronized String unpack(String nodeId, byte[] buf, String reqRsp)
			throws Exception {
		if (buf == null || buf.length == 0) {
			throw new Exception("buf is null or buf length is 0");
		}
		if (PackagerFactory.getFixedPackagerByNodeId(nodeId) != null) {
			if ("C026".equals(nodeId)) {
				for (int i = 0; i < buf.length; i++) {
					if (buf[i] == 0x00) {
						buf[i] = 0x20;
					}
				}
			}
			if ("C021".equals(nodeId)) {// 联通报文特殊处理
				StringBuilder sb = new StringBuilder();
				C021Head head = new C021Packager().unpackHeader(buf, reqRsp);
				String xmlHeader = head.getXmlHead();
				String idenStr = head.getIdenStr();

				FixedTransBitMap fixedTransBitMap = PackagerFactory
						.getFixedBitMap(nodeId, reqRsp, idenStr);
				if (fixedTransBitMap == null) {
//					log.writeLog("unpack end---------------------------------");
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + idenStr);
				}

				String bitMapStr = PackagerFactory.getFixedBitMap(nodeId,
						reqRsp, idenStr).getFields();
//				log.writeLog("bitMapStr=" + bitMapStr);

				if (bitMapStr == null || "".equals(bitMapStr)) {
//					log.writeLog("unpack end---------------------------------");
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + idenStr);
				}
				int headLen = head.getHeadLen();

				String xmlBody = new C021Packager().unpackBody(nodeId, buf,
						bitMapStr, headLen, idenStr.trim());
				sb.append("<root>");
				sb.append(xmlHeader);
				sb.append(xmlBody);
				sb.append("</root>");
				return sb.toString();
			} else if ("C029".equals(nodeId) && buf.length < 49) {

				byte[] ascBuf = EBCaASCtransfer.pub_base_EBCtoASC(buf,
						buf.length);
				StringBuilder sb = new StringBuilder();
				C028Head head = new C028Packager().unpackHeader(ascBuf);
				String xmlHeader = head.getXmlHead();

				sb.append("<root>");
				sb.append(xmlHeader);

				sb.append("</root>");
				return sb.toString();
			} else {
				IsoMsg2PlatMsg convert = new IsoMsg2PlatMsg();
				BaseHeader header = (BaseHeader) PackagerFactory
						.getFixedHeaderPackagerBySysID(nodeId, reqRsp).clone();

				convert.setHeader(header);

				NodeForm node = PackagerFactory.getNodeInfo(nodeId, "req");
				String mtiFlag = "1";
				if (node != null) {
					mtiFlag = node.getMsgType();
				}

				XMLPackager xmlPackager = new XMLPackager();
				FixedISOMsg m = new FixedISOMsg();
				m.setMtiFlag(mtiFlag);
				ISOPackager packager = PackagerFactory
						.getFixedPackagerByNodeId(nodeId);

				int hlen = 0;
				String headerXML = "";
				try {
					if (header != null) {
						header.unpack(buf);

						headerXML = header.xmlUnpack();
					}
					hlen = header.getHLen();
				} catch (Exception e) {
//					log.writeLog("unpack end---------------------------------");
					throw e;
				}
				String transId = header.getIdenStr();

				// 水费报文接口的交易识别码特殊处理，响应交易识别码加上报文尾
				// if("C010".equals(nodeId) && "rsp".equals(reqRsp)){
				// transId+=new String(buf,buf.length-3,3);
				// }

				if ("C026".equals(nodeId)
						&& (transId.startsWith("1") || transId.startsWith("2"))) {// 移动对帐交易特殊处理
					StringBuffer sb = new StringBuffer();
					sb.append("<root>");
					String msg = new String(buf);
					sb.append("<HF2>");
					sb.append(msg.substring(3, 7));
					sb.append("</HF2>");
					sb.append("<HF3>");
					sb.append(msg.substring(7, 9));
					sb.append("</HF3>");
					sb.append("</root>");
					return sb.toString();
				}

				FixedTransBitMap fixedTransBitMap = PackagerFactory
						.getFixedBitMap(nodeId, reqRsp, transId);
				if (fixedTransBitMap == null) {
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + transId);
				}

				String bitMapStr = PackagerFactory.getFixedBitMap(nodeId,
						reqRsp, transId).getFields();

				if (bitMapStr == null || "".equals(bitMapStr)) {
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + transId);

				}
				// bitMapStr.split("");
				m.setBitMapStr(bitMapStr);
				m.setPackager(packager);
				byte[] nhData = new byte[buf.length - hlen];

				System.arraycopy(buf, hlen, nhData, 0, nhData.length);

				m.unpack(nhData);
				m.setPackager(xmlPackager);

				byte[] xmlBytes = m.pack();
				StringBuffer sb = new StringBuffer();
				sb.append("<root>");
				sb.append(headerXML);
				sb.append(new String(xmlBytes));
				sb.append("</root>");

				 System.out.println(sb.toString());

				String xml = convert.transform(sb.toString());
				return xml;
			}

		} else {
			throw new Exception("node not found:" + nodeId);
		}
	}

	private boolean hf10lengthFlag(String xml) {

		int start = xml.indexOf("<HF10>");
		int end = xml.indexOf("</HF10>");
		if (start < 0 || end < 0)
			return true;
		String hf10 = xml.substring(start + 6, end);
		if (hf10.length() < 26)
			return true;
		else
			return false;
	}
}
