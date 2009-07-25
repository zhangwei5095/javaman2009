package com.nasoft.iso;

import org.ndot.ips.db.pojo.PMsgDef;

/**
 * 
 * Modify by : NDot
 * 
 * ���岻ͬ���ĵı���ͷ���������򣬱����峤�������򣬱����ܳ����������Ƿ������Ϣ���͵���Ϣ
 * 
 */
public class NodeForm {

	private String nodeId;// �ڵ�ID,��:C023
	private String reqRsp;// �������ͣ��磺req��rsp
	private String headLenField;// ����ͷ�������ţ�û����N C023:N
	private String bodyLenField;// �����峤�����ţ�û����N C023:3(�����峤���ڵ�����)
	private String msgLenField;// �����ܳ������ţ�û����N C023:N
	private String msgLen1Field;// �����ܳ���ȥ����ʾ���ĳ��ȵ���ĳ��ȵ����ţ�û����N C023:N
	private String msgType;// 0���������������� 1�������������� C023:0

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
