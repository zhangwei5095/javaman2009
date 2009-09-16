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
 * ��С����ѧ�� ֮ J2EE��
 * 
 *<P>
 * 
 * ��Ŀ���ƣ�Spring2.5_study
 * 
 *<P>
 * 
 * �ļ����� DBService.java
 * 
 *<P>
 * 
 * �� ��:Ҫ����������Ƶ� ������
 * 
 * 
 *<P>
 * 
 * 
 * �� ��: SunJincheng
 * 
 *<P>
 * 
 * ����ʱ��: 2009-9-10
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
		System.out.println("�������.....");
		throw new RuntimeException();
	}

	public void delete(Usertab persistentInstance) {
		this.delete(persistentInstance);
		System.out.println("ɾ������.....");
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
