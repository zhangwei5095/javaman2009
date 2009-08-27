package org.ndot.spring25.junit;

import org.junit.Test;
import org.ndot.spring25.People;
import org.ndot.spring25.container.NDotSimpleXMLApplicationContext;
import org.ndot.spring25.instance.Canada;
import org.ndot.spring25.instance.Chinise;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� Spring25Tester.java
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
 * ����ʱ��: 2009-8-20
 * 
 */
public class Spring25Tester {
	/**
	 * �򵥵Ĳ��������Ĵ�����bean �Ļ�ȡ
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
	 * �� �ܣ������Զ����IOC��������bean
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
	 * �� �ܣ�����ʵ����bean�����ַ�ʽ
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
