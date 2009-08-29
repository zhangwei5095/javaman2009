package org.ndot.spring25.autowire;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� AutowireMainBean.java
 * 
 *<P>
 * 
 * �� ��:
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-8-29
 * 
 */
public class AutowireMainBean {
	private String simpleP;

	private AutowireBeanA autowireBeanA;
	private AutowireBeanB autowireBeanB;

	public AutowireMainBean(String simpleP, AutowireBeanA autowireBeanA,
			AutowireBeanB autowireBeanB) {
		this.simpleP = simpleP;
		this.autowireBeanA = autowireBeanA;
		this.autowireBeanB = autowireBeanB;
	}

	public AutowireMainBean(AutowireBeanA autowireBeanA,
			AutowireBeanB autowireBeanB) {
		this.autowireBeanA = autowireBeanA;
		this.autowireBeanB = autowireBeanB;
	}

	public AutowireMainBean() {
		super();
	}

	public String getSimpleP() {
		return simpleP;
	}

	public void setSimpleP(String simpleP) {
		this.simpleP = simpleP;
	}

	public AutowireBeanA getAutowireBeanA() {
		return autowireBeanA;
	}

	public void setAutowireBeanA(AutowireBeanA autowireBeanA) {
		this.autowireBeanA = autowireBeanA;
	}

	public AutowireBeanB getAutowireBeanB() {
		return autowireBeanB;
	}

	public void setAutowireBeanB(AutowireBeanB autowireBeanB) {
		this.autowireBeanB = autowireBeanB;
	}

}
