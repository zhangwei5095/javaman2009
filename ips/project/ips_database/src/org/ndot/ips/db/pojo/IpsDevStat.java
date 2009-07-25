package org.ndot.ips.db.pojo;

/**
 * IpsDevStat entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsDevStat implements java.io.Serializable {

	// Fields

	private String devid;
	private String signstat;
	private String signdatetime;
	private String upddatetime;
	private String devstat;
	private String deverrinf;
	private String devaccterrinf;

	// Constructors

	/** default constructor */
	public IpsDevStat() {
	}

	/** minimal constructor */
	public IpsDevStat(String devid) {
		this.devid = devid;
	}

	/** full constructor */
	public IpsDevStat(String devid, String signstat, String signdatetime,
			String upddatetime, String devstat, String deverrinf,
			String devaccterrinf) {
		this.devid = devid;
		this.signstat = signstat;
		this.signdatetime = signdatetime;
		this.upddatetime = upddatetime;
		this.devstat = devstat;
		this.deverrinf = deverrinf;
		this.devaccterrinf = devaccterrinf;
	}

	// Property accessors

	public String getDevid() {
		return this.devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}

	public String getSignstat() {
		return this.signstat;
	}

	public void setSignstat(String signstat) {
		this.signstat = signstat;
	}

	public String getSigndatetime() {
		return this.signdatetime;
	}

	public void setSigndatetime(String signdatetime) {
		this.signdatetime = signdatetime;
	}

	public String getUpddatetime() {
		return this.upddatetime;
	}

	public void setUpddatetime(String upddatetime) {
		this.upddatetime = upddatetime;
	}

	public String getDevstat() {
		return this.devstat;
	}

	public void setDevstat(String devstat) {
		this.devstat = devstat;
	}

	public String getDeverrinf() {
		return this.deverrinf;
	}

	public void setDeverrinf(String deverrinf) {
		this.deverrinf = deverrinf;
	}

	public String getDevaccterrinf() {
		return this.devaccterrinf;
	}

	public void setDevaccterrinf(String devaccterrinf) {
		this.devaccterrinf = devaccterrinf;
	}

}