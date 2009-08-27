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
 * 文件名： Chinise.java
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
public class Chinise extends People {

	public Chinise(String msg) {
		this.setMsg(msg);
	}

	@Override
	public void say() {
		System.out.println("中国人说：" + getMsg());
	}

}
