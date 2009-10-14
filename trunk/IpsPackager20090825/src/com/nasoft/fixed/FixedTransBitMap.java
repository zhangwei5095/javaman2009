package com.nasoft.fixed;

import java.util.List;

import org.jdom.Element;

import com.nasoft.Utilities;

/**
 * TODO �̶���������ʶ���࣬����NodeId+ReqRsp+transIdʶ��һ�ཻ�ױ���
 *
 */
public class FixedTransBitMap {
	private String NodeId;//�ڵ���
	private String ReqRsp;//����Ӧ���־rspӦ��req����
	private String transId;//����ʶ����
	int MsgLen;//�����峤��
	String Fields;//��λͼ��־
	
	/**
	 * 
	 * @param root
	 * @param nodeId
	 * @param trasnId
	 * @param reqRsp
	 */
	public FixedTransBitMap(Element root,String nodeId,String reqRsp,String trasnId){
		List nodeList = root.getChildren();
		for (int i = 0; i < nodeList.size(); i++) 
		{
			Element e = (Element) nodeList.get(i);
			String elementNodeId = Utilities.getXmlNodeValue(e.getChild("NodeId",root.getNamespace()), "");
			String elementReqRsp = Utilities.getXmlNodeValue(e.getChild("ReqRsp",root.getNamespace()), "");
			String elementTransId = Utilities.getXmlNodeValue(e.getChild("TransId",root.getNamespace()), "");
			
			if(nodeId.equals(elementNodeId) && reqRsp.equals(elementReqRsp) && trasnId.equals(elementTransId)){
				this.NodeId=elementNodeId;
				this.ReqRsp=elementReqRsp;
				this.transId=elementTransId;
				String fields=Utilities.getXmlNodeValue(e.getChild("Fields",root.getNamespace()), "");
				int msgLen=Integer.parseInt(Utilities.getXmlNodeValue(e.getChild("MsgLen",root.getNamespace()), "0"));
				this.Fields=fields;
				this.MsgLen=msgLen;
			}
		}
	}
	
	/**
	 * @return the fields
	 */
	public String getFields() {
		return Fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(String fields) {
		Fields = fields;
	}
	/**
	 * @return the msgLen
	 */
	public int getMsgLen() {
		return MsgLen;
	}
	/**
	 * @param msgLen the msgLen to set
	 */
	public void setMsgLen(int msgLen) {
		MsgLen = msgLen;
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
	/**
	 * @return the transId
	 */
	public String getTransId() {
		return transId;
	}
	/**
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}
}
