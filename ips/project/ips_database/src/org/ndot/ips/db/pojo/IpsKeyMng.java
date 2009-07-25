package org.ndot.ips.db.pojo;

/**
 * IpsKeyMng entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsKeyMng implements java.io.Serializable {

	// Fields

	private IpsKeyMngId id;
	private String encmethod;
	private String keylen;
	private String keyvalue;
	private String indexno;
	private String pinmode;

	// Constructors

	/** default constructor */
	public IpsKeyMng() {
	}

	/** minimal constructor */
	public IpsKeyMng(IpsKeyMngId id) {
		this.id = id;
	}

	/** full constructor */
	public IpsKeyMng(IpsKeyMngId id, String encmethod, String keylen,
			String keyvalue, String indexno, String pinmode) {
		this.id = id;
		this.encmethod = encmethod;
		this.keylen = keylen;
		this.keyvalue = keyvalue;
		this.indexno = indexno;
		this.pinmode = pinmode;
	}

	// Property accessors

	public IpsKeyMngId getId() {
		return this.id;
	}

	public void setId(IpsKeyMngId id) {
		this.id = id;
	}

	public String getEncmethod() {
		return this.encmethod;
	}

	public void setEncmethod(String encmethod) {
		this.encmethod = encmethod;
	}

	public String getKeylen() {
		return this.keylen;
	}

	public void setKeylen(String keylen) {
		this.keylen = keylen;
	}

	public String getKeyvalue() {
		return this.keyvalue;
	}

	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}

	public String getIndexno() {
		return this.indexno;
	}

	public void setIndexno(String indexno) {
		this.indexno = indexno;
	}

	public String getPinmode() {
		return this.pinmode;
	}

	public void setPinmode(String pinmode) {
		this.pinmode = pinmode;
	}

}