package org.ndot.spring25.container;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� NDotPropertyDefinetion.java
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
 * ����ʱ��: 2009-8-20
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
