package org.ndot.beanutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：J2se_study
 * 
 *<P>
 * 
 * 文件名： BeanUtilsTest.java
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
public class BeanUtilsTest {

	// BeanUtils.cloneBean()--克隆目标bean
	public static void cloneBean(Person person) throws Exception {
		Person p = (Person) BeanUtils.cloneBean(person);
		System.out.println(p.getAddress().getCity());
		System.out.println(person.getAddress().getCity());
		p.getAddress().setCity("hengyang");
		System.out.println("---------------------------");
		System.out.println(p.getAddress().getCity());
		System.out.println(person.getAddress().getCity());
		System.out.println(p.getAddress() == person.getAddress());
		System.out.println("以上证明了 浅克隆......");
	}

	// BeanUtils.copyProperties()--拷贝目标bean中属性的值，能进行类型转换
	public static void copyProperties(Person person) throws Exception {
		Person p = new Person();
		BeanUtils.copyProperties(p, person);
		System.out.println(p.getAddress().getCity());
		System.out.println(person.getAddress().getCity());
		p.getAddress().setCity("hengyang");
		System.out.println("---------------------------");
		System.out.println(p.getAddress().getCity());
		System.out.println(person.getAddress().getCity());
		System.out.println(p.getAddress() == person.getAddress());
		System.out.println("以上证明了 BeanUtils 浅拷贝......");
	}

	// PropertyUtils.copyProperties()--拷贝目标bean中属性的值，不能进行类型转换
	public static void propertyUtilsCopyProperties(Person person)
			throws Exception {
		Person p = new Person();
		PropertyUtils.copyProperties(p, person);
		System.out.println(p.getAddress().getCity());
		System.out.println(person.getAddress().getCity());
		p.getAddress().setCity("hengyang");
		System.out.println("---------------------------");
		System.out.println(p.getAddress().getCity());
		System.out.println(person.getAddress().getCity());
		System.out.println(p.getAddress() == person.getAddress());
		System.out.println("以上证明了 PropertyUtils 浅拷贝......");
	}

	// BeanUtils.copyProperty()--拷贝一个值给目标Bean的一个属性，能进行类型转换
	public static void copyProperty(Person person) throws Exception {
		Person p = new Person();
		BeanUtils.copyProperty(p, "age", "12");
		System.out.println(p.getAge());
	}

	// BeanUtils.describe()--得到一个bean的所有描述，返回的是一个map
	public static void describe(Person person) throws Exception {
		Map map = BeanUtils.describe(person);
		Set set = map.keySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Object o = it.next();
			System.out.println(o + ": " + map.get(o));
		}
		System.out.println("--------------------------------------");
		// Collection c = map.values();
		// for(Object o : c) {
		// System.out.println(o);
		// }
	}

	// PropertyUtils.describe()--得到一个bean的所有描述，返回的是一个map
	public static void propertyUtilsDescribe(Person person) throws Exception {
		Map map = PropertyUtils.describe(person);
		Set set = map.keySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			Object o = it.next();
			System.out.println(o + ": " + map.get(o));
		}
		System.out.println("--------------------------------------");
		// Collection c = map.values();
		// for(Object o : c) {
		// System.out.println(o);
		// }
		// System.out.println("----------------------------");
	}

	// BeanUtils.populate()--使用一个map为bean赋值
	public static void populate(Person person) throws Exception {
		Map map = PropertyUtils.describe(person);
		Person p = new Person();
		BeanUtils.populate(p, map);
		System.out.println(p.getName());
		System.out.println(p.getSex());
		System.out.println(p.getAge());
		System.out.println(p.getAddress().getProvince());
		System.out.println(p.getGoods().get(1));
		System.out.println(p.getContact().get("student"));
		System.out.println("--------------------------------------");
	}

	// BeanUtils.getArrayProperty()--将目标bean的一个属性的值转换成一个字符串数组
	public static void getArrayProperty(Person person) throws Exception {
		String[] array = BeanUtils.getArrayProperty(person, "goods");
		for (String str : array) {
			System.out.println(str);
		}
	}

	// BeanUtils.getIndexedProperty()--可以检索目标bean中数组或集合类型的属性指定索引的值
	public static void getIndexedProperty(Person person) throws Exception {
		System.out.println(BeanUtils.getIndexedProperty(person, "goods[0]"));
		System.out.println(BeanUtils.getIndexedProperty(person, "goods", 1));
	}

	// BeanUtils.getMappedProperty()--可以检索目标bean中具有map类型的属性中对应键的值
	public static void getMappedProperty(Person person) throws Exception {
		System.out.println(BeanUtils.getMappedProperty(person,
				"contact(student)"));
		System.out.println(BeanUtils.getMappedProperty(person, "contact",
				"teacher"));
	}

	// BeanUtils.getNestedProperty()--得到目标bean中嵌套属性的值
	public static void getNestedProperty(Person person) throws Exception {
		System.out.println(BeanUtils.getNestedProperty(person,
				"address.province"));
	}

	// MethodUtils.invokeMethod()--动态调用方法
	public static void invokeMethod() throws Exception {
		// MethodUtils.invokeMethod(Object arg1,String arg2,Object[] arg3)
		// arg1 一个bean的名称
		// arg2 指定bean的方法名
		// arg3 方法中的参数列表
		MethodUtils.invokeMethod(new Person(), "sayHello", new Object[] {});
		// MethodUtils.invokeMethod(Object arg1,String arg2,Object arg3)
		// arg1 一个bean的名称
		// arg2 指定bean的方法名
		// arg3 方法中的参数
		MethodUtils.invokeExactMethod(new Person(), "f", "hello");
		;
	}

	// MethodUtils.getAccessibleMethod()--得到方法
	public static void getAccessibleMethod() throws Exception {
		// MethodUtils.getAccessibleMethod(Class clazz,String methodName,Class[]
		// parameterTypes)
		// clazz 一个bean的类对象
		// methodName bean中的方法名
		// parameterTypes 方法中参数的类型列表
		System.out.println(MethodUtils.getAccessibleMethod(Person.class, "f",
				new Class[] { String.class }));
		System.out.println(MethodUtils.getAccessibleMethod(Person.class, "f",
				String.class));
	}

	// ConstructorUtils.invokeConstructor()
	public static void invokeConstructor() throws Exception {
		// ConstructorUtils.invokeConstructor(Class clazz,Object[] args)
		// clazz 一个bean的类对象
		// args bean的构造函数的参数列表
		Person p1 = (Person) ConstructorUtils.invokeConstructor(Person.class,
				new Object[] {});
		Person p2 = (Person) ConstructorUtils.invokeConstructor(Person.class,
				"Tom");
		System.out.println(p2.getName());
	}

	// ConstructorUtils.getAccessibleConstructor()
	public static void getAccessibleConstructor() throws Exception {
		// ConstructorUtils.getAccessibleConstructor(Class clazz,Class[]
		// parameterTypes)
		// clazz 一个bean的类对象
		// parameterTypes bean的构造函数的参数类型列表
		System.out.println(ConstructorUtils.getAccessibleConstructor(
				Person.class, String.class));
		System.out.println(ConstructorUtils.getAccessibleConstructor(
				Person.class, new Class[] { String.class, String.class }));
	}

	public static void main(String[] args) throws Exception {
		Address address = new Address();
		address.setProvince("hunan");
		address.setCity("changsha");
		address.setStreet("wuyilu");
		address.setZipcode("410000");

		List goods = new ArrayList();
		goods.add("sporting");
		goods.add("singing");
		goods.add("dancing");

		Map contact = new HashMap();
		contact.put("student", "Tom");
		contact.put("teacher", "Lucy");

		Person person = new Person();
		person.setName("Mike");
		person.setSex("male");
		person.setAge(25);
		person.setAddress(address);
		person.setGoods(goods);
		person.setContact(contact);

		BeanUtilsTest.cloneBean(person);
		BeanUtilsTest.copyProperties(person);
		BeanUtilsTest.propertyUtilsCopyProperties(person);
		BeanUtilsTest.copyProperty(person);
		BeanUtilsTest.describe(person);
		BeanUtilsTest.propertyUtilsDescribe(person);
		BeanUtilsTest.getArrayProperty(person);
		BeanUtilsTest.getIndexedProperty(person);
		BeanUtilsTest.getMappedProperty(person);
		BeanUtilsTest.getNestedProperty(person);
		BeanUtilsTest.populate(person);

		// 得到一个bean中指定属性的值，相当于getter
		 System.out.println(BeanUtils.getProperty(person,"name"));
		// //修改一个bean中指定属性的值，相当于setter
		 BeanUtils.setProperty(person,"name","Tom");
		 System.out.println(BeanUtils.getProperty(person,"name"));
		 System.out.println("--------------------------------------");
		//		     
		 BeanUtilsTest.invokeMethod();
		 System.out.println("--------------------------------------");
		 BeanUtilsTest.getAccessibleMethod();
		 System.out.println("--------------------------------------");
		 BeanUtilsTest.invokeConstructor();
		 System.out.println("--------------------------------------");
		 BeanUtilsTest.getAccessibleConstructor();

	}

}
