package org.ndot.spring25.annotation;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� Family.java
 * 
 *<P>
 * 
 * �� ��: ��ͥ
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-8-29
 * 
 */
public class Family {
	@Autowired
	ApplicationContext ctx;

	@Resource
	ApplicationContext ctx2;
	// ����Լ�ͥ���ֽ���ע�룬��ע��idΪfamilyName�� bean
	@Autowired(required = true)
	@Qualifier(value = "familyName")
	private String fname;
	private Map<String, Member> Members;
	private Address address;

	public String getFname() {
		return fname;
	}

	public Address getAddress() {
		return address;
	}

	@Resource(name = "familyAddress")
	public void setAddress(@Qualifier(value = "injectAddress") Address address) {
		this.address = address;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Map<String, Member> getMembers() {
		return Members;
	}

	@Autowired
	public void setMembers(Map<String, Member> members) {
		Members = members;
	}

}
