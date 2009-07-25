package org.ndot.ips.db.pojo;

/**
 * PMsgDefId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PMsgDefId implements java.io.Serializable {

	// Fields

	private String nodeid;
	private String reqrsp;

	// Constructors

	/** default constructor */
	public PMsgDefId() {
	}

	/** full constructor */
	public PMsgDefId(String nodeid, String reqrsp) {
		this.nodeid = nodeid;
		this.reqrsp = reqrsp;
	}

	// Property accessors

	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getReqrsp() {
		return this.reqrsp;
	}

	public void setReqrsp(String reqrsp) {
		this.reqrsp = reqrsp;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PMsgDefId))
			return false;
		PMsgDefId castOther = (PMsgDefId) other;

		return ((this.getNodeid() == castOther.getNodeid()) || (this
				.getNodeid() != null
				&& castOther.getNodeid() != null && this.getNodeid().equals(
				castOther.getNodeid())))
				&& ((this.getReqrsp() == castOther.getReqrsp()) || (this
						.getReqrsp() != null
						&& castOther.getReqrsp() != null && this.getReqrsp()
						.equals(castOther.getReqrsp())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getNodeid() == null ? 0 : this.getNodeid().hashCode());
		result = 37 * result
				+ (getReqrsp() == null ? 0 : this.getReqrsp().hashCode());
		return result;
	}

}