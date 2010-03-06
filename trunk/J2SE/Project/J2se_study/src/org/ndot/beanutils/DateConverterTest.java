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

		// ���õ��Ǹ���Ϊû��ָ��ת����
		{
			D2 d2 = new D2();
			try {
				BeanUtils.copyProperties(d2, d1);
				System.out.println(d2.getMyDate());
			} catch (Exception e) {
				System.err.println("2.ûָ��ʱ��ת����");
			}
		}

		// ���õ�ָ��ת����
		{
			ConvertUtils.register(new DateConverter(), Date.class);// ע��һ��������
			ConvertUtils.register(new DateConverter(), String.class);// ע��һ���ַ���
			D2 d2 = new D2();
			try {
				BeanUtils.copyProperties(d2, d1);
				System.out.println(d2.getMyDate());
			} catch (Exception e) {
				System.out.println("3.ûָ��ʱ��ת����");
			}
		}
	}

	public static void main(String[] args) {
		DateConverterTest dt = new DateConverterTest();
		dt.test();
	}
}
