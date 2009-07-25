package org.ndot.ips.db.pojo;

/**
 * PIsoDefineId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PIsoDefineId implements java.io.Serializable {

	// Fields

	private String nodeid;
	private String fieldtype;
	private String fieldno;

	// Constructors

	/** default constructor */
	public PIsoDefineId() {
	}

	/** full constructor */
	public PIsoDefineId(String nodeid, String fieldtype, String fieldno) {
		this.nodeid = nodeid;
		this.fieldtype = fieldtype;
		this.fieldno = fieldno;
	}

	// Property accessors

	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getFieldtype() {
		return this.fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public String getFieldno() {
		return this.fieldno;
	}

	public void setFieldno(String fieldno) {
		this.fieldno = fieldno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PIsoDefineId))
			return false;
		PIsoDefineId castOther = (PIsoDefineId) other;

		return ((this.getNodeid() == castOther.getNodeid()) || (this
				.getNodeid() != null
				&& castOther.getNodeid() != null && this.getNodeid().equals(
				castOther.getNodeid())))
				&& ((this.getFieldtype() == castOther.getFieldtype()) || (this
						.getFieldtype() != null
						&& castOther.getFieldtype() != null && this
						.getFieldtype().equals(castOther.getFieldtype())))
				&& ((this.getFieldno() == castOther.getFieldno()) || (this
						.getFieldno() != null
						&& castOther.getFieldno() != null && this.getFieldno()
						.equals(castOther.getFieldno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getNodeid() == null ? 0 : this.getNodeid().hashCode());
		result = 37 * result
				+ (getFieldtype() == null ? 0 : this.getFieldtype().hashCode());
		result = 37 * result
				+ (getFieldno() == null ? 0 : this.getFieldno().hashCode());
		return result;
	}

}