package org.ndot.spring25.scop;

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
 * 文件名： UserInfo.java
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
@Repository(value="user")
@Scope(value = "prototype")
public class UserInfo {
	public UserInfo() {
		System.out.println("Create UserInfo Instance......");
	}
}
