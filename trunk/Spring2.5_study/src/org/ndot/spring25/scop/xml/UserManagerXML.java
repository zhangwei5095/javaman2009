package org.ndot.spring25.scop.xml;

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
