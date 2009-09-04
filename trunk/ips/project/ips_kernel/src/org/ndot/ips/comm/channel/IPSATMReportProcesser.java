package org.ndot.ips.comm.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ndot.ips.comm.IPSReportChannel;
import org.ndot.ips.comm.IPSReportProcesser;
import org.ndot.ips.constant.IPSChannelId;
import org.ndot.ips.constant.IPSConstantConfig;
import org.ndot.ips.constant.IPSInnerChannelID;
import org.ndot.ips.constant.IPSInnerErrorCode;
import org.ndot.ips.constant.IPSKeyType;
import org.ndot.ips.constant.IPSReportFactory;
import org.ndot.ips.constant.IPSSysParamCode;
import org.ndot.ips.constant.IPSTransTypes;
import org.ndot.ips.db.pojo.IpsCardbin;
import org.ndot.ips.db.pojo.IpsDevInf;
import org.ndot.ips.db.pojo.IpsDevStat;
import org.ndot.ips.db.pojo.IpsErrrspcodeMapId;
import org.ndot.ips.db.pojo.IpsInTransflow;
import org.ndot.ips.db.pojo.IpsJnlToday;
import org.ndot.ips.db.pojo.IpsKeyMng;
import org.ndot.ips.db.pojo.IpsKeyMngId;
import org.ndot.ips.db.pojo.IpsRspCode;
import org.ndot.ips.db.pojo.IpsRspCodeId;
import org.ndot.ips.db.pojo.IpsTranscodeMap;
import org.ndot.ips.log.IPSLogLevel;
import org.ndot.ips.security.CrypTool;
import org.ndot.ips.security.Des;
import org.ndot.ips.util.FormatStrings;
import org.ndot.ips.util.GenDateTime;

import com.nasoft.IPSReport;
import com.nasoft.IPSReportFieldType;
import com.nasoft.iso.ISOException;
import com.nasoft.iso.ISOUtil;

/**
 * ��С����ѧ�� ֮ J2EE��
 * 
 * ��Ŀ���ƣ�IPSNBComm
 * 
 * �ļ����� IPSATMReportProcesser.java
 * 
 * �� ��:
 * 
 * �� ��: NDot
 * 
 * ����ʱ��: 2009 2009-6-21
 * 
 */
public class IPSATMReportProcesser extends IPSReportProcesser {
	// ���״����������漰���ñ�����Ϣ
	Map<String, IPSReport> memReports = new HashMap<String, IPSReport>();
	// �жϽ������� 0-����δ���� 1-���ڽ��ڽ��� 2-�������� 3-�����ཻ��
	String transType = "";
	// ����ڲ����״���
	IpsTranscodeMap inTransCodeMap = null;
	// ����豸��Ϣ
	IpsDevInf devinfo = null;
	// �ñʽ��׵ı�����ˮ��
	String jnlno = "00000000";
	// ��ǰ���ͽ��׵� �豸iP
	String devIP = "";

	public IPSATMReportProcesser() {
		super();
		setLog(Logger.getLogger(IPSATMReportProcesser.class));
	}

	@Override
	public IPSReport processer(IPSReportChannel channel,
			IPSReport reqReportObj, String devIP) {
		try {
			this.devIP = devIP;
			jnlno = String.valueOf(getSeq().next());
			String innerMethodRenrn = "";// �ڲ�����������

			writeLog(IPSLogLevel.INFO, "IPS���յ�ATM-C003������(XML)�� \n"
					+ reqReportObj.formatToXml() + " \n", reqReportObj
					.getBody());

			// ��¼ATM�͵�������Ϣ
			memReports.put(IPSReportFactory.FROM_C003_REQ_REPORT, reqReportObj);

			// �жϽ�������
			innerMethodRenrn = checkReportType(reqReportObj, channel
					.getChannelId());
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// ����豸��Ϣ
			innerMethodRenrn = getDevinfo(reqReportObj.getFieldValue(41));
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// ����ڲ����״���
			innerMethodRenrn = getInTransCode(reqReportObj, transType, channel
					.getChannelId());
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// �����ʼ��ˮ
			innerMethodRenrn = insertJNO(reqReportObj);
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// ���н��״�������ø�ATM��Ӧ����
			return doBusiness(channel, reqReportObj);

		} catch (Exception e) {
			writeLog(IPSLogLevel.INFO, "IPSϵͳ�쳣 ATM-C003 ���� �ı��ı�����.");
			return null;
		}
	}

	/**
	 * ���뱾����ˮ
	 * 
	 * @param reqReportObj
	 * @return ���뱾����ˮ�����Ϣ��
	 */
	private String insertJNO(IPSReport reqReportObj) {
		String result = IPSInnerErrorCode.IPS000;
		try {
			String dateTime = GenDateTime.getDateTime();
			IpsJnlToday jnl = new IpsJnlToday();
			jnl.setLocaljnlno(jnlno);
			jnl.setLocaldatetime(dateTime);
			jnl.setIntranscode(inTransCodeMap.getIntranscode());
			jnl.setTranstype(this.transType);
			jnl.setErrcode("000");
			jnl.setChanlcode(inTransCodeMap.getChanlcode());
			jnl.setChanlmsgcode(inTransCodeMap.getChanlmsgcode());
			jnl.setChanltranscode(inTransCodeMap.getChanltranscode());
			jnl.setChanljnlno(reqReportObj.getFieldValue(11));
			jnl.setChanlrspcode("");
			jnl.setCorecode(inTransCodeMap.getCorecode());
			jnl.setCoremsgcode(inTransCodeMap.getCoremsgcode());
			jnl.setCoretranscode(inTransCodeMap.getCoretranscode());
			jnl.setCoreclrdate("");
			jnl.setCorerspcode("");
			jnl.setCorejnlno("");
			jnl.setHostcode(inTransCodeMap.getHostcode());
			jnl.setHostmsgcode(inTransCodeMap.getHostmsgcode());
			jnl.setHosttranscode(inTransCodeMap.getHosttranscode());
			jnl.setHostclrdate("");
			jnl.setHostjnlno("");
			jnl.setHostrspcode("");
			jnl.setDevid(reqReportObj.getFieldValue(41));
			jnl.setCardno(reqReportObj.getFieldValue(2));
			jnl.setTranamt(reqReportObj.getFieldValue(4));
			jnl.setCardexpdate("");
			jnl.setDevtranstime(reqReportObj.getFieldValue(12));
			jnl.setDevtransdate(reqReportObj.getFieldValue(13));
			jnl.setMctcode(reqReportObj.getFieldValue(42));
			jnl.setMcttype(reqReportObj.getFieldValue(18));
			jnl.setTrancurrcode(reqReportObj.getFieldValue(49));
			jnl.setInputtype(reqReportObj.getFieldValue(22));
			jnl.setCondcode(reqReportObj.getFieldValue(25));
			jnl.setTrack2(reqReportObj.getFieldValue(35));
			jnl.setTrack3(reqReportObj.getFieldValue(36));
			jnl.setAddbal("");
			jnl.setOpeninstcode("");
			jnl.setAccfrom(reqReportObj.getFieldValue(102));
			jnl.setAccto(reqReportObj.getFieldValue(103));
			jnl.setFee1("0");
			jnl.setFee2("0");
			jnl.setFee3("0");
			jnl.setTransinstcode("");
			jnl.setHostchkflag("1");
			jnl.setTermchkflag("1");
			jnl.setMsgreasoncode(reqReportObj.getFieldValue(51));
			jnl.setBatchno(reqReportObj.getFieldValue(72));
			jnl.setOrigdata(reqReportObj.getFieldValue(90));
			jnl.setAuthrspcode("");
			jnl.setChkflag("1");
			jnl.setCoremodflag("1");
			jnl.setHostmodflag("1");
			jnl.setTranstat("");
			jnl.setLocaldateyear(dateTime.substring(0, 4));
			jnl.setLocaldatemon(dateTime.substring(4, 6));
			jnl.setLocaldateday(dateTime.substring(6, 8));
			getBusinessDBServices().saveIpsJnlToday(jnl);
		} catch (Exception e) {
			result = IPSInnerErrorCode.IPS310;
		}
		return result;
	}

	private String getDevinfo(String devid) {
		devinfo = getBusinessDBServices().findIpsDevInfById(devid);
		if (null == devinfo) {
			writeLog(IPSLogLevel.INFO, "IPS �ն˱�ʶ��Ϊ��" + devid
					+ " ���ն��豸δ���壬ϵͳ������룺��" + IPSInnerErrorCode.IPS302 + "��");
			return IPSInnerErrorCode.IPS302;
		} else {
			return IPSInnerErrorCode.IPS000;
		}
	}

	@SuppressWarnings("unchecked")
	private IPSReport doBusiness(IPSReportChannel channel,
			IPSReport reqReportObj) {
		IPSReport rspToATM = null;
		try {
			// ��ý��״������̶�����Ϣ
			String[] conditions = new String[] { "INTRANSCODE='"
					+ inTransCodeMap.getIntranscode() + "'" };
			List<IpsInTransflow> ipsInTransflows = this.getDbJdbcTool()
					.queryInTransflowForList(IpsInTransflow.class,
							"IPS_IN_TRANSFLOW", null, conditions);

			for (int i = 0; i < ipsInTransflows.size(); i++) {
				IpsInTransflow curentIpsInTransflow = getProcessInfo(
						ipsInTransflows, String.valueOf(i));
				// ���й������
				String result = publicCheck(curentIpsInTransflow, reqReportObj);
				if (!IPSInnerErrorCode.IPS000.equals(result)) {// �������ʧ�ܣ���֯ATMӦ����
					// result �ǹ������Ĵ�����룬����result��Ϣ��֯����Ӧ���� IPSTODO
					// ʧ�ܱ���
					String reqRspCode = geReqRspErrorCode(result);
					return IPSReportFactory.getReport(
							IPSReportFactory.C002_TO_C003_ERR_RSP_REPORT,
							jnlno, null, null, reqRspCode, devinfo, null, null,
							memReports);
				}
				// �������ɹ�

				if (curentIpsInTransflow.getQueryorgjnlflag().equals("1")) {// ����Զ��ˮ
					// IPSTODO

				}
				String destChannelId = curentIpsInTransflow.getDestnodeid();
				if (IPSChannelId.CORE.equals(destChannelId)) {// �����ĵ�������
					// ��֯�����ĵ�������
					IPSReport toCoreRsqReport = null;
					if (IPSTransTypes.IPSTRAN1001.equals(inTransCodeMap
							.getIntranscode())) {
						// ����ѯ
						toCoreRsqReport = IPSReportFactory.getReport(
								IPSReportFactory.C003_TO_C002_REQ_REPORT_1001,
								jnlno, null, null, "", devinfo,
								curentIpsInTransflow, inTransCodeMap,
								this.memReports);
						atmToCorePin(reqReportObj, toCoreRsqReport);
						memReports.put(
								IPSReportFactory.C003_TO_C002_REQ_REPORT_1001,
								toCoreRsqReport);
					}

					if (IPSTransTypes.IPSTRAN1004.equals(inTransCodeMap
							.getIntranscode())) {
						// ȡ��
						toCoreRsqReport = IPSReportFactory.getReport(
								IPSReportFactory.C003_TO_C002_REQ_REPORT_1004,
								jnlno, null, null, "", devinfo,
								curentIpsInTransflow, inTransCodeMap,
								this.memReports);
						atmToCorePin(reqReportObj, toCoreRsqReport);
						memReports.put(
								IPSReportFactory.C003_TO_C002_REQ_REPORT_1004,
								toCoreRsqReport);
					}
					writeLog(IPSLogLevel.INFO, "IPS��֯ �� ����-C002 ���� ����(XML)�� \n"
							+ toCoreRsqReport.formatToXml() + " \n",
							toCoreRsqReport.getBody());

					// ����ķ��������ģ������ܺ��ĵ�Ӧ����
					IPSReport coreRspReport = ((IPSReportChannel) channel
							.getCtx().getBean(destChannelId))
							.callClient(reqToCore(toCoreRsqReport));
					if (null != coreRspReport) {
						writeLog(IPSLogLevel.INFO,
								"IPS���� ����-C002�� Ӧ�� ����(XML)�� \n"
										+ coreRspReport.formatToXml() + " \n",
								coreRspReport.getBody());
						memReports.put(IPSReportFactory.FROM_C002_RSP_REPORT,
								coreRspReport);
					} else {
						// ������Ӧ��
						writeLog(IPSLogLevel.INFO,
								"IPS���� ����-C002 �� ATM-C003 �� Ӧ�� ���ĳ�ʱ");
						ProcessOverTime(memReports);
						// ʧ�ܱ���
						String reqRspCode = geReqRspErrorCode(IPSInnerErrorCode.IPS313);
						return null;
					}
				} else if (IPSChannelId.CUPS.equals(destChannelId)) {// ��������������
					IPSReport cupsRspReport = ((IPSReportChannel) channel
							.getCtx().getBean(destChannelId))
							.callClient(reqToCUPS(reqReportObj));

					memReports.put(String.valueOf(i), cupsRspReport);

				} else if ("end".equals(destChannelId)) {
					if ("0".equalsIgnoreCase(curentIpsInTransflow.getId()
							.getProcessno())) {
						// ��ǰ�����ǹ����ཻ��
						rspToATM = doManagerBusiness(curentIpsInTransflow,
								reqReportObj);
					} else {
						// ��֯��ATM��Ӧ����
						String rspCodeFromC002 = "";
						String rspCodeToC003 = "";
						IPSReport coreRspReport = memReports
								.get(IPSReportFactory.FROM_C002_RSP_REPORT);

						if (null != coreRspReport) {// �ý����к���Ӧ��
							// ��ú���Ӧ����
							rspCodeFromC002 = coreRspReport.getFieldValue(39);
							IpsRspCodeId id = new IpsRspCodeId(
									IPSChannelId.ATM, IPSChannelId.CORE,
									rspCodeFromC002);
							IpsRspCode rspCodeInfo = this
									.getBusinessDBServices()
									.findIpsRspCodeById(id);
							rspCodeToC003 = rspCodeInfo.getReqrspcode();
							if (IPSTransTypes.IPSTRAN1001.equals(inTransCodeMap
									.getIntranscode())) {
								// ����ѯ
								rspToATM = IPSReportFactory
										.getReport(
												IPSReportFactory.C002_TO_C003_RSP_REPORT_1001,
												jnlno, null, null,
												rspCodeToC003, devinfo, null,
												null, memReports);
							}
							if (IPSTransTypes.IPSTRAN1004.equals(inTransCodeMap
									.getIntranscode())) {
								// ȡ��
								rspToATM = IPSReportFactory
										.getReport(
												IPSReportFactory.C002_TO_C003_RSP_REPORT_1004,
												jnlno, null, null,
												rspCodeToC003, devinfo, null,
												null, memReports);
							}
						}

						// ���º�����������
						{
							// TODO
						}
						// ������ˮ
						String uresult = updeatJNO(jnlno, coreRspReport,
								rspToATM);
					}

				}

			}

		} catch (ISOException e) {
			e.printStackTrace();
		}
		return rspToATM;
	}

	private void atmToCorePin(IPSReport reqReportObj, IPSReport toCoreRsqReport) {
		try {
			IPSReport temReqReportObj = memReports
					.get(IPSReportFactory.FROM_C003_REQ_REPORT);
			// ����Կ
			String mk = getIpsConstantConfig().getMainKey();
			IpsKeyMng oriPinkey = getIpsKeyMng(IPSInnerChannelID.ATM,
					IPSChannelId.ATM, reqReportObj.getFieldValue(41),
					IPSKeyType.PINKEY);
			IpsKeyMng pinkey = getIpsKeyMng(IPSInnerChannelID.ATM,
					IPSChannelId.CORE, "0000000000", IPSKeyType.PINKEY);
			// ����pinת��
			String hexStr = CrypTool.pinTransfer(temReqReportObj
					.getFieldValue(52), temReqReportObj.getFieldValue(2), mk,
					oriPinkey.getKeyvalue(), "1", oriPinkey.getEncmethod(),
					"DES", mk, pinkey.getKeyvalue(), "1", pinkey.getPinmode(),
					"DES", "", "", "", "0", "0");
			toCoreRsqReport.setFieldValue(52, hexStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IPSReport doManagerBusiness(IpsInTransflow ipsInTransflow,
			IPSReport reqReportObj) {
		try {
			String intranscode = inTransCodeMap.getIntranscode();
			if (IPSTransTypes.IPSTRAN0001.equalsIgnoreCase(intranscode)) {
				// ǩ��
				return do0001_0002(ipsInTransflow, "1");
			}
			if (IPSTransTypes.IPSTRAN0002.equalsIgnoreCase(intranscode)) {
				// ǩ��
				return do0001_0002(ipsInTransflow, "0");
			}
			if (IPSTransTypes.IPSTRAN0003.equalsIgnoreCase(intranscode)) {
				// ��ʼCDK
				return do0003(reqReportObj, ipsInTransflow);
			}
			if (IPSTransTypes.IPSTRAN0004.equalsIgnoreCase(intranscode)) {
				// ��ʼPINMAC
				return do0004(reqReportObj, ipsInTransflow);
			}
			if (IPSTransTypes.IPSTRAN0006.equalsIgnoreCase(intranscode)) {
				// ״̬����
				return do0006(reqReportObj, ipsInTransflow);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private IPSReport do0006(IPSReport reqReportObj,
			IpsInTransflow ipsInTransflow) {
		// ״̬����
		String errorCode = IPSInnerErrorCode.IPS000;
		try {
			IpsDevStat devstat = getBusinessDBServices().findIpsDevStatById(
					devinfo.getDevid());
			devstat.setUpddatetime(reqReportObj.getFieldValue(13)
					+ reqReportObj.getFieldValue(12));
			devstat.setDevstat(reqReportObj.getFieldValue(24));
			devstat.setDeverrinf(reqReportObj.getFieldValue(122));
			getBusinessDBServices().updeateIpsDevStat(devstat);

		} catch (Exception e) {
			e.printStackTrace();
			errorCode = IPSInnerErrorCode.IPS330;
		}
		Map<String, String> sysParamSet = new HashMap<String, String>();
		// ������������
		sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
				.findIpsSysParamById(IPSSysParamCode.IPS0001).getParamvalue());
		String reqRspCode = "";
		if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
			String rsprspcode = "02";// �˳ɹ�
			sysParamSet.put(IPSSysParamCode.IPS0002, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0002)
					.getParamvalue());
			// ���Ʋ����汾��
			sysParamSet.put(IPSSysParamCode.IPS0003, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0003)
					.getParamvalue());
			rsprspcode = "05";// ״̬֪ͨ�ɹ�
			reqRspCode = getReqRspcode(rsprspcode);
		} else {
			// �ն�״̬֪ͨ����ʧ��
			reqRspCode = geReqRspErrorCode(errorCode);
		}
		return IPSReportFactory.getReport(
				IPSReportFactory.T0_C003_RSP_REPORT_0006, jnlno, null,
				sysParamSet, reqRspCode, devinfo, ipsInTransflow,
				this.inTransCodeMap, this.memReports);
	}

	private IPSReport do0004(IPSReport reqReportObj,
			IpsInTransflow ipsInTransflow) {

		try {
			String errorCode = IPSInnerErrorCode.IPS000;
			String keyValue = "";
			try {
				String BF53 = reqReportObj.getFieldValue(53);
				String devid = this.devinfo.getDevid();
				String mk = this.getIpsConstantConfig().getMainKey();
				String mmkey = genIpsKeyMng(devid, IPSKeyType.MMKEY)
						.getKeyvalue();
				IpsKeyMng key = null;
				if (BF53.startsWith("1")) {
					key = genIpsKeyMng(devid, IPSKeyType.MACKEY);
				} else {
					key = genIpsKeyMng(devid, IPSKeyType.PINKEY);
				}
				String keyLen = key.getKeylen();
				keyValue = Des.genKeyByLen(keyLen);
				// ������Կ
				String saveKeyValue = CrypTool.getKey(mk, "DES", keyValue, key
						.getEncmethod(), mmkey);
				key.setKeyvalue(saveKeyValue);
				this.getSecurityDBService().updateIpsKeyMng(key);
			} catch (Exception e) {
				errorCode = IPSInnerErrorCode.IPS322;
			}

			Map<String, String> sysParamSet = new HashMap<String, String>();
			// ������������
			sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0001)
					.getParamvalue());
			String reqRspCode = "";
			if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
				String rspRspCode = "03";
				reqRspCode = getReqRspcode(rspRspCode);
			} else {
				// ʧ�ܱ���
				reqRspCode = geReqRspErrorCode(errorCode);
			}
			IPSReport rspReport = IPSReportFactory.getReport(
					IPSReportFactory.T0_C003_RSP_REPORT_0004, jnlno, ISOUtil
							.hex2byte(keyValue), sysParamSet, reqRspCode,
					devinfo, ipsInTransflow, this.inTransCodeMap,
					this.memReports);
			try {
				String macbuff = rspReport.getMacbuf();
				// ����Կ
				String mk = getIpsConstantConfig().getMainKey();
				IpsKeyMng mmk = getIpsKeyMng(IPSInnerChannelID.ATM,
						IPSChannelId.ATM, reqReportObj.getFieldValue(41),
						IPSKeyType.MMKEY);
				IpsKeyMng mackey = getIpsKeyMng(IPSInnerChannelID.ATM,
						IPSChannelId.ATM, reqReportObj.getFieldValue(41),
						IPSKeyType.MACKEY);
				String hexStr = CrypTool.genMacReturn8Bytes(macbuff, mk, "DES",
						mmk.getKeyvalue(), mmk.getEncmethod(), keyValue, mackey
								.getEncmethod(), "ASC");
				byte[] BF128 = ISOUtil.hex2byte(hexStr);
				rspReport.setFieldValue(128, BF128,
						IPSReportFieldType.IPS_BINARY);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return rspReport;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private IPSReport do0003(IPSReport reqReportObj,
			IpsInTransflow ipsInTransflow) {

		try {
			String errorCode = IPSInnerErrorCode.IPS000;
			String BF96 = reqReportObj.getFieldValue(96);
			String devid = this.devinfo.getDevid();
			String mk = this.getIpsConstantConfig().getMainKey();
			IpsKeyMng cdkey = genIpsKeyMng(devid, IPSKeyType.CDKEY);
			String cdkValue = cdkey.getKeyvalue();
			// ����У����
			String cv = CrypTool.genCheckValue(mk, "DES", cdkValue);
			cv += "30303030";
			String mmKeyValue = "";
			if (cv.equalsIgnoreCase(BF96)) {
				// У����ƥ��
				// �������ݿ���������cdk
				IpsKeyMng mmkey = genIpsKeyMng(devid, IPSKeyType.MMKEY);
				String keyLen = mmkey.getKeylen();
				mmKeyValue = Des.genKeyByLen(keyLen);
				// ������Կ
				String saveKeyValue = CrypTool.getKey(mk, "DES", cdkValue,
						cdkey.getEncmethod(), mmKeyValue);
				mmkey.setKeyvalue(saveKeyValue);
				this.getSecurityDBService().updateIpsKeyMng(mmkey);
			} else {
				errorCode = IPSInnerErrorCode.IPS323;
			}
			Map<String, String> sysParamSet = new HashMap<String, String>();
			// ������������
			sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0001)
					.getParamvalue());
			String reqRspCode = "";
			if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
				String rspRspCode = "04";
				reqRspCode = getReqRspcode(rspRspCode);
			} else {
				// ʧ�ܱ���
				reqRspCode = geReqRspErrorCode(errorCode);
			}
			return IPSReportFactory.getReport(
					IPSReportFactory.T0_C003_RSP_REPORT_0003, jnlno, ISOUtil
							.hex2byte(mmKeyValue), sysParamSet, reqRspCode,
					devinfo, ipsInTransflow, this.inTransCodeMap,
					this.memReports);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private IpsKeyMng genIpsKeyMng(String devid, String keyType) {
		IpsKeyMngId ipsKeyMngId = new IpsKeyMngId(IPSInnerChannelID.ATM,
				IPSChannelId.ATM, devid, keyType);
		IpsKeyMng ipskey = getSecurityDBService()
				.findIpsKeyMngById(ipsKeyMngId);
		return ipskey;
	}

	private IPSReport do0001_0002(IpsInTransflow ipsInTransflow, String signStat) {
		// ǩ��ǩ�˽���
		String errorCode = IPSInnerErrorCode.IPS000;
		try {
			IpsDevStat devstat = getBusinessDBServices().findIpsDevStatById(
					devinfo.getDevid());
			if (null == devstat) {
				devstat = new IpsDevStat(devinfo.getDevid());
				devstat.setSignstat(signStat);
				devstat.setSigndatetime(GenDateTime.getDateTime());
				getBusinessDBServices().saveIpsDevStat(devstat);
			} else {
				devstat.setSignstat(signStat);
				devstat.setSigndatetime(GenDateTime.getDateTime());
				getBusinessDBServices().updeateIpsDevStat(devstat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorCode = IPSInnerErrorCode.IPS329;
		}
		Map<String, String> sysParamSet = new HashMap<String, String>();
		// ������������
		sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
				.findIpsSysParamById(IPSSysParamCode.IPS0001).getParamvalue());
		String reqRspCode = "";
		if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
			String rsprspcode = "02";// �˳ɹ�

			if ("1".equalsIgnoreCase(signStat)) {
				// ����汾��
				sysParamSet.put(IPSSysParamCode.IPS0002,
						getBusinessDBServices().findIpsSysParamById(
								IPSSysParamCode.IPS0002).getParamvalue());
				// ���Ʋ����汾��
				sysParamSet.put(IPSSysParamCode.IPS0003,
						getBusinessDBServices().findIpsSysParamById(
								IPSSysParamCode.IPS0003).getParamvalue());
				rsprspcode = "01";// ǩ���ɹ�
			}
			reqRspCode = getReqRspcode(rsprspcode);
		} else {
			// ǩ��/��ʧ��
			reqRspCode = geReqRspErrorCode(errorCode);
		}
		return IPSReportFactory.getReport(
				IPSReportFactory.T0_C003_RSP_REPORT_0001, jnlno, null,
				sysParamSet, reqRspCode, devinfo, ipsInTransflow,
				this.inTransCodeMap, this.memReports);
	}

	/**
	 * ����ƽ̨������룬��ȡ���󷽵�Ӧ����
	 * 
	 * @param errorCode
	 * @return
	 */
	private String geReqRspErrorCode(String errorCode) {
		String reqRspCode;
		IpsErrrspcodeMapId errId = new IpsErrrspcodeMapId(errorCode,
				IPSChannelId.ATM);
		reqRspCode = this.getIpsConstantConfig().getIpsErrrspcodeMapSet().get(
				errId).getReqrspcode();
		return reqRspCode;
	}

	/**
	 * ���ݽӿڶ����rsprspcode��������󷽵�Ӧ����reqrspcode
	 * 
	 * @param rsprspcode
	 * @return
	 */
	private String getReqRspcode(String rsprspcode) {
		String reqsyscode = IPSChannelId.ATM;
		String rspsyscode = IPSChannelId.ATMP;

		IpsRspCodeId rspCodeId = new IpsRspCodeId(reqsyscode, rspsyscode,
				rsprspcode);

		String reqRspCode = getIpsConstantConfig().getIpsRspCodeSet().get(
				rspCodeId).getReqrspcode();
		return reqRspCode;
	}

	/**
	 * ���г�ʱ����
	 * 
	 * @param memReports
	 */
	private void ProcessOverTime(Map<String, IPSReport> memReports) {

	}

	private String updeatJNO(String jnlno2, IPSReport coreRspReport,
			IPSReport toATMRspReport) {

		String result = "000";
		try {

			IpsJnlToday jnl = getBusinessDBServices()
					.findIpsJnlTodayById(jnlno);
			jnl.setChanlrspcode(toATMRspReport.getFieldValue(39));
			jnl.setCoreclrdate(coreRspReport.getFieldValue(14));
			jnl.setCorejnlno(coreRspReport.getFieldValue(5));
			jnl.setCorerspcode(coreRspReport.getFieldValue(39));
			jnl.setCardexpdate(coreRspReport.getFieldValue(54));
			jnl.setAddbal(coreRspReport.getFieldValue(54));
			jnl.setOpeninstcode(coreRspReport.getFieldValue(40));
			jnl.setTransinstcode(coreRspReport.getFieldValue(99));
			jnl.setRefnumber(coreRspReport.getFieldValue(37));
			jnl.setAuthrspcode(coreRspReport.getFieldValue(38));
			jnl.setCoremodflag("1");
			if (toATMRspReport.getFieldValue(39).equals("00")) {
				jnl.setTranstat("1");
			} else {
				jnl.setTranstat("2");
			}

			getBusinessDBServices().updateIpsJnlToday(jnl);
		} catch (Exception e) {
			result = "001";
		}
		return result;
	}

	private String getInTransCode(IPSReport reqReportObj, String transType,
			String channelId) {
		String result = IPSInnerErrorCode.IPS000;
		try {
			String channelCode = "";// ��������
			String channelReportCode = "";// ���������� TODO�� Ҫ��ʵ���ݿ⡿
			String channelTransCode = "";// ����������
			if (IPSChannelId.CORE.equals(channelId)) {// ���Ժ���
				channelCode = reqReportObj.getHeaderFieldValue(4);
				channelReportCode = reqReportObj.getHeaderFieldValue(2);
				channelTransCode = reqReportObj.getHeaderFieldValue(3);
			} else {// ����ATM
				channelCode = reqReportObj.getFieldValue(7);
				channelReportCode = reqReportObj.getFieldValue(0);
				channelTransCode = reqReportObj.getFieldValue(3);
			}
			String[] conditions = new String[] {
					"TRANSTYPE='" + transType + "'",
					"CHANLCODE='" + channelCode + "'",
					"CHANLMSGCODE='" + channelReportCode + "'",
					"CHANLTRANSCODE='" + channelTransCode + "'" };
			this.inTransCodeMap = (IpsTranscodeMap) this.getDbJdbcTool()
					.queryForObject(IpsTranscodeMap.class, "IPS_TRANSCODE_MAP",
							null, conditions);
		} catch (Exception e) {
			writeLog(IPSLogLevel.INFO, "IPS �����޷�ʶ��ϵͳ������룺��"
					+ IPSInnerErrorCode.IPS315 + "��");
			result = IPSInnerErrorCode.IPS315;
		}
		return result;
	}

	private String checkReportType(IPSReport reqReportObj, String channelId)
			throws ISOException {
		String result = IPSInnerErrorCode.IPS000;
		try {

			// �жϽ������� 0-����δ���� 1-���ڽ��ڽ��� 2-�������� 3-�����ཻ��
			String msgType = reqReportObj.getFieldValue(0);
			// ���ʺ�
			String mainNo = reqReportObj.getFieldValue(2);
			// ת������
			String outNo = reqReportObj.getFieldValue(102);
			// ת������
			String inNo = reqReportObj.getFieldValue(103);
			// ���ױ�־
			String transTag = reqReportObj.getFieldValue(20);
			if (msgType.indexOf("08") > -1) {
				this.transType = "3";
				return result;
			}
			if (transTag.startsWith("1")) {// �۽���
				this.transType = "1";
				return result;
			}
			List<IpsCardbin> ipsCardbinList = this.getIpsConstantConfig()
					.getIpsCardbinList();
			if (null != mainNo && !"".equals(mainNo)) {
				// �����˺�
				String tranT = CheckCountType(ipsCardbinList, mainNo);
				if (tranT.equals("1")) {
					tranT = CheckCountType(ipsCardbinList, outNo);
					if (tranT.equals("1")) {
						tranT = CheckCountType(ipsCardbinList, inNo);
					}
				}
				this.transType = tranT;
				return result;
			}
		} catch (Exception e) {
			result = IPSInnerErrorCode.IPS300;
		}

		return result;
	}

	private String CheckCountType(List<IpsCardbin> ipsCardbinList,
			String countNo) {
		if (null != countNo && !"".equalsIgnoreCase(countNo)) {
			String comp = countNo.substring(0, 6);
			for (IpsCardbin card : ipsCardbinList) {
				if (card.getString1().equalsIgnoreCase(comp)) {
					return "1";
				}
			}

			return "2";
		} else {
			return "1";
		}
	}

	private IPSReport reqToCUPS(IPSReport reqReportObj) {
		return null;
	}

	/**
	 * 
	 * @param reqReportObj
	 *            ������
	 * @return �����������͵������� ��֯ ���ͺ��ĵ�������
	 */
	private IPSReport reqToCore(IPSReport reqReportObj) {
		// IPSTODO
		return reqReportObj;
	}

	/**
	 * 
	 * @param reqReportObj
	 *            ������
	 * @return ��������� �����Ƿ����á�macУ�顢����豸��Ϣ���Ƿ������ƣ��Ƿ��ǿ��ɿ����������豸״̬IPS_DEV_STAT
	 */
	private String publicCheck(IpsInTransflow curentIpsInTransflow,
			IPSReport reqReportObj) {
		// �����Ƿ�����
		if (!"1".equals(curentIpsInTransflow.getTranstat())) {
			return IPSInnerErrorCode.IPS335;// ����û������
		}
		// ����macУ��
		if ("1".equals(curentIpsInTransflow.getChkmacflag())) {
			// ����macУ��
			try {
				String macBuf = reqReportObj.getMacbuf();
				IPSConstantConfig sc = getIpsConstantConfig();
				String mk = sc.getMainKey();
				IpsKeyMng ipskey = getIpsKeyMng(IPSInnerChannelID.ATM,
						IPSChannelId.ATM, reqReportObj.getFieldValue(41),
						IPSKeyType.MACKEY);
				String mackey = ipskey.getKeyvalue();
				String flag = "ASC";
				String keyArith = "DES";
				String macArith = "DES";

				String macCode = CrypTool.getMacReturn8Bytes(macBuf, mk,
						mackey, flag, keyArith, macArith);
				System.out.println(macBuf);
				System.out.println(mk);
				System.out.println(mackey);
				System.out.println(flag);
				System.out.println(keyArith);
				System.out.println(macArith);
				System.out.println("������MacStr�� " + macCode);
				String reqMac = reqReportObj.getFieldValue(128);
				System.out.println("������MacStr�� " + reqMac);
				if (!reqMac.equalsIgnoreCase(macCode)) {
					// macЧ��ʧ��
					return IPSInnerErrorCode.IPS316;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return IPSInnerErrorCode.IPS307;
			}
		}
		// ����豸��Ϣ
		if (!devinfo.getIpaddr().equalsIgnoreCase(devIP)) {
			// �豸IP��ƥ��
			return IPSInnerErrorCode.IPS303;
		}
		try {
			if (!(reqReportObj.getFieldValue(51)).equalsIgnoreCase(devinfo
					.getTeller())) {
				// ��Ա�Ų�ƥ��
				return IPSInnerErrorCode.IPS304;
			}
		} catch (ISOException e) {
			// ��Ա�Ų�ƥ��
			return IPSInnerErrorCode.IPS304;
		}
		if (!"1".equalsIgnoreCase(devinfo.getUsestat())) {
			// �豸û������
			return IPSInnerErrorCode.IPS301;
		}
		return IPSInnerErrorCode.IPS000;
	}

	private IpsKeyMng getIpsKeyMng(String innerChannerID, String channelID,
			String devid, String keytype) {
		IpsKeyMngId ipsKeyMngId = new IpsKeyMngId(innerChannerID, channelID,
				devid, keytype);
		IpsKeyMng ipskey = getSecurityDBService()
				.findIpsKeyMngById(ipsKeyMngId);
		return ipskey;
	}

	private IpsInTransflow getProcessInfo(List<IpsInTransflow> ipsInTransflows,
			String no) {
		IpsInTransflow ipsInTransflow = null;
		for (IpsInTransflow tem : ipsInTransflows) {
			if (tem.getId().getProcessno().equals(no)) {
				ipsInTransflow = tem;
				break;
			}
		}
		return ipsInTransflow;
	}

	/**
	 * 
	 * @param tranceType
	 *            ��������
	 * @param channelCode
	 *            ��������
	 * @param channelReportCode
	 *            ����������
	 * @param channelTransCode
	 *            ����������
	 * @return ���״��������Ϣ
	 */
	@SuppressWarnings("unused")
	private IpsTranscodeMap getInTransCode(String tranceType,
			String channelCode, String channelReportCode,
			String channelTransCode) {
		System.out.println(System.currentTimeMillis());
		String[] conditions = new String[] { "TRANSTYPE=" + tranceType,
				"CHANLCODE=" + channelCode,
				"CHANLTRANSCODE=" + channelTransCode };
		IpsTranscodeMap t = (IpsTranscodeMap) this.getDbJdbcTool()
				.queryForObject(IpsTranscodeMap.class, "IPS_TRANSCODE_MAP",
						null, conditions);
		return t;
	}

}
