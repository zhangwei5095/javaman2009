package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsRspCode entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsRspCode
 * @author MyEclipse Persistence Tools
 */

public class IpsRspCodeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsRspCodeDAO.class);
	// property constants
	public static final String REQRSPCODE = "reqrspcode";
	public static final String EATCARDFLAG = "eatcardflag";
	public static final String RSPCODEINF = "rspcodeinf";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsRspCode transientInstance) {
		log.debug("saving IpsRspCode instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsRspCode persistentInstance) {
		log.debug("deleting IpsRspCode instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsRspCode findById(org.ndot.ips.db.pojo.IpsRspCodeId id) {
		log.debug("getting IpsRspCode instance with id: " + id);
		try {
			IpsRspCode instance = (IpsRspCode) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsRspCode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsRspCode instance) {
		log.debug("finding IpsRspCode instance by example");
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
		log.debug("finding IpsRspCode instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsRspCode as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReqrspcode(Object reqrspcode) {
		return findByProperty(REQRSPCODE, reqrspcode);
	}

	public List findByEatcardflag(Object eatcardflag) {
		return findByProperty(EATCARDFLAG, eatcardflag);
	}

	public List findByRspcodeinf(Object rspcodeinf) {
		return findByProperty(RSPCODEINF, rspcodeinf);
	}

	public List findAll() {
		log.debug("finding all IpsRspCode instances");
		try {
			String queryString = "from IpsRspCode";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsRspCode merge(IpsRspCode detachedInstance) {
		log.debug("merging IpsRspCode instance");
		try {
			IpsRspCode result = (IpsRspCode) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsRspCode instance) {
		log.debug("attaching dirty IpsRspCode instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsRspCode instance) {
		log.debug("attaching clean IpsRspCode instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsRspCodeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsRspCodeDAO) ctx.getBean("IpsRspCodeDAO");
	}
}