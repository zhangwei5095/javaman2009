package org.ndot.beanutils;

import java.util.Map;

import org.apache.commons.beanutils.LazyDynaBean;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� LazyDynaBeanTest.java
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
public class LazyDynaBeanTest {
	public static void main(String[] args) {
		// ����һ��LazyDynaBean��ʵ�������ͺ���ͨ��beanһ��
		LazyDynaBean customer = new LazyDynaBean();
		customer.set("name", "Tom");
		customer.set("sex", "male");
		customer.set("age", new Integer(25));
		// ��map��ʽ�����Ը�ֵ����һ�����������������ڶ��������Ǽ���������������ֵ
		customer.set("address", "province", "hunan");
		// ���������һ������򼯺ϣ�������������ʽ��ֵ
		// ��һ���������������򼯺������ڶ�������������������������������ͼ����д��ֵ
		customer.set("orders", 0, "order001");
		customer.set("orders", 1, "order002");

		System.out.println("name: " + customer.get("name"));
		System.out.println("sex: " + customer.get("sex"));
		System.out.println("age: " + customer.get("age"));
		System.out.println("address: "
				+ ((Map) customer.get("address")).get("province"));
		System.out.println("orders: " + customer.get("orders", 0) + " "
				+ customer.get("orders", 1));
	}

}
