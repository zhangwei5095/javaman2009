package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsDevInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsDevInf
 * @author MyEclipse Persistence Tools
 */

public class IpsDevInfDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsDevInfDAO.class);
	// property constants
	public static final String DEVNAME = "devname";
	public static final String INSTCODE = "instcode";
	public static final String UUNIONCODE = "uunioncode";
	public static final String DEVTYPE = "devtype";
	public static final String MADEVENDORID = "madevendorid";
	public static final String MADEDATE = "madedate";
	public static final String SUPPLYVENDORID = "supplyvendorid";
	public static final String SVCVENDORID = "svcvendorid";
	public static final String DEVSERIALNO = "devserialno";
	public static final String FIRSTSVCDATE = "firstsvcdate";
	public static final String LASTSVCDATE = "lastsvcdate";
	public static final String INSTALLLOC = "installloc";
	public static final String PROTOCOL = "protocol";
	public static final String IPADDR = "ipaddr";
	public static final String PORT = "port";
	public static final String BRANDID = "brandid";
	public static final String MODEL = "model";
	public static final String INSTALLTYPE = "installtype";
	public static final String INSIDEBANKFLG = "insidebankflg";
	public static final String DEVOWNER = "devowner";
	public static final String SVC24HFLG = "svc24hflg";
	public static final String SVCSTARTTIME = "svcstarttime";
	public static final String SVCENDTIME = "svcendtime";
	public static final String OS = "os";
	public static final String TELLER = "teller";
	public static final String USESTAT = "usestat";
	public static final String ATMPROGVER = "atmprogver";
	public static final String INSTALLLOCTYPE = "installloctype";
	public static final String SELFBANKFLG = "selfbankflg";
	public static final String REMOCTRLTYPE = "remoctrltype";
	public static final String DEVUPGRADEFLG = "devupgradeflg";
	public static final String FILERESOLUTION = "fileresolution";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsDevInf transientInstance) {
		log.debug("saving IpsDevInf instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(IpsDevInf persistentInstance) {
		log.debug("deleting IpsDevInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsDevInf findById(java.lang.String id) {
		log.debug("getting IpsDevInf instance with id: " + id);
		try {
			IpsDevInf instance = (IpsDevInf) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsDevInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsDevInf instance) {
		log.debug("finding IpsDevInf instance by example");
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
		log.debug("finding IpsDevInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsDevInf as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDevname(Object devname) {
		return findByProperty(DEVNAME, devname);
	}

	public List findByInstcode(Object instcode) {
		return findByProperty(INSTCODE, instcode);
	}

	public List findByUunioncode(Object uunioncode) {
		return findByProperty(UUNIONCODE, uunioncode);
	}

	public List findByDevtype(Object devtype) {
		return findByProperty(DEVTYPE, devtype);
	}

	public List findByMadevendorid(Object madevendorid) {
		return findByProperty(MADEVENDORID, madevendorid);
	}

	public List findByMadedate(Object madedate) {
		return findByProperty(MADEDATE, madedate);
	}

	public List findBySupplyvendorid(Object supplyvendorid) {
		return findByProperty(SUPPLYVENDORID, supplyvendorid);
	}

	public List findBySvcvendorid(Object svcvendorid) {
		return findByProperty(SVCVENDORID, svcvendorid);
	}

	public List findByDevserialno(Object devserialno) {
		return findByProperty(DEVSERIALNO, devserialno);
	}

	public List findByFirstsvcdate(Object firstsvcdate) {
		return findByProperty(FIRSTSVCDATE, firstsvcdate);
	}

	public List findByLastsvcdate(Object lastsvcdate) {
		return findByProperty(LASTSVCDATE, lastsvcdate);
	}

	public List findByInstallloc(Object installloc) {
		return findByProperty(INSTALLLOC, installloc);
	}

	public List findByProtocol(Object protocol) {
		return findByProperty(PROTOCOL, protocol);
	}

	public List findByIpaddr(Object ipaddr) {
		return findByProperty(IPADDR, ipaddr);
	}

	public List findByPort(Object port) {
		return findByProperty(PORT, port);
	}

	public List findByBrandid(Object brandid) {
		return findByProperty(BRANDID, brandid);
	}

	public List findByModel(Object model) {
		return findByProperty(MODEL, model);
	}

	public List findByInstalltype(Object installtype) {
		return findByProperty(INSTALLTYPE, installtype);
	}

	public List findByInsidebankflg(Object insidebankflg) {
		return findByProperty(INSIDEBANKFLG, insidebankflg);
	}

	public List findByDevowner(Object devowner) {
		return findByProperty(DEVOWNER, devowner);
	}

	public List findBySvc24hflg(Object svc24hflg) {
		return findByProperty(SVC24HFLG, svc24hflg);
	}

	public List findBySvcstarttime(Object svcstarttime) {
		return findByProperty(SVCSTARTTIME, svcstarttime);
	}

	public List findBySvcendtime(Object svcendtime) {
		return findByProperty(SVCENDTIME, svcendtime);
	}

	public List findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List findByTeller(Object teller) {
		return findByProperty(TELLER, teller);
	}

	public List findByUsestat(Object usestat) {
		return findByProperty(USESTAT, usestat);
	}

	public List findByAtmprogver(Object atmprogver) {
		return findByProperty(ATMPROGVER, atmprogver);
	}

	public List findByInstallloctype(Object installloctype) {
		return findByProperty(INSTALLLOCTYPE, installloctype);
	}

	public List findBySelfbankflg(Object selfbankflg) {
		return findByProperty(SELFBANKFLG, selfbankflg);
	}

	public List findByRemoctrltype(Object remoctrltype) {
		return findByProperty(REMOCTRLTYPE, remoctrltype);
	}

	public List findByDevupgradeflg(Object devupgradeflg) {
		return findByProperty(DEVUPGRADEFLG, devupgradeflg);
	}

	public List findByFileresolution(Object fileresolution) {
		return findByProperty(FILERESOLUTION, fileresolution);
	}

	public List findAll() {
		log.debug("finding all IpsDevInf instances");
		try {
			String queryString = "from IpsDevInf";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsDevInf merge(IpsDevInf detachedInstance) {
		log.debug("merging IpsDevInf instance");
		try {
			IpsDevInf result = (IpsDevInf) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsDevInf instance) {
		log.debug("attaching dirty IpsDevInf instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsDevInf instance) {
		log.debug("attaching clean IpsDevInf instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsDevInfDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IpsDevInfDAO) ctx.getBean("IpsDevInfDAO");
	}
}