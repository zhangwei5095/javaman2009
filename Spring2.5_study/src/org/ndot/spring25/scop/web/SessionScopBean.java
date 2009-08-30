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
 * 文件名： SessionScopBean.java
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
@Repository(value = "SessionScopBean")
@Scope(value = "session")
public class SessionScopBean {

	private String loginName;
	private String loginRole;

	public SessionScopBean() {
		this.loginName = "Session-none";
		this.loginRole = "Session-none";
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
