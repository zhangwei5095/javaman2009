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
 * 文件名： BeanA.java
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
public class BeanA {
	private String stringp;
	private int intp;
	private BeanB beanp;

	public BeanA() {

	}

	public String getStringp() {
		return stringp;
	}

	public void setStringp(String stringp) {
		this.stringp = stringp;
	}

	public int getIntp() {
		return intp;
	}

	public void setIntp(int intp) {
		this.intp = intp;
	}

	public BeanB getBeanp() {
		return beanp;
	}

	public void setBeanp(BeanB beanp) {
		this.beanp = beanp;
	}

	public BeanA(String stringp, int intp, BeanB beanp) {
		super();
		this.stringp = stringp;
		this.intp = intp;
		this.beanp = beanp;
	}

	public BeanA(String stringp, int intp) {
		super();
		this.stringp = stringp;
		this.intp = intp;
	}

	public BeanB injectBeanBInstance() {
		return null;
	}

}
