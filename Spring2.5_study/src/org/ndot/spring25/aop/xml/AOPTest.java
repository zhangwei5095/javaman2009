package org.ndot.spring25.aop.xml;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 *
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 *
 * �ļ����� AOPTest.java
 * 
 *<P>
 *
 * �� ��:
 * 
 *
 *<P>
 *
 *
 * �� ��: SunJincheng
 * 
 *<P>
 *
 * ����ʱ��: 2009-9-16
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
	 * ������������
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
	 * ����After-Throwing
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