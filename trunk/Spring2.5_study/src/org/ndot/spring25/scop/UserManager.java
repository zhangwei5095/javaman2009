package org.ndot.spring25.scop;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� UserManager.java
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
@Service(value = "userManager")
@Scope(value = "singleton")
public class UserManager {
	@Resource
	ApplicationContext ctx;

	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Autowired
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * lookup-method
	 */

	public UserInfo getNewUserInfo() {
		return (UserInfo) ctx.getBean("user");
	}

}
