package org.ndot.ips.db.pojo;

/**
 * PIsoDefine entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PIsoDefine implements java.io.Serializable {

	// Fields

	private PIsoDefineId id;
	private String fieldname;
	private String prefixtype;
	private String prefixlen;
	private String datatype;
	private String datalen;
	private String padtype;
	private String shortcut;

	// Constructors

	/** default constructor */
	public PIsoDefine() {
	}

	/** minimal constructor */
	public PIsoDefine(PIsoDefineId id, String fieldname) {
		this.id = id;
		this.fieldname = fieldname;
	}

	/** full constructor */
	public PIsoDefine(PIsoDefineId id, String fieldname, String prefixtype,
			String prefixlen, String datatype, String datalen, String padtype,
			String shortcut) {
		this.id = id;
		this.fieldname = fieldname;
		this.prefixtype = prefixtype;
		this.prefixlen = prefixlen;
		this.datatype = datatype;
		this.datalen = datalen;
		this.padtype = padtype;
		this.shortcut = shortcut;
	}

	// Property accessors

	public PIsoDefineId getId() {
		return this.id;
	}

	public void setId(PIsoDefineId id) {
		this.id = id;
	}

	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getPrefixtype() {
		return this.prefixtype;
	}

	public void setPrefixtype(String prefixtype) {
		this.prefixtype = prefixtype;
	}

	public String getPrefixlen() {
		return this.prefixlen;
	}

	public void setPrefixlen(String prefixlen) {
		this.prefixlen = prefixlen;
	}

	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDatalen() {
		return this.datalen;
	}

	public void setDatalen(String datalen) {
		this.datalen = datalen;
	}

	public String getPadtype() {
		return this.padtype;
	}

	public void setPadtype(String padtype) {
		this.padtype = padtype;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

}