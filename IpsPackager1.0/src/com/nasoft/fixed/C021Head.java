package com.nasoft.fixed;

public class C021Head {
	private String xmlHeader;
	private int headLen;
	private String idenStr;
	private byte[] byteHeader;
	/**
	 * @return the headLen
	 */
	public int getHeadLen() {
		return headLen;
	}
	/**
	 * @param headLen the headLen to set
	 */
	public void setHeadLen(int headLen) {
		this.headLen = headLen;
	}
	/**
	 * @return the idenStr
	 */
	public String getIdenStr() {
		return idenStr;
	}
	/**
	 * @param idenStr the idenStr to set
	 */
	public void setIdenStr(String idenStr) {
		this.idenStr = idenStr;
	}
	/**
	 * @return the xmlHead
	 */
	public String getXmlHead() {
		return xmlHeader;
	}
	/**
	 * @param xmlHead the xmlHead to set
	 */
	public void setXmlHead(String xmlHead) {
		this.xmlHeader = xmlHead;
	}
	/**
	 * @return the byteHeader
	 */
	public byte[] getByteHeader() {
		return byteHeader;
	}
	/**
	 * @param byteHeader the byteHeader to set
	 */
	public void setByteHeader(byte[] byteHeader) {
		this.byteHeader = byteHeader;
	}
	

}
