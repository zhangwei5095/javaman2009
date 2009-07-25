package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsErrrspcodeMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsErrrspcodeMap
 * @author MyEclipse Persistence Tools
 */

public class IpsErrrspcodeMapDAO extends HibernateDaoSupport  {
	private static final Log log = LogFactory.getLog(IpsErrrspcodeMapDAO.class);
	// property constants
	public static final String REQRSPCODE = "reqrspcode";
	public static final String EATCARDFLAG = "eatcardflag";
	public static final String RSPCODEINF = "rspcodeinf";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsErrrspcodeMap transientInstance) {
		log.debug("saving IpsErrrspcodeMap instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsErrrspcodeMap persistentInstance) {
		log.debug("deleting IpsErrrspcodeMap instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsErrrspcodeMap findById(org.ndot.ips.db.pojo.IpsErrrspcodeMapId id) {
		log.debug("getting IpsErrrspcodeMap instance with id: " + id);
		try {
			IpsErrrspcodeMap instance = (IpsErrrspcodeMap) getHibernateTemplate()
					.get("org.ndot.ips.db.pojo.IpsErrrspcodeMap", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsErrrspcodeMap instance) {
		log.debug("finding IpsErrrspcodeMap instance by example");
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
		log.debug("finding IpsErrrspcodeMap instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IpsErrrspcodeMap as model where model."
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
		log.debug("finding all IpsErrrspcodeMap instances");
		try {
			String queryString = "from IpsErrrspcodeMap";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsErrrspcodeMap merge(IpsErrrspcodeMap detachedInstance) {
		log.debug("merging IpsErrrspcodeMap instance");
		try {
			IpsErrrspcodeMap result = (IpsErrrspcodeMap) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsErrrspcodeMap instance) {
		log.debug("attaching dirty IpsErrrspcodeMap instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsErrrspcodeMap instance) {
		log.debug("attaching clean IpsErrrspcodeMap instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsErrrspcodeMapDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsErrrspcodeMapDAO) ctx.getBean("IpsErrrspcodeMapDAO");
	}
}