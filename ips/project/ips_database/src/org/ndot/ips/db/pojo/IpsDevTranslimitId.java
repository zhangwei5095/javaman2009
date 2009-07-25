package org.ndot.ips.db.pojo;

/**
 * IpsDevTranslimitId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsDevTranslimitId implements java.io.Serializable {

	// Fields

	private String devid;
	private String intranscode;

	// Constructors

	/** default constructor */
	public IpsDevTranslimitId() {
	}

	/** full constructor */
	public IpsDevTranslimitId(String devid, String intranscode) {
		this.devid = devid;
		this.intranscode = intranscode;
	}

	// Property accessors

	public String getDevid() {
		return this.devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}

	public String getIntranscode() {
		return this.intranscode;
	}

	public void setIntranscode(String intranscode) {
		this.intranscode = intranscode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsDevTranslimitId))
			return false;
		IpsDevTranslimitId castOther = (IpsDevTranslimitId) other;

		return ((this.getDevid() == castOther.getDevid()) || (this.getDevid() != null
				&& castOther.getDevid() != null && this.getDevid().equals(
				castOther.getDevid())))
				&& ((this.getIntranscode() == castOther.getIntranscode()) || (this
						.getIntranscode() != null
						&& castOther.getIntranscode() != null && this
						.getIntranscode().equals(castOther.getIntranscode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDevid() == null ? 0 : this.getDevid().hashCode());
		result = 37
				* result
				+ (getIntranscode() == null ? 0 : this.getIntranscode()
						.hashCode());
		return result;
	}

}