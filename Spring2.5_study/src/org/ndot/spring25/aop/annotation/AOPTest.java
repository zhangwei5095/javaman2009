package org.ndot.spring25.aop.annotation;

import org.ndot.spring25.aop.xml.AService;
import org.ndot.spring25.aop.xml.BServiceImpl;
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
	 * ������������
	 */
	public void testCall()
	{
		System.out.println("SpringTest JUnit test");
		helloWorld.sayHello("NDot");
	}

	/**
	 * ������������
	 */
	public void testCallWithException()
	{
		System.out.println("SpringTest JUnit test");
		helloWorld.sayHelloWithException("NDot");
	}
	
	
	
}