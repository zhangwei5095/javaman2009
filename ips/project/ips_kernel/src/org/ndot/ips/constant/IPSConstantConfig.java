package org.ndot.ips.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ndot.ips.db.pojo.IpsCardbin;
import org.ndot.ips.db.pojo.IpsErrrspcodeMap;
import org.ndot.ips.db.pojo.IpsErrrspcodeMapId;
import org.ndot.ips.db.pojo.IpsMainKey;
import org.ndot.ips.db.pojo.IpsRspCode;
import org.ndot.ips.db.pojo.IpsRspCodeId;
import org.ndot.ips.db.services.BusinessDBServices;
import org.ndot.ips.db.services.SecurityDBService;

/**
 * 【小蚂蚁学堂 之 J2EE】
 * 
 * 项目名称：ips_kernel
 * 
 * 文件名： SecurityConfig.java
 * 
 * 功 能:
 * 
 * 作 者: NDot
 * 
 * 创建时间: 2009 2009-7-16
 * 
 */
public class IPSConstantConfig {
	private BusinessDBServices businessDBServices;
	private SecurityDBService securityDBService;
	private String mainKey = "";
	private Map<IpsErrrspcodeMapId, IpsErrrspcodeMap> ipsErrrspcodeMapSet = new HashMap<IpsErrrspcodeMapId, IpsErrrspcodeMap>();
	private Map<IpsRspCodeId, IpsRspCode> ipsRspCodeSet = new HashMap<IpsRspCodeId, IpsRspCode>();
	private List<IpsCardbin> ipsCardbinList = new ArrayList<IpsCardbin>();

	public Map<IpsErrrspcodeMapId, IpsErrrspcodeMap> getIpsErrrspcodeMapSet() {
		return ipsErrrspcodeMapSet;
	}

	public void setIpsErrrspcodeMapSet(
			Map<IpsErrrspcodeMapId, IpsErrrspcodeMap> ipsErrrspcodeMapSet) {
		this.ipsErrrspcodeMapSet = ipsErrrspcodeMapSet;
	}

	public Map<IpsRspCodeId, IpsRspCode> getIpsRspCodeSet() {
		return ipsRspCodeSet;
	}

	public void setIpsRspCodeSet(Map<IpsRspCodeId, IpsRspCode> ipsRspCodeSet) {
		this.ipsRspCodeSet = ipsRspCodeSet;
	}

	public SecurityDBService getSecurityDBService() {
		return securityDBService;
	}

	public void setSecurityDBService(SecurityDBService securityDBService) {
		this.securityDBService = securityDBService;
	}

	public void init() {
		// 获得数据库中的主密钥
		List<IpsMainKey> mainkeys = securityDBService.findIpsMainKeyAll();
		for (IpsMainKey mk : mainkeys) {
			setMainKey(mk.getMainkey());
		}
		// 系統错误代码转换表
		List<IpsErrrspcodeMap> ipsErrrspcodeMapSets = this.businessDBServices
				.findIpsErrrspcodeMapAll();
		for (IpsErrrspcodeMap ipsErrrspcodeMap : ipsErrrspcodeMapSets) {
			this.ipsErrrspcodeMapSet.put(ipsErrrspcodeMap.getId(),
					ipsErrrspcodeMap);
		}
		// 渠道应答代码转换表
		List<IpsRspCode> ipsRspCodeSets = this.businessDBServices
				.findIpsRspCodeAll();
		for (IpsRspCode ipsRspCode : ipsRspCodeSets) {
			this.ipsRspCodeSet.put(ipsRspCode.getId(), ipsRspCode);
		}
		// 卡bin信息
		this.ipsCardbinList = this.businessDBServices.findIpsCardbinAll();

	}

	public BusinessDBServices getBusinessDBServices() {
		return businessDBServices;
	}

	public void setBusinessDBServices(BusinessDBServices businessDBServices) {
		this.businessDBServices = businessDBServices;
	}

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public List<IpsCardbin> getIpsCardbinList() {
		return ipsCardbinList;
	}

	public void setIpsCardbinList(List<IpsCardbin> ipsCardbinList) {
		this.ipsCardbinList = ipsCardbinList;
	}

}
