package org.ndot.ips.db.pojo;

/**
 * IpsEncParamId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsEncParamId implements java.io.Serializable {

	// Fields

	private String platformid;
	private String nodeid;

	// Constructors

	/** default constructor */
	public IpsEncParamId() {
	}

	/** full constructor */
	public IpsEncParamId(String platformid, String nodeid) {
		this.platformid = platformid;
		this.nodeid = nodeid;
	}

	// Property accessors

	public String getPlatformid() {
		return this.platformid;
	}

	public void setPlatformid(String platformid) {
		this.platformid = platformid;
	}

	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsEncParamId))
			return false;
		IpsEncParamId castOther = (IpsEncParamId) other;

		return ((this.getPlatformid() == castOther.getPlatformid()) || (this
				.getPlatformid() != null
				&& castOther.getPlatformid() != null && this.getPlatformid()
				.equals(castOther.getPlatformid())))
				&& ((this.getNodeid() == castOther.getNodeid()) || (this
						.getNodeid() != null
						&& castOther.getNodeid() != null && this.getNodeid()
						.equals(castOther.getNodeid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPlatformid() == null ? 0 : this.getPlatformid()
						.hashCode());
		result = 37 * result
				+ (getNodeid() == null ? 0 : this.getNodeid().hashCode());
		return result;
	}

}