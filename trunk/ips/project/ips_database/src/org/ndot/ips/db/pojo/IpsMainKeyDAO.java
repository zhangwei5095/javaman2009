package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsMainKey entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsMainKey
 * @author MyEclipse Persistence Tools
 */

public class IpsMainKeyDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsMainKeyDAO.class);
	// property constants
	public static final String MAINKEY = "mainkey";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsMainKey transientInstance) {
		log.debug("saving IpsMainKey instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsMainKey persistentInstance) {
		log.debug("deleting IpsMainKey instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsMainKey findById(java.lang.String id) {
		log.debug("getting IpsMainKey instance with id: " + id);
		try {
			IpsMainKey instance = (IpsMainKey) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsMainKey", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsMainKey instance) {
		log.debug("finding IpsMainKey instance by example");
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
		log.debug("finding IpsMainKey instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsMainKey as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMainkey(Object mainkey) {
		return findByProperty(MAINKEY, mainkey);
	}

	public List findAll() {
		log.debug("finding all IpsMainKey instances");
		try {
			String queryString = "from IpsMainKey";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsMainKey merge(IpsMainKey detachedInstance) {
		log.debug("merging IpsMainKey instance");
		try {
			IpsMainKey result = (IpsMainKey) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsMainKey instance) {
		log.debug("attaching dirty IpsMainKey instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsMainKey instance) {
		log.debug("attaching clean IpsMainKey instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsMainKeyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsMainKeyDAO) ctx.getBean("IpsMainKeyDAO");
	}
}