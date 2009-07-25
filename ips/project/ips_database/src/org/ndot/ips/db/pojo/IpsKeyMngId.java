package org.ndot.ips.db.pojo;

/**
 * IpsKeyMngId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsKeyMngId implements java.io.Serializable {

	// Fields

	private String platformid;
	private String nodeid;
	private String subnodeid;
	private String keytype;

	// Constructors

	/** default constructor */
	public IpsKeyMngId() {
	}

	/** full constructor */
	public IpsKeyMngId(String platformid, String nodeid, String subnodeid,
			String keytype) {
		this.platformid = platformid;
		this.nodeid = nodeid;
		this.subnodeid = subnodeid;
		this.keytype = keytype;
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

	public String getSubnodeid() {
		return this.subnodeid;
	}

	public void setSubnodeid(String subnodeid) {
		this.subnodeid = subnodeid;
	}

	public String getKeytype() {
		return this.keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsKeyMngId))
			return false;
		IpsKeyMngId castOther = (IpsKeyMngId) other;

		return ((this.getPlatformid() == castOther.getPlatformid()) || (this
				.getPlatformid() != null
				&& castOther.getPlatformid() != null && this.getPlatformid()
				.equals(castOther.getPlatformid())))
				&& ((this.getNodeid() == castOther.getNodeid()) || (this
						.getNodeid() != null
						&& castOther.getNodeid() != null && this.getNodeid()
						.equals(castOther.getNodeid())))
				&& ((this.getSubnodeid() == castOther.getSubnodeid()) || (this
						.getSubnodeid() != null
						&& castOther.getSubnodeid() != null && this
						.getSubnodeid().equals(castOther.getSubnodeid())))
				&& ((this.getKeytype() == castOther.getKeytype()) || (this
						.getKeytype() != null
						&& castOther.getKeytype() != null && this.getKeytype()
						.equals(castOther.getKeytype())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPlatformid() == null ? 0 : this.getPlatformid()
						.hashCode());
		result = 37 * result
				+ (getNodeid() == null ? 0 : this.getNodeid().hashCode());
		result = 37 * result
				+ (getSubnodeid() == null ? 0 : this.getSubnodeid().hashCode());
		result = 37 * result
				+ (getKeytype() == null ? 0 : this.getKeytype().hashCode());
		return result;
	}

}