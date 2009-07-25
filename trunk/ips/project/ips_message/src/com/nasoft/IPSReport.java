package com.nasoft;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.IPSHeaderField;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSReport.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-23
 * 
 */
public class IPSReport {
	Map<String, IPSHeaderField> header;
	ISOMsg body;

	public IPSReport(Map<String, IPSHeaderField> header, ISOMsg body) {
		this.header = header;
		this.body = body;
	}

	public IPSReport() {
		super();
		body = new ISOMsg(0);
		header = new HashMap<String, IPSHeaderField>();
	}

	public Map<String, IPSHeaderField> getHeader() {
		return header;
	}

	public void setHeader(Map<String, IPSHeaderField> header) {
		this.header = header;
	}

	public ISOMsg getBody() {
		return body;
	}

	public void setBody(ISOMsg body) {
		this.body = body;
	}

	/**
	 * 
	 * @param fieldNo
	 * @return 获得指报文体定域的值信息
	 * @throws ISOException
	 */
	public String getFieldValue(int fieldNo) throws ISOException {
		Object vobj = body.getValue(fieldNo);
		if (null == vobj) {
			return "";
		}

		if (vobj instanceof byte[]) {
			byte[] b = (byte[]) vobj;
			return ISOUtil.byte2HexNoSpaceStr(b, b.length);
		}

		return (String) vobj;
	}

	/**
	 * 
	 * @param fieldNo
	 * @return 获得指定报文头域的值信息
	 * @throws ISOException
	 */
	public String getHeaderFieldValue(int fieldNo) throws ISOException {
		IPSHeaderField hf = header.get(String.valueOf(fieldNo));
		String value = hf.getValue();
		return null == value ? "" : value;
	}

	/**
	 * 
	 * @param fieldNo
	 * @param value
	 * @throws ISOException
	 */
	public void setHeaderFieldValue(int fieldNo, String value)
			throws ISOException {
		header.put(String.valueOf(fieldNo), new IPSHeaderField(fieldNo, value));
	}

	/**
	 * 
	 * @param fieldNo
	 * @param value
	 * @throws ISOException
	 */
	public void setFieldValue(int fieldNo, String value) throws ISOException {
		setFieldValue(fieldNo, value, IPSReportFieldType.IPS_STRING);
	}

	/**
	 * 
	 * @param fieldNo
	 * @param value
	 * @throws ISOException
	 */
	public void setFieldValue(int fieldNo, Object value, String type)
			throws ISOException {
		if (IPSReportFieldType.IPS_STRING.equalsIgnoreCase(type)) {
			body.set(fieldNo, (String) value);
		}
		if (IPSReportFieldType.IPS_BINARY.equalsIgnoreCase(type)) {
			body.set(fieldNo, (byte[]) value);
		}
	}

	/**
	 * 
	 * @param headers
	 *            <key,value>形式的报文头信息
	 * @return Xml格式的报文头信息
	 */
	public String formatToXml() {

		StringBuffer xmlHeader = new StringBuffer();
		xmlHeader.append("<header>");
		Set<String> keys = header.keySet();
		IPSHeaderField tem;
		for (String key : keys) {
			tem = header.get(key);
			xmlHeader.append("<" + tem.getName() + ">" + tem.getValue() + "</"
					+ tem.getName() + ">");
		}
		xmlHeader.append("</header>");
		return xmlHeader.toString();

	}
}
