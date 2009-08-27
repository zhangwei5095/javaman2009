package org.ndot.spring25.junit;

import org.junit.Test;
import org.ndot.spring25.People;
import org.ndot.spring25.container.NDotSimpleXMLApplicationContext;
import org.ndot.spring25.instance.Canada;
import org.ndot.spring25.instance.Chinise;
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
 * 文件名： Spring25Tester.java
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
public class Spring25Tester {
	/**
	 * 简单的测试容器的创建和bean 的获取
	 */
	@Test
	public void simpleTest() {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "beginApplicationContext.xml" });
			People p = (People) ctx.getBean("people");
			System.out.println(p.getName());
			System.out.println(p.getAddress().getCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：利用自定义的IOC容器管理bean
	 * 
	 *<P>
	 */
	@Test
	public void ndotSimpleXMLApplicationContextTest() {
		try {
			NDotSimpleXMLApplicationContext ctx = new NDotSimpleXMLApplicationContext(
					new String[] { "beginApplicationContext.xml" });
			People p = (People) ctx.getBean("people");
			System.out.println(p.getName());
			System.out.println(p.getAddress().getCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 功 能：测试实例化bean的三种方式
	 * 
	 *<P>
	 */

	@Test
	public void beanInstanceTest() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "beanInstanceApplicationContext.xml" });
		Canada canada = (Canada) ctx.getBean("canada");
		canada.say();
		
		Chinise chinise = (Chinise) ctx.getBean("chinise");
		chinise.say();

		Canada fcanada = (Canada) ctx.getBean("fcanada");
		fcanada.say();
		
		Chinise sfchinise = (Chinise) ctx.getBean("sfchinise");
		sfchinise.say();
	}
}
