package org.ndot.ips.db.pojo;

/**
 * IpsInTransflow entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsInTransflow implements java.io.Serializable {

	// Fields

	private IpsInTransflowId id;
	private String tranname;
	private String transtat;
	private String transcategory;
	private String starttag;
	private String destnodeid;
	private String flowprocessno;
	private String chkmacflag;
	private String chkpinflag;
	private String reverseflag;
	private String resendflag;
	private String launchtranscode;
	private String safflag;
	private String queryorgjnlflag;
	private String overtime;

	// Constructors

	/** default constructor */
	public IpsInTransflow() {
	}

	/** minimal constructor */
	public IpsInTransflow(IpsInTransflowId id, String transtat) {
		this.id = id;
		this.transtat = transtat;
	}

	/** full constructor */
	public IpsInTransflow(IpsInTransflowId id, String tranname,
			String transtat, String transcategory, String starttag,
			String destnodeid, String flowprocessno, String chkmacflag,
			String chkpinflag, String reverseflag, String resendflag,
			String launchtranscode, String safflag, String queryorgjnlflag,
			String overtime) {
		this.id = id;
		this.tranname = tranname;
		this.transtat = transtat;
		this.transcategory = transcategory;
		this.starttag = starttag;
		this.destnodeid = destnodeid;
		this.flowprocessno = flowprocessno;
		this.chkmacflag = chkmacflag;
		this.chkpinflag = chkpinflag;
		this.reverseflag = reverseflag;
		this.resendflag = resendflag;
		this.launchtranscode = launchtranscode;
		this.safflag = safflag;
		this.queryorgjnlflag = queryorgjnlflag;
		this.overtime = overtime;
	}

	// Property accessors

	public IpsInTransflowId getId() {
		return this.id;
	}

	public void setId(IpsInTransflowId id) {
		this.id = id;
	}

	public String getTranname() {
		return this.tranname;
	}

	public void setTranname(String tranname) {
		this.tranname = tranname;
	}

	public String getTranstat() {
		return this.transtat;
	}

	public void setTranstat(String transtat) {
		this.transtat = transtat;
	}

	public String getTranscategory() {
		return this.transcategory;
	}

	public void setTranscategory(String transcategory) {
		this.transcategory = transcategory;
	}

	public String getStarttag() {
		return this.starttag;
	}

	public void setStarttag(String starttag) {
		this.starttag = starttag;
	}

	public String getDestnodeid() {
		return this.destnodeid;
	}

	public void setDestnodeid(String destnodeid) {
		this.destnodeid = destnodeid;
	}

	public String getFlowprocessno() {
		return this.flowprocessno;
	}

	public void setFlowprocessno(String flowprocessno) {
		this.flowprocessno = flowprocessno;
	}

	public String getChkmacflag() {
		return this.chkmacflag;
	}

	public void setChkmacflag(String chkmacflag) {
		this.chkmacflag = chkmacflag;
	}

	public String getChkpinflag() {
		return this.chkpinflag;
	}

	public void setChkpinflag(String chkpinflag) {
		this.chkpinflag = chkpinflag;
	}

	public String getReverseflag() {
		return this.reverseflag;
	}

	public void setReverseflag(String reverseflag) {
		this.reverseflag = reverseflag;
	}

	public String getResendflag() {
		return this.resendflag;
	}

	public void setResendflag(String resendflag) {
		this.resendflag = resendflag;
	}

	public String getLaunchtranscode() {
		return this.launchtranscode;
	}

	public void setLaunchtranscode(String launchtranscode) {
		this.launchtranscode = launchtranscode;
	}

	public String getSafflag() {
		return this.safflag;
	}

	public void setSafflag(String safflag) {
		this.safflag = safflag;
	}

	public String getQueryorgjnlflag() {
		return this.queryorgjnlflag;
	}

	public void setQueryorgjnlflag(String queryorgjnlflag) {
		this.queryorgjnlflag = queryorgjnlflag;
	}

	public String getOvertime() {
		return this.overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

}