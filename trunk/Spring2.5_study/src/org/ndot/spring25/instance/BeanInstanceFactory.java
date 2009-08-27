package org.ndot.spring25.instance;

import java.util.List;

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
 * 文件名： BeanInstanceFactory.java
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
public class BeanInstanceFactory {
	public People createPeople(String type, String msg) {
		if ("chinise".equals(type)) {
			return new Chinise(msg);
		} else if ("canada".equals(type)) {
			return new Canada(msg);
		} else {
			return new People();
		}
	}
}
