package org.ndot.ips.db.pojo;

/**
 * IpsEncParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsEncParam implements java.io.Serializable {

	// Fields

	private IpsEncParamId id;
	private String enctype;
	private String subnodeflag;

	// Constructors

	/** default constructor */
	public IpsEncParam() {
	}

	/** minimal constructor */
	public IpsEncParam(IpsEncParamId id) {
		this.id = id;
	}

	/** full constructor */
	public IpsEncParam(IpsEncParamId id, String enctype, String subnodeflag) {
		this.id = id;
		this.enctype = enctype;
		this.subnodeflag = subnodeflag;
	}

	// Property accessors

	public IpsEncParamId getId() {
		return this.id;
	}

	public void setId(IpsEncParamId id) {
		this.id = id;
	}

	public String getEnctype() {
		return this.enctype;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	public String getSubnodeflag() {
		return this.subnodeflag;
	}

	public void setSubnodeflag(String subnodeflag) {
		this.subnodeflag = subnodeflag;
	}

}