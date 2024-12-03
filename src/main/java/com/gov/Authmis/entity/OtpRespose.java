package com.gov.Authmis.entity;

public class OtpRespose {
    private String asaType;
    private String txn;
    private String status;
    private String UUID;
    private int responseStatus;
    private String Error;

    // Constructors, getters, and setters

    public String getAsaType() {
        return asaType;
    }

    public void setAsaType(String asaType) {
        this.asaType = asaType;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getResponseStatus() {
        return responseStatus;
    }
    

    public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}
