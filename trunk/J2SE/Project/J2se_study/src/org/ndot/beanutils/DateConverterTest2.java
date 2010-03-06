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
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� DateConverterTest.java
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
public class DateConverterTest2 {

	static {
		ConvertUtils.deregister(Date.class);
		DateConverter dateConverter = new DateConverter();
		ConvertUtils.register(dateConverter, Date.class);
	}

	@SuppressWarnings("unchecked")
	public void test() {
		D1 d1 = new D1();
		try {
			Map map = new HashMap();
			map.put("myDate", "2009-02-22");

			D2 d21 = new D2();

			BeanUtils.populate(d1, map);
			System.out.println(d1.getMyDate());

			BeanUtils.populate(d21, map);
			System.out.println(d21.getMyDate());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DateConverterTest2 dt = new DateConverterTest2();
		dt.test();
	}
}
