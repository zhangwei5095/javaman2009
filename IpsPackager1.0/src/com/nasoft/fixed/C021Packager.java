package com.nasoft.fixed;

import java.io.StringReader;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.nasoft.PackagerFactory;
import com.nasoft.Utilities;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOFieldPackager;
import com.nasoft.iso.ISOUtil;

public class C021Packager {
	public static void main(String args[]) {
		try {
			String xml = "<body><HF1>11</HF1><HF2>0022</HF2><HF3>3 </HF3><HF4>4</HF4><HF5>R       </HF5></body>";
			// byte [] bb=packHeader(xml,"req");
			// System.out.println(ISOUtil.byte2HexNoSpaceStr(bb, bb.length));
			// String head=unpackHeader(bb,"req");
			// System.out.println(head);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public  C021Head unpackHeader(byte[] buf, String reqRsp)
			throws Exception {
		C021Head head = new C021Head();
		String idenStr = "";
		int offset = 0;
		String hf1 = "";
		String hf2 = "";
		String hf3 = "";
		String hf4 = "";
		String hf5 = "";
		if ("req".equals(reqRsp)) {
			byte[] b1 = new byte[1];
			System.arraycopy(buf, 0, b1, 0, 1);
			hf1 = ISOUtil.byte2HexNoSpaceStr(b1, 1);
			offset += 1;
			hf2 = new String(buf, offset, 4);
			offset += 4;
			hf3 = new String(buf, offset, 2);
			offset += 2;
			hf4 = new String(buf, offset, 1);
			offset += 1;
			hf5 = new String(buf, offset, 1);
			idenStr = hf5.trim();
			if ("C".equals(hf5) || ("Q".equals(hf5))) {
				offset += 1;

			} else {
				hf5 = hf5 + "       ";
				offset += 8;
			}

		} else {
			hf1 = new String(buf, offset, 1);
			idenStr = hf1.trim();
			if ("C".equals(hf1) || ("Q".equals(hf1))) {
				offset += 1;
			} else {
				hf1 = hf1 + "       ";
				offset += 8;
			}
			byte[] b1 = new byte[1];
			System.arraycopy(buf, offset, b1, 0, 1);
			hf2 = ISOUtil.byte2HexNoSpaceStr(b1, 1);
			offset += 1;

			hf3 = new String(buf, offset, 4);
			offset += 4;

		}

		head.setHeadLen(offset);
		head.setIdenStr(idenStr);

		StringBuffer resultXml = new StringBuffer();

		resultXml.append("<HF1>");
		resultXml.append(hf1);
		resultXml.append("</HF1>");

		resultXml.append("<HF2>");
		resultXml.append(hf2);
		resultXml.append("</HF2>");

		resultXml.append("<HF3>");
		resultXml.append(hf3);
		resultXml.append("</HF3>");

		if ("req".equals(reqRsp)) {
			resultXml.append("<HF4>");
			resultXml.append(hf4);
			resultXml.append("</HF4>");

			resultXml.append("<HF5>");
			resultXml.append(hf5);
			resultXml.append("</HF5>");
		}

		head.setXmlHead(resultXml.toString());

		return head;
	}

	public  C021Head packHeader(String xmlHeader, String reqRsp)
			throws Exception {
		C021Head head = new C021Head();
		byte[] buf = new byte[0];
		String idenStr = "";
		int length = 0;
		// System.out.println("xmlHeader="+xmlHeader);
		SAXBuilder sb = new SAXBuilder();
		StringReader sr = new StringReader(xmlHeader);

		Document doc = sb.build(sr);
		Element root = doc.getRootElement();
		String hf1 = Utilities.getXmlNodeValue(root.getChild("HF1", root
				.getNamespace()), "");
		String hf2 = Utilities.getXmlNodeValue(root.getChild("HF2", root
				.getNamespace()), "");
		String hf3 = Utilities.getXmlNodeValue(root.getChild("HF3", root
				.getNamespace()), "");
		String hf4 = Utilities.getXmlNodeValue(root.getChild("HF4", root
				.getNamespace()), "");
		String hf5 = Utilities.getXmlNodeValue(root.getChild("HF5", root
				.getNamespace()), "");
		String transType = "";
		if ("req".equals(reqRsp)) {
			transType = hf5.trim();
		} else {
			transType = hf1.trim();
		}
		int transLen = 0;
		int offset = 0;
		if ("req".equals(reqRsp)) {
			if ("C".equals(transType) || ("Q".equals(transType))) {
				buf = new byte[9];
				transLen = 1;
			} else {
				buf = new byte[16];
				transLen = 8;
			}
			// System.out.println("hf1="+hf1);
			byte[] byteHf1 = ISOUtil.hex2byte("02");

			System.arraycopy(byteHf1, 0, buf, offset, 1);
			offset += 1;

			hf2 = padStr(hf2, 4, "l", "0");
			hf3 = padStr(hf3, 2, "r", " ");
			hf4 = padStr(hf4, 1, "r", " ");
			hf5 = padStr(hf5, transLen, "r", " ");
			idenStr = hf5.trim();

			System.arraycopy(hf2.getBytes(), 0, buf, offset, 4);
			offset += 4;
			System.arraycopy(hf3.getBytes(), 0, buf, offset, 2);
			offset += 2;
			System.arraycopy(hf4.getBytes(), 0, buf, offset, 1);
			offset += 1;
			System.arraycopy(hf5.getBytes(), 0, buf, offset, transLen);
			offset += transLen;

		} else {
			if ("C".equals(transType) || ("Q".equals(transType))) {
				buf = new byte[6];
				transLen = 1;
			} else {
				buf = new byte[13];
				transLen = 8;
			}
			idenStr = hf1.trim();
			hf1 = padStr(hf1, transLen, "r", " ");
			byte[] byteHf2 = ISOUtil.hex2byte("02");
			hf3 = padStr(hf3, 4, "l", "0");
			System.arraycopy(hf1.getBytes(), 0, buf, offset, transLen);
			offset += transLen;
			System.arraycopy(byteHf2, 0, buf, offset, 1);
			offset += 1;
			System.arraycopy(hf3.getBytes(), 0, buf, offset, 4);
			offset += 4;

		}
		head.setByteHeader(buf);
		head.setHeadLen(offset);
		head.setIdenStr(idenStr);

		return head;
	}

	public  byte[] pack(String nodeId, String xmlBody, String bitMapStr)
			throws Exception {

		if (bitMapStr.charAt(bitMapStr.length() - 1) == ',') {
			bitMapStr = bitMapStr.substring(0, bitMapStr.length() - 1);
		}
		String[] bitMapArray = bitMapStr.split(",");

		int fldLen = bitMapArray.length;
		FixedPackager packager = (FixedPackager) PackagerFactory
				.getFixedPackagerByNodeId(nodeId);
		ISOFieldPackager[] fld = packager.getFld();

		SAXBuilder sb = new SAXBuilder();
		StringReader sr = new StringReader(xmlBody);

		Document doc = sb.build(sr);
		Element root = doc.getRootElement();

		Vector v = new Vector();
		byte[] b;
		int len = 0;
		int pakLen = 1;
		boolean hasField5 = false;
		for (int i = 0; i < fldLen; i++) {
			int fldNo = Integer.parseInt(bitMapArray[i]);
			if (fldNo == 5) {
				hasField5 = true;
				break;
			}
		}
		for (int i = 0; i < fldLen; i++) {
			int fldNo = Integer.parseInt(bitMapArray[i]);
			ISOFieldPackager fp = fld[fldNo];
			if (fldNo != 5) {
				pakLen += fp.getLength();
			}
		}
		int fld5Len = 0;
		if (!hasField5) {
			b = new byte[pakLen];
		} else {
			String bf5 = Utilities.getXmlNodeValue(root.getChild("BF5", root
					.getNamespace()), "");
			fld5Len = bf5.length();
			b = new byte[pakLen + fld5Len];
		}

		int offset = 0;
		for (int i = 0; i < fldLen; i++) {
			try {
				int fldNo = Integer.parseInt(bitMapArray[i]);
				ISOFieldPackager fp = fld[fldNo];
				if (fp == null)
					throw new ISOException("null field packager");
				int currLen = 0;
				if (fldNo == 5) {
					currLen = fld5Len;
				} else {
					currLen = fp.getLength();
				}
				String bf = Utilities.getXmlNodeValue(root.getChild("BF"
						+ fldNo, root.getNamespace()), "");
				if (bf.length() < currLen) {
					bf = padStr(bf, currLen, "r", " ");
				}
				if (bf.length() > currLen) {
					throw new Exception("pack error field" + fldNo
							+ ", the length" + bf.length()
							+ "is too long! the max length is " + currLen);
				}
				System.arraycopy(bf.getBytes("GBK"), 0, b, offset, currLen);
				offset += currLen;

			} catch (ISOException e) {

				throw e;
			}
		}

		byte[] byteEnd = ISOUtil.hex2byte("03");
		System.arraycopy(byteEnd, 0, b, offset, 1);
		offset += 1;
		// byte[] check=new byte[1];
		// check[0]=xOrArray(b);
		// System.arraycopy(check, 0, b, offset, 1);
		// offset+=1;
		return b;
	}

	public  byte xOrArray(byte[] array) {
		byte result = 0x00;
		if (array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				result = (byte) (result ^ array[i]);
			}
		}
		return result;
	}

	public  String unpackBody(String nodeId, byte[] body,
			String bitMapStr, int offset, String type) throws Exception {
		String xmlData = "";
		if (bitMapStr.charAt(bitMapStr.length() - 1) == ',') {
			bitMapStr = bitMapStr.substring(0, bitMapStr.length() - 1);
		}
		String[] bitMapArray = bitMapStr.split(",");
		int fldLen = bitMapArray.length;
		FixedPackager packager = (FixedPackager) PackagerFactory
				.getFixedPackagerByNodeId(nodeId);
		ISOFieldPackager[] fld = packager.getFld();
		boolean hasField5 = false;
		for (int i = 0; i < fldLen; i++) {
			int fldNo = Integer.parseInt(bitMapArray[i]);
			if (fldNo == 5) {
				hasField5 = true;
				break;
			}
		}
		int pakLen = 0;

		for (int i = 0; i < fldLen; i++) {
			int fldNo = Integer.parseInt(bitMapArray[i]);
			ISOFieldPackager fp = fld[fldNo];
			if (fldNo != 5) {
				pakLen += fp.getLength();
			}
		}
		int fld5Len = 0;
		if (hasField5) {
			if ("J".equalsIgnoreCase(type)) {//对应交费时候计算详细域
				fld5Len = body.length - offset - pakLen - 4;
			} else {//对应查询时候计算详细域
				fld5Len = body.length - offset - pakLen - 2;
			}
		}

		StringBuffer resultXml = new StringBuffer();

		for (int i = 0; i < fldLen; i++) {
			int fldNo = Integer.parseInt(bitMapArray[i]);
			ISOFieldPackager fp = fld[fldNo];
			if (fldNo != 5) {
				pakLen = fp.getLength();
			} else {
				pakLen = fld5Len;
			}
			int currLen = 0;
			if (fldNo == 5) {
				currLen = fld5Len;
			} else {
				currLen = fp.getLength();
			}

			resultXml.append("<BF" + fldNo + ">");
			resultXml.append(new String(body, offset, currLen));
			resultXml.append("</BF" + fldNo + ">");
			System.out.println("current content : " + resultXml.toString());
			offset += pakLen;

		}

		return resultXml.toString();
	}

	/**
	 * 
	 * @param str
	 * @param padType
	 *            l左补，r右补
	 * @param padContent
	 * @return
	 */
	private  String padStr(String str, int padLen, String padType,
			String padContent) {
		int strLen = str.length();
		if (strLen < padLen) {
			if ("l".equals(padType)) {
				while (strLen < padLen) {
					str = padContent + str;
					strLen++;
				}
			} else if ("r".equals(padType)) {
				while (strLen < padLen) {
					str = str + padContent;
					strLen++;
				}
			}
		}
		return str;
	}

}
