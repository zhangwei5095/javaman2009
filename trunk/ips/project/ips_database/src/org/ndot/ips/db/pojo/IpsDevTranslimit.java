package org.ndot.ips.db.pojo;

/**
 * IpsDevTranslimit entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsDevTranslimit implements java.io.Serializable {

	// Fields

	private IpsDevTranslimitId id;
	private String devtype;

	// Constructors

	/** default constructor */
	public IpsDevTranslimit() {
	}

	/** minimal constructor */
	public IpsDevTranslimit(IpsDevTranslimitId id) {
		this.id = id;
	}

	/** full constructor */
	public IpsDevTranslimit(IpsDevTranslimitId id, String devtype) {
		this.id = id;
		this.devtype = devtype;
	}

	// Property accessors

	public IpsDevTranslimitId getId() {
		return this.id;
	}

	public void setId(IpsDevTranslimitId id) {
		this.id = id;
	}

	public String getDevtype() {
		return this.devtype;
	}

	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}

}