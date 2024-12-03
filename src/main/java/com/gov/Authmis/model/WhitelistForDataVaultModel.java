package com.gov.Authmis.model;

import java.sql.Date;
import java.util.List;

public class WhitelistForDataVaultModel {
    
	private Long id;
    private String subAuaCode;
    private String subAuaName;
    private List<String> ipAddresses;
    private String serviceType;
    private String appCode;
    private Long status;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String UpdatedBy;
    private String appName;
    
    
    
    

    // Getters and Setters
       
 
    public String getServiceType() {
		return serviceType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getSubAuaName() {
		return subAuaName;
	}

	public void setSubAuaName(String subAuaName) {
		this.subAuaName = subAuaName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSubAuaCode() {
        return subAuaCode;
    }

    public void setSubAuaCode(String subAuaCode) {
        this.subAuaCode = subAuaCode;
    }

    public List<String> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(List<String> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}
    


}
