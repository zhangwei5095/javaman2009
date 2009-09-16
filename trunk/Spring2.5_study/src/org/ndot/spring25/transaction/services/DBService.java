package org.ndot.spring25.transaction.services;

import java.util.List;

import org.ndot.spring25.transaction.pojo.Usertab;

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
 * ����ʱ��: 2009-9-10
 * 
 */
public interface DBService {
	List<Usertab> findAll();

	void save(Usertab transientInstance);

	void delete(Usertab persistentInstance);

	Usertab findById(java.lang.String id);

}