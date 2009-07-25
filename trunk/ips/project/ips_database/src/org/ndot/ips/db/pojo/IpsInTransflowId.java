package org.ndot.ips.db.pojo;

/**
 * IpsInTransflowId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsInTransflowId implements java.io.Serializable {

	// Fields

	private String intranscode;
	private String processno;

	// Constructors

	/** default constructor */
	public IpsInTransflowId() {
	}

	/** full constructor */
	public IpsInTransflowId(String intranscode, String processno) {
		this.intranscode = intranscode;
		this.processno = processno;
	}

	// Property accessors

	public String getIntranscode() {
		return this.intranscode;
	}

	public void setIntranscode(String intranscode) {
		this.intranscode = intranscode;
	}

	public String getProcessno() {
		return this.processno;
	}

	public void setProcessno(String processno) {
		this.processno = processno;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IpsInTransflowId))
			return false;
		IpsInTransflowId castOther = (IpsInTransflowId) other;

		return ((this.getIntranscode() == castOther.getIntranscode()) || (this
				.getIntranscode() != null
				&& castOther.getIntranscode() != null && this.getIntranscode()
				.equals(castOther.getIntranscode())))
				&& ((this.getProcessno() == castOther.getProcessno()) || (this
						.getProcessno() != null
						&& castOther.getProcessno() != null && this
						.getProcessno().equals(castOther.getProcessno())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIntranscode() == null ? 0 : this.getIntranscode()
						.hashCode());
		result = 37 * result
				+ (getProcessno() == null ? 0 : this.getProcessno().hashCode());
		return result;
	}

}