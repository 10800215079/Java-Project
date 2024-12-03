package com.gov.Authmis.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class UsersRoleMapping {
	private int srno;
	private String SSOID;
	private String aadhaarId;
	private String displayName;
	private String employeeNumber;
	private String mobile;
	private String mailOfficial;
	private String designation;
	private String user_roles;
	private String subauaname;
	private String subauacode;
	private String role_id;
	private String creater_aua_ssoid;
	private String creater_roles_id;
	private String status;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/mm/yy")
	private String CREATED_DATE;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/mm/yy")
	private String DEACTIVATION_DATE;
	private String DEACTIVATED_BY_SSOID;
	private String DEACTIVATED_ROLEID;
	public UsersRoleMapping(int srno, String sSOID, String aadhaarId, String displayName, String employeeNumber,
			String mobile, String mailOfficial, String designation, String user_roles, String subauaname,
			String subauacode, String role_id, String creater_aua_ssoid, String creater_roles_id, String status,
			String cREATED_DATE, String dEACTIVATION_DATE, String dEACTIVATED_BY_SSOID, String dEACTIVATED_ROLEID) {
		super();
		this.srno = srno;
		SSOID = sSOID;
		this.aadhaarId = aadhaarId;
		this.displayName = displayName;
		this.employeeNumber = employeeNumber;
		this.mobile = mobile;
		this.mailOfficial = mailOfficial;
		this.designation = designation;
		this.user_roles = user_roles;
		this.subauaname = subauaname;
		this.subauacode = subauacode;
		this.role_id = role_id;
		this.creater_aua_ssoid = creater_aua_ssoid;
		this.creater_roles_id = creater_roles_id;
		this.status = status;
		CREATED_DATE = cREATED_DATE;
		DEACTIVATION_DATE = dEACTIVATION_DATE;
		DEACTIVATED_BY_SSOID = dEACTIVATED_BY_SSOID;
		DEACTIVATED_ROLEID = dEACTIVATED_ROLEID;
	}
	public UsersRoleMapping() {
		super();
	}
	public int getSrno() {
		return srno;
	}
	public String getSSOID() {
		return SSOID;
	}
	public String getAadhaarId() {
		return aadhaarId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public String getMobile() {
		return mobile;
	}
	public String getMailOfficial() {
		return mailOfficial;
	}
	public String getDesignation() {
		return designation;
	}
	public String getUser_roles() {
		return user_roles;
	}
	public String getSubauaname() {
		return subauaname;
	}
	public String getSubauacode() {
		return subauacode;
	}
	public String getRole_id() {
		return role_id;
	}
	public String getCreater_aua_ssoid() {
		return creater_aua_ssoid;
	}
	public String getCreater_roles_id() {
		return creater_roles_id;
	}
	public String getStatus() {
		return status;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public String getDEACTIVATION_DATE() {
		return DEACTIVATION_DATE;
	}
	public String getDEACTIVATED_BY_SSOID() {
		return DEACTIVATED_BY_SSOID;
	}
	public String getDEACTIVATED_ROLEID() {
		return DEACTIVATED_ROLEID;
	}
	public void setSrno(int srno) {
		this.srno = srno;
	}
	public void setSSOID(String sSOID) {
		SSOID = sSOID;
	}
	public void setAadhaarId(String aadhaarId) {
		this.aadhaarId = aadhaarId;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setMailOfficial(String mailOfficial) {
		this.mailOfficial = mailOfficial;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void setUser_roles(String user_roles) {
		this.user_roles = user_roles;
	}
	public void setSubauaname(String subauaname) {
		this.subauaname = subauaname;
	}
	public void setSubauacode(String subauacode) {
		this.subauacode = subauacode;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public void setCreater_aua_ssoid(String creater_aua_ssoid) {
		this.creater_aua_ssoid = creater_aua_ssoid;
	}
	public void setCreater_roles_id(String creater_roles_id) {
		this.creater_roles_id = creater_roles_id;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public void setDEACTIVATION_DATE(String dEACTIVATION_DATE) {
		DEACTIVATION_DATE = dEACTIVATION_DATE;
	}
	public void setDEACTIVATED_BY_SSOID(String dEACTIVATED_BY_SSOID) {
		DEACTIVATED_BY_SSOID = dEACTIVATED_BY_SSOID;
	}
	public void setDEACTIVATED_ROLEID(String dEACTIVATED_ROLEID) {
		DEACTIVATED_ROLEID = dEACTIVATED_ROLEID;
	}
	@Override
	public String toString() {
		return "UsersRoleMapping [srno=" + srno + ", SSOID=" + SSOID + ", aadhaarId=" + aadhaarId + ", displayName="
				+ displayName + ", employeeNumber=" + employeeNumber + ", mobile=" + mobile + ", mailOfficial="
				+ mailOfficial + ", designation=" + designation + ", user_roles=" + user_roles + ", subauaname="
				+ subauaname + ", subauacode=" + subauacode + ", role_id=" + role_id + ", creater_aua_ssoid="
				+ creater_aua_ssoid + ", creater_roles_id=" + creater_roles_id + ", status=" + status
				+ ", CREATED_DATE=" + CREATED_DATE + ", DEACTIVATION_DATE=" + DEACTIVATION_DATE
				+ ", DEACTIVATED_BY_SSOID=" + DEACTIVATED_BY_SSOID + ", DEACTIVATED_ROLEID=" + DEACTIVATED_ROLEID + "]";
	}
	
	
}
