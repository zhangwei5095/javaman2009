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
 * �ļ����� Chinise.java
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
public class Chinise extends People {

	public Chinise(String msg) {
		this.setMsg(msg);
	}

	@Override
	public void say() {
		System.out.println("�й���˵��" + getMsg());
	}

}
