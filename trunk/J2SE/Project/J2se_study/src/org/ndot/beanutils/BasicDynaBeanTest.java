package org.ndot.beanutils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： BasicDynaBeanTest.java
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

public class BasicDynaBeanTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		  //创建一个动态属性数组
		     DynaProperty[] properties = new DynaProperty[] {
		       new DynaProperty("name",String.class),
		       new DynaProperty("sex",String.class),
		       new DynaProperty("age",int.class),
		       new DynaProperty("address",Map.class)
		     };
		      
		     //创建一个BasicDynaClass的实例
		     //第一个参数设置动态bean的名称
		     //第二个参数设置BasicDynaBean的类对象
		     //第三个参数设置一个动态属性数组
		     BasicDynaClass dynaClass = new BasicDynaClass("customer",BasicDynaBean.class,properties);
		     //通过dynaClass得到动态bean的一个实例
		     DynaBean customer = dynaClass.newInstance();
		     
		     //给属性设值
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
