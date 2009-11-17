package org.ndot.simples.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：NDotCommPlat
 * 
 *<P>
 * 
 * 文件名： SpringMinaStart.java
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
 * 创建时间: 2009-11-17
 * 
 */
public class SpringMinaStart {

	/**
	 * <p>
	 * 功能:
	 * 
	 * <p>
	 * 
	 * @param args
	 *            <p>
	 *            作者:孙金城
	 *            <p>
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}

}
