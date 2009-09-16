package org.ndot.spring25.transaction.services;

import java.util.List;

import org.ndot.spring25.transaction.pojo.Usertab;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 *<P>
 * 
 * 项目名称：Spring2.5_study
 * 
 *<P>
 * 
 * 文件名： DBService.java
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
 * 创建时间: 2009-9-10
 * 
 */
public interface DBService {
	List<Usertab> findAll();

	void save(Usertab transientInstance);

	void delete(Usertab persistentInstance);

	Usertab findById(java.lang.String id);

}