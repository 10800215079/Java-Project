package com.gov.Authmis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subaua_ekyc_lk")
public class ShareLicenceKeyEntity {
	
	@Id
	@Column(name = "SRNO")
	private Long srno;

	@Column(name = "SUB_AUA_CODE")
	private String subAuaCode;

	@Column(name = "SUB_AUA_NAME")
	private String subAuaName;

	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;
	
	@Column(name = "SUB_AUA_LK ")
	private String subAuaLk;

	public ShareLicenceKeyEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShareLicenceKeyEntity(Long srno, String subAuaCode, String subAuaName, Date expiryDate, String subAuaLk,
			Integer status) {
		super();
		this.srno = srno;
		this.subAuaCode = subAuaCode;
		this.subAuaName = subAuaName;
		this.expiryDate = expiryDate;
		this.subAuaLk = subAuaLk;
		this.status = status;
	}

	@Override
	public String toString() {
		return "ShareLicenceKeyEntity [srno=" + srno + ", subAuaCode=" + subAuaCode + ", subAuaName=" + subAuaName
				+ ", expiryDate=" + expiryDate + ", subAuaLk=" + subAuaLk + ", status=" + status + "]";
	}

	public String getSubAuaLk() {
		return subAuaLk;
	}

	public void setSubAuaLk(String subAuaLk) {
		this.subAuaLk = subAuaLk;
	}

	@Column(name = "STATUS", length = 10)
	private Integer status;

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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	

}
