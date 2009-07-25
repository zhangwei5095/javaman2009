package org.ndot.ips.db.pojo;

/**
 * IpsSysParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsSysParam implements java.io.Serializable {

	// Fields

	private String paramcode;
	private String paramname;
	private String paramvalue;
	private String parammodflg;
	private String paramdesc;

	// Constructors

	/** default constructor */
	public IpsSysParam() {
	}

	/** minimal constructor */
	public IpsSysParam(String paramcode, String paramvalue, String parammodflg) {
		this.paramcode = paramcode;
		this.paramvalue = paramvalue;
		this.parammodflg = parammodflg;
	}

	/** full constructor */
	public IpsSysParam(String paramcode, String paramname, String paramvalue,
			String parammodflg, String paramdesc) {
		this.paramcode = paramcode;
		this.paramname = paramname;
		this.paramvalue = paramvalue;
		this.parammodflg = parammodflg;
		this.paramdesc = paramdesc;
	}

	// Property accessors

	public String getParamcode() {
		return this.paramcode;
	}

	public void setParamcode(String paramcode) {
		this.paramcode = paramcode;
	}

	public String getParamname() {
		return this.paramname;
	}

	public void setParamname(String paramname) {
		this.paramname = paramname;
	}

	public String getParamvalue() {
		return this.paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	public String getParammodflg() {
		return this.parammodflg;
	}

	public void setParammodflg(String parammodflg) {
		this.parammodflg = parammodflg;
	}

	public String getParamdesc() {
		return this.paramdesc;
	}

	public void setParamdesc(String paramdesc) {
		this.paramdesc = paramdesc;
	}

}