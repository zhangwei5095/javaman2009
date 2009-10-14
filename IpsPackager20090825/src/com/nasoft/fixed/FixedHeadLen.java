package com.nasoft.fixed;

import java.util.List;

import org.jdom.Element;

import com.nasoft.Utilities;

public class FixedHeadLen {
	private String NodeId;//节点编号
	private String ReqRsp;//请求应答标志rsp应答req请求
	private int headLen;// the length of the message head
	
	
	public FixedHeadLen(Element root,String nodeId,String reqRsp){
		List nodeList = root.getChildren();
		for (int i = 0; i < nodeList.size(); i++) 
		{
			Element e = (Element) nodeList.get(i);
			String elementNodeId = Utilities.getXmlNodeValue(e.getChild("NodeId",root.getNamespace()), "");
			String elementReqRsp = Utilities.getXmlNodeValue(e.getChild("ReqRsp",root.getNamespace()), "");
			if(nodeId.equals(elementNodeId) && reqRsp.equals(elementReqRsp)){
				this.NodeId=elementNodeId;
				this.ReqRsp=elementReqRsp;
				int headLen=Integer.parseInt(Utilities.getXmlNodeValue(e.getChild("HeadLen",root.getNamespace()), "0"));
				this.headLen=headLen;
				break;
			}
		}
		
	}
	
	/**
	 * @return the headLen
	 */
	public int getHeadLen() {
		return headLen;
	}
	/**
	 * @param headLen the headLen to set
	 */
	public void setHeadLen(int headLen) {
		this.headLen = headLen;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return NodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}
	/**
	 * @return the reqRsp
	 */
	public String getReqRsp() {
		return ReqRsp;
	}
	/**
	 * @param reqRsp the reqRsp to set
	 */
	public void setReqRsp(String reqRsp) {
		ReqRsp = reqRsp;
	}
}
