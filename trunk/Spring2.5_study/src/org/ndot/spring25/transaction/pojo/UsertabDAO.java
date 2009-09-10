package org.ndot.spring25.transaction.pojo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * Usertab entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.spring25.transaction.pojo.Usertab
 * @author MyEclipse Persistence Tools
 */
@Repository
public class UsertabDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(UsertabDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String AGE = "age";
	public static final String SEX = "sex";

	@Autowired
	public void setSuperSessionFactory(
			@Qualifier(value = "sessionFactory") SessionFactory sessionFactioy) {
		this.setSessionFactory(sessionFactioy);
	}

	protected void initDao() {
	}

	public void save(Usertab transientInstance) {
		log.debug("saving Usertab instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Usertab persistentInstance) {
		log.debug("deleting Usertab instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Usertab findById(java.lang.String id) {
		log.debug("getting Usertab instance with id: " + id);
		try {
			Usertab instance = (Usertab) getHibernateTemplate().get(
					"org.ndot.spring25.transaction.pojo.Usertab", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Usertab instance) {
		log.debug("finding Usertab instance by example");
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
		log.debug("finding Usertab instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Usertab as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findAll() {
		log.debug("finding all Usertab instances");
		try {
			String queryString = "from Usertab";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Usertab merge(Usertab detachedInstance) {
		log.debug("merging Usertab instance");
		try {
			Usertab result = (Usertab) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Usertab instance) {
		log.debug("attaching dirty Usertab instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Usertab instance) {
		log.debug("attaching clean Usertab instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UsertabDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UsertabDAO) ctx.getBean("UsertabDAO");
	}
}