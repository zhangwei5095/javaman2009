package org.ndot.spring25.container;

import java.util.ArrayList;
import java.util.List;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� NDotBeanDefinition.java
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
public class NDotBeanDefinition {
	private String id;
	private String clazz;
	private List<NDotPropertyDefinetion> properties = new ArrayList<NDotPropertyDefinetion>();

	public List<NDotPropertyDefinetion> getProperties() {
		return properties;
	}

	public void setProperties(List<NDotPropertyDefinetion> properties) {
		this.properties = properties;
	}

	public NDotBeanDefinition(String id, String clazz) {
		this.id = id;
		this.clazz = clazz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public NDotBeanDefinition() {
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

}
