package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsEvilCard entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsEvilCard
 * @author MyEclipse Persistence Tools
 */

public class IpsEvilCardDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsEvilCardDAO.class);
	// property constants
	public static final String CARDFLAG = "cardflag";
	public static final String SETREASON = "setreason";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsEvilCard transientInstance) {
		log.debug("saving IpsEvilCard instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsEvilCard persistentInstance) {
		log.debug("deleting IpsEvilCard instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsEvilCard findById(java.lang.String id) {
		log.debug("getting IpsEvilCard instance with id: " + id);
		try {
			IpsEvilCard instance = (IpsEvilCard) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsEvilCard", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsEvilCard instance) {
		log.debug("finding IpsEvilCard instance by example");
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
		log.debug("finding IpsEvilCard instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsEvilCard as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCardflag(Object cardflag) {
		return findByProperty(CARDFLAG, cardflag);
	}

	public List findBySetreason(Object setreason) {
		return findByProperty(SETREASON, setreason);
	}

	public List findAll() {
		log.debug("finding all IpsEvilCard instances");
		try {
			String queryString = "from IpsEvilCard";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsEvilCard merge(IpsEvilCard detachedInstance) {
		log.debug("merging IpsEvilCard instance");
		try {
			IpsEvilCard result = (IpsEvilCard) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsEvilCard instance) {
		log.debug("attaching dirty IpsEvilCard instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsEvilCard instance) {
		log.debug("attaching clean IpsEvilCard instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsEvilCardDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsEvilCardDAO) ctx.getBean("IpsEvilCardDAO");
	}
}