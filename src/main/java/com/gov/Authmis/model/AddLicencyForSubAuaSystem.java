package com.gov.Authmis.model;

import java.sql.Date;

import javax.persistence.Column;


public class AddLicencyForSubAuaSystem {

	private Long srno;

	private String subAuaCode;

	private String subAuaName;

	private String kuaLk;

	private String kuaCode;

	private String subAuaLk;

	private Date expiryDate;
	
	@Column(name = "STATUS")
	private int status;

	private String kukLkCode;

	private String pocnameM;

	private String pocmobile1M;

	private String pocmobile2M;

	private String pocnameT;

	private String pocmoble1T;

	private String pocmobileT;

	private String pocemailM;

	private String pocemailT;

	private String pocaddM;

	private String pocaddT;

	private String purpose;

	private String pocmobile1T;

	public AddLicencyForSubAuaSystem(Long srno, String subAuaCode, String subAuaName, String kuaLk, String kuaCode,
			String subAuaLk, Date expiryDate, int status, String kukLkCode, String pocnameM, String pocmobile1m,
			String pocmobile2m, String pocnameT, String pocmoble1t, String pocmobileT, String pocemailM,
			String pocemailT, String pocaddM, String pocaddT, String purpose, String pocmobile1t) {
		super();
		this.srno = srno;
		this.subAuaCode = subAuaCode;
		this.subAuaName = subAuaName;
		this.kuaLk = kuaLk;
		this.kuaCode = kuaCode;
		this.subAuaLk = subAuaLk;
		this.expiryDate = expiryDate;
		this.status = status;
		this.kukLkCode = kukLkCode;
		this.pocnameM = pocnameM;
		pocmobile1M = pocmobile1m;
		pocmobile2M = pocmobile2m;
		this.pocnameT = pocnameT;
		pocmoble1T = pocmoble1t;
		this.pocmobileT = pocmobileT;
		this.pocemailM = pocemailM;
		this.pocemailT = pocemailT;
		this.pocaddM = pocaddM;
		this.pocaddT = pocaddT;
		this.purpose = purpose;
		pocmobile1T = pocmobile1t;
	}

	public AddLicencyForSubAuaSystem() {
		super();
	}

	public Long getSrno() {
		return srno;
	}

	public String getSubAuaCode() {
		return subAuaCode;
	}

	public String getSubAuaName() {
		return subAuaName;
	}

	public String getKuaLk() {
		return kuaLk;
	}

	public String getKuaCode() {
		return kuaCode;
	}

	public String getSubAuaLk() {
		return subAuaLk;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public int getStatus() {
		return status;
	}

	public String getKukLkCode() {
		return kukLkCode;
	}

	public String getPocnameM() {
		return pocnameM;
	}

	public String getPocmobile1M() {
		return pocmobile1M;
	}

	public String getPocmobile2M() {
		return pocmobile2M;
	}

	public String getPocnameT() {
		return pocnameT;
	}

	public String getPocmoble1T() {
		return pocmoble1T;
	}

	public String getPocmobileT() {
		return pocmobileT;
	}

	public String getPocemailM() {
		return pocemailM;
	}

	public String getPocemailT() {
		return pocemailT;
	}

	public String getPocaddM() {
		return pocaddM;
	}

	public String getPocaddT() {
		return pocaddT;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getPocmobile1T() {
		return pocmobile1T;
	}

	public void setSrno(Long srno) {
		this.srno = srno;
	}

	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}

	public void setSubAuaName(String subAuaName) {
		this.subAuaName = subAuaName;
	}

	public void setKuaLk(String kuaLk) {
		this.kuaLk = kuaLk;
	}

	public void setKuaCode(String kuaCode) {
		this.kuaCode = kuaCode;
	}

	public void setSubAuaLk(String subAuaLk) {
		this.subAuaLk = subAuaLk;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setKukLkCode(String kukLkCode) {
		this.kukLkCode = kukLkCode;
	}

	public void setPocnameM(String pocnameM) {
		this.pocnameM = pocnameM;
	}

	public void setPocmobile1M(String pocmobile1m) {
		pocmobile1M = pocmobile1m;
	}

	public void setPocmobile2M(String pocmobile2m) {
		pocmobile2M = pocmobile2m;
	}

	public void setPocnameT(String pocnameT) {
		this.pocnameT = pocnameT;
	}

	public void setPocmoble1T(String pocmoble1t) {
		pocmoble1T = pocmoble1t;
	}

	public void setPocmobileT(String pocmobileT) {
		this.pocmobileT = pocmobileT;
	}

	public void setPocemailM(String pocemailM) {
		this.pocemailM = pocemailM;
	}

	public void setPocemailT(String pocemailT) {
		this.pocemailT = pocemailT;
	}

	public void setPocaddM(String pocaddM) {
		this.pocaddM = pocaddM;
	}

	public void setPocaddT(String pocaddT) {
		this.pocaddT = pocaddT;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setPocmobile1T(String pocmobile1t) {
		pocmobile1T = pocmobile1t;
	}

	@Override
	public String toString() {
		return "AddLicencyForSubAuaSystem [srno=" + srno + ", subAuaCode=" + subAuaCode + ", subAuaName=" + subAuaName
				+ ", kuaLk=" + kuaLk + ", kuaCode=" + kuaCode + ", subAuaLk=" + subAuaLk + ", expiryDate=" + expiryDate
				+ ", status=" + status + ", kukLkCode=" + kukLkCode + ", pocnameM=" + pocnameM + ", pocmobile1M="
				+ pocmobile1M + ", pocmobile2M=" + pocmobile2M + ", pocnameT=" + pocnameT + ", pocmoble1T=" + pocmoble1T
				+ ", pocmobileT=" + pocmobileT + ", pocemailM=" + pocemailM + ", pocemailT=" + pocemailT + ", pocaddM="
				+ pocaddM + ", pocaddT=" + pocaddT + ", purpose=" + purpose + ", pocmobile1T=" + pocmobile1T + "]";
	}

	

	
}
