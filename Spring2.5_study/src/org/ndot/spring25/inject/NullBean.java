package org.ndot.spring25.inject;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： NullBean.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-8-27
 * 
 */
public class NullBean {
	private String nullValue;
	private String emptyStr;
	public String getNullValue() {
		return nullValue;
	}
	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}
	public String getEmptyStr() {
		return emptyStr;
	}

	public void setEmptyStr(String emptyStr) {
		this.emptyStr = emptyStr;
	}
}
