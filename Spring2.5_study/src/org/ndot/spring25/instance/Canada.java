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
 * 文件名： Canada.java
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
public class Canada extends People {
	public Canada() {

	}

	public Canada(String msg) {
		setMsg(msg);
	}

	@Override
	public void say() {
		System.out.println("Canada say ：" + getMsg());
	}
}
