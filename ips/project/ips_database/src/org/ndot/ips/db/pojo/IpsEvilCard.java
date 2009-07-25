package org.ndot.ips.db.pojo;

/**
 * IpsEvilCard entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsEvilCard implements java.io.Serializable {

	// Fields

	private String cardno;
	private String cardflag;
	private String setreason;

	// Constructors

	/** default constructor */
	public IpsEvilCard() {
	}

	/** full constructor */
	public IpsEvilCard(String cardno, String cardflag, String setreason) {
		this.cardno = cardno;
		this.cardflag = cardflag;
		this.setreason = setreason;
	}

	// Property accessors

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardflag() {
		return this.cardflag;
	}

	public void setCardflag(String cardflag) {
		this.cardflag = cardflag;
	}

	public String getSetreason() {
		return this.setreason;
	}

	public void setSetreason(String setreason) {
		this.setreason = setreason;
	}

}