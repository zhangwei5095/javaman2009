package org.ndot.spring25.annotation;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： Family.java
 * 
 *<P>
 * 
 * 功 能: 家庭
 * 
 * 
 *<P>
 * 
 * 
 * 作 者: SunJincheng
 * 
 *<P>
 * 
 * 创建时间: 2009-8-29
 * 
 */
public class Family {
	@Autowired
	ApplicationContext ctx;

	@Resource
	ApplicationContext ctx2;
	// 必须对家庭名字进行注入，且注入id为familyName的 bean
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
