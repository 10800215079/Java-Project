package com.gov.Authmis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subaua")
public class ViewShareLicenceKeyDetails {

    @Id
    @Column(name = "TID")
    private Long tid;

    @Column(name = "SUBAUA_CODE")
    private String subAuaCode;

    @Column(name = "ORGNAME")
    private String orgName;

    @Column(name = "ACTIVE")
    private Integer active;

    @Column(name = "OFFICERNAME")
    private String officerName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    // Getters and Setters

   
    public String getSubAuaCode() {
        return subAuaCode;
    }

    public void setSubAuaCode(String subAuaCode) {
        this.subAuaCode = subAuaCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
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

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public ViewShareLicenceKeyDetails(Long tid, String subAuaCode, String orgName, Integer active, String officerName,
			String phone, String email) {
		super();
		this.tid = tid;
		this.subAuaCode = subAuaCode;
		this.orgName = orgName;
		this.active = active;
		this.officerName = officerName;
		this.phone = phone;
		this.email = email;
	}

	public ViewShareLicenceKeyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ViewShareLicenceKeyDetails [tid=" + tid + ", subAuaCode=" + subAuaCode + ", orgName=" + orgName
				+ ", active=" + active + ", officerName=" + officerName + ", phone=" + phone + ", email=" + email + "]";
	}

	
    
}