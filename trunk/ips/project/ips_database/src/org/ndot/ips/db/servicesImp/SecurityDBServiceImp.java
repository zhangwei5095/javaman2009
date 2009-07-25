package org.ndot.ips.db.servicesImp;

import java.util.ArrayList;
import java.util.List;

import org.ndot.ips.db.pojo.IpsEncParam;
import org.ndot.ips.db.pojo.IpsEncParamDAO;
import org.ndot.ips.db.pojo.IpsEncParamId;
import org.ndot.ips.db.pojo.IpsKeyMng;
import org.ndot.ips.db.pojo.IpsKeyMngDAO;
import org.ndot.ips.db.pojo.IpsKeyMngId;
import org.ndot.ips.db.pojo.IpsMainKey;
import org.ndot.ips.db.pojo.IpsMainKeyDAO;
import org.ndot.ips.db.services.SecurityDBService;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_database
 * 
 * 文件名： SecurityDBServiceImp.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-7-16
 * 
 */
public class SecurityDBServiceImp implements SecurityDBService {
	private IpsMainKeyDAO ipsMainKeyDAO;
	private IpsKeyMngDAO ipsKeyMngDAO;
	private IpsEncParamDAO ipsEncParamDAO;

	public void attachIpsMainKeyClean(IpsMainKey instance) {
		// TODO Auto-generated method stub

	}

	public void attachIpsMainKeyDirty(IpsMainKey instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsMainKey(IpsMainKey persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsMainKey> findIpsMainKeyAll() {
		List<IpsMainKey> result = new ArrayList<IpsMainKey>();
		for (Object obj : ipsMainKeyDAO.findAll()) {
			result.add((IpsMainKey) obj);
		}
		return result;
	}

	public List<IpsMainKey> findIpsMainKeyByExample(IpsMainKey instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsMainKey findIpsMainKeyById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsMainKey> findIpsMainKeyByMainkey(Object mainkey) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsMainKey> findIpsMainKeyByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsMainKey mergeIpsMainKey(IpsMainKey detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsMainKey(IpsMainKey transientInstance) {
		// TODO Auto-generated method stub

	}

	public void attachIpsKeyMngClean(IpsKeyMng instance) {
		// TODO Auto-generated method stub

	}

	public void attachIpsKeyMngDirty(IpsKeyMng instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsKeyMng(IpsKeyMng persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsKeyMng> findIpsKeyMngAll() {
		List<IpsKeyMng> result = new ArrayList<IpsKeyMng>();
		for (Object obj : ipsKeyMngDAO.findAll()) {
			result.add((IpsKeyMng) obj);
		}
		return result;
	}

	public List<IpsKeyMng> findIpsKeyMngByEncmethod(Object encmethod) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsKeyMng> findIpsKeyMngByExample(IpsKeyMng instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsKeyMng findIpsKeyMngById(IpsKeyMngId id) {
		return ipsKeyMngDAO.findById(id);
	}

	public List<IpsKeyMng> findIpsKeyMngByIndexno(Object indexno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsKeyMng> findIpsKeyMngByKeylen(Object keylen) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsKeyMng> findIpsKeyMngByKeyvalue(Object keyvalue) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsKeyMng> findIpsKeyMngByPinmode(Object pinmode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsKeyMng> findIpsKeyMngByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsKeyMng mergeIpsKeyMng(IpsKeyMng detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsKeyMng(IpsKeyMng transientInstance) {
		// TODO Auto-generated method stub

	}
	public void updateIpsKeyMng(IpsKeyMng transientInstance) {
		this.ipsKeyMngDAO.update(transientInstance);

	}

	public void attachIpsEncParamClean(IpsEncParam instance) {
		// TODO Auto-generated method stub

	}

	public void attachIpsEncParamDirty(IpsEncParam instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsEncParam(IpsEncParam persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsEncParam> findIpsEncParamAll() {
		List<IpsEncParam> result = new ArrayList<IpsEncParam>();
		for (Object obj : ipsEncParamDAO.findAll()) {
			result.add((IpsEncParam) obj);
		}
		return result;
	}

	public List<IpsEncParam> findIpsEncParamByEnctype(Object enctype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEncParam> findIpsEncParamByExample(IpsEncParam instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsEncParam findIpsEncParamById(IpsEncParamId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEncParam> findIpsEncParamByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEncParam> findIpsEncParamBySubnodeflag(Object subnodeflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsEncParam mergeIpsEncParam(IpsEncParam detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsEncParam(IpsEncParam transientInstance) {
		// TODO Auto-generated method stub

	}

	public IpsMainKeyDAO getIpsMainKeyDAO() {
		return ipsMainKeyDAO;
	}

	public void setIpsMainKeyDAO(IpsMainKeyDAO ipsMainKeyDAO) {
		this.ipsMainKeyDAO = ipsMainKeyDAO;
	}

	public IpsKeyMngDAO getIpsKeyMngDAO() {
		return ipsKeyMngDAO;
	}

	public void setIpsKeyMngDAO(IpsKeyMngDAO ipsKeyMngDAO) {
		this.ipsKeyMngDAO = ipsKeyMngDAO;
	}

	public IpsEncParamDAO getIpsEncParamDAO() {
		return ipsEncParamDAO;
	}

	public void setIpsEncParamDAO(IpsEncParamDAO ipsEncParamDAO) {
		this.ipsEncParamDAO = ipsEncParamDAO;
	}
}