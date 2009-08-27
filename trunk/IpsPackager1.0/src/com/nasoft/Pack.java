package com.nasoft;

import com.nasoft.fixed.C021Head;
import com.nasoft.fixed.C021Packager;
import com.nasoft.fixed.C028Packager;
import com.nasoft.fixed.FixedTransBitMap;
import com.nasoft.iso.FixedISOMsg;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;
import com.nasoft.iso.packager.XMLPackager;
import com.nasoft.util.EBCaASCtransfer;
import com.nasoft.xml.PlatMsg2IsoMsg;

public class Pack {
	public synchronized byte[] pack(String nodeId, String xml, String reqRsp)
			throws Exception {

		// TestLog log = TestLog.getNewInstance();
		// log.writeLog("nodeId=" + nodeId);
		// log.writeLog(Thread.currentThread().getName() + " : xml=" + xml);
		// log.writeLog("reqRsp=" + reqRsp);
		if (xml == null || "".equals(xml)) {
			// log.writeLog("end pack--------------------------");
			throw new Exception("xml is null");
		}
		if (PackagerFactory.getFixedPackagerByNodeId(nodeId) != null) {
			if ("C021".equals(nodeId)) {// 联通报文
				C021Head header = new C021Packager().packHeader(xml, reqRsp);
				String idenStr = header.getIdenStr();
				int headLen = header.getHeadLen();

				FixedTransBitMap fixedTransBitMap = PackagerFactory
						.getFixedBitMap(nodeId, reqRsp, idenStr);
				if (fixedTransBitMap == null) {
					// log.writeLog("end pack--------------------------");
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + idenStr);
				}

				String bitMapStr = PackagerFactory.getFixedBitMap(nodeId,
						reqRsp, idenStr).getFields();

				if (bitMapStr == null || "".equals(bitMapStr)) {
					// log.writeLog("end pack--------------------------");
					throw new Exception(
							"this transaction is not configed in database,nodeId=C021,reqRsp="
									+ reqRsp + ",transType=" + idenStr);
				}
				byte[] head = header.getByteHeader();
				byte[] body = new C021Packager().pack("C021", xml, bitMapStr);

				String fieldName = "";
				if ("req".equals(reqRsp)) {
					fieldName = "HF2";
				} else {
					fieldName = "HF3";
				}

				if (xml.indexOf(fieldName) != -1) {

					String tmpHeaderXML = "";
					tmpHeaderXML += xml.substring(0, xml.indexOf("<"
							+ fieldName));
					tmpHeaderXML += xml.substring(xml.indexOf("</" + fieldName
							+ ">") + 5);
					xml = tmpHeaderXML;

					// tmpHeaderXML+="<"+fieldName+">"+(header.getHeadLen()+body.length)+"</"+fieldName+">";
					// tmpHeaderXML+=xml.substring(xml.indexOf("</"+fieldName+">")+5);
					// xml=tmpHeaderXML;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(xml.substring(0, xml.indexOf("<root>") + 6));
				int pos = xml.indexOf("<root>") + 6;
				String hf5 = xml.substring(xml.indexOf("<HF5>") + 5, xml
						.indexOf("</HF5>"));
				// System.out.println("hf5="+hf5);
				if ("C".equals(hf5) || ("Q".equals(hf5))) {
					sb.append("<" + fieldName + ">" + (body.length + 3) + "</"
							+ fieldName + ">");
				} else {
					sb.append("<" + fieldName + ">" + (body.length + 10) + "</"
							+ fieldName + ">");
				}

				// sb.append("<"+fieldName+">"+(body.length-2)+"</"+fieldName+">");
				sb.append(xml.substring(xml.indexOf("<root>") + 6));
				xml = sb.toString();

				// log.writeLog(Thread.currentThread().getName() + " : 111xml="
				// + xml);
				header = new C021Packager().packHeader(xml, reqRsp);
				head = header.getByteHeader();

				byte[] pack = new byte[headLen + body.length];
				System.arraycopy(head, 0, pack, 0, head.length);
				System.arraycopy(body, 0, pack, head.length, body.length);

				byte[] checkedMsg = new byte[pack.length + 1];
				System.arraycopy(pack, 0, checkedMsg, 0, pack.length);
				byte[] check = new byte[1];
				check[0] = new C021Packager().xOrArray(pack);
				System.arraycopy(check, 0, checkedMsg, pack.length, 1);
				// log.writeLog("end pack--------------------------");
				return checkedMsg;
			} else if ("C028".equals(nodeId) && hf10lengthFlag(xml)) {
				byte[] ascByte = new C028Packager().packHeader(xml, reqRsp);
				// log.writeLog("end pack--------------------------");
				return EBCaASCtransfer.pub_base_ASCtoEBC(ascByte,
						ascByte.length);
			} else {
				PlatMsg2IsoMsg convert = new PlatMsg2IsoMsg();

				BaseHeader header = (BaseHeader) PackagerFactory
						.getFixedHeaderPackagerBySysID(nodeId, reqRsp).clone();

				convert.setHeader(header);
				// log.writeLog("header.LEN=" + header.getHLen());
				xml = convert.transform(xml);
				// log.writeLog(Thread.currentThread().getName() + " : 140xml="
				// + xml);
				int start = xml.indexOf("<isomsg>");
				int end = xml.indexOf("</isomsg>") + 9;
				xml = xml.replace("<field id=\"0\" value",
						"<field id=\"1\" value");
				String bodyXML = xml.substring(start, end);
				// log.writeLog("bodyXML=" + bodyXML);
				String headerXML = "";
				if (header != null) {
					start = xml.indexOf("<header>");
					end = xml.indexOf("</header>") + 9;
					headerXML = xml.substring(start, end);
				}

				header.xmlPack(headerXML);
				// log.writeLog("header=" + headerXML);
				NodeForm node = PackagerFactory.getNodeInfo(nodeId, reqRsp);
				String mtiFlag = "0";
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

				FixedISOMsg m = new FixedISOMsg();
				m.setMtiFlag(mtiFlag);
				XMLPackager xmlPackager = new XMLPackager();
				String transId = header.getIdenStr();
				// log.writeLog("transId=" + transId);

				// 水费报文接口的交易识别码特殊处理，响应交易识别码加上报文尾
				// if("C010".equals(nodeId) && "rsp".equals(reqRsp)){
				// int tailStart = xml.indexOf("<BF27>");
				// int tailEnd = xml.indexOf("</BF27>") + 7;
				// transId+=bodyXML.substring(tailStart,tailEnd);
				// }

				FixedTransBitMap fixedTransBitMap = PackagerFactory
						.getFixedBitMap(nodeId, reqRsp, transId);
				if (fixedTransBitMap == null) {
					// log.writeLog("end pack--------------------------");
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + transId);
				}
				String bitMapStr = PackagerFactory.getFixedBitMap(nodeId,
						reqRsp, transId).getFields();
				if (bitMapStr == null || "".equals(bitMapStr)) {
					// log.writeLog("end pack--------------------------");
					throw new Exception(
							"this transaction is not configed in database,nodeId="
									+ nodeId + ",reqRsp=" + reqRsp
									+ ",transType=" + transId);
				}

				m.setBitMapStr(bitMapStr);
				m.setPackager(xmlPackager);
				m.unpack(bodyXML.getBytes("utf-8"));
				ISOPackager packager = PackagerFactory
						.getFixedPackagerByNodeId(nodeId);
				m.setPackager(packager);
				byte[] body = m.pack();
				if ("C030".equals(nodeId)) {// 添加包体分隔符
					byte[] tem = new byte[body.length + "\t".getBytes().length];
					System.arraycopy(body, 0, tem, 0, body.length);
					System.arraycopy("\t".getBytes(), 0, tem, body.length, "\t"
							.getBytes().length);
					body = tem;
				}
				int len = body.length;
				// log.writeLog("body=" + ISOUtil.byte2HexStr(body,
				// body.length));

				// 组报文头长度
				if (!"N".equals(headLenField)) {
					String fieldName = "HF" + headLenField;
					if (headerXML.indexOf(fieldName) != -1) {

						String tmpHeaderXML = "";
						tmpHeaderXML += headerXML.substring(0, headerXML
								.indexOf("<" + fieldName));
						tmpHeaderXML += headerXML.substring(headerXML
								.indexOf("</" + fieldName + ">") + 6);
						headerXML = tmpHeaderXML;
					}

					StringBuilder sb = new StringBuilder();
					sb.append(headerXML.substring(0, 8));
					if ("C026".equals(nodeId)
							&& (transId.startsWith("1") || transId
									.startsWith("2"))) {
						sb.append("<" + fieldName + ">"
								+ (header.getHLen() - 2) + "</" + fieldName
								+ ">");
					} else {
						sb.append("<" + fieldName + ">" + (header.getHLen())
								+ "</" + fieldName + ">");
					}

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
						tmpHeaderXML += headerXML.substring(headerXML
								.indexOf("</" + fieldName + ">") + 6);
						headerXML = tmpHeaderXML;
					}

					StringBuilder sb = new StringBuilder();
					sb.append(headerXML.substring(0, 8));
					int bodyLength = 0;
					if ("C030".equals(nodeId)) {// 内蒙电费包体长度，不包含报文头和报文体处的分隔符"\t"
						bodyLength = body.length - "\t".getBytes().length;
					} else {
						bodyLength = body.length;
					}
					sb.append("<" + fieldName + ">" + bodyLength + "</"
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
						tmpHeaderXML += headerXML.substring(headerXML
								.indexOf("</" + fieldName + ">") + 6);
						headerXML = tmpHeaderXML;
					}

					StringBuilder sb = new StringBuilder();
					sb.append(headerXML.substring(0, 8));
					if ("C026".equals(nodeId)
							&& (transId.startsWith("1") || transId
									.startsWith("2"))) {
						sb.append("<" + fieldName + ">"
								+ (body.length + header.getHLen() - 2) + "</"
								+ fieldName + ">");
					} else {
						sb.append("<" + fieldName + ">"
								+ (body.length + header.getHLen()) + "</"
								+ fieldName + ">");
					}

					sb.append(headerXML.substring(8));
					headerXML = sb.toString();

				}

				// 组报文总长度-4位报文长度信息
				if (!"N".equals(msgLen1Field)) {
					String fieldName = "HF" + msgLen1Field;
					int fieldLen = header.getFieldByName(fieldName).Len;
					if (headerXML.indexOf(fieldName) != -1) {

						String tmpHeaderXML = "";
						tmpHeaderXML += headerXML.substring(0, headerXML
								.indexOf("<" + fieldName));
						tmpHeaderXML += headerXML.substring(headerXML
								.indexOf("</" + fieldName + ">") + 6);
						headerXML = tmpHeaderXML;
					}

					StringBuilder sb = new StringBuilder();
					sb.append(headerXML.substring(0, 8));
					if ("C026".equals(nodeId)
							&& (transId.startsWith("1") || transId
									.startsWith("2"))) {
						sb
								.append("<"
										+ fieldName
										+ ">"
										+ (body.length + header.getHLen()
												- fieldLen - 2) + "</"
										+ fieldName + ">");
					} else {
						sb.append("<" + fieldName + ">"
								+ (body.length + header.getHLen() - fieldLen)
								+ "</" + fieldName + ">");
					}

					sb.append(headerXML.substring(8));
					headerXML = sb.toString();

				}

				for (int i = 0; i < 10; i++) {

					String fieldName = "HF" + (i + 1);
					if (headerXML.indexOf(fieldName) == -1) {

						StringBuilder sb = new StringBuilder();
						sb.append(headerXML.substring(0, 8));
						sb.append("<" + fieldName + ">" + " " + "</"
								+ fieldName + ">");
						sb.append(headerXML.substring(8));
						headerXML = sb.toString();
					}

				}

				// log.writeLog("第 " + Thread.currentThread().getName()
				// + " 线程 : headerXML=" + headerXML);
				if (header != null) {
					byte[] h = new byte[0];
					h = header.xmlPack(headerXML);
					// log.writeLog("head=" + ISOUtil.byte2HexStr(h, h.length));
					if ("C026".equals(nodeId)
							&& (transId.startsWith("1") || transId
									.startsWith("2"))) {
						len += h.length - 2;
					} else {
						len += h.length;
					}

					byte[] data = new byte[len];
					if ("C026".equals(nodeId)
							&& (transId.startsWith("1") || transId
									.startsWith("2"))) {// 移动接口对帐交易去掉交易码
						System.arraycopy(h, 0, data, 0, header.getLength() - 2);
						System.arraycopy(body, 0, data, h.length - 2,
								body.length);
					} else {
						System.arraycopy(h, 0, data, 0, header.getLength());
						System.arraycopy(body, 0, data, h.length, body.length);
					}
					// // log.writeLog("data="
					// + ISOUtil.byte2HexStr(data, data.length));
					// log.writeLog("packsuccess");
					// log.writeLog("end pack--------------------------");
					return data;
				}

				// log.writeLog("data=" + ISOUtil.byte2HexStr(body,
				// body.length));
				// log.writeLog("packsuccess");
				// log.writeLog("end pack--------------------------");
				return body;
			}
		}
		else {
			// log.writeLog("end pack--------------------------");
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
