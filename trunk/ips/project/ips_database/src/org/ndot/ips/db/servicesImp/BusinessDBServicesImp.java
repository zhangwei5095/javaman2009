package org.ndot.ips.db.servicesImp;

import java.util.ArrayList;
import java.util.List;

import org.ndot.ips.db.pojo.IpsCardbin;
import org.ndot.ips.db.pojo.IpsCardbinDAO;
import org.ndot.ips.db.pojo.IpsDevInf;
import org.ndot.ips.db.pojo.IpsDevInfDAO;
import org.ndot.ips.db.pojo.IpsDevStat;
import org.ndot.ips.db.pojo.IpsDevStatDAO;
import org.ndot.ips.db.pojo.IpsDevTranslimit;
import org.ndot.ips.db.pojo.IpsDevTranslimitDAO;
import org.ndot.ips.db.pojo.IpsDevTranslimitId;
import org.ndot.ips.db.pojo.IpsErrrspcodeMap;
import org.ndot.ips.db.pojo.IpsErrrspcodeMapDAO;
import org.ndot.ips.db.pojo.IpsErrrspcodeMapId;
import org.ndot.ips.db.pojo.IpsEvilCard;
import org.ndot.ips.db.pojo.IpsEvilCardDAO;
import org.ndot.ips.db.pojo.IpsInTransflow;
import org.ndot.ips.db.pojo.IpsInTransflowDAO;
import org.ndot.ips.db.pojo.IpsInTransflowId;
import org.ndot.ips.db.pojo.IpsJnlToday;
import org.ndot.ips.db.pojo.IpsJnlTodayDAO;
import org.ndot.ips.db.pojo.IpsRspCode;
import org.ndot.ips.db.pojo.IpsRspCodeDAO;
import org.ndot.ips.db.pojo.IpsRspCodeId;
import org.ndot.ips.db.pojo.IpsSaf;
import org.ndot.ips.db.pojo.IpsSafDAO;
import org.ndot.ips.db.pojo.IpsSafId;
import org.ndot.ips.db.pojo.IpsSysParam;
import org.ndot.ips.db.pojo.IpsSysParamDAO;
import org.ndot.ips.db.pojo.IpsTranscodeMap;
import org.ndot.ips.db.pojo.IpsTranscodeMapDAO;
import org.ndot.ips.db.services.BusinessDBServices;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_database_all
 * 
 * 文件名： BusinessDBServicesImp.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-6-27
 * 
 */
public class BusinessDBServicesImp implements BusinessDBServices {
	IpsDevInfDAO ipsDevInfDAO;
	IpsErrrspcodeMapDAO ipsErrrspcodeMapDAO;
	IpsInTransflowDAO ipsInTransflowDAO;
	IpsRspCodeDAO ipsRspCodeDAO;
	IpsTranscodeMapDAO ipsTranscodeMapDAO;
	IpsJnlTodayDAO ipsJnlTodayDAO;
	IpsDevStatDAO ipsDevStatDAO;
	IpsDevTranslimitDAO ipsDevTranslimitDAO;
	IpsSafDAO ipsSafDAO;
	IpsCardbinDAO ipsCardbinDAO;
	IpsEvilCardDAO ipsEvilCardDAO;
	IpsSysParamDAO ipsSysParamDAO;

	/* start 设备信息表 start */
	public void attachIpsDevInfClean(IpsDevInf instance) {
		// TODO Auto-generated method stub

	}

	public void attachIpsDevInfDirty(IpsDevInf instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsDevInf(IpsDevInf persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsDevInf> findIpsDevInfAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByAtmprogver(Object atmprogver) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByBrandid(Object brandid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByDevname(Object devname) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByDevowner(Object devowner) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByDevserialno(Object devserialno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByDevtype(Object devtype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByDevupgradeflg(Object devupgradeflg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByExample(IpsDevInf instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByFileresolution(Object fileresolution) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByFirstsvcdate(Object firstsvcdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsDevInf findIpsDevInfById(String id) {
		return this.ipsDevInfDAO.findById(id);
	}

	public List<IpsDevInf> findIpsDevInfByInsidebankflg(Object insidebankflg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByInstallloc(Object installloc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByInstallloctype(Object installloctype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByInstalltype(Object installtype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByInstcode(Object instcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByIpaddr(Object ipaddr) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByLastsvcdate(Object lastsvcdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByMadedate(Object madedate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByMadevendorid(Object madevendorid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByModel(Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByOs(Object os) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByPort(Object port) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByProtocol(Object protocol) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByRemoctrltype(Object remoctrltype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfBySelfbankflg(Object selfbankflg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfBySupplyvendorid(Object supplyvendorid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfBySvc24hflg(Object svc24hflg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfBySvcendtime(Object svcendtime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfBySvcstarttime(Object svcstarttime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfBySvcvendorid(Object svcvendorid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByTeller(Object teller) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByUsestat(Object usestat) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevInf> findIpsDevInfByUunioncode(Object uunioncode) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsDevInf mergeIpsDevInf(IpsDevInf detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsDevInf(IpsDevInf transientInstance) {
		// TODO Auto-generated method stub

	}

	/* end设备信息表end */

	/* start 错误码对照表 start */

	public void attachCleanIpsErrrspcodeMap(IpsErrrspcodeMap instance) {
		// TODO Auto-generated method stub

	}

	public void attachDirtyIpsErrrspcodeMap(IpsErrrspcodeMap instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsErrrspcodeMap(IpsErrrspcodeMap persistentInstance) {
		// TODO Auto-generated method stub

	}

	public IpsErrrspcodeMap findById(IpsErrrspcodeMapId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsErrrspcodeMap> findIpsErrrspcodeMapAll() {
		List<IpsErrrspcodeMap> result = new ArrayList<IpsErrrspcodeMap>();
		for (Object obj : ipsErrrspcodeMapDAO.findAll()) {
			result.add((IpsErrrspcodeMap) obj);
		}
		return result;
	}

	public List<IpsErrrspcodeMap> findIpsErrrspcodeMapByEatcardflag(
			Object eatcardflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsErrrspcodeMap> findIpsErrrspcodeMapByExample(
			IpsErrrspcodeMap instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsErrrspcodeMap> findIpsErrrspcodeMapByProperty(
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsErrrspcodeMap> findIpsErrrspcodeMapByReqrspcode(
			Object reqrspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsErrrspcodeMap> findIpsErrrspcodeMapByRspcodeinf(
			Object rspcodeinf) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsErrrspcodeMap mergeIpsErrrspcodeMap(
			IpsErrrspcodeMap detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsErrrspcodeMap(IpsErrrspcodeMap transientInstance) {
		// TODO Auto-generated method stub

	}

	/* end 错误码对照表 end */
	/* start 交易流程定义表start */
	public void attachCleanIpsInTransflow(IpsInTransflow instance) {
		// TODO Auto-generated method stub

	}

	public void attachDirtyIpsInTransflow(IpsInTransflow instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsInTransflow(IpsInTransflow persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsInTransflow> findIpsInTransflowAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByChkmacflag(Object chkmacflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByChkpinflag(Object chkpinflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByDestnodeid(Object destnodeid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByExample(
			IpsInTransflow instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByFlowprocessno(
			Object flowprocessno) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsInTransflow findIpsInTransflowById(IpsInTransflowId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByLaunchtranscode(
			Object launchtranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByOvertime(Object overtime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByProperty(
			String propertyName, Object value) {
		return this.getIpsInTransflowDAO().findByProperty(propertyName, value);
	}

	public List<IpsInTransflow> findIpsInTransflowByQueryorgjnlflag(
			Object queryorgjnlflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByResendflag(Object resendflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByReverseflag(
			Object reverseflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowBySafflag(Object safflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByStarttag(Object starttag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByTranname(Object tranname) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByTranscategory(
			Object transcategory) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsInTransflow> findIpsInTransflowByTranstat(Object transtat) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsInTransflow mergeIpsInTransflow(IpsInTransflow detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsInTransflow(IpsInTransflow transientInstance) {
		// TODO Auto-generated method stub

	}

	/* end 交易流程定义表end */
	/* start 应答码表 start */
	public void attachCleanIpsRspCode(IpsRspCode instance) {
		// TODO Auto-generated method stub

	}

	public void attachDirtyIpsRspCode(IpsRspCode instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsRspCode(IpsRspCode persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsRspCode> findIpsRspCodeAll() {
		List<IpsRspCode> result = new ArrayList<IpsRspCode>();
		for (Object obj : ipsRspCodeDAO.findAll()) {
			result.add((IpsRspCode) obj);
		}
		return result;
	}

	public IpsRspCode findIpsRspCodeById(IpsRspCodeId id) {
		return this.ipsRspCodeDAO.findById(id);
	}

	public List<IpsRspCode> findIpsRspCodeByEatcardflag(Object eatcardflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsRspCode> findIpsRspCodeByExample(IpsRspCode instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsRspCode> findIpsRspCodeByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsRspCode> findIpsRspCodeByReqrspcode(Object reqrspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsRspCode> findIpsRspCodeByRspcodeinf(Object rspcodeinf) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsRspCode mergeIpsRspCode(IpsRspCode detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsRspCode(IpsRspCode transientInstance) {
		// TODO Auto-generated method stub

	}

	/* end 应答码表 end */
	/* start 交易代码对照表 start */
	public void attachCleanIpsTranscodeMap(IpsTranscodeMap instance) {
		// TODO Auto-generated method stub

	}

	public void attachDirtyIpsTranscodeMap(IpsTranscodeMap instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsTranscodeMap(IpsTranscodeMap persistentInstance) {
		// TODO Auto-generated method stub

	}

	public List<IpsRspCode> findIpsRspCodeAll1() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsRspCode findIpsRspCodeIdById(IpsRspCodeId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByTransdesc(Object transdesc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByChanlcode(Object chanlcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByChanlmsgcode(
			Object chanlmsgcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByChanltranscode(
			Object chanltranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByCorecode(Object corecode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByCoremsgcode(
			Object coremsgcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByCoretranscode(
			Object coretranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByExample(
			IpsTranscodeMap instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByHostcode(Object hostcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByHostmsgcode(
			Object hostmsgcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByHosttranscode(
			Object hosttranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsTranscodeMap findIpsTranscodeMapById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByProperty(
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByTransabstinf(
			Object transabstinf) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsTranscodeMap> findIpsTranscodeMapByTranstype(Object transtype) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsTranscodeMap mergeIpsTranscodeMap(IpsTranscodeMap detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsTranscodeMap(IpsTranscodeMap transientInstance) {
		// TODO Auto-generated method stub

	}

	/* end 交易代码对照表 end */
	/* start 交易流水表 start */
	public void attachCleanIpsJnlToday(IpsJnlToday instance) {
		// TODO Auto-generated method stub

	}

	public void attachDirtyIpsJnlToday(IpsJnlToday instance) {
		// TODO Auto-generated method stub

	}

	public void deleteIpsJnlToday(IpsJnlToday persistentInstance) {
		// TODO Auto-generated method stub

	}

	public void updateIpsJnlToday(IpsJnlToday transientInstance) {
		this.ipsJnlTodayDAO.update(transientInstance);
	}

	public List<IpsJnlToday> findAllIpsJnlToday() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByAccfrom(Object accfrom) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByAccto(Object accto) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByAddbal(Object addbal) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByAuthrspcode(Object authrspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByBatchno(Object batchno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCardexpdate(Object cardexpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCardno(Object cardno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByChanlcode(Object chanlcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByChanljnlno(Object chanljnlno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByChanlmsgcode(Object chanlmsgcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByChanlrspcode(Object chanlrspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByChanltranscode(
			Object chanltranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByChkflag(Object chkflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCondcode(Object condcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCoreclrdate(Object coreclrdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCorecode(Object corecode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCorejnlno(Object corejnlno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCoremodflag(Object coremodflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCoremsgcode(Object coremsgcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCorerspcode(Object corerspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByCoretranscode(Object coretranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByDevid(Object devid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByDevtransdate(Object devtransdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByDevtranstime(Object devtranstime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByErrcode(Object errcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByExample(IpsJnlToday instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByFee1(Object fee1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByFee2(Object fee2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByFee3(Object fee3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostchkflag(Object hostchkflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostclrdate(Object hostclrdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostcode(Object hostcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostjnlno(Object hostjnlno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostmodflag(Object hostmodflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostmsgcode(Object hostmsgcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHostrspcode(Object hostrspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByHosttranscode(Object hosttranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsJnlToday findIpsJnlTodayById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByInputtype(Object inputtype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByIntranscode(Object intranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByLocaldateday(Object localdateday) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByLocaldatemon(Object localdatemon) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByLocaldatetime(Object localdatetime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByLocaldateyear(Object localdateyear) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByMctcode(Object mctcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByMcttype(Object mcttype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByMsgreasoncode(Object msgreasoncode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByOpeninstcode(Object openinstcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByOrigdata(Object origdata) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByRefnumber(Object refnumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTellerid(Object tellerid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTermchkflag(Object termchkflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTrack2(Object track2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTrack3(Object track3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTranamt(Object tranamt) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTrancurrcode(Object trancurrcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTransinstcode(Object transinstcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTranstat(Object transtat) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsJnlToday> findIpsJnlTodayByTranstype(Object transtype) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsJnlToday mergeIpsJnlToday(IpsJnlToday detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsJnlToday(IpsJnlToday transientInstance) {
		this.ipsJnlTodayDAO.save(transientInstance);
	}

	/* end 当日交易流水表 end */

	public IpsDevInfDAO getIpsDevInfDAO() {
		return ipsDevInfDAO;
	}

	public void setIpsDevInfDAO(IpsDevInfDAO ipsDevInfDAO) {
		this.ipsDevInfDAO = ipsDevInfDAO;
	}

	public IpsErrrspcodeMapDAO getIpsErrrspcodeMapDAO() {
		return ipsErrrspcodeMapDAO;
	}

	public void setIpsErrrspcodeMapDAO(IpsErrrspcodeMapDAO ipsErrrspcodeMapDAO) {
		this.ipsErrrspcodeMapDAO = ipsErrrspcodeMapDAO;
	}

	public IpsInTransflowDAO getIpsInTransflowDAO() {
		return ipsInTransflowDAO;
	}

	public void setIpsInTransflowDAO(IpsInTransflowDAO ipsInTransflowDAO) {
		this.ipsInTransflowDAO = ipsInTransflowDAO;
	}

	public IpsRspCodeDAO getIpsRspCodeDAO() {
		return ipsRspCodeDAO;
	}

	public void setIpsRspCodeDAO(IpsRspCodeDAO ipsRspCodeDAO) {
		this.ipsRspCodeDAO = ipsRspCodeDAO;
	}

	public IpsTranscodeMapDAO getIpsTranscodeMapDAO() {
		return ipsTranscodeMapDAO;
	}

	public void setIpsTranscodeMapDAO(IpsTranscodeMapDAO ipsTranscodeMapDAO) {
		this.ipsTranscodeMapDAO = ipsTranscodeMapDAO;
	}

	public IpsJnlTodayDAO getIpsJnlTodayDAO() {
		return ipsJnlTodayDAO;
	}

	public void setIpsJnlTodayDAO(IpsJnlTodayDAO ipsJnlTodayDAO) {
		this.ipsJnlTodayDAO = ipsJnlTodayDAO;
	}

	/**
	 * 
	 * @param propertyNames
	 *            属性名
	 * @param propertyValues
	 *            属性值
	 * @return 满足条件的对象列表
	 */
	public List<IpsTranscodeMap> getByProperties(String[] propertyNames,
			String[] propertyValues) {
		return this.getIpsTranscodeMapDAO().getByProperties(propertyNames,
				propertyValues);
	}

	public List<IpsTranscodeMap> getByProperties(String[] propertyNames,
			String[] propertyValues, int fistRow, int maxRow) {
		return this.getIpsTranscodeMapDAO().getByProperties(propertyNames,
				propertyValues);
	}

	

	public IpsDevStatDAO getIpsDevStatDAO() {
		return ipsDevStatDAO;
	}

	public void setIpsDevStatDAO(IpsDevStatDAO ipsDevStatDAO) {
		this.ipsDevStatDAO = ipsDevStatDAO;
	}

	public IpsDevTranslimitDAO getIpsDevTranslimitDAO() {
		return ipsDevTranslimitDAO;
	}

	public void setIpsDevTranslimitDAO(IpsDevTranslimitDAO ipsDevTranslimitDAO) {
		this.ipsDevTranslimitDAO = ipsDevTranslimitDAO;
	}

	public IpsSafDAO getIpsSafDAO() {
		return ipsSafDAO;
	}

	public void setIpsSafDAO(IpsSafDAO ipsSafDAO) {
		this.ipsSafDAO = ipsSafDAO;
	}

	public IpsCardbinDAO getIpsCardbinDAO() {
		return ipsCardbinDAO;
	}

	public void setIpsCardbinDAO(IpsCardbinDAO ipsCardbinDAO) {
		this.ipsCardbinDAO = ipsCardbinDAO;
	}

	public IpsEvilCardDAO getIpsEvilCardDAO() {
		return ipsEvilCardDAO;
	}

	public void setIpsEvilCardDAO(IpsEvilCardDAO ipsEvilCardDAO) {
		this.ipsEvilCardDAO = ipsEvilCardDAO;
	}

	public IpsSysParamDAO getIpsSysParamDAO() {
		return ipsSysParamDAO;
	}

	public void setIpsSysParamDAO(IpsSysParamDAO ipsSysParamDAO) {
		this.ipsSysParamDAO = ipsSysParamDAO;
	}

	public void attachIpsCardbinClean(IpsCardbin instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsCardbinDirty(IpsCardbin instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsDevStatClean(IpsDevStat instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsDevStatDirty(IpsDevStat instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsDevTranslimitClean(IpsDevTranslimit instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsDevTranslimitDirty(IpsDevTranslimit instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsEvilCardClean(IpsEvilCard instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsEvilCardDirty(IpsEvilCard instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsSafClean(IpsSaf instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsSafDirty(IpsSaf instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsSysParamClean(IpsSysParam instance) {
		// TODO Auto-generated method stub
		
	}

	public void attachIpsSysParamDirty(IpsSysParam instance) {
		// TODO Auto-generated method stub
		
	}

	public void deleteIpsCardbin(IpsCardbin persistentInstance) {
		// TODO Auto-generated method stub
		
	}

	public void deleteIpsDevStat(IpsDevStat persistentInstance) {
		// TODO Auto-generated method stub
		
	}

	public void deleteIpsDevTranslimit(IpsDevTranslimit persistentInstance) {
		// TODO Auto-generated method stub
		
	}

	public void deleteIpsEvilCard(IpsEvilCard persistentInstance) {
		// TODO Auto-generated method stub
		
	}

	public void deleteIpsSaf(IpsSaf persistentInstance) {
		// TODO Auto-generated method stub
		
	}

	public void deleteIpsSysParam(IpsSysParam persistentInstance) {
		// TODO Auto-generated method stub
		
	}

	public List<IpsCardbin> findIpsCardbinAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByBankid(Object bankid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByBankname(Object bankname) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByCardflag(Object cardflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByCardid(Object cardid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByExample(IpsCardbin instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsCardbin findIpsCardbinById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByLength1(Object length1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByLength2(Object length2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByLength3(Object length3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByOffset1(Object offset1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByOffset2(Object offset2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByOffset3(Object offset3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByPanlen(Object panlen) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByPanoffset(Object panoffset) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByString1(Object string1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByString2(Object string2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByString3(Object string3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsCardbin> findIpsCardbinByTranlist(Object tranlist) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatByDevaccterrinf(Object devaccterrinf) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatByDeverrinf(Object deverrinf) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatByDevstat(Object devstat) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatByExample(IpsDevStat instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsDevStat findIpsDevStatById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatBySigndatetime(Object signdatetime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatBySignstat(Object signstat) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevStat> findIpsDevStatByUpddatetime(Object upddatetime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevTranslimit> findIpsDevTranslimitAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevTranslimit> findIpsDevTranslimitByDevtype(Object devtype) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevTranslimit> findIpsDevTranslimitByExample(
			IpsDevTranslimit instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsDevTranslimit findIpsDevTranslimitById(IpsDevTranslimitId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsDevTranslimit> findIpsDevTranslimitByProperty(
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEvilCard> findIpsEvilCardAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEvilCard> findIpsEvilCardByCardflag(Object cardflag) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEvilCard> findIpsEvilCardByExample(IpsEvilCard instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsEvilCard findIpsEvilCardById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEvilCard> findIpsEvilCardByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsEvilCard> findIpsEvilCardBySetreason(Object setreason) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByClrdate(Object clrdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByDestid(Object destid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByEndtime(Object endtime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByExample(IpsSaf instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsSaf findIpsSafById(IpsSafId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByIntranscode(Object intranscode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByOvertime(Object overtime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByRspcode(Object rspcode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafBySendpac(Object sendpac) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafBySendtimes(Object sendtimes) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafBySourceid(Object sourceid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSaf> findIpsSafByState(Object state) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSysParam> findIpsSysParamAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSysParam> findIpsSysParamByExample(IpsSysParam instance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsSysParam findIpsSysParamById(String id) {
		return this.ipsSysParamDAO.findById(id);
	}

	public List<IpsSysParam> findIpsSysParamByParamdesc(Object paramdesc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSysParam> findIpsSysParamByParammodflg(Object parammodflg) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSysParam> findIpsSysParamByParamname(Object paramname) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSysParam> findIpsSysParamByParamvalue(Object paramvalue) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IpsSysParam> findIpsSysParamByProperty(String propertyName,
			Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsSysParam merge(IpsSysParam detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsCardbin mergeIpsCardbin(IpsCardbin detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsDevStat mergeIpsDevStat(IpsDevStat detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsDevTranslimit mergeIpsDevTranslimit(
			IpsDevTranslimit detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsEvilCard mergeIpsEvilCard(IpsEvilCard detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public IpsSaf mergeIpsSaf(IpsSaf detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveIpsCardbin(IpsCardbin transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void saveIpsDevStat(IpsDevStat transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void saveIpsDevTranslimit(IpsDevTranslimit transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void saveIpsEvilCard(IpsEvilCard transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void saveIpsSaf(IpsSaf transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void saveIpsSysParam(IpsSysParam transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void saveOrUpdeateIpsDevStat(IpsDevStat transientInstance) {
		// TODO Auto-generated method stub
		
	}

	public void updeateIpsDevStat(IpsDevStat transientInstance) {
		// TODO Auto-generated method stub
		
	}

}
