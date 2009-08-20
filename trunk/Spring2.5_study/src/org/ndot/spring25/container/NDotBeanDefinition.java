package org.ndot.spring25.container;

import java.util.ArrayList;
import java.util.List;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： NDotBeanDefinition.java
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
