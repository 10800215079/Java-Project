package com.gov.Authmis.entity;

import javax.persistence.*;

import javax.persistence.*;

@Entity
@Table(name = "SUBAUA", schema = "AADHAAR")
public class OtpAuthenticationRequest {
   
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO) 
   // @Column(name = "TID")
	
	@Id
    @Column(name = "SUBAUA_CODE")
    private String subAuaCode;
   
    @Column(name = "SUBAUA_LK")
    private String subAuaLk;

    @Column(name = "SUBAUA_CERFILE")
    private String subAuaCerFile;

    @Column(name = "FROMDATE")
    private String fromDate;

    @Column(name = "TODATE")
    private String toDate;

    @Column(name = "ORGNAME")
    private String orgName;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "OTHERDEPART")
    private String otherDepart;

    @Column(name = "ACTIVE")
    private Long active;

    @Column(name = "OFFICERNAME")
    private String officerName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "IS_REG_WITH_UID")
    private Long isRegWithUid;

    @Column(name = "MPOCNAME")
    private String mpocName;

    @Column(name = "MPOCEMAIL")
    private String mpocEmail;

    @Column(name = "MPOCCONTACT")
    private String mpocContact;

    @Column(name = "MPOCDESIGNATION")
    private String mpocDesignation;

    @Lob
    @Column(name = "APPROVALDOC")
    private byte[] approvalDoc;

    @Column(name = "APPROVALDATE")
    private String approvalDate;

    @Column(name = "UPDATEDDATE")
    private String updatedDate;

    @Column(name = "CREATEDDATE")
    private String createdDate;

    @Column(name = "SCHEME_PURPOSE")
    private String schemePurpose;

    @Column(name = "SECTIONAADHARACT")
    private String sectionAadharAct;

    @Column(name = "TXN")
    private String txn;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "DEREGISTRATIONAPPROVALDATE")
    private String deRegistrationApprovalDate;
    
    @Lob
    @Column(name = "DEREGISTRATIONAPPROVALDOC")
    private byte[] deRegistrationApprovalDoc;

    @Transient
    private String otp;

    @Transient
    private String uuid;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubAuaCode() {
		return subAuaCode;
	}

	public void setSubAuaCode(String subAuaCode) {
		this.subAuaCode = subAuaCode;
	}

	public String getSubAuaLk() {
		return subAuaLk;
	}

	public void setSubAuaLk(String subAuaLk) {
		this.subAuaLk = subAuaLk;
	}

	public String getSubAuaCerFile() {
		return subAuaCerFile;
	}

	public void setSubAuaCerFile(String subAuaCerFile) {
		this.subAuaCerFile = subAuaCerFile;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOtherDepart() {
		return otherDepart;
	}

	public void setOtherDepart(String otherDepart) {
		this.otherDepart = otherDepart;
	}

	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}

	public String getOfficerName() {
		return officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Long getIsRegWithUid() {
		return isRegWithUid;
	}

	public void setIsRegWithUid(Long isRegWithUid) {
		this.isRegWithUid = isRegWithUid;
	}

	public String getMpocName() {
		return mpocName;
	}

	public void setMpocName(String mpocName) {
		this.mpocName = mpocName;
	}

	public String getMpocEmail() {
		return mpocEmail;
	}

	public void setMpocEmail(String mpocEmail) {
		this.mpocEmail = mpocEmail;
	}

	public String getMpocContact() {
		return mpocContact;
	}

	public void setMpocContact(String mpocContact) {
		this.mpocContact = mpocContact;
	}

	public String getMpocDesignation() {
		return mpocDesignation;
	}

	public void setMpocDesignation(String mpocDesignation) {
		this.mpocDesignation = mpocDesignation;
	}

	public byte[] getApprovalDoc() {
		return approvalDoc;
	}

	public void setApprovalDoc(byte[] approvalDoc) {
		this.approvalDoc = approvalDoc;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getSchemePurpose() {
		return schemePurpose;
	}

	public void setSchemePurpose(String schemePurpose) {
		this.schemePurpose = schemePurpose;
	}

	public String getSectionAadharAct() {
		return sectionAadharAct;
	}

	public void setSectionAadharAct(String sectionAadharAct) {
		this.sectionAadharAct = sectionAadharAct;
	}

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDeRegistrationApprovalDate() {
		return deRegistrationApprovalDate;
	}

	public void setDeRegistrationApprovalDate(String deRegistrationApprovalDate) {
		this.deRegistrationApprovalDate = deRegistrationApprovalDate;
	}

	public byte[] getDeRegistrationApprovalDoc() {
		return deRegistrationApprovalDoc;
	}

	public void setDeRegistrationApprovalDoc(byte[] deRegistrationApprovalDoc) {
		this.deRegistrationApprovalDoc = deRegistrationApprovalDoc;
	}

}
