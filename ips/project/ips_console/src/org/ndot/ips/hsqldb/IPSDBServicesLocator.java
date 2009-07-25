package org.ndot.ips.hsqldb;

import java.util.List;

import org.ndot.ips.db.pojo.PIsoDefine;
import org.ndot.ips.db.services.BusinessDBServices;
import org.ndot.ips.db.services.ISODBService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IPSDBServicesLocator {

	private static IPSDBServicesLocator dbServicesLocator;

	private ApplicationContext factory;

	private IPSDBServicesLocator() {
		String[] config = new String[] { "ipsDBContext.xml" };
		factory = new ClassPathXmlApplicationContext(config);
	}

	public static synchronized IPSDBServicesLocator getInstance() {
		if (dbServicesLocator == null) {
			dbServicesLocator = new IPSDBServicesLocator();
		}
		return dbServicesLocator;
	}

	public ISODBService getISODBService() {
		return (ISODBService) factory.getBean("isoDbSservice");
	}

	public BusinessDBServices getBusinessDBServices() {
		return (BusinessDBServices) factory.getBean("businessDBServices");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("start ....");

		ISODBService s = IPSDBServicesLocator.getInstance().getISODBService();

		// List<PMsgDef> list = s.pMsgDefFindAll();
		// for (PMsgDef msgDef : list) {
		// System.out.println(msgDef.getMsgtype());
		// }
		List<PIsoDefine> list2 = s.isoDefineFindAll();
		for (PIsoDefine isoDefine : list2) {
			System.out.println(isoDefine.getId().getFieldno());
		}
		// List rs = s.Find("P_ISO_DEFINE", new String[] { "distinct NODEID" },
		// "");
		// for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
		// Map obj = (Map) iterator.next();
		// System.out.println(obj.get("NODEID"));
		// }
		// System.out.println("dkd");
		
	}

}
