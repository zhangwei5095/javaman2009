package org.ndot.spring25.aop.annotation;

import org.ndot.spring25.aop.xml.AService;
import org.ndot.spring25.aop.xml.BServiceImpl;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 *
 * 项目名称：Spring2.5_study
 * 
 *<P>
 *
 * 文件名： AOPTest.java
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
 * 创建时间: 2009-9-16
 * 
 */
public class AOPTest extends AbstractDependencyInjectionSpringContextTests {

	private HelloWorld helloWorld;
	public HelloWorld getHelloWorld() {
		return helloWorld;
	}


	public void setHelloWorld(HelloWorld helloWorld) {
		this.helloWorld = helloWorld;
	}


	protected String[] getConfigLocations() {
		String[] configs = new String[] { "/aopAnnotationApplicationContext.xml"};
		return configs;
	}
	
	
	/**
	 * 测试正常调用
	 */
	public void testCall()
	{
		System.out.println("SpringTest JUnit test");
		helloWorld.sayHello("NDot");
	}

	/**
	 * 测试正常调用
	 */
	public void testCallWithException()
	{
		System.out.println("SpringTest JUnit test");
		helloWorld.sayHelloWithException("NDot");
	}
	
	
	
}