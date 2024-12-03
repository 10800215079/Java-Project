package com.gov.Authmis.model;

import java.sql.Blob;
import java.sql.Timestamp;


public class RequestForIpWhitelistModel {

	private Long id;
	private String subAUA_Code;
	private String subAUA_Name;
	private String ipAddress;
	private String ipBelongsTo;
	private String appName;
	private String applicationUrl;
	private String requestType;
	private String serviceUrlType;
	private String isConsentTaken;
	private Blob consentDocument;
	private String schemeName;
	private String aadhaarAct;
	private Integer status;
	private String updatedBy;
	private String createdBy;
	private Timestamp updatedDate;
	private Timestamp createdDate;
	private String reason;
	private Blob publishedDoc;
	private Integer isDocPublished;


}
