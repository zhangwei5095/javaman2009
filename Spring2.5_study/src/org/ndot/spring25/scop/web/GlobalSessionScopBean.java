package org.ndot.spring25.scop.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： GlobalSessionScopBean.java
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
 * 创建时间: 2009-8-30
 * 
 */
@Repository(value = "GlobalSessionScopBean")
@Scope(value = "globalSession")
public class GlobalSessionScopBean {

	private String loginName;
	private String loginRole;

	public GlobalSessionScopBean() {
		this.loginName = "GlobalSession-none";
		this.loginRole = "GlobalSession-none";
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginRole() {
		return loginRole;
	}

	public void setLoginRole(String loginRole) {
		this.loginRole = loginRole;
	}

}
