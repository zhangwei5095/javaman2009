package org.ndot.ips.junit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� TestMain.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-24
 * 
 */
public class StartIPS {
	public void test(String... str) {

	}

	public static void main(String[] args) {
		try {
			String path = "ips/WEB-INF/";
			String config1 = path + "ipsApplicationContext.xml";
			ApplicationContext context = new FileSystemXmlApplicationContext(
					new String[] { config1 });

			// IPSReportChannel atm = (IPSATMReportChannel)
			// context.getBean("C003");
			// atm.runServer();

			// String channelTransCode = "110100";
			// String channelCode = "10";
			// String tranceType = "1";
			// String channelReportCode = "0200";
			// BusinessDBServices s = (BusinessDBServices) context
			// .getBean("businessDBServices");
			// System.out.println("���óɹ���.....��������");
			// String[] propertyNames = new String[] { "transtype", "chanlcode",
			// "chanlmsgcode", "chanltranscode" };
			// String[] propertyValues = new String[] { "1", "10", "0200",
			// "110100" };
			// List<IpsTranscodeMap> res = s.getByProperties(propertyNames,
			// propertyValues);
			// for (IpsTranscodeMap tem : res) {
			// System.out.println("�ڲ����״��룺" + tem.getIntranscode());
			// }
			// String[] conditions = new String[] { "TRANSTYPE=" + tranceType,
			// "CHANLCODE=" + channelCode,
			// "CHANLTRANSCODE=" + channelTransCode };
			// DBJdbcTool d = (DBJdbcTool) context.getBean("dbjdbctool");
			// IpsTranscodeMap t = (IpsTranscodeMap) d.queryForObject(
			// IpsTranscodeMap.class, "IPS_TRANSCODE_MAP", null,
			// conditions);
			// System.out.println("�ڲ����״��룺" + t.getIntranscode());
			// conditions = new String[] { "INTRANSCODE=1004" };
			// List ipsInTransflows = d.queryInTransflowForList(
			// IpsInTransflow.class, "IPS_IN_TRANSFLOW", null, conditions);
			// for (Object obj : ipsInTransflows) {
			// System.out.println("���״���Ϊ 1004 �����̶����ţ� "
			// + ((IpsInTransflow) obj).getFlowprocessno());
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
