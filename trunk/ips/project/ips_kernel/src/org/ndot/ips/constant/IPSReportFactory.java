package org.ndot.ips.constant;

import java.util.Map;

import org.ndot.ips.db.pojo.IpsDevInf;
import org.ndot.ips.db.pojo.IpsInTransflow;
import org.ndot.ips.db.pojo.IpsTranscodeMap;
import org.ndot.ips.util.GenDateTime;

import com.nasoft.IPSReport;
import com.nasoft.IPSReportFieldType;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_kernel
 * 
 * 文件名： IPSReportFactory.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-7-2
 * 
 */
public class IPSReportFactory {
	// ATM-C003发送的请求报文
	public static String FROM_C003_REQ_REPORT = "FROM_C003_REQ_REPORT";
	// 前置经 FROM_C003_REQ_REPORT 组织到CORE-C002的请求报文
	public static String C003_TO_C002_REQ_REPORT = "C003_TO_C002_REQ_REPORT";
	// 核心给ATM的应答报文
	public static String FROM_C002_RSP_REPORT = "FROM_C002_RSP_REPORT";
	// 前置经FROM_C002_RSP_REPORT 组织到ATM-C003的应答报文
	public static String C002_TO_C003_RSP_REPORT = "C002_TO_C003_RSP_REPORT";
	// 前置经由 FROM_C003_REQ_REPORT 组织到ATM-C003的报文
	public static String T0_C003_RSP_REPORT_0001 = "T0_C003_RSP_REPORT_0001";
	public static String T0_C003_RSP_REPORT_0003 = "T0_C003_RSP_REPORT_0003";
	public static String T0_C003_RSP_REPORT_0004 = "T0_C003_RSP_REPORT_0004";
	public static String T0_C003_RSP_REPORT_0006 = "T0_C003_RSP_REPORT_0006";

	public static IPSReport getErrorRspToC003(String errorCode,
			Map<String, IPSReport> reports) {
		return getErrorRspToC003(errorCode, reports, null, null);
	}

	public static IPSReport getErrorRspToC003(String errorCode,
			Map<String, IPSReport> reports, IpsInTransflow ipsInTransflow,
			IpsTranscodeMap ipsTranscodeMap) {
		String rspErrorCode = "ZZ";
		if (IPSInnerErrorCode.IPS313.equals(errorCode)) {
			// 核心应答超时
			if (IPSTransTypes.IPSTRAN1001.equals(ipsTranscodeMap
					.getIntranscode())) {
				// 余额查询

			}

		}
		return null;
	}

	public static IPSReport getReport(String reportType, String jnlno,
			byte[] keyValue, Map<String, String> sysParamSet, String rspCode,
			IpsDevInf devinfo, IpsInTransflow ipsInTransflow,
			IpsTranscodeMap ipsTranscodeMap, Map<String, IPSReport> reports) {
		if (IPSReportFactory.T0_C003_RSP_REPORT_0001
				.equalsIgnoreCase(reportType)) {
			// 签到/退成功应答报文
			return createT0_C003_RSP_REPORT_0001(rspCode, sysParamSet, devinfo,
					ipsInTransflow, ipsTranscodeMap, reports);
		}
		if (IPSReportFactory.T0_C003_RSP_REPORT_0003
				.equalsIgnoreCase(reportType)) {
			// 初始CDK成功应答报文
			return IPSReportFactory.createT0_C003_RSP_REPORT_0003(rspCode,
					keyValue, sysParamSet, devinfo, ipsTranscodeMap, reports);
		}
		if (IPSReportFactory.T0_C003_RSP_REPORT_0004
				.equalsIgnoreCase(reportType)) {
			// PIN/MACKEY
			return IPSReportFactory.createT0_C003_RSP_REPORT_0004(rspCode,
					keyValue, sysParamSet, devinfo, ipsTranscodeMap, reports);
		}
		if (IPSReportFactory.T0_C003_RSP_REPORT_0006
				.equalsIgnoreCase(reportType)) {
			// PIN/MACKEY
			return IPSReportFactory.createT0_C003_RSP_REPORT_0006(rspCode,
					keyValue, sysParamSet, devinfo, ipsTranscodeMap, reports);
		}
		if (IPSReportFactory.C003_TO_C002_REQ_REPORT.equals(reportType)) {
			// ATM到核心的请求报文
			return createC003_TO_C002_REQ_REPORT(jnlno, devinfo,
					ipsTranscodeMap, reports);
		}
		if (IPSReportFactory.C002_TO_C003_RSP_REPORT.equals(reportType)) {
			return IPSReportFactory.createC002_TO_C003_RSP_REPORT(rspCode,
					reports);
		}

		return null;

	}

	private static IPSReport createT0_C003_RSP_REPORT_0006(String rspCode,
			byte[] keyValue, Map<String, String> sysParamSet,
			IpsDevInf devinfo, IpsTranscodeMap ipsTranscodeMap,
			Map<String, IPSReport> reports) {

		try {
			IPSReport devReqReport = reports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			IPSReport toAtmRspReport = new IPSReport();

			// 设置报文头
			toAtmRspReport.setHeaderFieldValue(2, devReqReport
					.getHeaderFieldValue(2));
			toAtmRspReport.setHeaderFieldValue(3, devReqReport
					.getHeaderFieldValue(3));
			toAtmRspReport.setHeaderFieldValue(4, devReqReport
					.getHeaderFieldValue(4));
			toAtmRspReport.setHeaderFieldValue(5, devReqReport
					.getHeaderFieldValue(5));
			// 设置报文体
			toAtmRspReport.setFieldValue(0, "0830");
			copyFields(devReqReport, 3, toAtmRspReport, 3);
			copyFields(devReqReport, 7, toAtmRspReport, 7);
			copyFields(devReqReport, 11, toAtmRspReport, 11);
			copyFields(devReqReport, 12, toAtmRspReport, 12);
			copyFields(devReqReport, 13, toAtmRspReport, 13);
			toAtmRspReport.setFieldValue(15, sysParamSet
					.get(IPSSysParamCode.IPS0001));
			toAtmRspReport.setFieldValue(39, rspCode);
			copyFields(devReqReport, 41, toAtmRspReport, 41);
			copyFields(devReqReport, 51, toAtmRspReport, 51);
			toAtmRspReport.setFieldValue(93, "00");
			if (sysParamSet.size() != 1) {
				// 签到 應答報文的附加信息
				String BF120Str = sysParamSet.get(IPSSysParamCode.IPS0002)
						+ sysParamSet.get(IPSSysParamCode.IPS0003)
						+ "00000000"+GenDateTime.getDateTime();
				toAtmRspReport.setFieldValue(120, BF120Str.getBytes(),
						IPSReportFieldType.IPS_BINARY);
			}
			return toAtmRspReport;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static IPSReport createT0_C003_RSP_REPORT_0004(String rspCode,
			byte[] keyValue, Map<String, String> sysParamSet,
			IpsDevInf devinfo, IpsTranscodeMap ipsTranscodeMap,
			Map<String, IPSReport> reports) {
		try {
			IPSReport devReqReport = reports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			IPSReport toAtmRspReport = new IPSReport();
			// 设置报文头
			toAtmRspReport.setHeaderFieldValue(2, devReqReport
					.getHeaderFieldValue(2));
			toAtmRspReport.setHeaderFieldValue(3, devReqReport
					.getHeaderFieldValue(3));
			toAtmRspReport.setHeaderFieldValue(4, devReqReport
					.getHeaderFieldValue(4));
			toAtmRspReport.setHeaderFieldValue(5, devReqReport
					.getHeaderFieldValue(5));
			// 设置报文体
			toAtmRspReport.setFieldValue(0, "0830");
			copyFields(devReqReport, 3, toAtmRspReport, 3);
			copyFields(devReqReport, 7, toAtmRspReport, 7);
			copyFields(devReqReport, 11, toAtmRspReport, 11);
			copyFields(devReqReport, 12, toAtmRspReport, 12);
			copyFields(devReqReport, 13, toAtmRspReport, 13);
			toAtmRspReport.setFieldValue(15, sysParamSet
					.get(IPSSysParamCode.IPS0001));
			toAtmRspReport.setFieldValue(39, rspCode);
			copyFields(devReqReport, 41, toAtmRspReport, 41);
			copyFields(devReqReport, 51, toAtmRspReport, 51);
			copyFields(devReqReport, 53, toAtmRspReport, 53);
			toAtmRspReport.setFieldValue(96, keyValue,
					IPSReportFieldType.IPS_BINARY);

			return toAtmRspReport;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static IPSReport createT0_C003_RSP_REPORT_0003(String rspCode,
			byte[] keyValue, Map<String, String> sysParamSet,
			IpsDevInf devinfo, IpsTranscodeMap ipsTranscodeMap,
			Map<String, IPSReport> reports) {
		try {
			IPSReport devReqReport = reports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			IPSReport toAtmRspReport = new IPSReport();
			// 设置报文头
			toAtmRspReport.setHeaderFieldValue(2, devReqReport
					.getHeaderFieldValue(2));
			toAtmRspReport.setHeaderFieldValue(3, devReqReport
					.getHeaderFieldValue(3));
			toAtmRspReport.setHeaderFieldValue(4, devReqReport
					.getHeaderFieldValue(4));
			toAtmRspReport.setHeaderFieldValue(5, devReqReport
					.getHeaderFieldValue(5));
			// 设置报文体
			toAtmRspReport.setFieldValue(0, "0830");
			copyFields(devReqReport, 3, toAtmRspReport, 3);
			copyFields(devReqReport, 7, toAtmRspReport, 7);
			copyFields(devReqReport, 11, toAtmRspReport, 11);
			copyFields(devReqReport, 12, toAtmRspReport, 12);
			copyFields(devReqReport, 13, toAtmRspReport, 13);
			toAtmRspReport.setFieldValue(15, sysParamSet
					.get(IPSSysParamCode.IPS0001));
			toAtmRspReport.setFieldValue(39, rspCode);
			copyFields(devReqReport, 41, toAtmRspReport, 41);
			copyFields(devReqReport, 42, toAtmRspReport, 42);
			copyFields(devReqReport, 51, toAtmRspReport, 51);
			copyFields(devReqReport, 53, toAtmRspReport, 53);
			toAtmRspReport.setFieldValue(96, keyValue,
					IPSReportFieldType.IPS_BINARY);
			return toAtmRspReport;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* 签到-应答报文 */
	private static IPSReport createT0_C003_RSP_REPORT_0001(String rspCode,
			Map<String, String> sysParamSet, IpsDevInf devinfo,
			IpsInTransflow ipsInTransflow, IpsTranscodeMap ipsTranscodeMap,
			Map<String, IPSReport> reports) {
		try {
			IPSReport devReqReport = reports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			IPSReport toAtmRspReport = new IPSReport();

			// 设置报文头
			toAtmRspReport.setHeaderFieldValue(2, devReqReport
					.getHeaderFieldValue(2));
			toAtmRspReport.setHeaderFieldValue(3, devReqReport
					.getHeaderFieldValue(3));
			toAtmRspReport.setHeaderFieldValue(4, devReqReport
					.getHeaderFieldValue(4));
			toAtmRspReport.setHeaderFieldValue(5, devReqReport
					.getHeaderFieldValue(5));
			// 设置报文体
			toAtmRspReport.setFieldValue(0, "0830");
			copyFields(devReqReport, 3, toAtmRspReport, 3);
			copyFields(devReqReport, 7, toAtmRspReport, 7);
			copyFields(devReqReport, 11, toAtmRspReport, 11);
			copyFields(devReqReport, 12, toAtmRspReport, 12);
			copyFields(devReqReport, 13, toAtmRspReport, 13);
			toAtmRspReport.setFieldValue(15, sysParamSet
					.get(IPSSysParamCode.IPS0001));
			toAtmRspReport.setFieldValue(39, rspCode);
			copyFields(devReqReport, 41, toAtmRspReport, 41);
			copyFields(devReqReport, 42, toAtmRspReport, 42);
			copyFields(devReqReport, 51, toAtmRspReport, 51);
			copyFields(devReqReport, 72, toAtmRspReport, 72);
			if (sysParamSet.size() != 1) {
				// 签到 應答報文的附加信息
				String BF120Str = sysParamSet.get(IPSSysParamCode.IPS0002)
						+ sysParamSet.get(IPSSysParamCode.IPS0003)
						+ GenDateTime.getDateTime();
				toAtmRspReport.setFieldValue(120, BF120Str.getBytes(),
						IPSReportFieldType.IPS_BINARY);
			}
			return toAtmRspReport;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* 余额查询-C003-C002-req交易 */
	private static IPSReport createC003_TO_C002_REQ_REPORT(String jnlno,
			IpsDevInf devinfo, IpsTranscodeMap inTransCodeMap,
			Map<String, IPSReport> reports) {
		try {

			IPSReport reqReportObj = reports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			IPSReport toCoreReqReport = new IPSReport();

			// 设置报文头
			toCoreReqReport.setHeaderFieldValue(2, inTransCodeMap
					.getCoremsgcode());
			toCoreReqReport.setHeaderFieldValue(3, inTransCodeMap
					.getCoretranscode());
			toCoreReqReport
					.setHeaderFieldValue(4, inTransCodeMap.getCorecode());
			// 设置报文体
			copyFields(reqReportObj, 2, toCoreReqReport, 2);
			toCoreReqReport.setFieldValue(10, "00000000");
			toCoreReqReport.setFieldValue(11, jnlno);
			copyFields(reqReportObj, 12, toCoreReqReport, 12);
			copyFields(reqReportObj, 13, toCoreReqReport, 13);
			copyFields(reqReportObj, 15, toCoreReqReport, 20);
			toCoreReqReport.setFieldValue(18, "6011");
			toCoreReqReport.setFieldValue(21, "AT");
			copyFields(reqReportObj, 22, toCoreReqReport, 22);
			copyFields(reqReportObj, 25, toCoreReqReport, 25);
			toCoreReqReport.setFieldValue(29, "00000000");
			toCoreReqReport.setFieldValue(30, "00000000");
			if (reqReportObj.getFieldValue(35).length() > 0) {
				copyFields(reqReportObj, 35, toCoreReqReport, 35);
			}
			copyFields(reqReportObj, 41, toCoreReqReport, 41);
			// 42
			toCoreReqReport.setFieldValue(42, devinfo.getInstcode());
			copyFields(reqReportObj, 51, toCoreReqReport, 51);
			// 52
			String pinstr = "";

			// IPSTODO 进行pin转换,
			/*
			 * pinstr = CrypTool.pinTransfer(reqReportObj.getFieldValue(52),
			 * accountNo, oriMk, oriPink, oriAcctFlag, oriPinArith, oriMArith,
			 * destMkey, destPinKey, destAcctFlag, destPinArith, destMArith, ip,
			 * port, timeout, oriEncFlag, destEncFlag);binary
			 */
			toCoreReqReport.setFieldValue(52, "1EC6D6086EE024FC");

			copyFields(reqReportObj, 53, toCoreReqReport, 53);
			// 99
			toCoreReqReport.setFieldValue(42, devinfo.getUunioncode());
			if (reqReportObj.getFieldValue(122).length() > 0) {
				copyFields(reqReportObj, 102, toCoreReqReport, 122);
			}
			return toCoreReqReport;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* 余额查询-C002-C003-rsp交易 */
	private static IPSReport createC002_TO_C003_RSP_REPORT(String rspCode,
			Map<String, IPSReport> reports) {
		try {
			IPSReport devReqReport = reports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			IPSReport coreRspReport = reports
					.get(IPSReportFactory.FROM_C002_RSP_REPORT);

			IPSReport toAtmRspReport = new IPSReport();

			// 设置报文头
			toAtmRspReport.setHeaderFieldValue(2, devReqReport
					.getHeaderFieldValue(2));
			toAtmRspReport.setHeaderFieldValue(3, devReqReport
					.getHeaderFieldValue(3));
			toAtmRspReport.setHeaderFieldValue(4, devReqReport
					.getHeaderFieldValue(4));
			toAtmRspReport.setHeaderFieldValue(5, devReqReport
					.getHeaderFieldValue(5));
			// 设置报文体
			toAtmRspReport.setFieldValue(0, "0210");
			copyFields(devReqReport, 2, toAtmRspReport, 2);
			copyFields(devReqReport, 3, toAtmRspReport, 3);
			copyFields(devReqReport, 7, toAtmRspReport, 7);
			copyFields(devReqReport, 11, toAtmRspReport, 11);
			copyFields(devReqReport, 12, toAtmRspReport, 12);
			copyFields(devReqReport, 13, toAtmRspReport, 13);
			if (null != coreRspReport) {
				copyFields(coreRspReport, 10, toAtmRspReport, 14);
				copyFields(coreRspReport, 14, toAtmRspReport, 15);
				copyFields(coreRspReport, 29, toAtmRspReport, 28);
				copyFields(coreRspReport, 37, toAtmRspReport, 37);
			} else {
				toAtmRspReport.setFieldValue(14, "0");
				toAtmRspReport.setFieldValue(15, "0");
				toAtmRspReport.setFieldValue(28, "0");
				toAtmRspReport.setFieldValue(37, "0");
			}
			copyFields(devReqReport, 18, toAtmRspReport, 18);
			copyFields(devReqReport, 22, toAtmRspReport, 22);
			copyFields(devReqReport, 25, toAtmRspReport, 25);

			toAtmRspReport.setFieldValue(39, rspCode);
			copyFields(coreRspReport, 40, toAtmRspReport, 40);
			copyFields(devReqReport, 41, toAtmRspReport, 41);
			copyFields(devReqReport, 42, toAtmRspReport, 42);
			copyFields(devReqReport, 49, toAtmRspReport, 49);
			copyFields(devReqReport, 51, toAtmRspReport, 51);
			copyFields(devReqReport, 53, toAtmRspReport, 53);
			if (null != coreRspReport
					&& coreRspReport.getFieldValue(54).length() > 0) {
				/*
				 * concat(substring($Start/group/FromCoreMsgRsp/root/BF54, 1,
				 * 13), substring($Start/group/FromCoreMsgRsp/root/BF54, 1, 1),
				 * substring($Start/group/FromCoreMsgRsp/root/BF54, 14, 12),
				 * substring($Start/group/FromCoreMsgRsp/root/BF54, 1, 1),
				 * substring($Start/group/FromCoreMsgRsp/root/BF54, 26,12))
				 */
				String bf54 = coreRspReport.getFieldValue(54).trim();
				String bf54_1_1 = bf54.substring(1, 2);
				String bf54_1_13 = bf54.substring(1, 14);
				String bf54_14_12 = bf54.substring(14, 26);
				String bf54_26_12 = bf54.substring(26, 38);
				String value = bf54_1_13 + bf54_1_1 + bf54_14_12 + bf54_1_1
						+ bf54_26_12;
				toAtmRspReport.setFieldValue(54, value);

				String bf61 = coreRspReport.getFieldValue(61);
				String bf61hexStr = ISOUtil.byte2HexNoSpaceStr(bf61.getBytes(),
						bf61.getBytes().length);
				String value61 = bf61hexStr + coreRspReport.getFieldValue(44);
				if (!"".equals(value61))
					toAtmRspReport.setFieldValue(61, value);
			} else {
				toAtmRspReport.setFieldValue(54, "");
			}

			copyFields(devReqReport, 72, toAtmRspReport, 72);
			toAtmRspReport.setFieldValue(93, rspCode);
			return toAtmRspReport;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void copyFields(IPSReport srcReport, int srcFieldNo,
			IPSReport tagReport, int tagFieldNo) throws ISOException {
		if (null != srcReport) {
			String v = srcReport.getFieldValue(srcFieldNo);
			if (!"".equals(v)) {
				tagReport.setFieldValue(tagFieldNo, srcReport
						.getFieldValue(srcFieldNo));
			}
		}

	}

}
