package org.ndot.spring25.junit;

import org.ndot.spring25.People;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： SimpleTester.java
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
public class SimpleTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx;

		try {
			ctx = new ClassPathXmlApplicationContext(
					new String[] { "applicationContext.xml" });

			People p = (People) ctx.getBean("people");
			System.out.println(p.getName());
			System.out.println(p.getAddress().getCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
