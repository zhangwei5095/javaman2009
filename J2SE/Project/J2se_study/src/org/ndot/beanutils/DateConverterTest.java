package org.ndot.beanutils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： DateConverterTest.java
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
public class DateConverterTest {

	@SuppressWarnings("unchecked")
	public void test() {
		D1 d1 = new D1();
		
		d1.setMyDate("1992-02-11 22:22:22");
		try {
			Map map = new HashMap();
			map.put("myDate", "2009-02-22");

			BeanUtils.populate(d1, map);
			System.out.println(d1.getMyDate());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 公用的那个因为没有指定转换器
		{
			D2 d2 = new D2();
			try {
				BeanUtils.copyProperties(d2, d1);
				System.out.println(d2.getMyDate());
			} catch (Exception e) {
				System.err.println("2.没指定时间转换器");
			}
		}

		// 公用的指定转换器
		{
			ConvertUtils.register(new DateConverter(), Date.class);// 注册一个日期类
			ConvertUtils.register(new DateConverter(), String.class);// 注册一个字符类
			D2 d2 = new D2();
			try {
				BeanUtils.copyProperties(d2, d1);
				System.out.println(d2.getMyDate());
			} catch (Exception e) {
				System.out.println("3.没指定时间转换器");
			}
		}
	}

	public static void main(String[] args) {
		DateConverterTest dt = new DateConverterTest();
		dt.test();
	}
}
