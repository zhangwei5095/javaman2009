package org.ndot.spring25.scop.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� GlobalSessionScopBean.java
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
 * ����ʱ��: 2009-8-30
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
