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
 * �ļ����� Canada.java
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
public class Canada extends People {
	public Canada() {

	}

	public Canada(String msg) {
		setMsg(msg);
	}

	@Override
	public void say() {
		System.out.println("Canada say ��" + getMsg());
	}
}
