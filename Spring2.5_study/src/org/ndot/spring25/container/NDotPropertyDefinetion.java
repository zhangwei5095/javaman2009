package org.ndot.spring25.container;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： NDotPropertyDefinetion.java
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
 * 创建时间: 2009-8-20
 * 
 */
public class NDotPropertyDefinetion {
	private String name;
	private String refBean;
	private String value;

	public String getValue() {
		return value;
	}

	public NDotPropertyDefinetion(String name, String refBean, String value) {
		super();
		this.name = name;
		this.refBean = refBean;
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefBean() {
		return refBean;
	}

	public void setRefBean(String refBean) {
		this.refBean = refBean;
	}

}
