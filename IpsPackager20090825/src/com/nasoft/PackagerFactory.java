package com.nasoft;

import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.nasoft.fixed.FixedTransBitMap;
import com.nasoft.iso.ISOPackager;
import com.nasoft.iso.NodeForm;
import com.nasoft.iso.header.BaseHeader;

public class PackagerFactory
{
	public static HashMap<String,ISOPackager> isoPackMap = new HashMap<String,ISOPackager>();
	private static HashMap<String,BaseHeader> isoHeaderPackMap = new HashMap<String,BaseHeader>();
//	public static HashMap<String,FixedHeadLen> fixedHeadLen =new HashMap<String,FixedHeadLen>();
	public static HashMap<String,FixedTransBitMap> fixedTransBitMap=new HashMap<String,FixedTransBitMap>();
	public static HashMap<String,ISOPackager> fixedPackMap = new HashMap<String,ISOPackager>();
	private static HashMap<String,BaseHeader> fixedHeaderPackMap = new HashMap<String,BaseHeader>();
	private static HashMap<String,NodeForm> nodeMap = new HashMap<String,NodeForm>();
	
	public static ISOPackager getIsoPackagerBySysID(String sysId)
	{
		if(isoPackMap.containsKey(sysId))
		{
			return isoPackMap.get(sysId);
		}else 
		{
			return null;
		}
	}
	
	public static BaseHeader getHeaderPackagerBySysID(String sysId)
	{
		if(isoHeaderPackMap.containsKey(sysId))
		{
			return isoHeaderPackMap.get(sysId);
		}else 
		{
			return null;
		}
	}
	
	public static NodeForm getNodeInfo(String nodeId,String reqRsp){
		String sysId=nodeId.trim()+reqRsp.trim();
		if(nodeMap.containsKey(sysId))
		{
			return nodeMap.get(sysId);
		}else 
		{
			return null;
		}
	}
	
	/**
	 * 取固定报文头packager
	 * @param sysId
	 * @return
	 */
	public static BaseHeader getFixedHeaderPackagerBySysID(String nodeId,String reqRsp)
	{
		String sysId=nodeId.trim()+reqRsp.trim();
		if(fixedHeaderPackMap.containsKey(sysId))
		{
			return fixedHeaderPackMap.get(sysId);
		}else 
		{
			return null;
		}
	}
	
	/**
	 * 取固定报文packager
	 * @param sysId
	 * @return
	 */
	public static ISOPackager getFixedPackagerByNodeId(String nodeId)
	{
		String sysId=nodeId.trim();
		if(fixedPackMap.containsKey(sysId))
		{
			return fixedPackMap.get(sysId);
		}else 
		{
			return null;
		}
	}
	
	/**
	 * 取固定报文位图，系统启动时运行
	 * @param nodeId
	 * @param reqRsp
	 * @return
	 */
	public static FixedTransBitMap getFixedBitMap(String nodeId,String reqRsp,String transId){
		if(nodeId==null ||reqRsp==null ||transId==null){
			return null;
		}
		
		String sysId=nodeId.trim()+reqRsp.trim()+transId.trim();
		if(fixedTransBitMap.containsKey(sysId)){
			return fixedTransBitMap.get(sysId);
		}
		else{
			if("C029".equals(nodeId) || "C028".equals(nodeId)){
				sysId=nodeId.trim()+reqRsp.trim()+"default";
				return fixedTransBitMap.get(sysId);
			}
			return null;
		}
	}
	
	
	public static void initNodeInfo(String xmlStr)throws Exception{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xmlStr);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		List allList = root.getChildren();
		Set uSet = new HashSet();
		
		for(int i=0; i<allList.size(); i++)
		{
			Element e = (Element)allList.get(i);
			String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeID",root.getNamespace()), "").trim();
			String reqRsp=Utilities.getXmlNodeValue(e.getChild("ReqRsp",root.getNamespace()), "").trim();
			String sysId=nodeId+reqRsp;
			NodeForm nodeForm=new NodeForm(e);
			nodeMap.put(sysId, nodeForm);
		}
	}
	
	/**
	 * 初始化固定报文头，系统启动时运行
	 * @param xml
	 * @throws Exception
	 */
	public static void initFixedHeaderMap(String xml) 
	throws Exception
{
	//String strXpath = "//Node[NodeID="+nodeId+" and FieldType=0]";
	SAXBuilder sb=new SAXBuilder();
	StringReader sr = new StringReader(xml);
	
	Document doc= sb.build(sr);
	Element root=doc.getRootElement();
//	List allList = XPath.selectNodes(root, "//Node");
	List allList = root.getChildren();
	Set uSet = new HashSet();
	for(int i=0; i<allList.size(); i++)
	{
		Element e = (Element)allList.get(i);
		String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeID",e.getNamespace()), "").trim();
		String reqRsp = Utilities.getXmlNodeValue(e.getChild("ReqRsp",e.getNamespace()), "").trim();
		String sysId=nodeId+reqRsp;
		if(!uSet.contains(sysId))
		{
			uSet.add(sysId);
		}
	}
	for(Iterator it = uSet.iterator(); it.hasNext() ;)
	{
		String sysId = (String)it.next();
		String nodeId="";
		String reqRsp="";
		if(sysId.indexOf("req")!=-1){
			nodeId=sysId.substring(0,sysId.indexOf("req"));
			reqRsp="req";
		}else if(sysId.indexOf("rsp")!=-1){
			nodeId=sysId.substring(0,sysId.indexOf("rsp"));
			reqRsp="rsp";
		}
		
		BaseHeader hp = new BaseHeader(root, nodeId,reqRsp);
		fixedHeaderPackMap.put(sysId, hp);
	}
}
	
	/**
	 * 初始化固定报文位图，系统启动时运行
	 * @param xmlStr
	 */
	public static void initFixedBitMap(String xmlStr)throws Exception{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xmlStr);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		List allList = root.getChildren();
		Set uSet = new HashSet();
		for(int i=0; i<allList.size(); i++)
		{
			Element e = (Element)allList.get(i);
			String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeId",root.getNamespace()), "").trim();
			String reqRsp = Utilities.getXmlNodeValue(e.getChild("ReqRsp",root.getNamespace()), "").trim();
			String transId = Utilities.getXmlNodeValue(e.getChild("TransId",root.getNamespace()), "").trim();
			String sysId=nodeId+reqRsp+transId;
			if(!uSet.contains(sysId))
			{
				uSet.add(sysId);
			}
		}
		
		for(Iterator it = uSet.iterator(); it.hasNext() ;)
		{
			String sysId = (String)it.next();
			String nodeId="";
			String reqRsp="";
			String transId="";
			if(sysId.indexOf("req")!=-1){
				nodeId=sysId.substring(0,sysId.indexOf("req"));
				reqRsp="req";
				transId=sysId.substring(sysId.indexOf("req")+3);
			}else if(sysId.indexOf("rsp")!=-1){
				nodeId=sysId.substring(0,sysId.indexOf("rsp"));
				reqRsp="rsp";
				transId=sysId.substring(sysId.indexOf("rsp")+3);
			}
			FixedTransBitMap hp = new FixedTransBitMap(root, nodeId,reqRsp,transId);
			fixedTransBitMap.put(sysId, hp);
		}
	}
	
	/**
	 * 初始化固定报文packager，系统启动时运行
	 * @param xml
	 * @throws Exception
	 */
	public static void initFixedPackagerMap(String xml) 
		throws Exception
	{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xml);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		List allList = XPath.selectNodes(root, "//Record");
		Set uSet = new HashSet();
		for(int i=0; i<allList.size(); i++)
		{
			Element e = (Element)allList.get(i);
			String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeID"), "");
			if(!uSet.contains(nodeId))
			{
				uSet.add(nodeId);
			}
		}
		
		for(Iterator it = uSet.iterator(); it.hasNext() ;)
		{
			String nodeId = (String)it.next();
			GenericFixedPackager nfp = new GenericFixedPackager();
			nfp.initFixedFieldArray(root, nodeId);
			fixedPackMap.put(nodeId, nfp);
		}
	}
	
	
	public static void initPackagerMap(String strXml) 
		throws Exception
	{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(strXml);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		List allList = root.getChildren();
		Set uSet = new HashSet();
		
		for(int i=0; i<allList.size(); i++)
		{
			Element e = (Element)allList.get(i);
			String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeID",root.getNamespace()), "");
			if(!uSet.contains(nodeId))
			{
				uSet.add(nodeId);
				
			}
		}
		for(Iterator it = uSet.iterator(); it.hasNext() ;)
		{
			String nodeId = (String)it.next();
			GenericPackager gp = new GenericPackager();
			gp.initFieldArray(root, nodeId);
			isoPackMap.put(nodeId, gp);
		}
	}
	public static void initHeaderMap(String xml) 
		throws Exception
	{
		SAXBuilder sb=new SAXBuilder();
		StringReader sr = new StringReader(xml);
		
		Document doc= sb.build(sr);
		Element root=doc.getRootElement();
		List allList = root.getChildren();
		Set uSet = new HashSet();
		for(int i=0; i<allList.size(); i++)
		{
			Element e = (Element)allList.get(i);
			String nodeId = Utilities.getXmlNodeValue(e.getChild("NodeID",e.getNamespace()), "");
			if(!uSet.contains(nodeId))
			{
				uSet.add(nodeId);
			}
		}
		for(Iterator it = uSet.iterator(); it.hasNext() ;)
		{
			String nodeId = (String)it.next();
			if(nodeId.equals("C002")){
				System.out.println();
			}
			BaseHeader hp = new BaseHeader(root, nodeId);
			isoHeaderPackMap.put(nodeId, hp);
		}
	}
	
	
}
