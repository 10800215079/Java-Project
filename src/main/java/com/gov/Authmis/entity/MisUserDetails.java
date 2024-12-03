package com.gov.Authmis.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MIS_USER_DETAILS", schema = "AADHAAR")
public class MisUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MIS_USER_DETAILS_SEQ")
    @SequenceGenerator(name = "MIS_USER_DETAILS_SEQ", sequenceName = "MIS_USER_DETAILS_SEQ", allocationSize = 1)
    
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SSOID")
    private String ssoId;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    // Getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
