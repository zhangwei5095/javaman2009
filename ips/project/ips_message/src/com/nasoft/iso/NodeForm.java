package com.nasoft.iso;

import org.ndot.ips.db.pojo.PMsgDef;

/**
 * 
 * Modify by : NDot
 * 
 * 定义不同报文的报文头长度所在域，报文体长度所在域，报文总长度所在域，是否包含消息类型等信息
 * 
 */
public class NodeForm {

	private String nodeId;// 节点ID,如:C023
	private String reqRsp;// 报文类型，如：req或rsp
	private String headLenField;// 报文头长度域编号，没有填N C023:N
	private String bodyLenField;// 报文体长度域编号，没有填N C023:3(报文体长度在第三域)
	private String msgLenField;// 报文总长度域编号，没有填N C023:N
	private String msgLen1Field;// 报文总长度去掉表示报文长度的域的长度的域编号，没有填N C023:N
	private String msgType;// 0：不包含报文类型 1：包含报文类型 C023:0

	public NodeForm(PMsgDef msgDef) {
		this.nodeId = msgDef.getId().getNodeid();
		this.reqRsp = msgDef.getId().getReqrsp();
		this.headLenField = msgDef.getHeadlenfield();
		this.bodyLenField = msgDef.getBodylenfield();
		this.msgLenField = msgDef.getMsglenfield();
		this.msgLen1Field = msgDef.getMsglen1field();
		this.msgType = msgDef.getMsgtype();
	}

	/**
	 * @return the bodyLenField
	 */
	public String getBodyLenField() {
		return bodyLenField;
	}

	/**
	 * @param bodyLenField
	 *            the bodyLenField to set
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
	 * @param headLenField
	 *            the headLenField to set
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
	 * @param msgLen1Field
	 *            the msgLen1Field to set
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
	 * @param msgLenField
	 *            the msgLenField to set
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
	 * @param msgType
	 *            the msgType to set
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
	 * @param nodeId
	 *            the nodeId to set
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
	 * @param reqRsp
	 *            the reqRsp to set
	 */
	public void setReqRsp(String reqRsp) {
		this.reqRsp = reqRsp;
	}

}
