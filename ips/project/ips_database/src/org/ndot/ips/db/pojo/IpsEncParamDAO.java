package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsEncParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsEncParam
 * @author MyEclipse Persistence Tools
 */

public class IpsEncParamDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsEncParamDAO.class);
	// property constants
	public static final String ENCTYPE = "enctype";
	public static final String SUBNODEFLAG = "subnodeflag";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsEncParam transientInstance) {
		log.debug("saving IpsEncParam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsEncParam persistentInstance) {
		log.debug("deleting IpsEncParam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsEncParam findById(org.ndot.ips.db.pojo.IpsEncParamId id) {
		log.debug("getting IpsEncParam instance with id: " + id);
		try {
			IpsEncParam instance = (IpsEncParam) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsEncParam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsEncParam instance) {
		log.debug("finding IpsEncParam instance by example");
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
		log.debug("finding IpsEncParam instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsEncParam as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEnctype(Object enctype) {
		return findByProperty(ENCTYPE, enctype);
	}

	public List findBySubnodeflag(Object subnodeflag) {
		return findByProperty(SUBNODEFLAG, subnodeflag);
	}

	public List findAll() {
		log.debug("finding all IpsEncParam instances");
		try {
			String queryString = "from IpsEncParam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsEncParam merge(IpsEncParam detachedInstance) {
		log.debug("merging IpsEncParam instance");
		try {
			IpsEncParam result = (IpsEncParam) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsEncParam instance) {
		log.debug("attaching dirty IpsEncParam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsEncParam instance) {
		log.debug("attaching clean IpsEncParam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsEncParamDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsEncParamDAO) ctx.getBean("IpsEncParamDAO");
	}
}