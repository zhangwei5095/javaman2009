package org.ndot.spring25.scop;

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
 * �ļ����� UserInfo.java
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
@Repository(value="user")
@Scope(value = "prototype")
public class UserInfo {
	public UserInfo() {
		System.out.println("Create UserInfo Instance......");
	}
}
