package com.gov.Authmis.entity;

import javax.xml.bind.annotation.XmlAttribute;

public class Auth {
    private String txn;
    private String status;

    @XmlAttribute
    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    @XmlAttribute
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
