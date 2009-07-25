package org.ndot.ips.db.pojo;

/**
 * IpsDevInf entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpsDevInf implements java.io.Serializable {

	// Fields

	private String devid;
	private String devname;
	private String instcode;
	private String uunioncode;
	private String devtype;
	private String madevendorid;
	private String madedate;
	private String supplyvendorid;
	private String svcvendorid;
	private String devserialno;
	private String firstsvcdate;
	private String lastsvcdate;
	private String installloc;
	private String protocol;
	private String ipaddr;
	private String port;
	private String brandid;
	private String model;
	private String installtype;
	private String insidebankflg;
	private String devowner;
	private String svc24hflg;
	private String svcstarttime;
	private String svcendtime;
	private String os;
	private String teller;
	private String usestat;
	private String atmprogver;
	private String installloctype;
	private String selfbankflg;
	private String remoctrltype;
	private String devupgradeflg;
	private String fileresolution;

	// Constructors

	/** default constructor */
	public IpsDevInf() {
	}

	/** minimal constructor */
	public IpsDevInf(String devid, String instcode, String uunioncode,
			String ipaddr, String port, String os, String teller, String usestat) {
		this.devid = devid;
		this.instcode = instcode;
		this.uunioncode = uunioncode;
		this.ipaddr = ipaddr;
		this.port = port;
		this.os = os;
		this.teller = teller;
		this.usestat = usestat;
	}

	/** full constructor */
	public IpsDevInf(String devid, String devname, String instcode,
			String uunioncode, String devtype, String madevendorid,
			String madedate, String supplyvendorid, String svcvendorid,
			String devserialno, String firstsvcdate, String lastsvcdate,
			String installloc, String protocol, String ipaddr, String port,
			String brandid, String model, String installtype,
			String insidebankflg, String devowner, String svc24hflg,
			String svcstarttime, String svcendtime, String os, String teller,
			String usestat, String atmprogver, String installloctype,
			String selfbankflg, String remoctrltype, String devupgradeflg,
			String fileresolution) {
		this.devid = devid;
		this.devname = devname;
		this.instcode = instcode;
		this.uunioncode = uunioncode;
		this.devtype = devtype;
		this.madevendorid = madevendorid;
		this.madedate = madedate;
		this.supplyvendorid = supplyvendorid;
		this.svcvendorid = svcvendorid;
		this.devserialno = devserialno;
		this.firstsvcdate = firstsvcdate;
		this.lastsvcdate = lastsvcdate;
		this.installloc = installloc;
		this.protocol = protocol;
		this.ipaddr = ipaddr;
		this.port = port;
		this.brandid = brandid;
		this.model = model;
		this.installtype = installtype;
		this.insidebankflg = insidebankflg;
		this.devowner = devowner;
		this.svc24hflg = svc24hflg;
		this.svcstarttime = svcstarttime;
		this.svcendtime = svcendtime;
		this.os = os;
		this.teller = teller;
		this.usestat = usestat;
		this.atmprogver = atmprogver;
		this.installloctype = installloctype;
		this.selfbankflg = selfbankflg;
		this.remoctrltype = remoctrltype;
		this.devupgradeflg = devupgradeflg;
		this.fileresolution = fileresolution;
	}

	// Property accessors

	public String getDevid() {
		return this.devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}

	public String getDevname() {
		return this.devname;
	}

	public void setDevname(String devname) {
		this.devname = devname;
	}

	public String getInstcode() {
		return this.instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}

	public String getUunioncode() {
		return this.uunioncode;
	}

	public void setUunioncode(String uunioncode) {
		this.uunioncode = uunioncode;
	}

	public String getDevtype() {
		return this.devtype;
	}

	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}

	public String getMadevendorid() {
		return this.madevendorid;
	}

	public void setMadevendorid(String madevendorid) {
		this.madevendorid = madevendorid;
	}

	public String getMadedate() {
		return this.madedate;
	}

	public void setMadedate(String madedate) {
		this.madedate = madedate;
	}

	public String getSupplyvendorid() {
		return this.supplyvendorid;
	}

	public void setSupplyvendorid(String supplyvendorid) {
		this.supplyvendorid = supplyvendorid;
	}

	public String getSvcvendorid() {
		return this.svcvendorid;
	}

	public void setSvcvendorid(String svcvendorid) {
		this.svcvendorid = svcvendorid;
	}

	public String getDevserialno() {
		return this.devserialno;
	}

	public void setDevserialno(String devserialno) {
		this.devserialno = devserialno;
	}

	public String getFirstsvcdate() {
		return this.firstsvcdate;
	}

	public void setFirstsvcdate(String firstsvcdate) {
		this.firstsvcdate = firstsvcdate;
	}

	public String getLastsvcdate() {
		return this.lastsvcdate;
	}

	public void setLastsvcdate(String lastsvcdate) {
		this.lastsvcdate = lastsvcdate;
	}

	public String getInstallloc() {
		return this.installloc;
	}

	public void setInstallloc(String installloc) {
		this.installloc = installloc;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getBrandid() {
		return this.brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getInstalltype() {
		return this.installtype;
	}

	public void setInstalltype(String installtype) {
		this.installtype = installtype;
	}

	public String getInsidebankflg() {
		return this.insidebankflg;
	}

	public void setInsidebankflg(String insidebankflg) {
		this.insidebankflg = insidebankflg;
	}

	public String getDevowner() {
		return this.devowner;
	}

	public void setDevowner(String devowner) {
		this.devowner = devowner;
	}

	public String getSvc24hflg() {
		return this.svc24hflg;
	}

	public void setSvc24hflg(String svc24hflg) {
		this.svc24hflg = svc24hflg;
	}

	public String getSvcstarttime() {
		return this.svcstarttime;
	}

	public void setSvcstarttime(String svcstarttime) {
		this.svcstarttime = svcstarttime;
	}

	public String getSvcendtime() {
		return this.svcendtime;
	}

	public void setSvcendtime(String svcendtime) {
		this.svcendtime = svcendtime;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getTeller() {
		return this.teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	public String getUsestat() {
		return this.usestat;
	}

	public void setUsestat(String usestat) {
		this.usestat = usestat;
	}

	public String getAtmprogver() {
		return this.atmprogver;
	}

	public void setAtmprogver(String atmprogver) {
		this.atmprogver = atmprogver;
	}

	public String getInstallloctype() {
		return this.installloctype;
	}

	public void setInstallloctype(String installloctype) {
		this.installloctype = installloctype;
	}

	public String getSelfbankflg() {
		return this.selfbankflg;
	}

	public void setSelfbankflg(String selfbankflg) {
		this.selfbankflg = selfbankflg;
	}

	public String getRemoctrltype() {
		return this.remoctrltype;
	}

	public void setRemoctrltype(String remoctrltype) {
		this.remoctrltype = remoctrltype;
	}

	public String getDevupgradeflg() {
		return this.devupgradeflg;
	}

	public void setDevupgradeflg(String devupgradeflg) {
		this.devupgradeflg = devupgradeflg;
	}

	public String getFileresolution() {
		return this.fileresolution;
	}

	public void setFileresolution(String fileresolution) {
		this.fileresolution = fileresolution;
	}

}