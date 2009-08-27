package org.ndot.spring25.inject;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� BeanB.java
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
public class BeanB {
	private Properties propp;
	private List listp;

	public BeanB() {
		System.out.println("Create BeanB instance ......");
	}

	private Map mapp;
	private Set setp;
	private Map<String, Float> floatMap;

	public Map<String, Float> getFloatMap() {
		return floatMap;
	}

	public void setFloatMap(Map<String, Float> floatMap) {
		this.floatMap = floatMap;
	}

	public Properties getPropp() {
		return propp;
	}

	public void setPropp(Properties propp) {
		this.propp = propp;
	}

	public List getListp() {
		return listp;
	}

	public void setListp(List listp) {
		this.listp = listp;
	}

	public Map getMapp() {
		return mapp;
	}

	public void setMapp(Map mapp) {
		this.mapp = mapp;
	}

	public Set getSetp() {
		return setp;
	}

	public void setSetp(Set setp) {
		this.setp = setp;
	}

	public void say() {
		System.out.println("I'am BeanB instance......:)");
	}
}
