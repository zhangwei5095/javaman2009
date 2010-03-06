package org.ndot.beanutils;

import org.apache.commons.beanutils.ConvertUtils;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� ConvertUtilsTest.java
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
 * ����ʱ��: 2009-12-26
 * 
 */
public class ConvertUtilsTest {
	public static void main(String[] args) {
		  Person person = new Person();
		  //�������ʵ��ת��ΪString���÷��ǳ����൱�����toString()����
		  System.out.println(ConvertUtils.convert(person));
		  
		  String str ="123456";
		  String ary = "45789";
		  //int ss = Integer.parseInt(str);
		  Integer s =(Integer)ConvertUtils.convert(str,Integer.class);
		  
		  String[] values = {"123","456","789","aafs"};
		  Object o = ConvertUtils.convert(values,Integer.class);
		  System.out.println(o);
		    }

}
