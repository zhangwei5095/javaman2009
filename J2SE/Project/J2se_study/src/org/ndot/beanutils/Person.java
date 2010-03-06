package org.ndot.beanutils;

import java.util.List;
import java.util.Map;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�J2se_study
 * 
 *<P>
 * 
 * �ļ����� Person.java
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
public class Person {
	private String name;
	private String sex;
	private int age;
	private Address address;
	private List goods;
	private Map contact;

	public Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public Person(String name, String sex) {
		this.name = name;
		this.sex = sex;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List getGoods() {
		return this.goods;
	}

	public void setGoods(List goods) {
		this.goods = goods;
	}

	public Map getContact() {
		return this.contact;
	}

	public void setContact(Map contact) {
		this.contact = contact;
	}

	public void sayHello() {
		System.out.println("Hello World!");
	}

	public void f(String str) {
		System.out.println("Person.f()..." + str);
	}

	public String toString() {
		return "Person.toString()...";
	}

}
