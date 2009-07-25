package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsSaf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsSaf
 * @author MyEclipse Persistence Tools
 */

public class IpsSafDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsSafDAO.class);
	// property constants
	public static final String INTRANSCODE = "intranscode";
	public static final String ENDTIME = "endtime";
	public static final String SOURCEID = "sourceid";
	public static final String DESTID = "destid";
	public static final String STATE = "state";
	public static final String RSPCODE = "rspcode";
	public static final String SENDTIMES = "sendtimes";
	public static final String OVERTIME = "overtime";
	public static final String CLRDATE = "clrdate";
	public static final String SENDPAC = "sendpac";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsSaf transientInstance) {
		log.debug("saving IpsSaf instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsSaf persistentInstance) {
		log.debug("deleting IpsSaf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsSaf findById(org.ndot.ips.db.pojo.IpsSafId id) {
		log.debug("getting IpsSaf instance with id: " + id);
		try {
			IpsSaf instance = (IpsSaf) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsSaf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsSaf instance) {
		log.debug("finding IpsSaf instance by example");
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
		log.debug("finding IpsSaf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsSaf as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIntranscode(Object intranscode) {
		return findByProperty(INTRANSCODE, intranscode);
	}

	public List findByEndtime(Object endtime) {
		return findByProperty(ENDTIME, endtime);
	}

	public List findBySourceid(Object sourceid) {
		return findByProperty(SOURCEID, sourceid);
	}

	public List findByDestid(Object destid) {
		return findByProperty(DESTID, destid);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByRspcode(Object rspcode) {
		return findByProperty(RSPCODE, rspcode);
	}

	public List findBySendtimes(Object sendtimes) {
		return findByProperty(SENDTIMES, sendtimes);
	}

	public List findByOvertime(Object overtime) {
		return findByProperty(OVERTIME, overtime);
	}

	public List findByClrdate(Object clrdate) {
		return findByProperty(CLRDATE, clrdate);
	}

	public List findBySendpac(Object sendpac) {
		return findByProperty(SENDPAC, sendpac);
	}

	public List findAll() {
		log.debug("finding all IpsSaf instances");
		try {
			String queryString = "from IpsSaf";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsSaf merge(IpsSaf detachedInstance) {
		log.debug("merging IpsSaf instance");
		try {
			IpsSaf result = (IpsSaf) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsSaf instance) {
		log.debug("attaching dirty IpsSaf instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsSaf instance) {
		log.debug("attaching clean IpsSaf instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsSafDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsSafDAO) ctx.getBean("IpsSafDAO");
	}
}