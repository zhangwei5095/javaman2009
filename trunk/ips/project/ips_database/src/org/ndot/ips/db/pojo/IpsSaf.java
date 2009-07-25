package org.ndot.ips.db.pojo;

/**
 * IpsSaf entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsSaf implements java.io.Serializable {

	// Fields

	private IpsSafId id;
	private String intranscode;
	private String endtime;
	private String sourceid;
	private String destid;
	private String state;
	private String rspcode;
	private String sendtimes;
	private String overtime;
	private String clrdate;
	private String sendpac;

	// Constructors

	/** default constructor */
	public IpsSaf() {
	}

	/** minimal constructor */
	public IpsSaf(IpsSafId id, String intranscode, String sourceid,
			String destid, String state, String sendtimes, String overtime,
			String clrdate, String sendpac) {
		this.id = id;
		this.intranscode = intranscode;
		this.sourceid = sourceid;
		this.destid = destid;
		this.state = state;
		this.sendtimes = sendtimes;
		this.overtime = overtime;
		this.clrdate = clrdate;
		this.sendpac = sendpac;
	}

	/** full constructor */
	public IpsSaf(IpsSafId id, String intranscode, String endtime,
			String sourceid, String destid, String state, String rspcode,
			String sendtimes, String overtime, String clrdate, String sendpac) {
		this.id = id;
		this.intranscode = intranscode;
		this.endtime = endtime;
		this.sourceid = sourceid;
		this.destid = destid;
		this.state = state;
		this.rspcode = rspcode;
		this.sendtimes = sendtimes;
		this.overtime = overtime;
		this.clrdate = clrdate;
		this.sendpac = sendpac;
	}

	// Property accessors

	public IpsSafId getId() {
		return this.id;
	}

	public void setId(IpsSafId id) {
		this.id = id;
	}

	public String getIntranscode() {
		return this.intranscode;
	}

	public void setIntranscode(String intranscode) {
		this.intranscode = intranscode;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getSourceid() {
		return this.sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getDestid() {
		return this.destid;
	}

	public void setDestid(String destid) {
		this.destid = destid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRspcode() {
		return this.rspcode;
	}

	public void setRspcode(String rspcode) {
		this.rspcode = rspcode;
	}

	public String getSendtimes() {
		return this.sendtimes;
	}

	public void setSendtimes(String sendtimes) {
		this.sendtimes = sendtimes;
	}

	public String getOvertime() {
		return this.overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getClrdate() {
		return this.clrdate;
	}

	public void setClrdate(String clrdate) {
		this.clrdate = clrdate;
	}

	public String getSendpac() {
		return this.sendpac;
	}

	public void setSendpac(String sendpac) {
		this.sendpac = sendpac;
	}

}