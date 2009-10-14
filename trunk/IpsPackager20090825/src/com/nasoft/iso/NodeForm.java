package com.nasoft.iso;

import org.jdom.Element;

import com.nasoft.Utilities;

public class NodeForm {

	private String nodeId;
	private String reqRsp;
	private String headLenField;
	private String bodyLenField;
	private String msgLenField;
	private String msgLen1Field;
	private String msgType;
	
	public NodeForm(Element node){
		String elementNodeId = Utilities.getXmlNodeValue(node.getChild("NodeID",node.getNamespace()), "");
		String elementReqRsp = Utilities.getXmlNodeValue(node.getChild("ReqRsp",node.getNamespace()), "");
		String elmentHeadLenField=Utilities.getXmlNodeValue(node.getChild("HeadLenField",node.getNamespace()), "");
		String elmentBodyLenField=Utilities.getXmlNodeValue(node.getChild("BodyLenField",node.getNamespace()), "");
		String elmentMsgLenField=Utilities.getXmlNodeValue(node.getChild("MsgLenField",node.getNamespace()), "");
		String elmentMsgLen1Field=Utilities.getXmlNodeValue(node.getChild("MsgLen1Field",node.getNamespace()), "");
		String elmentMsgType=Utilities.getXmlNodeValue(node.getChild("MsgType",node.getNamespace()), "");
		this.nodeId=elementNodeId;
		this.reqRsp=elementReqRsp;
		this.headLenField=elmentHeadLenField;
		this.bodyLenField=elmentBodyLenField;
		this.msgLenField=elmentMsgLenField;
		this.msgLen1Field=elmentMsgLen1Field;
		this.msgType=elmentMsgType;
	}
	
	/**
	 * @return the bodyLenField
	 */
	public String getBodyLenField() {
		return bodyLenField;
	}
	/**
	 * @param bodyLenField the bodyLenField to set
	 */
	public void setBodyLenField(String bodyLenField) {
		this.bodyLenField = bodyLenField;
	}
	/**
	 * @return the headLenField
	 */
	public String getHeadLenField() {
		return headLenField;
	}
	/**
	 * @param headLenField the headLenField to set
	 */
	public void setHeadLenField(String headLenField) {
		this.headLenField = headLenField;
	}
	/**
	 * @return the msgLen1Field
	 */
	public String getMsgLen1Field() {
		return msgLen1Field;
	}
	/**
	 * @param msgLen1Field the msgLen1Field to set
	 */
	public void setMsgLen1Field(String msgLen1Field) {
		this.msgLen1Field = msgLen1Field;
	}
	/**
	 * @return the msgLenField
	 */
	public String getMsgLenField() {
		return msgLenField;
	}
	/**
	 * @param msgLenField the msgLenField to set
	 */
	public void setMsgLenField(String msgLenField) {
		this.msgLenField = msgLenField;
	}
	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the reqRsp
	 */
	public String getReqRsp() {
		return reqRsp;
	}
	/**
	 * @param reqRsp the reqRsp to set
	 */
	public void setReqRsp(String reqRsp) {
		this.reqRsp = reqRsp;
	}
	
	
}
