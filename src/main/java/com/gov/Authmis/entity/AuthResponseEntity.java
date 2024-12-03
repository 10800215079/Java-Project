package com.gov.Authmis.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authresponse")
public class AuthResponseEntity {
    private String UUID;
    private int status;
    private String asaType;
    
    @XmlElement(name = "message")
    private String message;
    private Auth auth;

    @XmlAttribute
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    @XmlAttribute
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlElement
    public String getAsaType() {
        return asaType;
    }

    public void setAsaType(String asaType) {
        this.asaType = asaType;
    }

    @XmlElement
    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
    
   
    public String getMessage() {
        return message;
    }
    
    public String setMessage() {
        return message;
    }
}

 
