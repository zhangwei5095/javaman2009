package org.ndot.spring25.junit;

import org.ndot.spring25.People;
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
 * �ļ����� SimpleTester.java
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
