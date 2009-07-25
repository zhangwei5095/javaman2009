package com.nasoft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ndot.ips.db.pojo.PIsoDefine;
import org.ndot.ips.db.pojo.PMsgDef;
import org.ndot.ips.db.servicesImp.ISODBServiceImp;

import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;

public class IPSReportConfigFactory {
	private ISODBServiceImp isoDBService;
	private HashMap<String, ISOPackager> isoBodyFieldMap = new HashMap<String, ISOPackager>();
	private HashMap<String, BaseHeader> isoHeaderFieldMap = new HashMap<String, BaseHeader>();
	private HashMap<String, NodeForm> nodeMap = new HashMap<String, NodeForm>();

	public IPSReportConfigFactory(ISODBServiceImp isoDBService) {
		super();
		this.isoDBService = isoDBService;
	}

	public ISOPackager getIsoPackagerBySysID(String sysId) {
		if (isoBodyFieldMap.containsKey(sysId)) {
			return isoBodyFieldMap.get(sysId);
		} else {
			return null;
		}
	}

	public BaseHeader getHeaderPackagerBySysID(String sysId) {
		if (isoHeaderFieldMap.containsKey(sysId)) {
			return isoHeaderFieldMap.get(sysId);
		} else {
			return null;
		}
	}

	public NodeForm getNodeInfo(String nodeId, String reqRsp) {
		String sysId = nodeId.trim() + reqRsp.trim();
		if (nodeMap.containsKey(sysId)) {
			return nodeMap.get(sysId);
		} else {
			return null;
		}
	}

	public HashMap<String, ISOPackager> getIsoPackMap() {
		return isoBodyFieldMap;
	}

	public void setIsoPackMap(HashMap<String, ISOPackager> isoPackMap) {
		this.isoBodyFieldMap = isoPackMap;
	}

	public HashMap<String, BaseHeader> getIsoHeaderPackMap() {
		return isoHeaderFieldMap;
	}

	public void setIsoHeaderPackMap(HashMap<String, BaseHeader> isoHeaderPackMap) {
		this.isoHeaderFieldMap = isoHeaderPackMap;
	}

	public HashMap<String, NodeForm> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(HashMap<String, NodeForm> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public IPSReportConfigFactory() throws Exception {

	}

	private void initIsoDefine() throws Exception {
		// 获得数据库配置的所有平台节点号（如银联C001、核心C002、ATMC003等）
		List rs = getIsoDBService().find("P_ISO_DEFINE",
				new String[] { "distinct NODEID" }, "");
		// 临时保存节点的列表
		List<String> nodeIdList = new ArrayList<String>();
		for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
			Map obj = (Map) iterator.next();
			nodeIdList.add((String) obj.get("NODEID"));
		}
		// 根据NODEID获取所有节点的配置信息（如：ATMC003涉及到的（小于等于128）域的配置信息
		for (String nodeid : nodeIdList) {
			// initPackagerMap
			// 获取指定节点（如ATMC003）的所有域配置信息
			List<PIsoDefine> isoList = getIsoDBService()
					.isoDefineFindByProperty("id.nodeid", nodeid);
			IPSGenericPackager gp = new IPSGenericPackager();
			gp.initFieldArray(isoList);
			// 将节点的Packager信息保存到平台Packager列表中
			isoBodyFieldMap.put(nodeid, gp);
			// initHeaderMap
			BaseHeader hp = new BaseHeader(isoList);
			isoHeaderFieldMap.put(nodeid, hp);
		}

	}

	public ISODBServiceImp getIsoDBService() {
		return isoDBService;
	}

	private void initNodeInfo() {
		List<PMsgDef> msgList = getIsoDBService().pMsgDefFindAll();
		for (PMsgDef msgDef : msgList) {
			String nodeId = msgDef.getId().getNodeid().trim();
			String reqRsp = msgDef.getId().getReqrsp().trim();
			String sysId = nodeId + reqRsp;
			NodeForm nodeForm = new NodeForm(msgDef);
			nodeMap.put(sysId, nodeForm);
		}

	}

	public HashMap<String, ISOPackager> getIsoBodyFieldMap() {
		return isoBodyFieldMap;
	}

	public void setIsoBodyFieldMap(HashMap<String, ISOPackager> isoBodyFieldMap) {
		this.isoBodyFieldMap = isoBodyFieldMap;
	}

	public HashMap<String, BaseHeader> getIsoHeaderFieldMap() {
		return isoHeaderFieldMap;
	}

	public void setIsoHeaderFieldMap(
			HashMap<String, BaseHeader> isoHeaderFieldMap) {
		this.isoHeaderFieldMap = isoHeaderFieldMap;
	}

	public void setIsoDBService(ISODBServiceImp isoDBService) {
		this.isoDBService = isoDBService;
		// 初始化所有的节点定义（如 ATMC003、POSC004等）
	}

	public void init() {
		try {
			initIsoDefine();
			initNodeInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
