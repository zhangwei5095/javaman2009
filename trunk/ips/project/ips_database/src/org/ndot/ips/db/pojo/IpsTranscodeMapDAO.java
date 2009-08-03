package org.ndot.ips.db.pojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsTranscodeMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsTranscodeMap
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("unchecked")
public class IpsTranscodeMapDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsTranscodeMapDAO.class);
	// property constants
	public static final String TRANSTYPE = "transtype";
	public static final String CHANLCODE = "chanlcode";
	public static final String CHANLMSGCODE = "chanlmsgcode";
	public static final String CHANLTRANSCODE = "chanltranscode";
	public static final String CORECODE = "corecode";
	public static final String COREMSGCODE = "coremsgcode";
	public static final String CORETRANSCODE = "coretranscode";
	public static final String HOSTCODE = "hostcode";
	public static final String HOSTMSGCODE = "hostmsgcode";
	public static final String HOSTTRANSCODE = "hosttranscode";
	public static final String TRANSABSTINF = "transabstinf";
	public static final String TRANSDESC = "transdesc";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsTranscodeMap transientInstance) {
		log.debug("saving IpsTranscodeMap instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsTranscodeMap persistentInstance) {
		log.debug("deleting IpsTranscodeMap instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsTranscodeMap findById(java.lang.String id) {
		log.debug("getting IpsTranscodeMap instance with id: " + id);
		try {
			IpsTranscodeMap instance = (IpsTranscodeMap) getHibernateTemplate()
					.get("org.ndot.ips.db.pojo.IpsTranscodeMap", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsTranscodeMap instance) {
		log.debug("finding IpsTranscodeMap instance by example");
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
		log.debug("finding IpsTranscodeMap instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from IpsTranscodeMap as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTranstype(Object transtype) {
		return findByProperty(TRANSTYPE, transtype);
	}

	public List findByChanlcode(Object chanlcode) {
		return findByProperty(CHANLCODE, chanlcode);
	}

	public List findByChanlmsgcode(Object chanlmsgcode) {
		return findByProperty(CHANLMSGCODE, chanlmsgcode);
	}

	public List findByChanltranscode(Object chanltranscode) {
		return findByProperty(CHANLTRANSCODE, chanltranscode);
	}

	public List findByCorecode(Object corecode) {
		return findByProperty(CORECODE, corecode);
	}

	public List findByCoremsgcode(Object coremsgcode) {
		return findByProperty(COREMSGCODE, coremsgcode);
	}

	public List findByCoretranscode(Object coretranscode) {
		return findByProperty(CORETRANSCODE, coretranscode);
	}

	public List findByHostcode(Object hostcode) {
		return findByProperty(HOSTCODE, hostcode);
	}

	public List findByHostmsgcode(Object hostmsgcode) {
		return findByProperty(HOSTMSGCODE, hostmsgcode);
	}

	public List findByHosttranscode(Object hosttranscode) {
		return findByProperty(HOSTTRANSCODE, hosttranscode);
	}

	public List findByTransabstinf(Object transabstinf) {
		return findByProperty(TRANSABSTINF, transabstinf);
	}

	public List findByTransdesc(Object transdesc) {
		return findByProperty(TRANSDESC, transdesc);
	}

	public List findAll() {
		log.debug("finding all IpsTranscodeMap instances");
		try {
			String queryString = "from IpsTranscodeMap";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsTranscodeMap merge(IpsTranscodeMap detachedInstance) {
		log.debug("merging IpsTranscodeMap instance");
		try {
			IpsTranscodeMap result = (IpsTranscodeMap) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsTranscodeMap instance) {
		log.debug("attaching dirty IpsTranscodeMap instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsTranscodeMap instance) {
		log.debug("attaching clean IpsTranscodeMap instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<IpsTranscodeMap> getByProperties(String[] propertyNames,
			String[] propertyValues) {
		return getByProperties(propertyNames, propertyValues, 0, 0);
	}

	/**
	 * 
	 * @param propertyNames
	 * @param propertyValues
	 * @param fistRow
	 * @param maxRow
	 * @return
	 */
	public List<IpsTranscodeMap> getByProperties(String[] propertyNames,
			String[] propertyValues, int fistRow, int maxRow) {

		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(IpsTranscodeMap.class);
			for (int i = 0; i < propertyNames.length; i++) {
				dCriteria.add(Expression
						.eq(propertyNames[i], propertyValues[i]));
			}
			List tem = new ArrayList();
			if (maxRow == 0) {
				tem = this.getHibernateTemplate().findByCriteria(dCriteria);
			} else {
				tem = this.getHibernateTemplate().findByCriteria(dCriteria,
						fistRow, maxRow);
			}
			List<IpsTranscodeMap> result = new ArrayList<IpsTranscodeMap>();
			for (Iterator iterator = tem.iterator(); iterator.hasNext();) {
				result.add((IpsTranscodeMap) iterator.next());
			}
			return result;
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}

	}

	public static IpsTranscodeMapDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsTranscodeMapDAO) ctx.getBean("IpsTranscodeMapDAO");
	}
}