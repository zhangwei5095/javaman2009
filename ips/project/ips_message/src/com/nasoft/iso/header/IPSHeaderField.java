package com.nasoft.iso.header;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSHeaderFiled.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-24
 * 
 */
public class IPSHeaderField {

	private int index;
	private String name;
	private String type;
	private String value;
	private int startPos = 0;
	public int len;
	private String idenFlag = null;

	public IPSHeaderField() {

	}

	public IPSHeaderField(int index) {
		this.index = index;
	}

	public IPSHeaderField(String name) {
		this.name = name;
	}

	public IPSHeaderField(int index, String value) {
		this.index = index;
		this.name = "HF" + index;
		this.value = value;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public String getIdenFlag() {
		return idenFlag;
	}

	public void setIdenFlag(String idenFlag) {
		this.idenFlag = idenFlag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
