package org.ndot.ips.db.pojo;

/**
 * IpsSafId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsSafId implements java.io.Serializable {

	// Fields

	private String localjnlno;
	private String localdatetime;

	// Constructors

	/** default constructor */
	public IpsSafId() {
	}

	/** full constructor */
	public IpsSafId(String localjnlno, String localdatetime) {
		this.localjnlno = localjnlno;
		this.localdatetime = localdatetime;
	}

	// Property accessors

	public String getLocaljnlno() {
		return this.localjnlno;
	}

	public void setLocaljnlno(String localjnlno) {
		this.localjnlno = localjnlno;
	}

	public String getLocaldatetime() {
		return this.localdatetime;
	}

	public void setLocaldatetime(String localdatetime) {
		this.localdatetime = localdatetime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsSafId))
			return false;
		IpsSafId castOther = (IpsSafId) other;

		return ((this.getLocaljnlno() == castOther.getLocaljnlno()) || (this
				.getLocaljnlno() != null
				&& castOther.getLocaljnlno() != null && this.getLocaljnlno()
				.equals(castOther.getLocaljnlno())))
				&& ((this.getLocaldatetime() == castOther.getLocaldatetime()) || (this
						.getLocaldatetime() != null
						&& castOther.getLocaldatetime() != null && this
						.getLocaldatetime()
						.equals(castOther.getLocaldatetime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getLocaljnlno() == null ? 0 : this.getLocaljnlno()
						.hashCode());
		result = 37
				* result
				+ (getLocaldatetime() == null ? 0 : this.getLocaldatetime()
						.hashCode());
		return result;
	}

}