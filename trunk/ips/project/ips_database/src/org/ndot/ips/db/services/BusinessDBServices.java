package org.ndot.ips.db.services;

import java.util.List;

import org.ndot.ips.db.pojo.IpsCardbin;
import org.ndot.ips.db.pojo.IpsDevInf;
import org.ndot.ips.db.pojo.IpsDevStat;
import org.ndot.ips.db.pojo.IpsDevTranslimit;
import org.ndot.ips.db.pojo.IpsErrrspcodeMap;
import org.ndot.ips.db.pojo.IpsEvilCard;
import org.ndot.ips.db.pojo.IpsInTransflow;
import org.ndot.ips.db.pojo.IpsJnlToday;
import org.ndot.ips.db.pojo.IpsRspCode;
import org.ndot.ips.db.pojo.IpsSaf;
import org.ndot.ips.db.pojo.IpsSysParam;
import org.ndot.ips.db.pojo.IpsTranscodeMap;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_database_all
 * 
 * 文件名： BusinessDBServices.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-27
 * 
 */
public interface BusinessDBServices {
	/* start IpsDevInf 设备信息表 start */

	void saveIpsDevInf(IpsDevInf transientInstance);

	void deleteIpsDevInf(IpsDevInf persistentInstance);

	IpsDevInf findIpsDevInfById(java.lang.String id);

	List<IpsDevInf> findIpsDevInfByExample(IpsDevInf instance);

	List<IpsDevInf> findIpsDevInfByProperty(String propertyName, Object value);

	List<IpsDevInf> findIpsDevInfByDevname(Object devname);

	List<IpsDevInf> findIpsDevInfByInstcode(Object instcode);

	List<IpsDevInf> findIpsDevInfByUunioncode(Object uunioncode);

	List<IpsDevInf> findIpsDevInfByDevtype(Object devtype);

	List<IpsDevInf> findIpsDevInfByMadevendorid(Object madevendorid);

	List<IpsDevInf> findIpsDevInfByMadedate(Object madedate);

	List<IpsDevInf> findIpsDevInfBySupplyvendorid(Object supplyvendorid);

	List<IpsDevInf> findIpsDevInfBySvcvendorid(Object svcvendorid);

	List<IpsDevInf> findIpsDevInfByDevserialno(Object devserialno);

	List<IpsDevInf> findIpsDevInfByFirstsvcdate(Object firstsvcdate);

	List<IpsDevInf> findIpsDevInfByLastsvcdate(Object lastsvcdate);

	List<IpsDevInf> findIpsDevInfByInstallloc(Object installloc);

	List<IpsDevInf> findIpsDevInfByProtocol(Object protocol);

	List<IpsDevInf> findIpsDevInfByIpaddr(Object ipaddr);

	List<IpsDevInf> findIpsDevInfByPort(Object port);

	List<IpsDevInf> findIpsDevInfByBrandid(Object brandid);

	List<IpsDevInf> findIpsDevInfByModel(Object model);

	List<IpsDevInf> findIpsDevInfByInstalltype(Object installtype);

	List<IpsDevInf> findIpsDevInfByInsidebankflg(Object insidebankflg);

	List<IpsDevInf> findIpsDevInfByDevowner(Object devowner);

	List<IpsDevInf> findIpsDevInfBySvc24hflg(Object svc24hflg);

	List<IpsDevInf> findIpsDevInfBySvcstarttime(Object svcstarttime);

	List<IpsDevInf> findIpsDevInfBySvcendtime(Object svcendtime);

	List<IpsDevInf> findIpsDevInfByOs(Object os);

	List<IpsDevInf> findIpsDevInfByTeller(Object teller);

	List<IpsDevInf> findIpsDevInfByUsestat(Object usestat);

	List<IpsDevInf> findIpsDevInfByAtmprogver(Object atmprogver);

	List<IpsDevInf> findIpsDevInfByInstallloctype(Object installloctype);

	List<IpsDevInf> findIpsDevInfBySelfbankflg(Object selfbankflg);

	List<IpsDevInf> findIpsDevInfByRemoctrltype(Object remoctrltype);

	List<IpsDevInf> findIpsDevInfByDevupgradeflg(Object devupgradeflg);

	List<IpsDevInf> findIpsDevInfByFileresolution(Object fileresolution);

	List<IpsDevInf> findIpsDevInfAll();

	IpsDevInf mergeIpsDevInf(IpsDevInf detachedInstance);

	void attachIpsDevInfDirty(IpsDevInf instance);

	void attachIpsDevInfClean(IpsDevInf instance);

	/* end IpsDevInf 设备信息表 end */

	/* start IpsErrrspcodeMa 错误代码对照表 start */

	void saveIpsErrrspcodeMap(IpsErrrspcodeMap transientInstance);

	void deleteIpsErrrspcodeMap(IpsErrrspcodeMap persistentInstance);

	IpsErrrspcodeMap findById(org.ndot.ips.db.pojo.IpsErrrspcodeMapId id);

	List<IpsErrrspcodeMap> findIpsErrrspcodeMapByExample(
			IpsErrrspcodeMap instance);

	List<IpsErrrspcodeMap> findIpsErrrspcodeMapByProperty(String propertyName,
			Object value);

	List<IpsErrrspcodeMap> findIpsErrrspcodeMapByReqrspcode(Object reqrspcode);

	List<IpsErrrspcodeMap> findIpsErrrspcodeMapByEatcardflag(Object eatcardflag);

	List<IpsErrrspcodeMap> findIpsErrrspcodeMapByRspcodeinf(Object rspcodeinf);

	List<IpsErrrspcodeMap> findIpsErrrspcodeMapAll();

	IpsErrrspcodeMap mergeIpsErrrspcodeMap(IpsErrrspcodeMap detachedInstance);

	void attachDirtyIpsErrrspcodeMap(IpsErrrspcodeMap instance);

	void attachCleanIpsErrrspcodeMap(IpsErrrspcodeMap instance);

	/* end IpsErrrspcodeMap错误代码对照表 end */
	/* start IpsInTransflow交易流程定义表 start */

	void saveIpsInTransflow(IpsInTransflow transientInstance);

	void deleteIpsInTransflow(IpsInTransflow persistentInstance);

	IpsInTransflow findIpsInTransflowById(
			org.ndot.ips.db.pojo.IpsInTransflowId id);

	List<IpsInTransflow> findIpsInTransflowByExample(IpsInTransflow instance);

	List<IpsInTransflow> findIpsInTransflowByProperty(String propertyName,
			Object value);

	List<IpsInTransflow> findIpsInTransflowByTranname(Object tranname);

	List<IpsInTransflow> findIpsInTransflowByTranstat(Object transtat);

	List<IpsInTransflow> findIpsInTransflowByTranscategory(Object transcategory);

	List<IpsInTransflow> findIpsInTransflowByStarttag(Object starttag);

	List<IpsInTransflow> findIpsInTransflowByDestnodeid(Object destnodeid);

	List<IpsInTransflow> findIpsInTransflowByFlowprocessno(Object flowprocessno);

	List<IpsInTransflow> findIpsInTransflowByChkmacflag(Object chkmacflag);

	List<IpsInTransflow> findIpsInTransflowByChkpinflag(Object chkpinflag);

	List<IpsInTransflow> findIpsInTransflowByReverseflag(Object reverseflag);

	List<IpsInTransflow> findIpsInTransflowByResendflag(Object resendflag);

	List<IpsInTransflow> findIpsInTransflowByLaunchtranscode(
			Object launchtranscode);

	List<IpsInTransflow> findIpsInTransflowBySafflag(Object safflag);

	List<IpsInTransflow> findIpsInTransflowByQueryorgjnlflag(
			Object queryorgjnlflag);

	List<IpsInTransflow> findIpsInTransflowByOvertime(Object overtime);

	List<IpsInTransflow> findIpsInTransflowAll();

	IpsInTransflow mergeIpsInTransflow(IpsInTransflow detachedInstance);

	void attachDirtyIpsInTransflow(IpsInTransflow instance);

	void attachCleanIpsInTransflow(IpsInTransflow instance);

	/* end IpsInTransflow交易流程定义表 end */
	/* start 应答码表 start */

	void saveIpsRspCode(IpsRspCode transientInstance);

	void deleteIpsRspCode(IpsRspCode persistentInstance);

	IpsRspCode findIpsRspCodeById(org.ndot.ips.db.pojo.IpsRspCodeId id);

	List<IpsRspCode> findIpsRspCodeByExample(IpsRspCode instance);

	List<IpsRspCode> findIpsRspCodeByProperty(String propertyName, Object value);

	List<IpsRspCode> findIpsRspCodeByReqrspcode(Object reqrspcode);

	List<IpsRspCode> findIpsRspCodeByEatcardflag(Object eatcardflag);

	List<IpsRspCode> findIpsRspCodeByRspcodeinf(Object rspcodeinf);

	List<IpsRspCode> findIpsRspCodeAll();

	IpsRspCode mergeIpsRspCode(IpsRspCode detachedInstance);

	void attachDirtyIpsRspCode(IpsRspCode instance);

	void attachCleanIpsRspCode(IpsRspCode instance);

	/* end 应答码表 end */

	/* start 交易代码对照表 start */

	void saveIpsTranscodeMap(IpsTranscodeMap transientInstance);

	void deleteIpsTranscodeMap(IpsTranscodeMap persistentInstance);

	IpsTranscodeMap findIpsTranscodeMapById(java.lang.String id);

	List<IpsTranscodeMap> findIpsTranscodeMapByExample(IpsTranscodeMap instance);

	List<IpsTranscodeMap> findIpsTranscodeMapByProperty(String propertyName,
			Object value);

	List<IpsTranscodeMap> findIpsTranscodeMapByTranstype(Object transtype);

	List<IpsTranscodeMap> findIpsTranscodeMapByChanlcode(Object chanlcode);

	List<IpsTranscodeMap> findIpsTranscodeMapByChanlmsgcode(Object chanlmsgcode);

	List<IpsTranscodeMap> findIpsTranscodeMapByChanltranscode(
			Object chanltranscode);

	List<IpsTranscodeMap> findIpsTranscodeMapByCorecode(Object corecode);

	List<IpsTranscodeMap> findIpsTranscodeMapByCoremsgcode(Object coremsgcode);

	List<IpsTranscodeMap> findIpsTranscodeMapByCoretranscode(
			Object coretranscode);

	List<IpsTranscodeMap> findIpsTranscodeMapByHostcode(Object hostcode);

	List<IpsTranscodeMap> findIpsTranscodeMapByHostmsgcode(Object hostmsgcode);

	List<IpsTranscodeMap> findIpsTranscodeMapByHosttranscode(
			Object hosttranscode);

	List<IpsTranscodeMap> findIpsTranscodeMapByTransabstinf(Object transabstinf);

	List<IpsTranscodeMap> findIpsTranscodeMapByTransdesc(Object transdesc);

	List<IpsTranscodeMap> findIpsTranscodeMapAll();

	IpsTranscodeMap mergeIpsTranscodeMap(IpsTranscodeMap detachedInstance);

	void attachDirtyIpsTranscodeMap(IpsTranscodeMap instance);

	void attachCleanIpsTranscodeMap(IpsTranscodeMap instance);

	List<IpsTranscodeMap> getByProperties(String[] propertyNames,
			String[] propertyValues);

	List<IpsTranscodeMap> getByProperties(String[] propertyNames,
			String[] propertyValues, int fistRow, int maxRow);

	/* end交易代码对照表end */
	/* start 当日交易流水表 start */
	void updateIpsJnlToday(IpsJnlToday transientInstance);

	void saveIpsJnlToday(IpsJnlToday transientInstance);

	void deleteIpsJnlToday(IpsJnlToday persistentInstance);

	IpsJnlToday findIpsJnlTodayById(java.lang.String id);

	List<IpsJnlToday> findIpsJnlTodayByExample(IpsJnlToday instance);

	List<IpsJnlToday> findIpsJnlTodayByProperty(String propertyName,
			Object value);

	List<IpsJnlToday> findIpsJnlTodayByLocaldatetime(Object localdatetime);

	List<IpsJnlToday> findIpsJnlTodayByIntranscode(Object intranscode);

	List<IpsJnlToday> findIpsJnlTodayByTranstype(Object transtype);

	List<IpsJnlToday> findIpsJnlTodayByErrcode(Object errcode);

	List<IpsJnlToday> findIpsJnlTodayByChanlcode(Object chanlcode);

	List<IpsJnlToday> findIpsJnlTodayByChanlmsgcode(Object chanlmsgcode);

	List<IpsJnlToday> findIpsJnlTodayByChanltranscode(Object chanltranscode);

	List<IpsJnlToday> findIpsJnlTodayByChanljnlno(Object chanljnlno);

	List<IpsJnlToday> findIpsJnlTodayByChanlrspcode(Object chanlrspcode);

	List<IpsJnlToday> findIpsJnlTodayByCorecode(Object corecode);

	List<IpsJnlToday> findIpsJnlTodayByCoremsgcode(Object coremsgcode);

	List<IpsJnlToday> findIpsJnlTodayByCoretranscode(Object coretranscode);

	List<IpsJnlToday> findIpsJnlTodayByCoreclrdate(Object coreclrdate);

	List<IpsJnlToday> findIpsJnlTodayByCorejnlno(Object corejnlno);

	List<IpsJnlToday> findIpsJnlTodayByCorerspcode(Object corerspcode);

	List<IpsJnlToday> findIpsJnlTodayByHostcode(Object hostcode);

	List<IpsJnlToday> findIpsJnlTodayByHostmsgcode(Object hostmsgcode);

	List<IpsJnlToday> findIpsJnlTodayByHosttranscode(Object hosttranscode);

	List<IpsJnlToday> findIpsJnlTodayByHostclrdate(Object hostclrdate);

	List<IpsJnlToday> findIpsJnlTodayByHostjnlno(Object hostjnlno);

	List<IpsJnlToday> findIpsJnlTodayByHostrspcode(Object hostrspcode);

	List<IpsJnlToday> findIpsJnlTodayByDevid(Object devid);

	List<IpsJnlToday> findIpsJnlTodayByCardno(Object cardno);

	List<IpsJnlToday> findIpsJnlTodayByTranamt(Object tranamt);

	List<IpsJnlToday> findIpsJnlTodayByCardexpdate(Object cardexpdate);

	List<IpsJnlToday> findIpsJnlTodayByDevtranstime(Object devtranstime);

	List<IpsJnlToday> findIpsJnlTodayByDevtransdate(Object devtransdate);

	List<IpsJnlToday> findIpsJnlTodayByMctcode(Object mctcode);

	List<IpsJnlToday> findIpsJnlTodayByMcttype(Object mcttype);

	List<IpsJnlToday> findIpsJnlTodayByTrancurrcode(Object trancurrcode);

	List<IpsJnlToday> findIpsJnlTodayByInputtype(Object inputtype);

	List<IpsJnlToday> findIpsJnlTodayByCondcode(Object condcode);

	List<IpsJnlToday> findIpsJnlTodayByTrack2(Object track2);

	List<IpsJnlToday> findIpsJnlTodayByTrack3(Object track3);

	List<IpsJnlToday> findIpsJnlTodayByAddbal(Object addbal);

	List<IpsJnlToday> findIpsJnlTodayByOpeninstcode(Object openinstcode);

	List<IpsJnlToday> findIpsJnlTodayByTransinstcode(Object transinstcode);

	List<IpsJnlToday> findIpsJnlTodayByAccfrom(Object accfrom);

	List<IpsJnlToday> findIpsJnlTodayByAccto(Object accto);

	List<IpsJnlToday> findIpsJnlTodayByFee1(Object fee1);

	List<IpsJnlToday> findIpsJnlTodayByFee2(Object fee2);

	List<IpsJnlToday> findIpsJnlTodayByFee3(Object fee3);

	List<IpsJnlToday> findIpsJnlTodayByHostchkflag(Object hostchkflag);

	List<IpsJnlToday> findIpsJnlTodayByTermchkflag(Object termchkflag);

	List<IpsJnlToday> findIpsJnlTodayByMsgreasoncode(Object msgreasoncode);

	List<IpsJnlToday> findIpsJnlTodayByRefnumber(Object refnumber);

	List<IpsJnlToday> findIpsJnlTodayByTellerid(Object tellerid);

	List<IpsJnlToday> findIpsJnlTodayByBatchno(Object batchno);

	List<IpsJnlToday> findIpsJnlTodayByOrigdata(Object origdata);

	List<IpsJnlToday> findIpsJnlTodayByAuthrspcode(Object authrspcode);

	List<IpsJnlToday> findIpsJnlTodayByChkflag(Object chkflag);

	List<IpsJnlToday> findIpsJnlTodayByCoremodflag(Object coremodflag);

	List<IpsJnlToday> findIpsJnlTodayByHostmodflag(Object hostmodflag);

	List<IpsJnlToday> findIpsJnlTodayByTranstat(Object transtat);

	List<IpsJnlToday> findIpsJnlTodayByLocaldateyear(Object localdateyear);

	List<IpsJnlToday> findIpsJnlTodayByLocaldatemon(Object localdatemon);

	List<IpsJnlToday> findIpsJnlTodayByLocaldateday(Object localdateday);

	List<IpsJnlToday> findAllIpsJnlToday();

	IpsJnlToday mergeIpsJnlToday(IpsJnlToday detachedInstance);

	void attachDirtyIpsJnlToday(IpsJnlToday instance);

	void attachCleanIpsJnlToday(IpsJnlToday instance);

	/* end 当日交易流水表 end */
	/* start 设备交易限制 start */

	void saveIpsDevTranslimit(IpsDevTranslimit transientInstance);

	void deleteIpsDevTranslimit(IpsDevTranslimit persistentInstance);

	IpsDevTranslimit findIpsDevTranslimitById(
			org.ndot.ips.db.pojo.IpsDevTranslimitId id);

	List<IpsDevTranslimit> findIpsDevTranslimitByExample(
			IpsDevTranslimit instance);

	List<IpsDevTranslimit> findIpsDevTranslimitByProperty(String propertyName,
			Object value);

	List<IpsDevTranslimit> findIpsDevTranslimitByDevtype(Object devtype);

	List<IpsDevTranslimit> findIpsDevTranslimitAll();

	IpsDevTranslimit mergeIpsDevTranslimit(IpsDevTranslimit detachedInstance);

	void attachIpsDevTranslimitDirty(IpsDevTranslimit instance);

	void attachIpsDevTranslimitClean(IpsDevTranslimit instance);

	/* end 设备交易限制 end */
	/* start 存储转发 start */

	void saveIpsSaf(IpsSaf transientInstance);

	void deleteIpsSaf(IpsSaf persistentInstance);

	IpsSaf findIpsSafById(org.ndot.ips.db.pojo.IpsSafId id);

	List<IpsSaf> findIpsSafByExample(IpsSaf instance);

	List<IpsSaf> findIpsSafByProperty(String propertyName, Object value);

	List<IpsSaf> findIpsSafByIntranscode(Object intranscode);

	List<IpsSaf> findIpsSafByEndtime(Object endtime);

	List<IpsSaf> findIpsSafBySourceid(Object sourceid);

	List<IpsSaf> findIpsSafByDestid(Object destid);

	List<IpsSaf> findIpsSafByState(Object state);

	List<IpsSaf> findIpsSafByRspcode(Object rspcode);

	List<IpsSaf> findIpsSafBySendtimes(Object sendtimes);

	List<IpsSaf> findIpsSafByOvertime(Object overtime);

	List<IpsSaf> findIpsSafByClrdate(Object clrdate);

	List<IpsSaf> findIpsSafBySendpac(Object sendpac);

	List<IpsSaf> findIpsSafAll();

	IpsSaf mergeIpsSaf(IpsSaf detachedInstance);

	void attachIpsSafDirty(IpsSaf instance);

	void attachIpsSafClean(IpsSaf instance);

	/* end 存储转发end */
	/*start 卡bin信息 start*/




	void saveIpsCardbin(IpsCardbin transientInstance);

	void deleteIpsCardbin(IpsCardbin persistentInstance);

	IpsCardbin findIpsCardbinById(java.lang.String id);

	List<IpsCardbin> findIpsCardbinByExample(IpsCardbin instance);

	List<IpsCardbin> findIpsCardbinByProperty(String propertyName, Object value);

	List<IpsCardbin> findIpsCardbinByBankid(Object bankid);

	List<IpsCardbin> findIpsCardbinByBankname(Object bankname);

	List<IpsCardbin> findIpsCardbinByCardid(Object cardid);

	List<IpsCardbin> findIpsCardbinByOffset1(Object offset1);

	List<IpsCardbin> findIpsCardbinByLength1(Object length1);

	List<IpsCardbin> findIpsCardbinByString1(Object string1);

	List<IpsCardbin> findIpsCardbinByOffset2(Object offset2);

	List<IpsCardbin> findIpsCardbinByLength2(Object length2);

	List<IpsCardbin> findIpsCardbinByString2(Object string2);

	List<IpsCardbin> findIpsCardbinByOffset3(Object offset3);

	List<IpsCardbin> findIpsCardbinByLength3(Object length3);

	List<IpsCardbin> findIpsCardbinByString3(Object string3);

	List<IpsCardbin> findIpsCardbinByPanoffset(Object panoffset);

	List<IpsCardbin> findIpsCardbinByPanlen(Object panlen);

	List<IpsCardbin> findIpsCardbinByCardflag(Object cardflag);

	List<IpsCardbin> findIpsCardbinByTranlist(Object tranlist);

	List<IpsCardbin> findIpsCardbinAll();

	IpsCardbin mergeIpsCardbin(IpsCardbin detachedInstance);

	void attachIpsCardbinDirty(IpsCardbin instance);

	void attachIpsCardbinClean(IpsCardbin instance);

	/*end 卡bin信息 end*/
	/*start可疑卡表信息 start*/


	 void saveIpsEvilCard(IpsEvilCard transientInstance);

	 void deleteIpsEvilCard(IpsEvilCard persistentInstance);

	 IpsEvilCard findIpsEvilCardById(java.lang.String id);

	 List<IpsEvilCard> findIpsEvilCardByExample(IpsEvilCard instance);

	 List<IpsEvilCard> findIpsEvilCardByProperty(String propertyName, Object value);

	 List<IpsEvilCard> findIpsEvilCardByCardflag(Object cardflag);

	 List<IpsEvilCard> findIpsEvilCardBySetreason(Object setreason);

	 List<IpsEvilCard> findIpsEvilCardAll();

	 IpsEvilCard mergeIpsEvilCard(IpsEvilCard detachedInstance);

	 void attachIpsEvilCardDirty(IpsEvilCard instance);

	 void attachIpsEvilCardClean(IpsEvilCard instance);


	/*end可疑卡表信息 start*/
	 
	 /*start设备状态信息 start*/

     void saveOrUpdeateIpsDevStat(IpsDevStat transientInstance);
     void updeateIpsDevStat(IpsDevStat transientInstance);
  
	 void saveIpsDevStat(IpsDevStat transientInstance);

	 void deleteIpsDevStat(IpsDevStat persistentInstance);

	 IpsDevStat findIpsDevStatById(java.lang.String id);

	 List<IpsDevStat> findIpsDevStatByExample(IpsDevStat instance);

	 List<IpsDevStat> findIpsDevStatByProperty(String propertyName, Object value);

	 List<IpsDevStat> findIpsDevStatBySignstat(Object signstat);

	 List<IpsDevStat> findIpsDevStatBySigndatetime(Object signdatetime);

	 List<IpsDevStat> findIpsDevStatByUpddatetime(Object upddatetime);

	 List<IpsDevStat> findIpsDevStatByDevstat(Object devstat);

	 List<IpsDevStat> findIpsDevStatByDeverrinf(Object deverrinf);

	 List<IpsDevStat> findIpsDevStatByDevaccterrinf(Object devaccterrinf);

	 List<IpsDevStat> findIpsDevStatAll();

	 IpsDevStat mergeIpsDevStat(IpsDevStat detachedInstance);

	 void attachIpsDevStatDirty(IpsDevStat instance);

	 void attachIpsDevStatClean(IpsDevStat instance);


	 /*end设备状态信息 end*/
	 /*start 系统运行参数表 start*/




	 void saveIpsSysParam(IpsSysParam transientInstance);

	 void deleteIpsSysParam(IpsSysParam persistentInstance);

	 IpsSysParam findIpsSysParamById(java.lang.String id);

	 List<IpsSysParam> findIpsSysParamByExample(IpsSysParam instance);

	 List<IpsSysParam> findIpsSysParamByProperty(String propertyName, Object value);

	 List<IpsSysParam> findIpsSysParamByParamname(Object paramname);

	 List<IpsSysParam> findIpsSysParamByParamvalue(Object paramvalue);

	 List<IpsSysParam> findIpsSysParamByParammodflg(Object parammodflg);

	 List<IpsSysParam> findIpsSysParamByParamdesc(Object paramdesc);

	 List<IpsSysParam> findIpsSysParamAll();

	 IpsSysParam merge(IpsSysParam detachedInstance);

	 void attachIpsSysParamDirty(IpsSysParam instance);

	 void attachIpsSysParamClean(IpsSysParam instance);


	 /*end 系统运行参数表 end*/
	

}
