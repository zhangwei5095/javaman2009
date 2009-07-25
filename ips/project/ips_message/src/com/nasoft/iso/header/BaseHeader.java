/*
 * Copyright (c) 2001 jPOS.org.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *    "This product includes software developed by the jPOS project 
 *    (http://www.jpos.org/)". Alternately, this acknowledgment may 
 *    appear in the software itself, if and wherever such third-party 
 *    acknowledgments normally appear.
 *
 * 4. The names "jPOS" and "jPOS.org" must not be used to endorse 
 *    or promote products derived from this software without prior 
 *    written permission. For written permission, please contact 
 *    license@jpos.org.
 *
 * 5. Products derived from this software may not be called "jPOS",
 *    nor may "jPOS" appear in their name, without prior written
 *    permission of the jPOS project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
 * IN NO EVENT SHALL THE JPOS PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the jPOS Project.  For more
 * information please see <http://www.jpos.org/>.
 */

package com.nasoft.iso.header;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.jdom.Element;
import org.ndot.ips.db.pojo.PIsoDefine;
import org.ndot.ips.db.pojo.PIsoDefineId;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.nasoft.iso.IPSDataTypes;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOHeader;
import com.nasoft.iso.ISOUtil;
import com.nasoft.util.EBCaASCtransfer;
import com.nasoft.util.Utilities;

public class BaseHeader extends DefaultHandler implements ISOHeader {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8574052426956386388L;

	protected HashMap DEFINE = new LinkedHashMap();

	protected byte[] header;

	protected Stack stk;

	// private XMLReader reader = null;

	protected int LENGTH = 46;

	protected String idenStr = "";

	public void initDefine() {

	}

	public void setField(String name, int index, String type, int len) {
		DEFINE.put(name, new BaseHeader.HeaderField(len, type, null, name,
				index));
	}

	/**
	 * 
	 * 固定报文报文头和报文体的每一个域的配置 其中报文头只要配置以下几个域： NodeID FieldType ReqRsp FieldNo
	 * FieldName DataType DataLen IdenFlag
	 * 
	 */
	public class HeaderField {
		public int Len;

		private String Type;

		private String Name;

		private int Index;

		private int StartPos = 0;

		private String idenFlag = null;

		public byte[] getValue() {
			byte[] value = new byte[Len];
			System.arraycopy(header, StartPos, value, 0, Len);
			return value;
		}

		public void setValue(byte[] value) {
			if (value != null) {
				System.arraycopy(value, 0, header, StartPos,
						value.length > Len ? Len : value.length);
			}
		}

		public HeaderField(int len, String type, byte[] value, int index) {
			this.Len = len;
			this.Type = type;
			this.Index = index;
		}

		public HeaderField(int len, String type, byte[] value, String name,
				int index) {
			this.Len = len;
			this.Type = type;
			this.Name = name;
			this.Index = index;
		}

		public HeaderField(int len, String type, byte[] value, String name,
				int index, String idenFlag) {
			this.Len = len;
			this.Type = type;
			this.Name = name;
			this.Index = index;
			this.idenFlag = idenFlag;
		}

	}

	public int getHLen() {
		return LENGTH;
	}

	public void setHLen(int len) {
		LENGTH = len;
	}

	public BaseHeader(byte[] h) throws ISOException {
		this();
		unpack(h);
	}

	public BaseHeader(Element node, String nodeId) throws Exception {
		initDefine(nodeId, node);
		this.calcStartPos();
		header = new byte[getHLen()];
	}

	public BaseHeader(Element node, String nodeId, String reqRsp)
			throws Exception {
		initDefine(nodeId, node, reqRsp);
		this.calcStartPos();
		header = new byte[getHLen()];
	}

	public BaseHeader(List<PIsoDefine> nodeInfoList) {
		int len = 0;
		for (PIsoDefine isoDefine : nodeInfoList) {
			if ("0".equals(isoDefine.getId().getFieldtype())) {
				PIsoDefineId id = isoDefine.getId();
				// FieldType 0 - 报文头，1 - 报文体
				String fieldNo;
				/*
				 * 报文头： 0 - String（右补空格） 1 - int（byte转成int） 2 - hex（byte转成16进制）
				 * 3 - number（左补0） 4 - ebcdicl(ebcdic码 左补0) 5 - ebcdicr(ebcdic码
				 * 右补0空格)
				 * 
				 * 报文体： 0 - ASCII字符 1 - BCD码 2 - EBDC码 3 - Binary二进制
				 * 
				 */
				String dataType;
				String dataLen;
				String fieldName;

				fieldNo = id.getFieldno();
				fieldName = isoDefine.getFieldname();
				dataLen = isoDefine.getDatalen();
				dataType = isoDefine.getDatatype();

				if (null == fieldNo || "".equals(fieldNo)) {
					fieldNo = "-1";
				}

				if (null == fieldName || "".equals(fieldName)) {
					fieldName = fieldNo;
				}

				if (null == dataType || "".equals(dataType)) {
					dataType = "0";
				}
				if (null == dataLen || "".equals(dataLen)) {
					dataLen = "0";
				}

				String dt = "string";
				switch (dataType.charAt(0)) {
				case '0':
					dt = "string";
					break;
				case '1':
					dt = "int";
					break;
				case '2':
					dt = "hex";
					break;
				case '3':
					dt = "number";
					break;
				case '4':
					dt = "ebcdicl";
					break;
				case '5':
					dt = "ebcdicr";
					break;
				}
				len += Integer.parseInt(dataLen);
				HeaderField hf = new HeaderField(Integer.parseInt(dataLen), dt,
						null, fieldName, Integer.parseInt(fieldNo));
				DEFINE.put(fieldName, hf);
			}
		}

		this.LENGTH = len;
		this.calcStartPos();
		header = new byte[getHLen()];

	}

	/**
	 * TODO 固定报文报文头初始化
	 * 
	 * @param nodeId
	 *            节点编号
	 * @param node
	 *            xml节点
	 * @param reqRsp
	 *            请求应答标志
	 * @throws Exception
	 */
	private void initDefine(String nodeId, Element node, String reqRsp)
			throws Exception {
		// String strXpath = "//Node[NodeID="+nodeId+" and FieldType=0]";
		List nodeList = node.getChildren();
		HashMap tmpMap = new HashMap();
		int len = 0;
		int iLen = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			Element e = (Element) nodeList.get(i);

			if (nodeId.equals(e.getChild("NodeID", node.getNamespace())
					.getValue())
					&& reqRsp.equals(e.getChild("ReqRsp", node.getNamespace())
							.getValue())
					&& "0".equals(e.getChild("FieldType", node.getNamespace())
							.getValue())) {
				String fieldNo;
				String dataType;
				String dataLen;
				String fieldName;
				String IdenFlag;

				fieldNo = Utilities.getXmlNodeValue(e.getChild("FieldNo", node
						.getNamespace()), "-1");
				fieldName = Utilities.getXmlNodeValue(e.getChild("FieldName",
						node.getNamespace()), "HF" + fieldNo);
				dataType = Utilities.getXmlNodeValue(e.getChild("DataType",
						node.getNamespace()), "0");
				dataLen = Utilities.getXmlNodeValue(e.getChild("DataLen", node
						.getNamespace()), "0");
				IdenFlag = Utilities.getXmlNodeValue(e.getChild("IdenFlag",
						node.getNamespace()), "0");
				String dt = "string";
				if ("1".equals(IdenFlag)) {
					iLen += Integer.parseInt(dataLen);
				}
				switch (dataType.charAt(0)) {
				case '0':
					dt = "string";
					break;
				case '1':
					dt = "int";
					break;
				case '2':
					dt = "hex";
					break;
				case '3':
					dt = "number";
					break;

				case '4':
					dt = "ebcdicl";
					break;
				case '5':
					dt = "ebcdicr";
					break;
				}
				len += Integer.parseInt(dataLen);
				HeaderField hf = new HeaderField(Integer.parseInt(dataLen), dt,
						null, fieldName, Integer.parseInt(fieldNo), IdenFlag);
				tmpMap.put(fieldNo, hf);
			}

		}
		this.LENGTH = len;

		/*
		 * HF begin with 1
		 */
		for (int i = 0; i < tmpMap.size(); i++) {
			HeaderField hf = (HeaderField) tmpMap.get(String.valueOf(i + 1));
			DEFINE.put(hf.Name, hf);
		}

	}

	private void initDefine(String nodeId, Element node) throws Exception {
		// String strXpath = "//Node[NodeID="+nodeId+" and FieldType=0]";
		List nodeList = node.getChildren();
		HashMap tmpMap = new HashMap();
		int len = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			Element e = (Element) nodeList.get(i);
			if (nodeId.equals(e.getChild("NodeID", node.getNamespace())
					.getValue())
					&& "0".equals(e.getChild("FieldType", node.getNamespace())
							.getValue())) {
				String fieldNo;
				String dataType;
				String dataLen;
				String fieldName;

				fieldNo = Utilities.getXmlNodeValue(e.getChild("FieldNo", node
						.getNamespace()), "-1");
				fieldName = Utilities.getXmlNodeValue(e.getChild("FieldName",
						node.getNamespace()), "HF" + fieldNo);
				dataType = Utilities.getXmlNodeValue(e.getChild("DataType",
						node.getNamespace()), "0");
				dataLen = Utilities.getXmlNodeValue(e.getChild("DataLen", node
						.getNamespace()), "0");
				String dt = "string";
				switch (dataType.charAt(0)) {
				case '0':
					dt = "string";
					break;
				case '1':
					dt = "int";
					break;
				case '2':
					dt = "hex";
					break;
				case '3':
					dt = "number";
					break;
				case '4':
					dt = "ebcdicl";
					break;
				case '5':
					dt = "ebcdicr";
					break;
				}
				len += Integer.parseInt(dataLen);
				HeaderField hf = new HeaderField(Integer.parseInt(dataLen), dt,
						null, fieldName, Integer.parseInt(fieldNo));
				tmpMap.put(fieldNo, hf);
			}

		}
		this.LENGTH = len;
		/*
		 * HF begin with 1
		 * 
		 */
		for (int i = 0; i < tmpMap.size(); i++) {
			HeaderField hf = (HeaderField) tmpMap.get(String.valueOf(i + 1));
			DEFINE.put(hf.Name, hf);
		}

	}

	public BaseHeader() throws ISOException {
		this.initDefine();
		this.calcStartPos();
		header = new byte[getHLen()];
	}

	public void calcStartPos() {
		Object[] o = DEFINE.values().toArray();
		int start = 0;
		for (int i = 0; i < o.length; i++) {
			HeaderField hf = (HeaderField) o[i];
			hf.StartPos = start;
			start += hf.Len;
		}
	}

	public byte[] pack() {
		return header;
	}

	public int unpack(byte[] h) {
		if (header == null) {
			this.header = new byte[LENGTH];
		}
		if (h != null) {
			System.arraycopy(h, 0, this.header, 0, LENGTH);
		}
		return header.length;
	}

	public Map<String, IPSHeaderField> mapUnpack(byte[] h) throws ISOException {
		Map<String, IPSHeaderField> headers = new HashMap<String, IPSHeaderField>();
		if (header == null) {
			this.header = new byte[LENGTH];
		}
		if (h != null) {
			System.arraycopy(h, 0, this.header, 0, LENGTH);
		}
		try {
			Object[] o = DEFINE.values().toArray();
			for (int i = 0; i < o.length; i++) {
				HeaderField hf = (HeaderField) o[i];
				IPSHeaderField ipsHeaderField = new IPSHeaderField(hf.Index);
				if (IPSDataTypes.INT.equalsIgnoreCase(hf.Type)) {
					ipsHeaderField.setType(IPSDataTypes.INT);
					ipsHeaderField
							.setValue(ISOUtil.bytes2IntStr(hf.getValue()));
					// IPSTODO idenStr有什么用处呢？
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += ISOUtil.bytes2IntStr(hf.getValue());
					}

				} else if (IPSDataTypes.HEX.equalsIgnoreCase(hf.Type)) {
					ipsHeaderField.setType(IPSDataTypes.HEX);
					ipsHeaderField.setValue(ISOUtil.hexString(hf.getValue()));
					// IPSTODO idenStr有什么用处呢？
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += ISOUtil.hexString(hf.getValue());
					}
				} else if (IPSDataTypes.BINARY.equals(hf.Type)) {
					ipsHeaderField.setType(IPSDataTypes.BINARY);
					ipsHeaderField.setValue(ISOUtil.byte2HexStr(hf.getValue(),
							hf.getValue().length));
					// IPSTODO idenStr有什么用处呢？
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += ISOUtil.byte2HexStr(hf.getValue(), hf
								.getValue().length);
					}
				} else if (IPSDataTypes.NUMBER.equals(hf.Type)) {
					ipsHeaderField.setType(IPSDataTypes.NUMBER);
					String unpacked = new String(hf.getValue());
					unpacked = String.valueOf(Integer.parseInt(unpacked));
					ipsHeaderField.setValue(unpacked);
					// IPSTODO idenStr有什么用处呢？
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += unpacked;
					}
				} else if (IPSDataTypes.EBCDICL.equals(hf.Type)
						|| IPSDataTypes.EBCDICR.equals(hf.Type)) {
					ipsHeaderField.setType(IPSDataTypes.EBCDIC);
					try {
						byte[] ascValue = EBCaASCtransfer.pub_base_EBCtoASC(hf
								.getValue(), hf.getValue().length);
						String unpacked = new String(ascValue);
						ipsHeaderField.setValue(unpacked);
						// IPSTODO idenStr有什么用处呢？
						if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
							this.idenStr += unpacked;
						}
					} catch (Exception e) {
						throw new ISOException(e);
					}

				} else {
					ipsHeaderField.setType(IPSDataTypes.STRING);
					ipsHeaderField.setValue(new String(hf.getValue()));
					// IPSTODO idenStr有什么用处呢？
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += new String(hf.getValue()).trim();
					}
				}
				ipsHeaderField.setStartPos(hf.StartPos);
				ipsHeaderField.setIdenFlag(hf.idenFlag);
				ipsHeaderField.setLen(hf.Len);
				ipsHeaderField.setName(hf.Name);
				headers.put(String.valueOf(ipsHeaderField.getIndex()),
						ipsHeaderField);
			}
		} catch (Exception e) {
			throw new ISOException(e);
		}
		return headers;
	}

	public byte[] xmlPack(String xml) throws ISOException {
		try {
			XMLReader reader = ISOUtil.genXmlReader();
			reader.setFeature("http://xml.org/sax/features/validation", false);
			reader.setContentHandler(this);
			reader.setErrorHandler(this);
			stk = new Stack();
			InputSource input = new InputSource(new ByteArrayInputStream(xml
					.getBytes()));
			((BaseHeader) reader.getContentHandler()).idenStr = "";
			reader.parse(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ISOException(e);
		}
		return this.header;
	}

	public String xmlUnpack() throws ISOException {
		try {
			this.idenStr = "";
			StringBuffer sb = new StringBuffer();
			sb.append("<header>");
			Object[] o = DEFINE.values().toArray();
			for (int i = 0; i < o.length; i++) {
				HeaderField hf = (HeaderField) o[i];
				sb.append("<" + hf.Name);
				if ("int".equalsIgnoreCase(hf.Type)) {
					sb.append(" type=\"int\">");
					sb.append(ISOUtil.bytes2IntStr(hf.getValue()));
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += ISOUtil.bytes2IntStr(hf.getValue());
					}

				} else if ("hex".equalsIgnoreCase(hf.Type)) {
					sb.append(" type=\"hex\">");
					sb.append(ISOUtil.hexString(hf.getValue()));
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += ISOUtil.hexString(hf.getValue());
					}
				} else if ("binary".equals(hf.Type)) {
					sb.append(" type=\"binary\">");
					sb.append(ISOUtil.byte2HexStr(hf.getValue(),
							hf.getValue().length));
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += ISOUtil.byte2HexStr(hf.getValue(), hf
								.getValue().length);
					}
				} else if ("number".equals(hf.Type)) {

					sb.append(" type=\"number\">");
					String unpacked = new String(hf.getValue());
					unpacked = String.valueOf(Integer.parseInt(unpacked));
					sb.append(unpacked);
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += unpacked;
					}
				} else if ("ebcdicl".equals(hf.Type)
						|| "ebcdicr".equals(hf.Type)) {

					sb.append(" type=\"ebcdic\">");
					try {
						byte[] ascValue = EBCaASCtransfer.pub_base_EBCtoASC(hf
								.getValue(), hf.getValue().length);
						String unpacked = new String(ascValue);
						sb.append(unpacked);
						if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
							this.idenStr += unpacked;
						}
					} catch (Exception e) {
						throw new ISOException(e);
					}

				} else {
					sb.append(">");
					sb.append(new String(hf.getValue()));
					if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
						this.idenStr += new String(hf.getValue()).trim();
					}
				}
				sb.append("</" + hf.Name + ">");
			}
			sb.append("</header>");
			return sb.toString();
		} catch (Exception e) {
			throw new ISOException(e);
		}
	}

	public void dump(PrintStream p, String indent) {
		if (header != null) {
			p.println(indent + "<header>" + ISOUtil.hexString(header)
					+ "</header>");
		}
	}

	public Object clone() {
		try {
			BaseHeader o = (BaseHeader) super.clone();
			o.header = new byte[getHLen()];
			return o;

		} catch (Exception e) {
			return null;
		}

	}

	public int getLength() {
		return getHLen();
	}

	public String getStrByName(String name) {
		return DEFINE.containsKey(name) ? new String(((HeaderField) DEFINE
				.get(name)).getValue()) : "";
	}

	public HeaderField getFieldByName(String name) {
		return DEFINE.containsKey(name) ? (HeaderField) DEFINE.get(name) : null;
	}

	public byte[] getBytesByName(String name) {
		return DEFINE.containsKey(name) ? ((HeaderField) DEFINE.get(name))
				.getValue() : null;
	}

	public void setBtyesValueByName(String name, byte[] value) {
		if (DEFINE.containsKey(name) || value != null) {
			HeaderField hf = (HeaderField) DEFINE.get(name);
			hf.setValue(value);
		}
	}

	public String getNameByIndex(int index) {
		Object[] o = DEFINE.values().toArray();
		if (index > 0 && index <= o.length) {
			HeaderField hf = (HeaderField) o[index - 1];
			return hf.Name;
		} else {
			return "";
		}
	}

	public int getIndexByName(String name) {
		return DEFINE.containsKey(name) ? ((HeaderField) DEFINE.get(name)).Index
				: -1;
	}

	/** ******************************************************************** */
	/*
	 * SAX Handler API
	 */
	public void startElement(String ns, String name, String qName,
			Attributes atts) throws SAXException {
		stk.push(name);
	}

	public void characters(char[] p0, int p1, int p2) throws SAXException {
		try {
			// 从栈中得到当前节点的信息
			String name = (String) stk.peek();
			String text = new String(p0, p1, p2);
			byte[] bvalue = text.getBytes();
			if (DEFINE.containsKey(name)) {
				HeaderField hf = (HeaderField) DEFINE.get(name);

				if (hf.idenFlag != null && "1".equals(hf.idenFlag)) {
					this.idenStr += text.trim();
				}
				String type = hf.Type;
				if (type == null || type == "") {
					type = "string";
				}

				if ("int".equalsIgnoreCase(type)) {
					hf.setValue(ISOUtil.int2Bytes(Integer.parseInt(text),
							hf.Len));
				} else if ("hex".equalsIgnoreCase(type)) {
					hf.setValue(ISOUtil.hex2byte(bvalue, 0, hf.Len));
				} else if ("number".equals(type)) {
					String txt = ISOUtil
							.int2Str(Integer.parseInt(text), hf.Len);
					hf.setValue(ISOUtil.str2FixedLenBytes(txt, hf.Len,
							(byte) '0'));
				} else if ("ebcdicr".equals(type)) {// 右补空格
					byte[] ascByte = ISOUtil.str2FixedLenBytes(text, hf.Len,
							(byte) ' ');

					byte[] ebcdByte = EBCaASCtransfer.pub_base_ASCtoEBC(
							ascByte, ascByte.length);
					hf.setValue(ebcdByte);
				} else if ("ebcdicl".equals(type)) {// 左补0
					String txt = ISOUtil
							.int2Str(Integer.parseInt(text), hf.Len);
					byte[] ascByte = ISOUtil.str2FixedLenBytes(txt, hf.Len,
							(byte) '0');

					byte[] ebcdByte = EBCaASCtransfer.pub_base_ASCtoEBC(
							ascByte, ascByte.length);
					hf.setValue(ebcdByte);
				} else {
					hf.setValue(ISOUtil.str2FixedLenBytes(text, hf.Len,
							(byte) ' '));
				}
			}
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

	public void endElement(String p0, String p1, String p2) throws SAXException {
		if (!stk.empty()) {
			stk.pop();
		}
	}

	/** ******************************************************************** */
	/**
	 * @return the idenStr
	 */
	public String getIdenStr() {
		return idenStr;
	}

	/**
	 * @param idenStr
	 *            the idenStr to set
	 */
	public void setIdenStr(String idenStr) {
		this.idenStr = idenStr;
	}
}
