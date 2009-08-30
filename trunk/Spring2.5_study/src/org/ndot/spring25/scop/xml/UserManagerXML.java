package org.ndot.spring25.scop.xml;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： UserManager.java
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
public class UserManagerXML {
	private UserInfoXML userInfo;

	public UserInfoXML getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoXML userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfoXML getNewUserInfo() {
		return null;
	}
}
