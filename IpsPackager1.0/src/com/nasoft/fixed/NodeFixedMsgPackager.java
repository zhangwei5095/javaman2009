package com.nasoft.fixed;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.nasoft.Utilities;

public class NodeFixedMsgPackager implements IFixedMsgPackager
{
	private String nodeId;
	private TranIdGetter tranIdGetter = null;
	private HashMap<String, IFixedMsgPackager> pMap = new  HashMap<String, IFixedMsgPackager>();
	
	public void init(Element root, String nodeId)
		throws Exception
	{
		String strXpath = "//Node[NodeID="+nodeId+"]";
		

		List nodeList = XPath.selectNodes(root, strXpath);
		Set uSet = new HashSet();
		
		for(int i=0; i<nodeList.size(); i++)
		{
			Element e = (Element)nodeList.get(i);
			String tranId = Utilities.getXmlNodeValue(e.getChild("TranCodeStr"), "");
			if(!uSet.contains(tranId))
			{
				uSet.add(tranId);
			}
		}
		for(Iterator it = uSet.iterator(); it.hasNext() ;)
		{
			String tranId = (String)it.next();
			BaseFixedMsgPackager fp = new BaseFixedMsgPackager();
			fp.initFldByXmlNode(root, nodeId, tranId);
			//fp.setTranId(tranId);
			pMap.put(tranId, fp); 	
		}
	}
	public String Msg2Xml(FixedMsg msg) throws Exception 
	{	
		String tranId = msg.getTranCode();
		IFixedMsgPackager packager = pMap.get(tranId);
		return packager.Msg2Xml(msg);
	}

	public FixedMsg Xml2Msg(FixedMsg msg, String xml) throws Exception 
	{
		String tranId = tranIdGetter.getTranId(xml);
		IFixedMsgPackager packager = pMap.get(tranId);
		packager.Xml2Msg(msg, xml);
		// TODO Auto-generated method stub
		return msg;
	}

	public byte[] pack(FixedMsg msg) throws Exception 
	{
		String tranId = msg.getTranCode();
		IFixedMsgPackager packager = pMap.get(tranId);
		return packager.pack(msg);
	}

	public int unpack(FixedMsg msg, byte[] b) throws Exception 
	{
		String tranId = tranIdGetter.getTranId(b, nodeId);
		IFixedMsgPackager packager = pMap.get(tranId);
		packager.unpack(msg, b);
		return 0;
	}

	public String getTranId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTranId(String tranId) {
		// TODO Auto-generated method stub
		
	}
	public TranIdGetter getTranIdGetter() {
		return tranIdGetter;
	}
	public void setTranIdGetter(TranIdGetter tranIdGetter) {
		this.tranIdGetter = tranIdGetter;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

}
