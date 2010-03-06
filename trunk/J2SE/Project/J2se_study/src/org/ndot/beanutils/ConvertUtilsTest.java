package org.ndot.beanutils;

import org.apache.commons.beanutils.ConvertUtils;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： ConvertUtilsTest.java
 * 
 *<P>
 * 
 * 功 能:
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-12-26
 * 
 */
public class ConvertUtilsTest {
	public static void main(String[] args) {
		  Person person = new Person();
		  //将任意的实例转变为String，用法非常简单相当与调用toString()方法
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
