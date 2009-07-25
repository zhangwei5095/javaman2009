package org.ndot.ips.db.pojo;

/**
 * PMsgDef entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PMsgDef implements java.io.Serializable {

	// Fields

	private PMsgDefId id;
	private String headlenfield;
	private String bodylenfield;
	private String msglenfield;
	private String msglen1field;
	private String msgtype;

	// Constructors

	/** default constructor */
	public PMsgDef() {
	}

	/** minimal constructor */
	public PMsgDef(PMsgDefId id) {
		this.id = id;
	}

	/** full constructor */
	public PMsgDef(PMsgDefId id, String headlenfield, String bodylenfield,
			String msglenfield, String msglen1field, String msgtype) {
		this.id = id;
		this.headlenfield = headlenfield;
		this.bodylenfield = bodylenfield;
		this.msglenfield = msglenfield;
		this.msglen1field = msglen1field;
		this.msgtype = msgtype;
	}

	// Property accessors

	public PMsgDefId getId() {
		return this.id;
	}

	public void setId(PMsgDefId id) {
		this.id = id;
	}

	public String getHeadlenfield() {
		return this.headlenfield;
	}

	public void setHeadlenfield(String headlenfield) {
		this.headlenfield = headlenfield;
	}

	public String getBodylenfield() {
		return this.bodylenfield;
	}

	public void setBodylenfield(String bodylenfield) {
		this.bodylenfield = bodylenfield;
	}

	public String getMsglenfield() {
		return this.msglenfield;
	}

	public void setMsglenfield(String msglenfield) {
		this.msglenfield = msglenfield;
	}

	public String getMsglen1field() {
		return this.msglen1field;
	}

	public void setMsglen1field(String msglen1field) {
		this.msglen1field = msglen1field;
	}

	public String getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}