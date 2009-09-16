package org.ndot.spring25.transaction.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.ndot.spring25.transaction.pojo.Usertab;
import org.ndot.spring25.transaction.pojo.UsertabDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * 功 能:要进行事物控制的 服务类
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
@Service
@Transactional
public class DBServiceImp implements DBService {
	@Resource
	private UsertabDAO userDao;

	public UsertabDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UsertabDAO userDao) {
		this.userDao = userDao;
	}

	public void save(Usertab transientInstance) {
		this.userDao.save(transientInstance);
		System.out.println("保存对象.....");
		throw new RuntimeException();
	}

	public void delete(Usertab persistentInstance) {
		this.delete(persistentInstance);
		System.out.println("删除对象.....");
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Usertab findById(java.lang.String id) {
		return this.userDao.findById(id);
	}

	@Transactional(readOnly = true)
	public List<Usertab> findAll() {
		List<Usertab> list = new ArrayList<Usertab>();
		List tem = this.userDao.findAll();
		if (null == tem)
			return list;
		for (Iterator iterator = tem.iterator(); iterator.hasNext();) {
			list.add((Usertab) iterator.next());
		}
		return list;
	}
}
