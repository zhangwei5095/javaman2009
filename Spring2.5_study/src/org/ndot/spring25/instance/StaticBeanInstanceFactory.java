package org.ndot.spring25.instance;

import org.ndot.spring25.People;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： StaticBeanInstanceFactory.java
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
 * 创建时间: 2009-8-24
 * 
 */
public class StaticBeanInstanceFactory {
	public static People createPeople(String type) {
		if ("chinise".equals(type)) {
			return new Chinise("静态工厂实例化bean");
		} else if ("canada".equals(type)) {
			return new Canada("静态工厂实例化bean");
		} else {
			return new People();
		}
	}
}
