package com.gov.Authmis.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class ParameterDetail {
    @Id
    private String TRANSACTION_ID;

    @Column(name = "SUB_AUA_CODE")
    private String subAuaCode;

    @Column(name = "ORG_NAME")
    private String orgName;

    @Column(name = "REQUEST_TYPE")
    private String requestType;

    @Column(name = "AADHAAR_ID")
    private String aadhaarId;

    @Column(name = "MAC_ADDRESS")
    private String macAddress;

    @Column(name = "APPNAME")
    private String appname;

    @Column(name = "AUTHENTICATION_STATUS")
    private String authenticationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    private String error;

    @Column(name = "RESPONSE_MESSAGE")
    private String responseMessage;

    @Column(name = "UID_RESPONSE_CODE")
    private String uidResponseCode;

    private String dc;

    @Column(name = "DP_ID")
    private String dpId;

    private String mi;

    @Column(name = "BT_AUA")
    private String btAua;

    @Column(name = "IP")
    private String ip;

    // Constructors, getters, setters, and other methods
}
