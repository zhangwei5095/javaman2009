package org.ndot.ips.db.pojo;

/**
 * IpsTranscodeMap entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsTranscodeMap implements java.io.Serializable {

	// Fields

	private String intranscode;
	private String transtype;
	private String chanlcode;
	private String chanlmsgcode;
	private String chanltranscode;
	private String corecode;
	private String coremsgcode;
	private String coretranscode;
	private String hostcode;
	private String hostmsgcode;
	private String hosttranscode;
	private String transabstinf;
	private String transdesc;

	// Constructors

	/** default constructor */
	public IpsTranscodeMap() {
	}

	/** minimal constructor */
	public IpsTranscodeMap(String intranscode, String chanlcode,
			String chanlmsgcode, String chanltranscode, String corecode,
			String coremsgcode, String coretranscode, String hostcode,
			String hostmsgcode, String hosttranscode) {
		this.intranscode = intranscode;
		this.chanlcode = chanlcode;
		this.chanlmsgcode = chanlmsgcode;
		this.chanltranscode = chanltranscode;
		this.corecode = corecode;
		this.coremsgcode = coremsgcode;
		this.coretranscode = coretranscode;
		this.hostcode = hostcode;
		this.hostmsgcode = hostmsgcode;
		this.hosttranscode = hosttranscode;
	}

	/** full constructor */
	public IpsTranscodeMap(String intranscode, String transtype,
			String chanlcode, String chanlmsgcode, String chanltranscode,
			String corecode, String coremsgcode, String coretranscode,
			String hostcode, String hostmsgcode, String hosttranscode,
			String transabstinf, String transdesc) {
		this.intranscode = intranscode;
		this.transtype = transtype;
		this.chanlcode = chanlcode;
		this.chanlmsgcode = chanlmsgcode;
		this.chanltranscode = chanltranscode;
		this.corecode = corecode;
		this.coremsgcode = coremsgcode;
		this.coretranscode = coretranscode;
		this.hostcode = hostcode;
		this.hostmsgcode = hostmsgcode;
		this.hosttranscode = hosttranscode;
		this.transabstinf = transabstinf;
		this.transdesc = transdesc;
	}

	// Property accessors

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

	public String getTransabstinf() {
		return this.transabstinf;
	}

	public void setTransabstinf(String transabstinf) {
		this.transabstinf = transabstinf;
	}

	public String getTransdesc() {
		return this.transdesc;
	}

	public void setTransdesc(String transdesc) {
		this.transdesc = transdesc;
	}

}