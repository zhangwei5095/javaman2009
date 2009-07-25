package com.nasoft;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOMsg;
import com.nasoft.iso.ISOUtil;
import com.nasoft.iso.header.IPSHeaderField;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSReport.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-23
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
	 * @return ���ָ�����嶨���ֵ��Ϣ
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
	 * @return ���ָ������ͷ���ֵ��Ϣ
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
	 *            <key,value>��ʽ�ı���ͷ��Ϣ
	 * @return Xml��ʽ�ı���ͷ��Ϣ
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
