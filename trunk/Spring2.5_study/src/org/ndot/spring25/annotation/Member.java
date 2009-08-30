package org.ndot.spring25.annotation;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： Member.java
 * 
 *<P>
 * 
 * 功 能:家庭成员
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-8-29
 * 
 */
public class Member {
	private String mname;
	private String msex;
	private String mrole;

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMsex() {
		return msex;
	}

	public void setMsex(String msex) {
		this.msex = msex;
	}

	public String getMrole() {
		return mrole;
	}

	public void setMrole(String mrole) {
		this.mrole = mrole;
	}
}
