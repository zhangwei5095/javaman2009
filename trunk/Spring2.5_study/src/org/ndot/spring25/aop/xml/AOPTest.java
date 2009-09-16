package org.ndot.spring25.aop.xml;

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
	
	private AService aService;
	
	private BServiceImpl bService;
	
	protected String[] getConfigLocations() {
		String[] configs = new String[] { "/aopXMLApplicationContext.xml"};
		return configs;
	}
	
	
	/**
	 * 测试正常调用
	 */
	public void testCall()
	{
		System.out.println("SpringTest JUnit test");
		aService.fooA("JUnit test fooA");
		aService.barA();
		bService.fooB();
		bService.barB("JUnit test barB",0);
	}
	
	/**
	 * 测试After-Throwing
	 */
	public void testThrow()
	{
		try {
			bService.barB("JUnit call barB",1);
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	public void setAService(AService service) {
		aService = service;
	}
	
	public void setBService(BServiceImpl service) {
		bService = service;
	}
}