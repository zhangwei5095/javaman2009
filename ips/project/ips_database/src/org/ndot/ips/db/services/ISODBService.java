package org.ndot.ips.db.services;

import java.util.List;

import org.ndot.ips.db.pojo.PIsoDefine;
import org.ndot.ips.db.pojo.PMsgDef;


public interface ISODBService {
	//通用
	List find(String tableName,String[] columnNames,String whereContent);
	//isoDefine操作接口
	void isoDefineSave(PIsoDefine transientInstance);

	void isoDefineDelete(PIsoDefine persistentInstance);

	PIsoDefine isoDefineFindById(org.ndot.ips.db.pojo.PIsoDefineId id);

	List<PIsoDefine>  isoDefineFindByExample(PIsoDefine instance);

	List<PIsoDefine> isoDefineFindByProperty(String propertyName, Object value);

	List<PIsoDefine>  isoDefineFindByFieldname(Object fieldname);

	List<PIsoDefine>  isoDefineFindByPrefixtype(Object prefixtype);

	List<PIsoDefine>  isoDefineFindByPrefixlen(Object prefixlen);

	List<PIsoDefine>  isoDefineFindByDatatype(Object datatype);

	List<PIsoDefine>  isoDefineFindByDatalen(Object datalen);

	List<PIsoDefine>  isoDefineFindByPadtype(Object padtype);

	List<PIsoDefine>  isoDefineFindByShortcut(Object shortcut);

	List<PIsoDefine>  isoDefineFindAll();

	PIsoDefine isoDefineMerge(PIsoDefine detachedInstance);

	void isoDefineAttachDirty(PIsoDefine instance);

	void isoDefineAttachClean(PIsoDefine instance);
	
//PMsgDef操作接口

	 void pMsgDefSave(PMsgDef transientInstance) ;

	 void pMsgDefFDelete(PMsgDef persistentInstance);
	 PMsgDef pMsgDefFindById(org.ndot.ips.db.pojo.PMsgDefId id) ;

	 List<PMsgDef> pMsgDefFindByExample(PMsgDef instance);

	 List<PMsgDef> pMsgDefFindByProperty(String propertyName, Object value);

	 List<PMsgDef> pMsgDefFindByHeadlenfield(Object headlenfield) ;
	 List<PMsgDef> pMsgDefFindByBodylenfield(Object bodylenfield) ;
	 List<PMsgDef> pMsgDefFindByMsglenfield(Object msglenfield) ;

	 List<PMsgDef> pMsgDefFindByMsglen1field(Object msglen1field) ;
	 List<PMsgDef> pMsgDefFindByMsgtype(Object msgtype) ;
	 List<PMsgDef> pMsgDefFindAll();

	 PMsgDef pMsgDefMerge(PMsgDef detachedInstance) ;

	 void pMsgDefAttachDirty(PMsgDef instance) ;

	 void pMsgDefAttachClean(PMsgDef instance) ;
}
