package org.ndot.spring25.inject;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� BeanA.java
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
 * ����ʱ��: 2009-8-27
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
