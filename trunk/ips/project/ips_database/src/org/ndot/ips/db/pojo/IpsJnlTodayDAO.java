package org.ndot.ips.db.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IpsJnlToday entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.ndot.ips.db.pojo.IpsJnlToday
 * @author MyEclipse Persistence Tools
 */

public class IpsJnlTodayDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(IpsJnlTodayDAO.class);
	// property constants
	public static final String LOCALDATETIME = "localdatetime";
	public static final String INTRANSCODE = "intranscode";
	public static final String TRANSTYPE = "transtype";
	public static final String ERRCODE = "errcode";
	public static final String CHANLCODE = "chanlcode";
	public static final String CHANLMSGCODE = "chanlmsgcode";
	public static final String CHANLTRANSCODE = "chanltranscode";
	public static final String CHANLJNLNO = "chanljnlno";
	public static final String CHANLRSPCODE = "chanlrspcode";
	public static final String CORECODE = "corecode";
	public static final String COREMSGCODE = "coremsgcode";
	public static final String CORETRANSCODE = "coretranscode";
	public static final String CORECLRDATE = "coreclrdate";
	public static final String COREJNLNO = "corejnlno";
	public static final String CORERSPCODE = "corerspcode";
	public static final String HOSTCODE = "hostcode";
	public static final String HOSTMSGCODE = "hostmsgcode";
	public static final String HOSTTRANSCODE = "hosttranscode";
	public static final String HOSTCLRDATE = "hostclrdate";
	public static final String HOSTJNLNO = "hostjnlno";
	public static final String HOSTRSPCODE = "hostrspcode";
	public static final String DEVID = "devid";
	public static final String CARDNO = "cardno";
	public static final String TRANAMT = "tranamt";
	public static final String CARDEXPDATE = "cardexpdate";
	public static final String DEVTRANSTIME = "devtranstime";
	public static final String DEVTRANSDATE = "devtransdate";
	public static final String MCTCODE = "mctcode";
	public static final String MCTTYPE = "mcttype";
	public static final String TRANCURRCODE = "trancurrcode";
	public static final String INPUTTYPE = "inputtype";
	public static final String CONDCODE = "condcode";
	public static final String TRACK2 = "track2";
	public static final String TRACK3 = "track3";
	public static final String ADDBAL = "addbal";
	public static final String OPENINSTCODE = "openinstcode";
	public static final String TRANSINSTCODE = "transinstcode";
	public static final String ACCFROM = "accfrom";
	public static final String ACCTO = "accto";
	public static final String FEE1 = "fee1";
	public static final String FEE2 = "fee2";
	public static final String FEE3 = "fee3";
	public static final String HOSTCHKFLAG = "hostchkflag";
	public static final String TERMCHKFLAG = "termchkflag";
	public static final String MSGREASONCODE = "msgreasoncode";
	public static final String REFNUMBER = "refnumber";
	public static final String TELLERID = "tellerid";
	public static final String BATCHNO = "batchno";
	public static final String ORIGDATA = "origdata";
	public static final String AUTHRSPCODE = "authrspcode";
	public static final String CHKFLAG = "chkflag";
	public static final String COREMODFLAG = "coremodflag";
	public static final String HOSTMODFLAG = "hostmodflag";
	public static final String TRANSTAT = "transtat";
	public static final String LOCALDATEYEAR = "localdateyear";
	public static final String LOCALDATEMON = "localdatemon";
	public static final String LOCALDATEDAY = "localdateday";

	protected void initDao() {
		// do nothing
	}

	public void save(IpsJnlToday transientInstance) {
		log.debug("saving IpsJnlToday instance");
		try {
//			 getHibernateTemplate().save(transientInstance);
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void update(IpsJnlToday transientInstance) {
		log.debug("updateing IpsJnlToday instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("updateing successful");
		} catch (RuntimeException re) {
			log.error("updateing failed", re);
			throw re;
		}
	}
	public void delete(IpsJnlToday persistentInstance) {
		log.debug("deleting IpsJnlToday instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public IpsJnlToday findById(java.lang.String id) {
		log.debug("getting IpsJnlToday instance with id: " + id);
		try {
			IpsJnlToday instance = (IpsJnlToday) getHibernateTemplate().get(
					"org.ndot.ips.db.pojo.IpsJnlToday", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(IpsJnlToday instance) {
		log.debug("finding IpsJnlToday instance by example");
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
		log.debug("finding IpsJnlToday instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from IpsJnlToday as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLocaldatetime(Object localdatetime) {
		return findByProperty(LOCALDATETIME, localdatetime);
	}

	public List findByIntranscode(Object intranscode) {
		return findByProperty(INTRANSCODE, intranscode);
	}

	public List findByTranstype(Object transtype) {
		return findByProperty(TRANSTYPE, transtype);
	}

	public List findByErrcode(Object errcode) {
		return findByProperty(ERRCODE, errcode);
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

	public List findByChanljnlno(Object chanljnlno) {
		return findByProperty(CHANLJNLNO, chanljnlno);
	}

	public List findByChanlrspcode(Object chanlrspcode) {
		return findByProperty(CHANLRSPCODE, chanlrspcode);
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

	public List findByCoreclrdate(Object coreclrdate) {
		return findByProperty(CORECLRDATE, coreclrdate);
	}

	public List findByCorejnlno(Object corejnlno) {
		return findByProperty(COREJNLNO, corejnlno);
	}

	public List findByCorerspcode(Object corerspcode) {
		return findByProperty(CORERSPCODE, corerspcode);
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

	public List findByHostclrdate(Object hostclrdate) {
		return findByProperty(HOSTCLRDATE, hostclrdate);
	}

	public List findByHostjnlno(Object hostjnlno) {
		return findByProperty(HOSTJNLNO, hostjnlno);
	}

	public List findByHostrspcode(Object hostrspcode) {
		return findByProperty(HOSTRSPCODE, hostrspcode);
	}

	public List findByDevid(Object devid) {
		return findByProperty(DEVID, devid);
	}

	public List findByCardno(Object cardno) {
		return findByProperty(CARDNO, cardno);
	}

	public List findByTranamt(Object tranamt) {
		return findByProperty(TRANAMT, tranamt);
	}

	public List findByCardexpdate(Object cardexpdate) {
		return findByProperty(CARDEXPDATE, cardexpdate);
	}

	public List findByDevtranstime(Object devtranstime) {
		return findByProperty(DEVTRANSTIME, devtranstime);
	}

	public List findByDevtransdate(Object devtransdate) {
		return findByProperty(DEVTRANSDATE, devtransdate);
	}

	public List findByMctcode(Object mctcode) {
		return findByProperty(MCTCODE, mctcode);
	}

	public List findByMcttype(Object mcttype) {
		return findByProperty(MCTTYPE, mcttype);
	}

	public List findByTrancurrcode(Object trancurrcode) {
		return findByProperty(TRANCURRCODE, trancurrcode);
	}

	public List findByInputtype(Object inputtype) {
		return findByProperty(INPUTTYPE, inputtype);
	}

	public List findByCondcode(Object condcode) {
		return findByProperty(CONDCODE, condcode);
	}

	public List findByTrack2(Object track2) {
		return findByProperty(TRACK2, track2);
	}

	public List findByTrack3(Object track3) {
		return findByProperty(TRACK3, track3);
	}

	public List findByAddbal(Object addbal) {
		return findByProperty(ADDBAL, addbal);
	}

	public List findByOpeninstcode(Object openinstcode) {
		return findByProperty(OPENINSTCODE, openinstcode);
	}

	public List findByTransinstcode(Object transinstcode) {
		return findByProperty(TRANSINSTCODE, transinstcode);
	}

	public List findByAccfrom(Object accfrom) {
		return findByProperty(ACCFROM, accfrom);
	}

	public List findByAccto(Object accto) {
		return findByProperty(ACCTO, accto);
	}

	public List findByFee1(Object fee1) {
		return findByProperty(FEE1, fee1);
	}

	public List findByFee2(Object fee2) {
		return findByProperty(FEE2, fee2);
	}

	public List findByFee3(Object fee3) {
		return findByProperty(FEE3, fee3);
	}

	public List findByHostchkflag(Object hostchkflag) {
		return findByProperty(HOSTCHKFLAG, hostchkflag);
	}

	public List findByTermchkflag(Object termchkflag) {
		return findByProperty(TERMCHKFLAG, termchkflag);
	}

	public List findByMsgreasoncode(Object msgreasoncode) {
		return findByProperty(MSGREASONCODE, msgreasoncode);
	}

	public List findByRefnumber(Object refnumber) {
		return findByProperty(REFNUMBER, refnumber);
	}

	public List findByTellerid(Object tellerid) {
		return findByProperty(TELLERID, tellerid);
	}

	public List findByBatchno(Object batchno) {
		return findByProperty(BATCHNO, batchno);
	}

	public List findByOrigdata(Object origdata) {
		return findByProperty(ORIGDATA, origdata);
	}

	public List findByAuthrspcode(Object authrspcode) {
		return findByProperty(AUTHRSPCODE, authrspcode);
	}

	public List findByChkflag(Object chkflag) {
		return findByProperty(CHKFLAG, chkflag);
	}

	public List findByCoremodflag(Object coremodflag) {
		return findByProperty(COREMODFLAG, coremodflag);
	}

	public List findByHostmodflag(Object hostmodflag) {
		return findByProperty(HOSTMODFLAG, hostmodflag);
	}

	public List findByTranstat(Object transtat) {
		return findByProperty(TRANSTAT, transtat);
	}

	public List findByLocaldateyear(Object localdateyear) {
		return findByProperty(LOCALDATEYEAR, localdateyear);
	}

	public List findByLocaldatemon(Object localdatemon) {
		return findByProperty(LOCALDATEMON, localdatemon);
	}

	public List findByLocaldateday(Object localdateday) {
		return findByProperty(LOCALDATEDAY, localdateday);
	}

	public List findAll() {
		log.debug("finding all IpsJnlToday instances");
		try {
			String queryString = "from IpsJnlToday";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public IpsJnlToday merge(IpsJnlToday detachedInstance) {
		log.debug("merging IpsJnlToday instance");
		try {
			IpsJnlToday result = (IpsJnlToday) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(IpsJnlToday instance) {
		log.debug("attaching dirty IpsJnlToday instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(IpsJnlToday instance) {
		log.debug("attaching clean IpsJnlToday instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IpsJnlTodayDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IpsJnlTodayDAO) ctx.getBean("IpsJnlTodayDAO");
	}
}