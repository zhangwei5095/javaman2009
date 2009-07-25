package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsKeyMng entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsKeyMng
 * @author MyEclipse Persistence Tools
 */

public class IpsKeyMngDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsKeyMngDAO.class);
	// property constants
	public static final String ENCMETHOD = "encmethod";
	public static final String KEYLEN = "keylen";
	public static final String KEYVALUE = "keyvalue";
	public static final String INDEXNO = "indexno";
	public static final String PINMODE = "pinmode";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsKeyMng transientInstance) {
		log.debug("saving IpsKeyMng instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(IpsKeyMng transientInstance) {
		log.debug("updating IpsKeyMng instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(IpsKeyMng persistentInstance) {
		log.debug("deleting IpsKeyMng instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsKeyMng findById(org.ndot.ips.db.pojo.IpsKeyMngId id) {
		log.debug("getting IpsKeyMng instance with id: " + id);
		try {
			IpsKeyMng instance = (IpsKeyMng) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsKeyMng", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsKeyMng instance) {
		log.debug("finding IpsKeyMng instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding IpsKeyMng instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsKeyMng as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEncmethod(Object encmethod) {
		return findByProperty(ENCMETHOD, encmethod);
	}

	public List findByKeylen(Object keylen) {
		return findByProperty(KEYLEN, keylen);
	}

	public List findByKeyvalue(Object keyvalue) {
		return findByProperty(KEYVALUE, keyvalue);
	}

	public List findByIndexno(Object indexno) {
		return findByProperty(INDEXNO, indexno);
	}

	public List findByPinmode(Object pinmode) {
		return findByProperty(PINMODE, pinmode);
	}

	public List findAll() {
		log.debug("finding all IpsKeyMng instances");
		try {
			String queryString = "from IpsKeyMng";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsKeyMng merge(IpsKeyMng detachedInstance) {
		log.debug("merging IpsKeyMng instance");
		try {
			IpsKeyMng result = (IpsKeyMng) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsKeyMng instance) {
		log.debug("attaching dirty IpsKeyMng instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsKeyMng instance) {
		log.debug("attaching clean IpsKeyMng instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsKeyMngDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsKeyMngDAO) ctx.getBean("IpsKeyMngDAO");
	}
}