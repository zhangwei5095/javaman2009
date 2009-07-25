package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsCardbin entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsCardbin
 * @author MyEclipse Persistence Tools
 */

public class IpsCardbinDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsCardbinDAO.class);
	// property constants
	public static final String BANKID = "bankid";
	public static final String BANKNAME = "bankname";
	public static final String CARDID = "cardid";
	public static final String OFFSET1 = "offset1";
	public static final String LENGTH1 = "length1";
	public static final String STRING1 = "string1";
	public static final String OFFSET2 = "offset2";
	public static final String LENGTH2 = "length2";
	public static final String STRING2 = "string2";
	public static final String OFFSET3 = "offset3";
	public static final String LENGTH3 = "length3";
	public static final String STRING3 = "string3";
	public static final String PANOFFSET = "panoffset";
	public static final String PANLEN = "panlen";
	public static final String CARDFLAG = "cardflag";
	public static final String TRANLIST = "tranlist";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsCardbin transientInstance) {
		log.debug("saving IpsCardbin instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsCardbin persistentInstance) {
		log.debug("deleting IpsCardbin instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsCardbin findById(java.lang.String id) {
		log.debug("getting IpsCardbin instance with id: " + id);
		try {
			IpsCardbin instance = (IpsCardbin) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsCardbin", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsCardbin instance) {
		log.debug("finding IpsCardbin instance by example");
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
		log.debug("finding IpsCardbin instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsCardbin as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBankid(Object bankid) {
		return findByProperty(BANKID, bankid);
	}

	public List findByBankname(Object bankname) {
		return findByProperty(BANKNAME, bankname);
	}

	public List findByCardid(Object cardid) {
		return findByProperty(CARDID, cardid);
	}

	public List findByOffset1(Object offset1) {
		return findByProperty(OFFSET1, offset1);
	}

	public List findByLength1(Object length1) {
		return findByProperty(LENGTH1, length1);
	}

	public List findByString1(Object string1) {
		return findByProperty(STRING1, string1);
	}

	public List findByOffset2(Object offset2) {
		return findByProperty(OFFSET2, offset2);
	}

	public List findByLength2(Object length2) {
		return findByProperty(LENGTH2, length2);
	}

	public List findByString2(Object string2) {
		return findByProperty(STRING2, string2);
	}

	public List findByOffset3(Object offset3) {
		return findByProperty(OFFSET3, offset3);
	}

	public List findByLength3(Object length3) {
		return findByProperty(LENGTH3, length3);
	}

	public List findByString3(Object string3) {
		return findByProperty(STRING3, string3);
	}

	public List findByPanoffset(Object panoffset) {
		return findByProperty(PANOFFSET, panoffset);
	}

	public List findByPanlen(Object panlen) {
		return findByProperty(PANLEN, panlen);
	}

	public List findByCardflag(Object cardflag) {
		return findByProperty(CARDFLAG, cardflag);
	}

	public List findByTranlist(Object tranlist) {
		return findByProperty(TRANLIST, tranlist);
	}

	public List findAll() {
		log.debug("finding all IpsCardbin instances");
		try {
			String queryString = "from IpsCardbin";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsCardbin merge(IpsCardbin detachedInstance) {
		log.debug("merging IpsCardbin instance");
		try {
			IpsCardbin result = (IpsCardbin) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsCardbin instance) {
		log.debug("attaching dirty IpsCardbin instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsCardbin instance) {
		log.debug("attaching clean IpsCardbin instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsCardbinDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsCardbinDAO) ctx.getBean("IpsCardbinDAO");
	}
}