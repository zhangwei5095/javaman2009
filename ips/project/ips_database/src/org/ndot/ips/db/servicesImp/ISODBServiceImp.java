package org.ndot.ips.db.servicesImp;

import java.util.List;

import org.ndot.ips.db.DBJdbcTool;
import org.ndot.ips.db.pojo.PIsoDefine;
import org.ndot.ips.db.pojo.PIsoDefineDAO;
import org.ndot.ips.db.pojo.PIsoDefineId;
import org.ndot.ips.db.pojo.PMsgDef;
import org.ndot.ips.db.pojo.PMsgDefDAO;
import org.ndot.ips.db.pojo.PMsgDefId;
import org.ndot.ips.db.services.ISODBService;


public class ISODBServiceImp implements ISODBService {
	private PIsoDefineDAO isoDef;
	private PMsgDefDAO pmsgDef;
	private DBJdbcTool dbJdbcTool;

	public ISODBServiceImp() {

	}

	public ISODBServiceImp(PIsoDefineDAO isoDef, PMsgDefDAO pmsgdef) {
		super();
		this.isoDef = isoDef;
		this.pmsgDef = pmsgdef;
	}


	public PIsoDefineDAO getIsoDef() {
		return isoDef;
	}

	public void setIsoDef(PIsoDefineDAO isoDef) {
		this.isoDef = isoDef;
	}

	public PMsgDefDAO getPmsgDef() {
		return pmsgDef;
	}

	public void setPmsgDef(PMsgDefDAO pmsgDef) {
		this.pmsgDef = pmsgDef;
	}

	public List<PIsoDefine>  isoDefineFindAll() {
		return isoDef.findAll();
	}
	
	public void isoDefineAttachClean(PIsoDefine instance) {
		isoDef.attachClean(instance);
	}

	public void isoDefineAttachDirty(PIsoDefine instance) {
		isoDef.attachDirty(instance);
	}

	public void isoDefineDelete(PIsoDefine persistentInstance) {
		isoDef.delete(persistentInstance);
	}

	public PIsoDefine isoDefineMerge(PIsoDefine detachedInstance) {
		return isoDef.merge(detachedInstance);
	}

	public List<PIsoDefine>  isoDefineFindByDatalen(Object datalen) {
		return isoDef.findByDatalen(datalen);
	}

	public List<PIsoDefine>  isoDefineFindByDatatype(Object datatype) {
		return isoDef.findByDatatype(datatype);
	}

	public List<PIsoDefine>  isoDefineFindByExample(PIsoDefine instance) {
		return isoDef.findByExample(instance);
	}

	public List<PIsoDefine>  isoDefineFindByFieldname(Object fieldname) {
		return isoDef.findByFieldname(fieldname);
	}

	public PIsoDefine isoDefineFindById(PIsoDefineId id) {
		return isoDef.findById(id);
	}

	public List<PIsoDefine>  isoDefineFindByPadtype(Object padtype) {
		return isoDef.findByPadtype(padtype);
	}

	public List<PIsoDefine>  isoDefineFindByProperty(String propertyName, Object value) {
		return isoDef.findByProperty(propertyName, value);
	}

	public List<PIsoDefine>  isoDefineFindByShortcut(Object shortcut) {
		return isoDef.findByShortcut(shortcut);
	}

	public List<PIsoDefine>  isoDefineFindByPrefixtype(Object prefixtype) {
		return isoDef.findByPrefixtype(prefixtype);
	}

	public List isoDefineFindByPrefixlen(Object prefixlen) {
		return isoDef.findByPrefixlen(prefixlen);
	}

	public void isoDefineSave(PIsoDefine transientInstance) {
		isoDef.save(transientInstance);
	}

	public void pMsgDefAttachClean(PMsgDef instance) {
		pmsgDef.attachClean(instance);
	}

	public void pMsgDefAttachDirty(PMsgDef instance) {
		pmsgDef.attachDirty(instance);
	}

	public void pMsgDefFDelete(PMsgDef persistentInstance) {
		pmsgDef.delete(persistentInstance);
	}

	public List<PMsgDef> pMsgDefFindByBodylenfield(Object bodylenfield) {
		return pmsgDef.findByBodylenfield(bodylenfield);
	}

	public List<PMsgDef> pMsgDefFindByExample(PMsgDef instance) {
		return pmsgDef.findByExample(instance);
	}

	public List<PMsgDef> pMsgDefFindByHeadlenfield(Object headlenfield) {
		return pmsgDef.findByHeadlenfield(headlenfield);
	}

	public PMsgDef pMsgDefFindById(PMsgDefId id) {
		return pmsgDef.findById(id);
	}

	public List<PMsgDef> pMsgDefFindByMsglen1field(Object msglen1field) {
		return pmsgDef.findByMsglen1field(msglen1field);
	}

	public List<PMsgDef> pMsgDefFindByMsglenfield(Object msglenfield) {
		return pmsgDef.findByMsglenfield(msglenfield);
	}

	public List<PMsgDef> pMsgDefFindByMsgtype(Object msgtype) {
		return pmsgDef.findByMsgtype(msgtype);
	}

	public List<PMsgDef> pMsgDefFindByProperty(String propertyName, Object value) {
		return pmsgDef.findByProperty(propertyName, value);
	}

	public PMsgDef pMsgDefMerge(PMsgDef detachedInstance) {
		return pmsgDef.merge(detachedInstance);
	}

	public void pMsgDefSave(PMsgDef transientInstance) {
		pmsgDef.save(transientInstance);
	}

	public List<PMsgDef> pMsgDefFindAll() {
		return pmsgDef.findAll();
	}

	public DBJdbcTool getDbJdbcTool() {
		return dbJdbcTool;
	}

	public void setDbJdbcTool(DBJdbcTool dbJdbcTool) {
		this.dbJdbcTool = dbJdbcTool;
	}

	public List find(String tableName, String[] columnNames, String whereContent) {
		return dbJdbcTool.find(tableName, columnNames, whereContent);
	}
	



	
	

}
