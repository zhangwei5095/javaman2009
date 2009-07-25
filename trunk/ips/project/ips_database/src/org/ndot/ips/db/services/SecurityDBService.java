package org.ndot.ips.db.services;

import java.util.List;

import org.ndot.ips.db.pojo.IpsEncParam;
import org.ndot.ips.db.pojo.IpsKeyMng;
import org.ndot.ips.db.pojo.IpsMainKey;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_database
 * 
 * 文件名： SecurityDBService.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-7-16
 * 
 */
public interface SecurityDBService {
	/* start MainKey start */

	public abstract void saveIpsMainKey(IpsMainKey transientInstance);

	public abstract void deleteIpsMainKey(IpsMainKey persistentInstance);

	public abstract IpsMainKey findIpsMainKeyById(java.lang.String id);

	public abstract List<IpsMainKey> findIpsMainKeyByExample(IpsMainKey instance);

	public abstract List<IpsMainKey> findIpsMainKeyByProperty(
			String propertyName, Object value);

	public abstract List<IpsMainKey> findIpsMainKeyByMainkey(Object mainkey);

	public abstract List<IpsMainKey> findIpsMainKeyAll();

	public abstract IpsMainKey mergeIpsMainKey(IpsMainKey detachedInstance);

	public abstract void attachIpsMainKeyDirty(IpsMainKey instance);

	public abstract void attachIpsMainKeyClean(IpsMainKey instance);

	/* end MainKey end */

	/* start IPSKeyMng start */

	public abstract void saveIpsKeyMng(IpsKeyMng transientInstance);
	public abstract void updateIpsKeyMng(IpsKeyMng transientInstance);

	public abstract void deleteIpsKeyMng(IpsKeyMng persistentInstance);

	public abstract IpsKeyMng findIpsKeyMngById(
			org.ndot.ips.db.pojo.IpsKeyMngId id);

	public abstract List<IpsKeyMng> findIpsKeyMngByExample(IpsKeyMng instance);

	public abstract List<IpsKeyMng> findIpsKeyMngByProperty(
			String propertyName, Object value);

	public abstract List<IpsKeyMng> findIpsKeyMngByEncmethod(Object encmethod);

	public abstract List<IpsKeyMng> findIpsKeyMngByKeylen(Object keylen);

	public abstract List<IpsKeyMng> findIpsKeyMngByKeyvalue(Object keyvalue);

	public abstract List<IpsKeyMng> findIpsKeyMngByIndexno(Object indexno);

	public abstract List<IpsKeyMng> findIpsKeyMngByPinmode(Object pinmode);

	public abstract List<IpsKeyMng> findIpsKeyMngAll();

	public abstract IpsKeyMng mergeIpsKeyMng(IpsKeyMng detachedInstance);

	public abstract void attachIpsKeyMngDirty(IpsKeyMng instance);

	public abstract void attachIpsKeyMngClean(IpsKeyMng instance);

	/* end IPSKeyMng end */
	/* start IpsEncParam start */

	public abstract void saveIpsEncParam(IpsEncParam transientInstance);

	public abstract void deleteIpsEncParam(IpsEncParam persistentInstance);

	public abstract IpsEncParam findIpsEncParamById(
			org.ndot.ips.db.pojo.IpsEncParamId id);

	public abstract List<IpsEncParam> findIpsEncParamByExample(
			IpsEncParam instance);

	public abstract List<IpsEncParam> findIpsEncParamByProperty(
			String propertyName, Object value);

	public abstract List<IpsEncParam> findIpsEncParamByEnctype(Object enctype);

	public abstract List<IpsEncParam> findIpsEncParamBySubnodeflag(
			Object subnodeflag);

	public abstract List<IpsEncParam> findIpsEncParamAll();

	public abstract IpsEncParam mergeIpsEncParam(IpsEncParam detachedInstance);

	public abstract void attachIpsEncParamDirty(IpsEncParam instance);

	public abstract void attachIpsEncParamClean(IpsEncParam instance);
	/* end IpsEncParam end */

}
