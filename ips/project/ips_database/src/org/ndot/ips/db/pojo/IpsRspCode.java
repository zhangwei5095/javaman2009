package org.ndot.ips.db.pojo;

/**
 * IpsRspCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsRspCode implements java.io.Serializable {

	// Fields

	private IpsRspCodeId id;
	private String reqrspcode;
	private String eatcardflag;
	private String rspcodeinf;

	// Constructors

	/** default constructor */
	public IpsRspCode() {
	}

	/** minimal constructor */
	public IpsRspCode(IpsRspCodeId id, String reqrspcode, String eatcardflag) {
		this.id = id;
		this.reqrspcode = reqrspcode;
		this.eatcardflag = eatcardflag;
	}
	 
	/** full constructor */
	public IpsRspCode(IpsRspCodeId id, String reqrspcode, String eatcardflag,
			String rspcodeinf) {
		this.id = id;
		this.reqrspcode = reqrspcode;
		this.eatcardflag = eatcardflag;
		this.rspcodeinf = rspcodeinf;
	}

	// Property accessors

	public IpsRspCodeId getId() {
		return this.id;
	}

	public void setId(IpsRspCodeId id) {
		this.id = id;
	}

	public String getReqrspcode() {
		return this.reqrspcode;
	}

	public void setReqrspcode(String reqrspcode) {
		this.reqrspcode = reqrspcode;
	}

	public String getEatcardflag() {
		return this.eatcardflag;
	}

	public void setEatcardflag(String eatcardflag) {
		this.eatcardflag = eatcardflag;
	}

	public String getRspcodeinf() {
		return this.rspcodeinf;
	}

	public void setRspcodeinf(String rspcodeinf) {
		this.rspcodeinf = rspcodeinf;
	}

}