package org.ndot.ips.db.pojo;

/**
 * IpsRspCodeId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsRspCodeId implements java.io.Serializable {

	// Fields

	private String reqsyscode;
	private String rspsyscode;
	private String rsprspcode;

	// Constructors

	/** default constructor */
	public IpsRspCodeId() {
	}

	/** full constructor */
	public IpsRspCodeId(String reqsyscode, String rspsyscode, String rsprspcode) {
		this.reqsyscode = reqsyscode;
		this.rspsyscode = rspsyscode;
		this.rsprspcode = rsprspcode;
	}

	// Property accessors

	public String getReqsyscode() {
		return this.reqsyscode;
	}

	public void setReqsyscode(String reqsyscode) {
		this.reqsyscode = reqsyscode;
	}

	public String getRspsyscode() {
		return this.rspsyscode;
	}

	public void setRspsyscode(String rspsyscode) {
		this.rspsyscode = rspsyscode;
	}

	public String getRsprspcode() {
		return this.rsprspcode;
	}

	public void setRsprspcode(String rsprspcode) {
		this.rsprspcode = rsprspcode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsRspCodeId))
			return false;
		IpsRspCodeId castOther = (IpsRspCodeId) other;

		return ((this.getReqsyscode() == castOther.getReqsyscode()) || (this
				.getReqsyscode() != null
				&& castOther.getReqsyscode() != null && this.getReqsyscode()
				.equals(castOther.getReqsyscode())))
				&& ((this.getRspsyscode() == castOther.getRspsyscode()) || (this
						.getRspsyscode() != null
						&& castOther.getRspsyscode() != null && this
						.getRspsyscode().equals(castOther.getRspsyscode())))
				&& ((this.getRsprspcode() == castOther.getRsprspcode()) || (this
						.getRsprspcode() != null
						&& castOther.getRsprspcode() != null && this
						.getRsprspcode().equals(castOther.getRsprspcode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getReqsyscode() == null ? 0 : this.getReqsyscode()
						.hashCode());
		result = 37
				* result
				+ (getRspsyscode() == null ? 0 : this.getRspsyscode()
						.hashCode());
		result = 37
				* result
				+ (getRsprspcode() == null ? 0 : this.getRsprspcode()
						.hashCode());
		return result;
	}

}