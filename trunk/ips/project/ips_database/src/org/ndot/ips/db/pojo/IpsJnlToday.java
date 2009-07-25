package org.ndot.ips.db.pojo;

/**
 * IpsJnlToday entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsJnlToday implements java.io.Serializable {

	// Fields

	private String localjnlno;
	private String localdatetime;
	private String intranscode;
	private String transtype;
	private String errcode;
	private String chanlcode;
	private String chanlmsgcode;
	private String chanltranscode;
	private String chanljnlno;
	private String chanlrspcode;
	private String corecode;
	private String coremsgcode;
	private String coretranscode;
	private String coreclrdate;
	private String corejnlno;
	private String corerspcode;
	private String hostcode;
	private String hostmsgcode;
	private String hosttranscode;
	private String hostclrdate;
	private String hostjnlno;
	private String hostrspcode;
	private String devid;
	private String cardno;
	private String tranamt;
	private String cardexpdate;
	private String devtranstime;
	private String devtransdate;
	private String mctcode;
	private String mcttype;
	private String trancurrcode;
	private String inputtype;
	private String condcode;
	private String track2;
	private String track3;
	private String addbal;
	private String openinstcode;
	private String transinstcode;
	private String accfrom;
	private String accto;
	private String fee1;
	private String fee2;
	private String fee3;
	private String hostchkflag;
	private String termchkflag;
	private String msgreasoncode;
	private String refnumber;
	private String tellerid;
	private String batchno;
	private String origdata;
	private String authrspcode;
	private String chkflag;
	private String coremodflag;
	private String hostmodflag;
	private String transtat;
	private String localdateyear;
	private String localdatemon;
	private String localdateday;

	// Constructors

	/** default constructor */
	public IpsJnlToday() {
	}

	/** minimal constructor */
	public IpsJnlToday(String localjnlno) {
		this.localjnlno = localjnlno;
	}

	/** full constructor */
	public IpsJnlToday(String localjnlno, String localdatetime,
			String intranscode, String transtype, String errcode,
			String chanlcode, String chanlmsgcode, String chanltranscode,
			String chanljnlno, String chanlrspcode, String corecode,
			String coremsgcode, String coretranscode, String coreclrdate,
			String corejnlno, String corerspcode, String hostcode,
			String hostmsgcode, String hosttranscode, String hostclrdate,
			String hostjnlno, String hostrspcode, String devid, String cardno,
			String tranamt, String cardexpdate, String devtranstime,
			String devtransdate, String mctcode, String mcttype,
			String trancurrcode, String inputtype, String condcode,
			String track2, String track3, String addbal, String openinstcode,
			String transinstcode, String accfrom, String accto, String fee1,
			String fee2, String fee3, String hostchkflag, String termchkflag,
			String msgreasoncode, String refnumber, String tellerid,
			String batchno, String origdata, String authrspcode,
			String chkflag, String coremodflag, String hostmodflag,
			String transtat, String localdateyear, String localdatemon,
			String localdateday) {
		this.localjnlno = localjnlno;
		this.localdatetime = localdatetime;
		this.intranscode = intranscode;
		this.transtype = transtype;
		this.errcode = errcode;
		this.chanlcode = chanlcode;
		this.chanlmsgcode = chanlmsgcode;
		this.chanltranscode = chanltranscode;
		this.chanljnlno = chanljnlno;
		this.chanlrspcode = chanlrspcode;
		this.corecode = corecode;
		this.coremsgcode = coremsgcode;
		this.coretranscode = coretranscode;
		this.coreclrdate = coreclrdate;
		this.corejnlno = corejnlno;
		this.corerspcode = corerspcode;
		this.hostcode = hostcode;
		this.hostmsgcode = hostmsgcode;
		this.hosttranscode = hosttranscode;
		this.hostclrdate = hostclrdate;
		this.hostjnlno = hostjnlno;
		this.hostrspcode = hostrspcode;
		this.devid = devid;
		this.cardno = cardno;
		this.tranamt = tranamt;
		this.cardexpdate = cardexpdate;
		this.devtranstime = devtranstime;
		this.devtransdate = devtransdate;
		this.mctcode = mctcode;
		this.mcttype = mcttype;
		this.trancurrcode = trancurrcode;
		this.inputtype = inputtype;
		this.condcode = condcode;
		this.track2 = track2;
		this.track3 = track3;
		this.addbal = addbal;
		this.openinstcode = openinstcode;
		this.transinstcode = transinstcode;
		this.accfrom = accfrom;
		this.accto = accto;
		this.fee1 = fee1;
		this.fee2 = fee2;
		this.fee3 = fee3;
		this.hostchkflag = hostchkflag;
		this.termchkflag = termchkflag;
		this.msgreasoncode = msgreasoncode;
		this.refnumber = refnumber;
		this.tellerid = tellerid;
		this.batchno = batchno;
		this.origdata = origdata;
		this.authrspcode = authrspcode;
		this.chkflag = chkflag;
		this.coremodflag = coremodflag;
		this.hostmodflag = hostmodflag;
		this.transtat = transtat;
		this.localdateyear = localdateyear;
		this.localdatemon = localdatemon;
		this.localdateday = localdateday;
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

	public String getIntranscode() {
		return this.intranscode;
	}

	public void setIntranscode(String intranscode) {
		this.intranscode = intranscode;
	}

	public String getTranstype() {
		return this.transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getErrcode() {
		return this.errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getChanlcode() {
		return this.chanlcode;
	}

	public void setChanlcode(String chanlcode) {
		this.chanlcode = chanlcode;
	}

	public String getChanlmsgcode() {
		return this.chanlmsgcode;
	}

	public void setChanlmsgcode(String chanlmsgcode) {
		this.chanlmsgcode = chanlmsgcode;
	}

	public String getChanltranscode() {
		return this.chanltranscode;
	}

	public void setChanltranscode(String chanltranscode) {
		this.chanltranscode = chanltranscode;
	}

	public String getChanljnlno() {
		return this.chanljnlno;
	}

	public void setChanljnlno(String chanljnlno) {
		this.chanljnlno = chanljnlno;
	}

	public String getChanlrspcode() {
		return this.chanlrspcode;
	}

	public void setChanlrspcode(String chanlrspcode) {
		this.chanlrspcode = chanlrspcode;
	}

	public String getCorecode() {
		return this.corecode;
	}

	public void setCorecode(String corecode) {
		this.corecode = corecode;
	}

	public String getCoremsgcode() {
		return this.coremsgcode;
	}

	public void setCoremsgcode(String coremsgcode) {
		this.coremsgcode = coremsgcode;
	}

	public String getCoretranscode() {
		return this.coretranscode;
	}

	public void setCoretranscode(String coretranscode) {
		this.coretranscode = coretranscode;
	}

	public String getCoreclrdate() {
		return this.coreclrdate;
	}

	public void setCoreclrdate(String coreclrdate) {
		this.coreclrdate = coreclrdate;
	}

	public String getCorejnlno() {
		return this.corejnlno;
	}

	public void setCorejnlno(String corejnlno) {
		this.corejnlno = corejnlno;
	}

	public String getCorerspcode() {
		return this.corerspcode;
	}

	public void setCorerspcode(String corerspcode) {
		this.corerspcode = corerspcode;
	}

	public String getHostcode() {
		return this.hostcode;
	}

	public void setHostcode(String hostcode) {
		this.hostcode = hostcode;
	}

	public String getHostmsgcode() {
		return this.hostmsgcode;
	}

	public void setHostmsgcode(String hostmsgcode) {
		this.hostmsgcode = hostmsgcode;
	}

	public String getHosttranscode() {
		return this.hosttranscode;
	}

	public void setHosttranscode(String hosttranscode) {
		this.hosttranscode = hosttranscode;
	}

	public String getHostclrdate() {
		return this.hostclrdate;
	}

	public void setHostclrdate(String hostclrdate) {
		this.hostclrdate = hostclrdate;
	}

	public String getHostjnlno() {
		return this.hostjnlno;
	}

	public void setHostjnlno(String hostjnlno) {
		this.hostjnlno = hostjnlno;
	}

	public String getHostrspcode() {
		return this.hostrspcode;
	}

	public void setHostrspcode(String hostrspcode) {
		this.hostrspcode = hostrspcode;
	}

	public String getDevid() {
		return this.devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getTranamt() {
		return this.tranamt;
	}

	public void setTranamt(String tranamt) {
		this.tranamt = tranamt;
	}

	public String getCardexpdate() {
		return this.cardexpdate;
	}

	public void setCardexpdate(String cardexpdate) {
		this.cardexpdate = cardexpdate;
	}

	public String getDevtranstime() {
		return this.devtranstime;
	}

	public void setDevtranstime(String devtranstime) {
		this.devtranstime = devtranstime;
	}

	public String getDevtransdate() {
		return this.devtransdate;
	}

	public void setDevtransdate(String devtransdate) {
		this.devtransdate = devtransdate;
	}

	public String getMctcode() {
		return this.mctcode;
	}

	public void setMctcode(String mctcode) {
		this.mctcode = mctcode;
	}

	public String getMcttype() {
		return this.mcttype;
	}

	public void setMcttype(String mcttype) {
		this.mcttype = mcttype;
	}

	public String getTrancurrcode() {
		return this.trancurrcode;
	}

	public void setTrancurrcode(String trancurrcode) {
		this.trancurrcode = trancurrcode;
	}

	public String getInputtype() {
		return this.inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public String getCondcode() {
		return this.condcode;
	}

	public void setCondcode(String condcode) {
		this.condcode = condcode;
	}

	public String getTrack2() {
		return this.track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getTrack3() {
		return this.track3;
	}

	public void setTrack3(String track3) {
		this.track3 = track3;
	}

	public String getAddbal() {
		return this.addbal;
	}

	public void setAddbal(String addbal) {
		this.addbal = addbal;
	}

	public String getOpeninstcode() {
		return this.openinstcode;
	}

	public void setOpeninstcode(String openinstcode) {
		this.openinstcode = openinstcode;
	}

	public String getTransinstcode() {
		return this.transinstcode;
	}

	public void setTransinstcode(String transinstcode) {
		this.transinstcode = transinstcode;
	}

	public String getAccfrom() {
		return this.accfrom;
	}

	public void setAccfrom(String accfrom) {
		this.accfrom = accfrom;
	}

	public String getAccto() {
		return this.accto;
	}

	public void setAccto(String accto) {
		this.accto = accto;
	}

	public String getFee1() {
		return this.fee1;
	}

	public void setFee1(String fee1) {
		this.fee1 = fee1;
	}

	public String getFee2() {
		return this.fee2;
	}

	public void setFee2(String fee2) {
		this.fee2 = fee2;
	}

	public String getFee3() {
		return this.fee3;
	}

	public void setFee3(String fee3) {
		this.fee3 = fee3;
	}

	public String getHostchkflag() {
		return this.hostchkflag;
	}

	public void setHostchkflag(String hostchkflag) {
		this.hostchkflag = hostchkflag;
	}

	public String getTermchkflag() {
		return this.termchkflag;
	}

	public void setTermchkflag(String termchkflag) {
		this.termchkflag = termchkflag;
	}

	public String getMsgreasoncode() {
		return this.msgreasoncode;
	}

	public void setMsgreasoncode(String msgreasoncode) {
		this.msgreasoncode = msgreasoncode;
	}

	public String getRefnumber() {
		return this.refnumber;
	}

	public void setRefnumber(String refnumber) {
		this.refnumber = refnumber;
	}

	public String getTellerid() {
		return this.tellerid;
	}

	public void setTellerid(String tellerid) {
		this.tellerid = tellerid;
	}

	public String getBatchno() {
		return this.batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getOrigdata() {
		return this.origdata;
	}

	public void setOrigdata(String origdata) {
		this.origdata = origdata;
	}

	public String getAuthrspcode() {
		return this.authrspcode;
	}

	public void setAuthrspcode(String authrspcode) {
		this.authrspcode = authrspcode;
	}

	public String getChkflag() {
		return this.chkflag;
	}

	public void setChkflag(String chkflag) {
		this.chkflag = chkflag;
	}

	public String getCoremodflag() {
		return this.coremodflag;
	}

	public void setCoremodflag(String coremodflag) {
		this.coremodflag = coremodflag;
	}

	public String getHostmodflag() {
		return this.hostmodflag;
	}

	public void setHostmodflag(String hostmodflag) {
		this.hostmodflag = hostmodflag;
	}

	public String getTranstat() {
		return this.transtat;
	}

	public void setTranstat(String transtat) {
		this.transtat = transtat;
	}

	public String getLocaldateyear() {
		return this.localdateyear;
	}

	public void setLocaldateyear(String localdateyear) {
		this.localdateyear = localdateyear;
	}

	public String getLocaldatemon() {
		return this.localdatemon;
	}

	public void setLocaldatemon(String localdatemon) {
		this.localdatemon = localdatemon;
	}

	public String getLocaldateday() {
		return this.localdateday;
	}

	public void setLocaldateday(String localdateday) {
		this.localdateday = localdateday;
	}

}