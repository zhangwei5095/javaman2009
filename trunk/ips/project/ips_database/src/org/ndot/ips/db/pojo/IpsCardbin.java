package org.ndot.ips.db.pojo;

/**
 * IpsCardbin entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsCardbin implements java.io.Serializable {

	// Fields

	private String seqno;
	private String bankid;
	private String bankname;
	private String cardid;
	private String offset1;
	private String length1;
	private String string1;
	private String offset2;
	private String length2;
	private String string2;
	private String offset3;
	private String length3;
	private String string3;
	private String panoffset;
	private String panlen;
	private String cardflag;
	private String tranlist;

	// Constructors

	/** default constructor */
	public IpsCardbin() {
	}

	/** minimal constructor */
	public IpsCardbin(String seqno, String bankid, String cardid,
			String offset1, String length1, String string1, String panoffset,
			String panlen, String cardflag, String tranlist) {
		this.seqno = seqno;
		this.bankid = bankid;
		this.cardid = cardid;
		this.offset1 = offset1;
		this.length1 = length1;
		this.string1 = string1;
		this.panoffset = panoffset;
		this.panlen = panlen;
		this.cardflag = cardflag;
		this.tranlist = tranlist;
	}

	/** full constructor */
	public IpsCardbin(String seqno, String bankid, String bankname,
			String cardid, String offset1, String length1, String string1,
			String offset2, String length2, String string2, String offset3,
			String length3, String string3, String panoffset, String panlen,
			String cardflag, String tranlist) {
		this.seqno = seqno;
		this.bankid = bankid;
		this.bankname = bankname;
		this.cardid = cardid;
		this.offset1 = offset1;
		this.length1 = length1;
		this.string1 = string1;
		this.offset2 = offset2;
		this.length2 = length2;
		this.string2 = string2;
		this.offset3 = offset3;
		this.length3 = length3;
		this.string3 = string3;
		this.panoffset = panoffset;
		this.panlen = panlen;
		this.cardflag = cardflag;
		this.tranlist = tranlist;
	}

	// Property accessors

	public String getSeqno() {
		return this.seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getBankid() {
		return this.bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getOffset1() {
		return this.offset1;
	}

	public void setOffset1(String offset1) {
		this.offset1 = offset1;
	}

	public String getLength1() {
		return this.length1;
	}

	public void setLength1(String length1) {
		this.length1 = length1;
	}

	public String getString1() {
		return this.string1;
	}

	public void setString1(String string1) {
		this.string1 = string1;
	}

	public String getOffset2() {
		return this.offset2;
	}

	public void setOffset2(String offset2) {
		this.offset2 = offset2;
	}

	public String getLength2() {
		return this.length2;
	}

	public void setLength2(String length2) {
		this.length2 = length2;
	}

	public String getString2() {
		return this.string2;
	}

	public void setString2(String string2) {
		this.string2 = string2;
	}

	public String getOffset3() {
		return this.offset3;
	}

	public void setOffset3(String offset3) {
		this.offset3 = offset3;
	}

	public String getLength3() {
		return this.length3;
	}

	public void setLength3(String length3) {
		this.length3 = length3;
	}

	public String getString3() {
		return this.string3;
	}

	public void setString3(String string3) {
		this.string3 = string3;
	}

	public String getPanoffset() {
		return this.panoffset;
	}

	public void setPanoffset(String panoffset) {
		this.panoffset = panoffset;
	}

	public String getPanlen() {
		return this.panlen;
	}

	public void setPanlen(String panlen) {
		this.panlen = panlen;
	}

	public String getCardflag() {
		return this.cardflag;
	}

	public void setCardflag(String cardflag) {
		this.cardflag = cardflag;
	}

	public String getTranlist() {
		return this.tranlist;
	}

	public void setTranlist(String tranlist) {
		this.tranlist = tranlist;
	}

}