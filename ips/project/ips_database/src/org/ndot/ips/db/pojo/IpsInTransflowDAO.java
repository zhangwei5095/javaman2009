package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsInTransflow entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsInTransflow
 * @author MyEclipse Persistence Tools
 */

public class IpsInTransflowDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsInTransflowDAO.class);
	// property constants
	public static final String TRANNAME = "tranname";
	public static final String TRANSTAT = "transtat";
	public static final String TRANSCATEGORY = "transcategory";
	public static final String STARTTAG = "starttag";
	public static final String DESTNODEID = "destnodeid";
	public static final String FLOWPROCESSNO = "flowprocessno";
	public static final String CHKMACFLAG = "chkmacflag";
	public static final String CHKPINFLAG = "chkpinflag";
	public static final String REVERSEFLAG = "reverseflag";
	public static final String RESENDFLAG = "resendflag";
	public static final String LAUNCHTRANSCODE = "launchtranscode";
	public static final String SAFFLAG = "safflag";
	public static final String QUERYORGJNLFLAG = "queryorgjnlflag";
	public static final String OVERTIME = "overtime";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsInTransflow transientInstance) {
		log.debug("saving IpsInTransflow instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsInTransflow persistentInstance) {
		log.debug("deleting IpsInTransflow instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsInTransflow findById(org.ndot.ips.db.pojo.IpsInTransflowId id) {
		log.debug("getting IpsInTransflow instance with id: " + id);
		try {
			IpsInTransflow instance = (IpsInTransflow) getHibernateTemplate()
					.get("org.ndot.ips.db.pojo.IpsInTransflow", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsInTransflow instance) {
		log.debug("finding IpsInTransflow instance by example");
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
		log.debug("finding IpsInTransflow instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IpsInTransflow as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTranname(Object tranname) {
		return findByProperty(TRANNAME, tranname);
	}

	public List findByTranstat(Object transtat) {
		return findByProperty(TRANSTAT, transtat);
	}

	public List findByTranscategory(Object transcategory) {
		return findByProperty(TRANSCATEGORY, transcategory);
	}

	public List findByStarttag(Object starttag) {
		return findByProperty(STARTTAG, starttag);
	}

	public List findByDestnodeid(Object destnodeid) {
		return findByProperty(DESTNODEID, destnodeid);
	}

	public List findByFlowprocessno(Object flowprocessno) {
		return findByProperty(FLOWPROCESSNO, flowprocessno);
	}

	public List findByChkmacflag(Object chkmacflag) {
		return findByProperty(CHKMACFLAG, chkmacflag);
	}

	public List findByChkpinflag(Object chkpinflag) {
		return findByProperty(CHKPINFLAG, chkpinflag);
	}

	public List findByReverseflag(Object reverseflag) {
		return findByProperty(REVERSEFLAG, reverseflag);
	}

	public List findByResendflag(Object resendflag) {
		return findByProperty(RESENDFLAG, resendflag);
	}

	public List findByLaunchtranscode(Object launchtranscode) {
		return findByProperty(LAUNCHTRANSCODE, launchtranscode);
	}

	public List findBySafflag(Object safflag) {
		return findByProperty(SAFFLAG, safflag);
	}

	public List findByQueryorgjnlflag(Object queryorgjnlflag) {
		return findByProperty(QUERYORGJNLFLAG, queryorgjnlflag);
	}

	public List findByOvertime(Object overtime) {
		return findByProperty(OVERTIME, overtime);
	}

	public List findAll() {
		log.debug("finding all IpsInTransflow instances");
		try {
			String queryString = "from IpsInTransflow";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsInTransflow merge(IpsInTransflow detachedInstance) {
		log.debug("merging IpsInTransflow instance");
		try {
			IpsInTransflow result = (IpsInTransflow) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsInTransflow instance) {
		log.debug("attaching dirty IpsInTransflow instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsInTransflow instance) {
		log.debug("attaching clean IpsInTransflow instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsInTransflowDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsInTransflowDAO) ctx.getBean("IpsInTransflowDAO");
	}
}