package org.ndot.ips.db.pojo;

/**
 * IpsErrrspcodeMapId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsErrrspcodeMapId implements java.io.Serializable {

	// Fields

	private String errcode;
	private String reqsyscode;

	// Constructors

	/** default constructor */
	public IpsErrrspcodeMapId() {
	}

	/** full constructor */
	public IpsErrrspcodeMapId(String errcode, String reqsyscode) {
		this.errcode = errcode;
		this.reqsyscode = reqsyscode;
	}

	// Property accessors

	public String getErrcode() {
		return this.errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getReqsyscode() {
		return this.reqsyscode;
	}

	public void setReqsyscode(String reqsyscode) {
		this.reqsyscode = reqsyscode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsErrrspcodeMapId))
			return false;
		IpsErrrspcodeMapId castOther = (IpsErrrspcodeMapId) other;

		return ((this.getErrcode() == castOther.getErrcode()) || (this
				.getErrcode() != null
				&& castOther.getErrcode() != null && this.getErrcode().equals(
				castOther.getErrcode())))
				&& ((this.getReqsyscode() == castOther.getReqsyscode()) || (this
						.getReqsyscode() != null
						&& castOther.getReqsyscode() != null && this
						.getReqsyscode().equals(castOther.getReqsyscode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getErrcode() == null ? 0 : this.getErrcode().hashCode());
		result = 37
				* result
				+ (getReqsyscode() == null ? 0 : this.getReqsyscode()
						.hashCode());
		return result;
	}

}