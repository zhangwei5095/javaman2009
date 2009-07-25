package org.ndot.ips.db.pojo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PIsoDefine entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.PIsoDefine
 * @author MyEclipse Persistence Tools
 */

public class PIsoDefineDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PIsoDefineDAO.class);
	// property constants
	public static final String FIELDNAME = "fieldname";
	public static final String PREFIXTYPE = "prefixtype";
	public static final String PREFIXLEN = "prefixlen";
	public static final String DATATYPE = "datatype";
	public static final String DATALEN = "datalen";
	public static final String PADTYPE = "padtype";
	public static final String SHORTCUT = "shortcut";

	protected void initDao() {
		// do nothing
	}

	public void save(PIsoDefine transientInstance) {
		log.debug("saving PIsoDefine instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PIsoDefine persistentInstance) {
		log.debug("deleting PIsoDefine instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PIsoDefine findById(org.ndot.ips.db.pojo.PIsoDefineId id) {
		log.debug("getting PIsoDefine instance with id: " + id);
		try {
			PIsoDefine instance = (PIsoDefine) getHibernateTemplate().get(
					"com.nasoft.atmp.pojo.PIsoDefine", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PIsoDefine> findByExample(PIsoDefine instance) {
		log.debug("finding PIsoDefine instance by example");
		try {
			List<PIsoDefine> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return (List<PIsoDefine>)results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<PIsoDefine> findByProperty(String propertyName, Object value) {
		log.debug("finding PIsoDefine instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PIsoDefine as model where model."
					+ propertyName + "= ?";
			return (List<PIsoDefine>)getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PIsoDefine> findByFieldname(Object fieldname) {
		return (List<PIsoDefine>)findByProperty(FIELDNAME, fieldname);
	}

	public List<PIsoDefine> findByPrefixtype(Object prefixtype) {
		return (List<PIsoDefine>)findByProperty(PREFIXTYPE, prefixtype);
	}

	public List<PIsoDefine> findByPrefixlen(Object prefixlen) {
		return (List<PIsoDefine>)findByProperty(PREFIXLEN, prefixlen);
	}

	public List<PIsoDefine> findByDatatype(Object datatype) {
		return (List<PIsoDefine>)findByProperty(DATATYPE, datatype);
	}

	public List<PIsoDefine> findByDatalen(Object datalen) {
		return (List<PIsoDefine>)findByProperty(DATALEN, datalen);
	}

	public List<PIsoDefine> findByPadtype(Object padtype) {
		return (List<PIsoDefine>)findByProperty(PADTYPE, padtype);
	}

	public List<PIsoDefine> findByShortcut(Object shortcut) {
		return (List<PIsoDefine>)findByProperty(SHORTCUT, shortcut);
	}

	public List<PIsoDefine> findAll() {
		log.debug("finding all PIsoDefine instances");
		try {
			String queryString = "from PIsoDefine";
			return (List<PIsoDefine>)getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}


	public PIsoDefine merge(PIsoDefine detachedInstance) {
		log.debug("merging PIsoDefine instance");
		try {
			PIsoDefine result = (PIsoDefine) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PIsoDefine instance) {
		log.debug("attaching dirty PIsoDefine instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PIsoDefine instance) {
		log.debug("attaching clean PIsoDefine instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public static PIsoDefineDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PIsoDefineDAO) ctx.getBean("PIsoDefineDAO");
	}
}