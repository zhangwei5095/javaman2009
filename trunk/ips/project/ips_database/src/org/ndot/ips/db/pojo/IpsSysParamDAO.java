package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsSysParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsSysParam
 * @author MyEclipse Persistence Tools
 */

public class IpsSysParamDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsSysParamDAO.class);
	// property constants
	public static final String PARAMNAME = "paramname";
	public static final String PARAMVALUE = "paramvalue";
	public static final String PARAMMODFLG = "parammodflg";
	public static final String PARAMDESC = "paramdesc";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsSysParam transientInstance) {
		log.debug("saving IpsSysParam instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsSysParam persistentInstance) {
		log.debug("deleting IpsSysParam instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsSysParam findById(java.lang.String id) {
		log.debug("getting IpsSysParam instance with id: " + id);
		try {
			IpsSysParam instance = (IpsSysParam) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsSysParam", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsSysParam instance) {
		log.debug("finding IpsSysParam instance by example");
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
		log.debug("finding IpsSysParam instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsSysParam as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByParamname(Object paramname) {
		return findByProperty(PARAMNAME, paramname);
	}

	public List findByParamvalue(Object paramvalue) {
		return findByProperty(PARAMVALUE, paramvalue);
	}

	public List findByParammodflg(Object parammodflg) {
		return findByProperty(PARAMMODFLG, parammodflg);
	}

	public List findByParamdesc(Object paramdesc) {
		return findByProperty(PARAMDESC, paramdesc);
	}

	public List findAll() {
		log.debug("finding all IpsSysParam instances");
		try {
			String queryString = "from IpsSysParam";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsSysParam merge(IpsSysParam detachedInstance) {
		log.debug("merging IpsSysParam instance");
		try {
			IpsSysParam result = (IpsSysParam) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsSysParam instance) {
		log.debug("attaching dirty IpsSysParam instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsSysParam instance) {
		log.debug("attaching clean IpsSysParam instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsSysParamDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsSysParamDAO) ctx.getBean("IpsSysParamDAO");
	}
}