package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsDevTranslimit entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsDevTranslimit
 * @author MyEclipse Persistence Tools
 */

public class IpsDevTranslimitDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsDevTranslimitDAO.class);
	// property constants
	public static final String DEVTYPE = "devtype";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsDevTranslimit transientInstance) {
		log.debug("saving IpsDevTranslimit instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsDevTranslimit persistentInstance) {
		log.debug("deleting IpsDevTranslimit instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsDevTranslimit findById(org.ndot.ips.db.pojo.IpsDevTranslimitId id) {
		log.debug("getting IpsDevTranslimit instance with id: " + id);
		try {
			IpsDevTranslimit instance = (IpsDevTranslimit) getHibernateTemplate()
					.get("org.ndot.ips.db.pojo.IpsDevTranslimit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsDevTranslimit instance) {
		log.debug("finding IpsDevTranslimit instance by example");
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
		log.debug("finding IpsDevTranslimit instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IpsDevTranslimit as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDevtype(Object devtype) {
		return findByProperty(DEVTYPE, devtype);
	}

	public List findAll() {
		log.debug("finding all IpsDevTranslimit instances");
		try {
			String queryString = "from IpsDevTranslimit";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsDevTranslimit merge(IpsDevTranslimit detachedInstance) {
		log.debug("merging IpsDevTranslimit instance");
		try {
			IpsDevTranslimit result = (IpsDevTranslimit) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsDevTranslimit instance) {
		log.debug("attaching dirty IpsDevTranslimit instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsDevTranslimit instance) {
		log.debug("attaching clean IpsDevTranslimit instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsDevTranslimitDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsDevTranslimitDAO) ctx.getBean("IpsDevTranslimitDAO");
	}
}