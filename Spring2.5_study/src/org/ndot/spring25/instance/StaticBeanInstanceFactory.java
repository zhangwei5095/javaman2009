package org.ndot.spring25.instance;

import org.ndot.spring25.People;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� StaticBeanInstanceFactory.java
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
 * ����ʱ��: 2009-8-24
 * 
 */
public class StaticBeanInstanceFactory {
	public static People createPeople(String type) {
		if ("chinise".equals(type)) {
			return new Chinise("��̬����ʵ����bean");
		} else if ("canada".equals(type)) {
			return new Canada("��̬����ʵ����bean");
		} else {
			return new People();
		}
	}
}
