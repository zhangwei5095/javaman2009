package org.ndot.simples.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�NDotCommPlat
 * 
 *<P>
 * 
 * �ļ����� SpringMinaStart.java
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
 * ����ʱ��: 2009-11-17
 * 
 */
public class SpringMinaStart {

	/**
	 * <p>
	 * ����:
	 * 
	 * <p>
	 * 
	 * @param args
	 *            <p>
	 *            ����:����
	 *            <p>
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}

}
