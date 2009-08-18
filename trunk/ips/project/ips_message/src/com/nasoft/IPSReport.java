package com.nasoft;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ndot.ips.util.FormatStrings;

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
		body = new ISOMsg();
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
		IPSHeaderField tem;
		for (int i = 1; i <= 10; i++) {
			tem = header.get(String.valueOf(i));
			if (null != tem) {
				xmlHeader.append("<" + tem.getName() + ">" + tem.getValue()
						+ "</" + tem.getName() + ">");
			} else {
				String fieldName = "HF" + i;
				xmlHeader.append("<" + fieldName + ">" + " " + "</" + fieldName
						+ ">");
			}
		}

		xmlHeader.append("</header>");
		return xmlHeader.toString();

	}

	/**
	 * 
	 * @return macbuffer
	 * @throws ISOException
	 */
	public String getMacbuf() throws ISOException {
		String BF1 = this.getFieldValue(0);
		String BF2 = this.getFieldValue(2);
		String BF3 = this.getFieldValue(3);
		String BF4 = this.getFieldValue(4);
		String BF11 = this.getFieldValue(11);
		String BF12 = this.getFieldValue(12);
		String BF13 = this.getFieldValue(13);
		String BF39 = this.getFieldValue(39);
		String BF102 = this.getFieldValue(102);
		String BF103 = this.getFieldValue(103);
		String macBuf = FormatStrings.genMacBuffer(BF1, BF2, BF3, BF4, BF11,
				BF12, BF13, BF39, BF102, BF103);
		return macBuf;
	}
}
