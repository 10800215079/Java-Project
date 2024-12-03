package com.gov.Authmis.entity;



import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SUBAUA_EKYC_LK")
public  class AddLicencyForSubAuaSystem  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long srno;
	
	@Column(name = "SUB_AUA_CODE")
	private String subAuaCode;
	
	@Column(name = "SUB_AUA_NAME")
	private String subAuaName;
	
	@Column(name = "KUA_LK")
	private String kuaLk;
	
	@Column(name = "KUA_CODE")
	private String kuaCode;
	
	@Column(name = "SUB_AUA_LK")
	private String subAuaLk;
	
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "KUA_LK_CODE")
	private String kukLkCode;
	
	@Column(name = "POCNAME_M")
	private String pocnameM;
	
	@Column(name = "POCMOBILE1_M")
	private String pocmobile1M;
	
	@Column(name = "POCMOBILE2_M")
	private String pocmobile2M;
	
	@Column(name = "POCNAME_T")
	private String pocnameT;
	
	@Column(name = "POCMOBLE1_T")
	private String pocmoble1T;
	
	@Column(name = "POCMOBILE2_T")
	private String pocmobileT;
	
	@Column(name = "POCEMAIL_M")
	private String pocemailM;
	
	@Column(name = "POCEMAIL_T")
	private String pocemailT;
	
	@Column(name = "POCADD_M")
	private String pocaddM;
	
	@Column(name = "POCADD_T")
	private String pocaddT;
	
	@Column(name = "PURPOSE")
	private String purpose;
	
	@Column(name = "POCMOBILE1_T")
	private String pocmobile1T;

	public AddLicencyForSubAuaSystem(Long srno, String subAuaCode, String subAuaName, String kuaLk, String kuaCode,
			String subAuaLk, Date expiryDate, String status, String kukLkCode, String pocnameM, String pocmobile1m,
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

	public void setSrno(Long srno) {
		this.srno = srno;
	}

	public String getSubAuaCode() {
		return subAuaCode;
	}

	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}

	public String getSubAuaName() {
		return subAuaName;
	}

	public void setSubAuaName(String subAuaName) {
		this.subAuaName = subAuaName;
	}

	public String getKuaLk() {
		return kuaLk;
	}

	public void setKuaLk(String kuaLk) {
		this.kuaLk = kuaLk;
	}

	public String getKuaCode() {
		return kuaCode;
	}

	public void setKuaCode(String kuaCode) {
		this.kuaCode = kuaCode;
	}

	public String getSubAuaLk() {
		return subAuaLk;
	}

	public void setSubAuaLk(String subAuaLk) {
		this.subAuaLk = subAuaLk;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKukLkCode() {
		return kukLkCode;
	}

	public void setKukLkCode(String kukLkCode) {
		this.kukLkCode = kukLkCode;
	}

	public String getPocnameM() {
		return pocnameM;
	}

	public void setPocnameM(String pocnameM) {
		this.pocnameM = pocnameM;
	}

	public String getPocmobile1M() {
		return pocmobile1M;
	}

	public void setPocmobile1M(String pocmobile1m) {
		pocmobile1M = pocmobile1m;
	}

	public String getPocmobile2M() {
		return pocmobile2M;
	}

	public void setPocmobile2M(String pocmobile2m) {
		pocmobile2M = pocmobile2m;
	}

	public String getPocnameT() {
		return pocnameT;
	}

	public void setPocnameT(String pocnameT) {
		this.pocnameT = pocnameT;
	}

	public String getPocmoble1T() {
		return pocmoble1T;
	}

	public void setPocmoble1T(String pocmoble1t) {
		pocmoble1T = pocmoble1t;
	}

	public String getPocmobileT() {
		return pocmobileT;
	}

	public void setPocmobileT(String pocmobileT) {
		this.pocmobileT = pocmobileT;
	}

	public String getPocemailM() {
		return pocemailM;
	}

	public void setPocemailM(String pocemailM) {
		this.pocemailM = pocemailM;
	}

	public String getPocemailT() {
		return pocemailT;
	}

	public void setPocemailT(String pocemailT) {
		this.pocemailT = pocemailT;
	}

	public String getPocaddM() {
		return pocaddM;
	}

	public void setPocaddM(String pocaddM) {
		this.pocaddM = pocaddM;
	}

	public String getPocaddT() {
		return pocaddT;
	}

	public void setPocaddT(String pocaddT) {
		this.pocaddT = pocaddT;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPocmobile1T() {
		return pocmobile1T;
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
