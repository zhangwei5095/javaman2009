package org.ndot.beanutils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� BasicDynaBeanTest.java
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

public class BasicDynaBeanTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		  //����һ����̬��������
		     DynaProperty[] properties = new DynaProperty[] {
		       new DynaProperty("name",String.class),
		       new DynaProperty("sex",String.class),
		       new DynaProperty("age",int.class),
		       new DynaProperty("address",Map.class)
		     };
		      
		     //����һ��BasicDynaClass��ʵ��
		     //��һ���������ö�̬bean������
		     //�ڶ�����������BasicDynaBean�������
		     //��������������һ����̬��������
		     BasicDynaClass dynaClass = new BasicDynaClass("customer",BasicDynaBean.class,properties);
		     //ͨ��dynaClass�õ���̬bean��һ��ʵ��
		     DynaBean customer = dynaClass.newInstance();
		     
		     //��������ֵ
		     Map map = new HashMap();
		     map.put("province","hunan");
		     map.put("city","changsha");
		     map.put("street","wuyilu");
		     map.put("zipcode","410000");
		     customer.set("name","Tom");
		     customer.set("sex","male");
		     customer.set("age",25);
		     customer.set("address",map);
		     
		     System.out.println("name: " + customer.get("name"));
		     System.out.println("sex: " + customer.get("sex"));
		     System.out.println("age: " + customer.get("age"));
		     System.out.println("address: " + ((Map)customer.get("address")).get("province")
		     + " " + ((Map)customer.get("address")).get("city")
		     + " " + ((Map)customer.get("address")).get("street")
		     + " " + ((Map)customer.get("address")).get("zipcode"));
		    }

}
