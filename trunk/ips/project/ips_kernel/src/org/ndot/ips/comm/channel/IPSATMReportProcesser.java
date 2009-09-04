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
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：IPSNBComm
 * 
 * 文件名： IPSATMReportProcesser.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-21
 * 
 */
public class IPSATMReportProcesser extends IPSReportProcesser {
	// 交易处理流程中涉及到得报文信息
	Map<String, IPSReport> memReports = new HashMap<String, IPSReport>();
	// 判断交易类型 0-交易未定义 1-行内金融交易 2-银联交易 3-管理类交易
	String transType = "";
	// 获得内部交易代码
	IpsTranscodeMap inTransCodeMap = null;
	// 获得设备信息
	IpsDevInf devinfo = null;
	// 该笔交易的本地流水号
	String jnlno = "00000000";
	// 当前上送交易的 设备iP
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
			String innerMethodRenrn = "";// 内部方法处理结果

			writeLog(IPSLogLevel.INFO, "IPS接收到ATM-C003请求报文(XML)： \n"
					+ reqReportObj.formatToXml() + " \n", reqReportObj
					.getBody());

			// 记录ATM送的请求信息
			memReports.put(IPSReportFactory.FROM_C003_REQ_REPORT, reqReportObj);

			// 判断交易类型
			innerMethodRenrn = checkReportType(reqReportObj, channel
					.getChannelId());
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// 获得设备信息
			innerMethodRenrn = getDevinfo(reqReportObj.getFieldValue(41));
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// 获得内部交易代码
			innerMethodRenrn = getInTransCode(reqReportObj, transType, channel
					.getChannelId());
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// 插入初始流水
			innerMethodRenrn = insertJNO(reqReportObj);
			if (!IPSInnerErrorCode.IPS000.equalsIgnoreCase(innerMethodRenrn)) {
				return IPSReportFactory.getErrorRspToC003(innerMethodRenrn,
						memReports);
			}
			// 进行交易处理，并获得给ATM的应答报文
			return doBusiness(channel, reqReportObj);

		} catch (Exception e) {
			writeLog(IPSLogLevel.INFO, "IPS系统异常 ATM-C003 请求 的报文被丢弃.");
			return null;
		}
	}

	/**
	 * 插入本地流水
	 * 
	 * @param reqReportObj
	 * @return 插入本地流水结果信息码
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
			writeLog(IPSLogLevel.INFO, "IPS 终端标识号为：" + devid
					+ " 的终端设备未定义，系统错误代码：【" + IPSInnerErrorCode.IPS302 + "】");
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
			// 获得交易处理流程定义信息
			String[] conditions = new String[] { "INTRANSCODE='"
					+ inTransCodeMap.getIntranscode() + "'" };
			List<IpsInTransflow> ipsInTransflows = this.getDbJdbcTool()
					.queryInTransflowForList(IpsInTransflow.class,
							"IPS_IN_TRANSFLOW", null, conditions);

			for (int i = 0; i < ipsInTransflows.size(); i++) {
				IpsInTransflow curentIpsInTransflow = getProcessInfo(
						ipsInTransflows, String.valueOf(i));
				// 进行公共检查
				String result = publicCheck(curentIpsInTransflow, reqReportObj);
				if (!IPSInnerErrorCode.IPS000.equals(result)) {// 公共检查失败，组织ATM应答报文
					// result 是公共检查的错误代码，根据result信息组织错误应答报文 IPSTODO
					// 失败报文
					String reqRspCode = geReqRspErrorCode(result);
					return IPSReportFactory.getReport(
							IPSReportFactory.C002_TO_C003_ERR_RSP_REPORT,
							jnlno, null, null, reqRspCode, devinfo, null, null,
							memReports);
				}
				// 公共检查成功

				if (curentIpsInTransflow.getQueryorgjnlflag().equals("1")) {// 查找远流水
					// IPSTODO

				}
				String destChannelId = curentIpsInTransflow.getDestnodeid();
				if (IPSChannelId.CORE.equals(destChannelId)) {// 到核心的请求报文
					// 组织到核心的请求报文
					IPSReport toCoreRsqReport = null;
					if (IPSTransTypes.IPSTRAN1001.equals(inTransCodeMap
							.getIntranscode())) {
						// 余额查询
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
						// 取款
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
					writeLog(IPSLogLevel.INFO, "IPS组织 至 核心-C002 请求 报文(XML)： \n"
							+ toCoreRsqReport.formatToXml() + " \n",
							toCoreRsqReport.getBody());

					// 向核心发送请求报文，并接受核心的应答报文
					IPSReport coreRspReport = ((IPSReportChannel) channel
							.getCtx().getBean(destChannelId))
							.callClient(reqToCore(toCoreRsqReport));
					if (null != coreRspReport) {
						writeLog(IPSLogLevel.INFO,
								"IPS接收 核心-C002的 应答 报文(XML)： \n"
										+ coreRspReport.formatToXml() + " \n",
								coreRspReport.getBody());
						memReports.put(IPSReportFactory.FROM_C002_RSP_REPORT,
								coreRspReport);
					} else {
						// 核心无应答
						writeLog(IPSLogLevel.INFO,
								"IPS接收 核心-C002 至 ATM-C003 的 应答 报文超时");
						ProcessOverTime(memReports);
						// 失败报文
						String reqRspCode = geReqRspErrorCode(IPSInnerErrorCode.IPS313);
						return null;
					}
				} else if (IPSChannelId.CUPS.equals(destChannelId)) {// 到银联的请求报文
					IPSReport cupsRspReport = ((IPSReportChannel) channel
							.getCtx().getBean(destChannelId))
							.callClient(reqToCUPS(reqReportObj));

					memReports.put(String.valueOf(i), cupsRspReport);

				} else if ("end".equals(destChannelId)) {
					if ("0".equalsIgnoreCase(curentIpsInTransflow.getId()
							.getProcessno())) {
						// 当前交易是管理类交易
						rspToATM = doManagerBusiness(curentIpsInTransflow,
								reqReportObj);
					} else {
						// 组织给ATM的应答报文
						String rspCodeFromC002 = "";
						String rspCodeToC003 = "";
						IPSReport coreRspReport = memReports
								.get(IPSReportFactory.FROM_C002_RSP_REPORT);

						if (null != coreRspReport) {// 该交易有核心应答
							// 获得核心应答码
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
								// 余额查询
								rspToATM = IPSReportFactory
										.getReport(
												IPSReportFactory.C002_TO_C003_RSP_REPORT_1001,
												jnlno, null, null,
												rspCodeToC003, devinfo, null,
												null, memReports);
							}
							if (IPSTransTypes.IPSTRAN1004.equals(inTransCodeMap
									.getIntranscode())) {
								// 取款
								rspToATM = IPSReportFactory
										.getReport(
												IPSReportFactory.C002_TO_C003_RSP_REPORT_1004,
												jnlno, null, null,
												rspCodeToC003, devinfo, null,
												null, memReports);
							}
						}

						// 更新核心清算日期
						{
							// TODO
						}
						// 跟新流水
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
			// 主密钥
			String mk = getIpsConstantConfig().getMainKey();
			IpsKeyMng oriPinkey = getIpsKeyMng(IPSInnerChannelID.ATM,
					IPSChannelId.ATM, reqReportObj.getFieldValue(41),
					IPSKeyType.PINKEY);
			IpsKeyMng pinkey = getIpsKeyMng(IPSInnerChannelID.ATM,
					IPSChannelId.CORE, "0000000000", IPSKeyType.PINKEY);
			// 进行pin转换
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
				// 签到
				return do0001_0002(ipsInTransflow, "1");
			}
			if (IPSTransTypes.IPSTRAN0002.equalsIgnoreCase(intranscode)) {
				// 签退
				return do0001_0002(ipsInTransflow, "0");
			}
			if (IPSTransTypes.IPSTRAN0003.equalsIgnoreCase(intranscode)) {
				// 初始CDK
				return do0003(reqReportObj, ipsInTransflow);
			}
			if (IPSTransTypes.IPSTRAN0004.equalsIgnoreCase(intranscode)) {
				// 初始PINMAC
				return do0004(reqReportObj, ipsInTransflow);
			}
			if (IPSTransTypes.IPSTRAN0006.equalsIgnoreCase(intranscode)) {
				// 状态上送
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
		// 状态上送
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
		// 核心清算日期
		sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
				.findIpsSysParamById(IPSSysParamCode.IPS0001).getParamvalue());
		String reqRspCode = "";
		if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
			String rsprspcode = "02";// 退成功
			sysParamSet.put(IPSSysParamCode.IPS0002, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0002)
					.getParamvalue());
			// 控制参数版本号
			sysParamSet.put(IPSSysParamCode.IPS0003, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0003)
					.getParamvalue());
			rsprspcode = "05";// 状态通知成功
			reqRspCode = getReqRspcode(rsprspcode);
		} else {
			// 终端状态通知交易失败
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
				// 保存密钥
				String saveKeyValue = CrypTool.getKey(mk, "DES", keyValue, key
						.getEncmethod(), mmkey);
				key.setKeyvalue(saveKeyValue);
				this.getSecurityDBService().updateIpsKeyMng(key);
			} catch (Exception e) {
				errorCode = IPSInnerErrorCode.IPS322;
			}

			Map<String, String> sysParamSet = new HashMap<String, String>();
			// 核心清算日期
			sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0001)
					.getParamvalue());
			String reqRspCode = "";
			if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
				String rspRspCode = "03";
				reqRspCode = getReqRspcode(rspRspCode);
			} else {
				// 失败报文
				reqRspCode = geReqRspErrorCode(errorCode);
			}
			IPSReport rspReport = IPSReportFactory.getReport(
					IPSReportFactory.T0_C003_RSP_REPORT_0004, jnlno, ISOUtil
							.hex2byte(keyValue), sysParamSet, reqRspCode,
					devinfo, ipsInTransflow, this.inTransCodeMap,
					this.memReports);
			try {
				String macbuff = rspReport.getMacbuf();
				// 主密钥
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
			// 生产校验码
			String cv = CrypTool.genCheckValue(mk, "DES", cdkValue);
			cv += "30303030";
			String mmKeyValue = "";
			if (cv.equalsIgnoreCase(BF96)) {
				// 校验码匹配
				// 根据数据库配置生产cdk
				IpsKeyMng mmkey = genIpsKeyMng(devid, IPSKeyType.MMKEY);
				String keyLen = mmkey.getKeylen();
				mmKeyValue = Des.genKeyByLen(keyLen);
				// 保存密钥
				String saveKeyValue = CrypTool.getKey(mk, "DES", cdkValue,
						cdkey.getEncmethod(), mmKeyValue);
				mmkey.setKeyvalue(saveKeyValue);
				this.getSecurityDBService().updateIpsKeyMng(mmkey);
			} else {
				errorCode = IPSInnerErrorCode.IPS323;
			}
			Map<String, String> sysParamSet = new HashMap<String, String>();
			// 核心清算日期
			sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
					.findIpsSysParamById(IPSSysParamCode.IPS0001)
					.getParamvalue());
			String reqRspCode = "";
			if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
				String rspRspCode = "04";
				reqRspCode = getReqRspcode(rspRspCode);
			} else {
				// 失败报文
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
		// 签到签退交易
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
		// 核心清算日期
		sysParamSet.put(IPSSysParamCode.IPS0001, getBusinessDBServices()
				.findIpsSysParamById(IPSSysParamCode.IPS0001).getParamvalue());
		String reqRspCode = "";
		if (IPSInnerErrorCode.IPS000.equalsIgnoreCase(errorCode)) {
			String rsprspcode = "02";// 退成功

			if ("1".equalsIgnoreCase(signStat)) {
				// 卡表版本号
				sysParamSet.put(IPSSysParamCode.IPS0002,
						getBusinessDBServices().findIpsSysParamById(
								IPSSysParamCode.IPS0002).getParamvalue());
				// 控制参数版本号
				sysParamSet.put(IPSSysParamCode.IPS0003,
						getBusinessDBServices().findIpsSysParamById(
								IPSSysParamCode.IPS0003).getParamvalue());
				rsprspcode = "01";// 签到成功
			}
			reqRspCode = getReqRspcode(rsprspcode);
		} else {
			// 签到/退失败
			reqRspCode = geReqRspErrorCode(errorCode);
		}
		return IPSReportFactory.getReport(
				IPSReportFactory.T0_C003_RSP_REPORT_0001, jnlno, null,
				sysParamSet, reqRspCode, devinfo, ipsInTransflow,
				this.inTransCodeMap, this.memReports);
	}

	/**
	 * 根据平台错误代码，获取请求方的应答码
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
	 * 根据接口定义的rsprspcode，获得请求方的应答码reqrspcode
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
	 * 进行超时处理
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
			String channelCode = "";// 渠道代码
			String channelReportCode = "";// 报文类型码 TODO【 要核实数据库】
			String channelTransCode = "";// 交易类型码
			if (IPSChannelId.CORE.equals(channelId)) {// 来自核心
				channelCode = reqReportObj.getHeaderFieldValue(4);
				channelReportCode = reqReportObj.getHeaderFieldValue(2);
				channelTransCode = reqReportObj.getHeaderFieldValue(3);
			} else {// 来自ATM
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
			writeLog(IPSLogLevel.INFO, "IPS 交易无法识别，系统错误代码：【"
					+ IPSInnerErrorCode.IPS315 + "】");
			result = IPSInnerErrorCode.IPS315;
		}
		return result;
	}

	private String checkReportType(IPSReport reqReportObj, String channelId)
			throws ISOException {
		String result = IPSInnerErrorCode.IPS000;
		try {

			// 判断交易类型 0-交易未定义 1-行内金融交易 2-银联交易 3-管理类交易
			String msgType = reqReportObj.getFieldValue(0);
			// 主帐号
			String mainNo = reqReportObj.getFieldValue(2);
			// 转出卡号
			String outNo = reqReportObj.getFieldValue(102);
			// 转出卡号
			String inNo = reqReportObj.getFieldValue(103);
			// 交易标志
			String transTag = reqReportObj.getFieldValue(20);
			if (msgType.indexOf("08") > -1) {
				this.transType = "3";
				return result;
			}
			if (transTag.startsWith("1")) {// 折交易
				this.transType = "1";
				return result;
			}
			List<IpsCardbin> ipsCardbinList = this.getIpsConstantConfig()
					.getIpsCardbinList();
			if (null != mainNo && !"".equals(mainNo)) {
				// 有主账号
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
	 *            请求报文
	 * @return 根据渠道上送的请求报文 组织 到和核心的请求报文
	 */
	private IPSReport reqToCore(IPSReport reqReportObj) {
		// IPSTODO
		return reqReportObj;
	}

	/**
	 * 
	 * @param reqReportObj
	 *            请求报文
	 * @return 公共检查结果 交易是否启用、mac校验、检查设备信息、是否交易限制，是否是可疑卡、并更新设备状态IPS_DEV_STAT
	 */
	private String publicCheck(IpsInTransflow curentIpsInTransflow,
			IPSReport reqReportObj) {
		// 交易是否启用
		if (!"1".equals(curentIpsInTransflow.getTranstat())) {
			return IPSInnerErrorCode.IPS335;// 交易没有启用
		}
		// 进行mac校验
		if ("1".equals(curentIpsInTransflow.getChkmacflag())) {
			// 进行mac校验
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
				System.out.println("生产的MacStr： " + macCode);
				String reqMac = reqReportObj.getFieldValue(128);
				System.out.println("请求报文MacStr： " + reqMac);
				if (!reqMac.equalsIgnoreCase(macCode)) {
					// mac效验失败
					return IPSInnerErrorCode.IPS316;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return IPSInnerErrorCode.IPS307;
			}
		}
		// 检查设备信息
		if (!devinfo.getIpaddr().equalsIgnoreCase(devIP)) {
			// 设备IP不匹配
			return IPSInnerErrorCode.IPS303;
		}
		try {
			if (!(reqReportObj.getFieldValue(51)).equalsIgnoreCase(devinfo
					.getTeller())) {
				// 柜员号不匹配
				return IPSInnerErrorCode.IPS304;
			}
		} catch (ISOException e) {
			// 柜员号不匹配
			return IPSInnerErrorCode.IPS304;
		}
		if (!"1".equalsIgnoreCase(devinfo.getUsestat())) {
			// 设备没有启用
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
	 *            交易类型
	 * @param channelCode
	 *            渠道代码
	 * @param channelReportCode
	 *            报文类型码
	 * @param channelTransCode
	 *            交易类型码
	 * @return 交易代码对照信息
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
