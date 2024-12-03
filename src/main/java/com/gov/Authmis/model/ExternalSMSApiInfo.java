package com.gov.Authmis.model;

public class ExternalSMSApiInfo {
	private String uniqueID;
    private String serviceName;
    private String language;
    private String[] mobileNo;
    private String templateId;
    private String message;

    // Getters and setters

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String[] mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
