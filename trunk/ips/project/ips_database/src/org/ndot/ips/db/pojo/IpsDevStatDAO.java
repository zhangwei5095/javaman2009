package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsDevStat entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsDevStat
 * @author MyEclipse Persistence Tools
 */

public class IpsDevStatDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsDevStatDAO.class);
	// property constants
	public static final String SIGNSTAT = "signstat";
	public static final String SIGNDATETIME = "signdatetime";
	public static final String UPDDATETIME = "upddatetime";
	public static final String DEVSTAT = "devstat";
	public static final String DEVERRINF = "deverrinf";
	public static final String DEVACCTERRINF = "devaccterrinf";

	protected void initDao() {
		// do nothing
	}

	public void saveOrUpdate(IpsDevStat transientInstance) {
		log.debug("saveOrUpdateing IpsDevStat instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("saveOrUpdate successful");
		} catch (RuntimeException re) {
			log.error("saveOrUpdate failed", re);
			throw re;
		}
	}
	
	public void update(IpsDevStat transientInstance) {
		log.debug("Updateing IpsDevStat instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("Update successful");
		} catch (RuntimeException re) {
			log.error("Update failed", re);
			throw re;
		}
	}
	public void save(IpsDevStat transientInstance) {
		log.debug("saving IpsDevStat instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsDevStat persistentInstance) {
		log.debug("deleting IpsDevStat instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsDevStat findById(java.lang.String id) {
		log.debug("getting IpsDevStat instance with id: " + id);
		try {
			IpsDevStat instance = (IpsDevStat) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsDevStat", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsDevStat instance) {
		log.debug("finding IpsDevStat instance by example");
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
		log.debug("finding IpsDevStat instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsDevStat as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySignstat(Object signstat) {
		return findByProperty(SIGNSTAT, signstat);
	}

	public List findBySigndatetime(Object signdatetime) {
		return findByProperty(SIGNDATETIME, signdatetime);
	}

	public List findByUpddatetime(Object upddatetime) {
		return findByProperty(UPDDATETIME, upddatetime);
	}

	public List findByDevstat(Object devstat) {
		return findByProperty(DEVSTAT, devstat);
	}

	public List findByDeverrinf(Object deverrinf) {
		return findByProperty(DEVERRINF, deverrinf);
	}

	public List findByDevaccterrinf(Object devaccterrinf) {
		return findByProperty(DEVACCTERRINF, devaccterrinf);
	}

	public List findAll() {
		log.debug("finding all IpsDevStat instances");
		try {
			String queryString = "from IpsDevStat";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsDevStat merge(IpsDevStat detachedInstance) {
		log.debug("merging IpsDevStat instance");
		try {
			IpsDevStat result = (IpsDevStat) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsDevStat instance) {
		log.debug("attaching dirty IpsDevStat instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsDevStat instance) {
		log.debug("attaching clean IpsDevStat instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsDevStatDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsDevStatDAO) ctx.getBean("IpsDevStatDAO");
	}
}