package com.gov.Authmis.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "ADD_APPLICATION")
public class AddApplicationForSecurityAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADD_APPLICATION_SEQ")
    @SequenceGenerator(name = "ADD_APPLICATION_SEQ", sequenceName = "ADD_APPLICATION_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEPARTMENT", length = 200, nullable = false)
    private String department;

    @Column(name = "APP_NAME", length = 200, nullable = false)
    private String appName;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @Column(name = "CREATED_BY", length = 100, nullable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "CREATED_DATE", nullable = false)
    private Timestamp createdDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

	@Override
	public String toString() {
		return "UploadSecurityAuditReportEntity [id=" + id + ", department=" + department + ", appName=" + appName
				+ ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate + "]";
	}

	public AddApplicationForSecurityAuditEntity(Long id, String department, String appName, Integer status, String createdBy,
			Timestamp createdDate) {
		super();
		this.id = id;
		this.department = department;
		this.appName = appName;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AddApplicationForSecurityAuditEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
