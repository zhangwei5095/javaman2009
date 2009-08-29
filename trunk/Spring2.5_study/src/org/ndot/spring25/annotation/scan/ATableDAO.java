package org.ndot.spring25.annotation.scan;

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
 * 文件名： ATableDAO.java
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
 * 创建时间: 2009-8-29
 * 
 */
@Repository
public class ATableDAO {
	public void doSave() {
		System.out.println("You have saved ATableDAO Datas");
	}
}
