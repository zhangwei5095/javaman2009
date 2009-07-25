package org.ndot.ips.db.pojo;

/**
 * IpsMainKey entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsMainKey implements java.io.Serializable {

	// Fields

	private String id;
	private String mainkey;

	// Constructors

	/** default constructor */
	public IpsMainKey() {
	}

	/** minimal constructor */
	public IpsMainKey(String id) {
		this.id = id;
	}

	/** full constructor */
	public IpsMainKey(String id, String mainkey) {
		this.id = id;
		this.mainkey = mainkey;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainkey() {
		return this.mainkey;
	}

	public void setMainkey(String mainkey) {
		this.mainkey = mainkey;
	}

}