package org.ndot.ips.db.pojo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PMsgDef entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.PMsgDef
 * @author MyEclipse Persistence Tools
 */

public class PMsgDefDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PMsgDefDAO.class);
	// property constants
	public static final String HEADLENFIELD = "headlenfield";
	public static final String BODYLENFIELD = "bodylenfield";
	public static final String MSGLENFIELD = "msglenfield";
	public static final String MSGLEN1FIELD = "msglen1field";
	public static final String MSGTYPE = "msgtype";

	protected void initDao() {
		// do nothing
	}

	public void save(PMsgDef transientInstance) {
		log.debug("saving PMsgDef instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PMsgDef persistentInstance) {
		log.debug("deleting PMsgDef instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PMsgDef findById(org.ndot.ips.db.pojo.PMsgDefId id) {
		log.debug("getting PMsgDef instance with id: " + id);
		try {
			PMsgDef instance = (PMsgDef) getHibernateTemplate().get(
					"com.nasoft.atmp.pojo.PMsgDef", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PMsgDef> findByExample(PMsgDef instance) {
		log.debug("finding PMsgDef instance by example");
		try {
			List<PMsgDef> results = (List<PMsgDef>)getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<PMsgDef> findByProperty(String propertyName, Object value) {
		log.debug("finding PMsgDef instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PMsgDef as model where model."
					+ propertyName + "= ?";
			return (List<PMsgDef>)getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PMsgDef> findByHeadlenfield(Object headlenfield) {
		return findByProperty(HEADLENFIELD, headlenfield);
	}

	public List<PMsgDef> findByBodylenfield(Object bodylenfield) {
		return findByProperty(BODYLENFIELD, bodylenfield);
	}

	public List<PMsgDef> findByMsglenfield(Object msglenfield) {
		return findByProperty(MSGLENFIELD, msglenfield);
	}

	public List<PMsgDef> findByMsglen1field(Object msglen1field) {
		return findByProperty(MSGLEN1FIELD, msglen1field);
	}

	public List<PMsgDef> findByMsgtype(Object msgtype) {
		return findByProperty(MSGTYPE, msgtype);
	}

	public List<PMsgDef> findAll() {
		log.debug("finding all PMsgDef instances");
		try {
			String queryString = "from PMsgDef";
			return (List<PMsgDef>)getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PMsgDef merge(PMsgDef detachedInstance) {
		log.debug("merging PMsgDef instance");
		try {
			PMsgDef result = (PMsgDef) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PMsgDef instance) {
		log.debug("attaching dirty PMsgDef instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PMsgDef instance) {
		log.debug("attaching clean PMsgDef instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PMsgDefDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PMsgDefDAO) ctx.getBean("PMsgDefDAO");
	}
}