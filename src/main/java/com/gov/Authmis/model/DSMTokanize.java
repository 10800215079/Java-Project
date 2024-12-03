package com.gov.Authmis.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "DSMTokanize")
public class DSMTokanize  {

	private String RefNo;
	private int statusCode;

	@XmlElement(name = "RefNo")
	public String getRefNo() {
		return RefNo;
	}

	public void setRefNo(String refNo) {
		RefNo = refNo;
	}

	@XmlElement(name = "statusCode")
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "DSMTokanize [RefNo=" + RefNo + ", statusCode=" + statusCode + "]";
	}
	/** protected String status;

    protected String AadhaarNo;

    protected String statusCode;
    protected String refNo;

    @XmlAttribute(name = "RefNo")
    public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	@XmlAttribute(name = "statusCode")
    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    @XmlElement(name = "AadhaarNo")
    public String getAadhaarNo() {
        return AadhaarNo;
    }

    public void setAadhaarNo(String value) {
        this.AadhaarNo = value;
    }

    @XmlElement(name = "statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String value) {
        this.statusCode = value;
    }

	@Override
	public String toString() {
		return "EncDecResponse [status=" + status + ", aadhaarId=" + AadhaarNo + ", statusCode=" + statusCode + "]";
	}

	**/
	 
	/*
	 * private String RefNo; private int statusCode;
	 * 
	 * @XmlElement(name = "RefNo") public String getRefNo() { return RefNo; }
	 * 
	 * public void setRefNo(String refNo) { RefNo = refNo; }
	 * 
	 * @XmlElement(name = "statusCode") public int getStatusCode() { return
	 * statusCode; }
	 * 
	 * public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
	 * 
	 * @Override public String toString() { return "DSMTokanize [RefNo=" + RefNo +
	 * ", statusCode=" + statusCode + "]"; }
	 */
	
}
