package org.ndot.spring25.instance;

import java.util.List;

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
 * �ļ����� BeanInstanceFactory.java
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
public class BeanInstanceFactory {
	public People createPeople(String type, String msg) {
		if ("chinise".equals(type)) {
			return new Chinise(msg);
		} else if ("canada".equals(type)) {
			return new Canada(msg);
		} else {
			return new People();
		}
	}
}
