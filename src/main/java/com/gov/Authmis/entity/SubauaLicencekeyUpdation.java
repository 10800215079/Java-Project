package com.gov.Authmis.entity;

import java.sql.Blob;
import java.sql.Date;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "SUBAUA")
public class SubauaLicencekeyUpdation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TID")
	private String TID;
	@Column(name = "SUBAUA_CODE")
	private String SUBAUA_CODE;
	@Column(name = "SUBAUA_LK")
	private String SUBAUA_LK;
	@Column(name = "SUBAUA_CERFILE")
	private String SUBAUA_CERFILE;
	@Column(name = "FROMDATE")
	private Date FROMDATE;
	@Column(name = "TODATE")
	private Date TODATE;
	@Column(name = "ORGNAME")
	private String ORGNAME;
	/*
	 * @Column(name = "CERFILE") private byte[] CERFILE;
	 */
	@Column(name = "DEPARTMENT")
	private String DEPARTMENT;
	@Column(name = "OTHERDEPART")
	private String OTHERDEPART;
	/*
	 * @Column(name = "DEPARTKID") private String DEPARTKID;
	 */
	@Column(name = "ACTIVE")
	private String ACTIVE;
	@Column(name = "OFFICERNAME")
	private String OFFICERNAME;
	@Column(name = "PHONE")
	private String PHONE;
	@Column(name = "EMAIL")
	private String EMAIL;
	@Column(name = "IS_REG_WITH_UID")
	private String IS_REG_WITH_UID;
	@Column(name = "MPOCNAME")
	private String MPOCNAME;
	@Column(name = "MPOCEMAIL")
	private String MPOCEMAIL;
	@Column(name = "MPOCCONTACT")
	private String MPOCCONTACT;
	@Column(name = "MPOCDESIGNATION")
	private String MPOCDESIGNATION;
	@Column(name = "APPROVALDATE")
	private String APPROVALDATE;
	@Column(name = "SCHEME_PURPOSE")
	private String SCHEME_PURPOSE;
	@Column(name = "SECTIONAADHARACT")
	private String SECTIONAADHARACT;
	@Column(name = "DESIGNATION")
	private String DESIGNATION;
	@Column(name = "URL")
	private String URL;
	@Lob
    @Column(name = "APPROVALDOC")
    private byte[] approvalDoc;
	@Column(name = "UPDATEDDATE")
	private String UPDATEDDATE;
	
	@Column(name = "DEREGISTRATIONAPPROVALDATE")
	private String DEREGISTRATIONAPPROVALDATE;

	@Lob
	@Column(name = "DEREGISTRATIONAPPROVALDOC")
	private  byte[] DEREGISTRATIONAPPROVALDOC;
	
	@Column(name = "DEACTIVATIONTXNID")
	private String DEACTIVATIONTXNID;


	public String getTID() {
		return TID;
	}
	public String getSUBAUA_CODE() {
		return SUBAUA_CODE;
	}
	public String getSUBAUA_LK() {
		return SUBAUA_LK;
	}
	public String getSUBAUA_CERFILE() {
		return SUBAUA_CERFILE;
	}
	public Date getFROMDATE() {
		return FROMDATE;
	}
	public Date getTODATE() {
		return TODATE;
	}
	public String getORGNAME() {
		return ORGNAME;
	}
	public String getDEPARTMENT() {
		return DEPARTMENT;
	}
	public String getOTHERDEPART() {
		return OTHERDEPART;
	}
	public String getACTIVE() {
		return ACTIVE;
	}
	public String getOFFICERNAME() {
		return OFFICERNAME;
	}
	public String getPHONE() {
		return PHONE;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public void setSUBAUA_CODE(String sUBAUA_CODE) {
		SUBAUA_CODE = sUBAUA_CODE;
	}
	public void setSUBAUA_LK(String sUBAUA_LK) {
		SUBAUA_LK = sUBAUA_LK;
	}
	public void setSUBAUA_CERFILE(String sUBAUA_CERFILE) {
		SUBAUA_CERFILE = sUBAUA_CERFILE;
	}
	public void setFROMDATE(Date fROMDATE) {
		FROMDATE = fROMDATE;
	}
	public void setTODATE(Date tODATE) {
		TODATE = tODATE;
	}
	public void setORGNAME(String oRGNAME) {
		ORGNAME = oRGNAME;
	}
	public void setDEPARTMENT(String dEPARTMENT) {
		DEPARTMENT = dEPARTMENT;
	}
	public void setOTHERDEPART(String oTHERDEPART) {
		OTHERDEPART = oTHERDEPART;
	}
	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}
	public void setOFFICERNAME(String oFFICERNAME) {
		OFFICERNAME = oFFICERNAME;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	
	public String getIS_REG_WITH_UID() {
		return IS_REG_WITH_UID;
	}
	public void setIS_REG_WITH_UID(String iS_REG_WITH_UID) {
		IS_REG_WITH_UID = iS_REG_WITH_UID;
	}
	public String getMPOCNAME() {
		return MPOCNAME;
	}
	public void setMPOCNAME(String mPOCNAME) {
		MPOCNAME = mPOCNAME;
	}
	public String getMPOCEMAIL() {
		return MPOCEMAIL;
	}
	public void setMPOCEMAIL(String mPOCEMAIL) {
		MPOCEMAIL = mPOCEMAIL;
	}
	public String getMPOCCONTACT() {
		return MPOCCONTACT;
	}
	public void setMPOCCONTACT(String mPOCCONTACT) {
		MPOCCONTACT = mPOCCONTACT;
	}
	public String getMPOCDESIGNATION() {
		return MPOCDESIGNATION;
	}
	public void setMPOCDESIGNATION(String mPOCDESIGNATION) {
		MPOCDESIGNATION = mPOCDESIGNATION;
	}
	public String getAPPROVALDATE() {
		return APPROVALDATE;
	}
	public void setAPPROVALDATE(String aPPROVALDATE) {
		APPROVALDATE = aPPROVALDATE;
	}
	public String getSCHEME_PURPOSE() {
		return SCHEME_PURPOSE;
	}
	public void setSCHEME_PURPOSE(String sCHEME_PURPOSE) {
		SCHEME_PURPOSE = sCHEME_PURPOSE;
	}
	public String getSECTIONAADHARACT() {
		return SECTIONAADHARACT;
	}
	public void setSECTIONAADHARACT(String sECTIONAADHARACT) {
		SECTIONAADHARACT = sECTIONAADHARACT;
	}
	public String getDESIGNATION() {
		return DESIGNATION;
	}
	public void setDESIGNATION(String dESIGNATION) {
		DESIGNATION = dESIGNATION;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}

	public byte[] getApprovalDoc() {
		return approvalDoc;
	}
	public void setApprovalDoc(byte[] approvalDoc) {
		this.approvalDoc = approvalDoc;
	}
	
	public String getUPDATEDDATE() {
		return UPDATEDDATE;
	}
	public void setUPDATEDDATE(String uPDATEDDATE) {
		UPDATEDDATE = uPDATEDDATE;
	}
	
	public String getDEREGISTRATIONAPPROVALDATE() {
		return DEREGISTRATIONAPPROVALDATE;
	}
	public void setDEREGISTRATIONAPPROVALDATE(String dEREGISTRATIONAPPROVALDATE) {
		DEREGISTRATIONAPPROVALDATE = dEREGISTRATIONAPPROVALDATE;
	}
	public byte[] getDEREGISTRATIONAPPROVALDOC() {
		return DEREGISTRATIONAPPROVALDOC;
	}
	public void setDEREGISTRATIONAPPROVALDOC(byte[] dEREGISTRATIONAPPROVALDOC) {
		DEREGISTRATIONAPPROVALDOC = dEREGISTRATIONAPPROVALDOC;
	}
	
	public String getDEACTIVATIONTXNID() {
		return DEACTIVATIONTXNID;
	}
	public void setDEACTIVATIONTXNID(String dEACTIVATIONTXNID) {
		DEACTIVATIONTXNID = dEACTIVATIONTXNID;
	}
	public SubauaLicencekeyUpdation() {
		super();
	}
	@Override
	public String toString() {
		return "SubauaLicencekeyUpdation [TID=" + TID + ", SUBAUA_CODE=" + SUBAUA_CODE + ", SUBAUA_LK=" + SUBAUA_LK
				+ ", SUBAUA_CERFILE=" + SUBAUA_CERFILE + ", FROMDATE=" + FROMDATE + ", TODATE=" + TODATE + ", ORGNAME="
				+ ORGNAME + ", DEPARTMENT=" + DEPARTMENT + ", OTHERDEPART=" + OTHERDEPART + ", ACTIVE=" + ACTIVE
				+ ", OFFICERNAME=" + OFFICERNAME + ", PHONE=" + PHONE + ", EMAIL=" + EMAIL + ", IS_REG_WITH_UID="
				+ IS_REG_WITH_UID + ", MPOCNAME=" + MPOCNAME + ", MPOCEMAIL=" + MPOCEMAIL + ", MPOCCONTACT="
				+ MPOCCONTACT + ", MPOCDESIGNATION=" + MPOCDESIGNATION + ", APPROVALDATE=" + APPROVALDATE
				+ ", SCHEME_PURPOSE=" + SCHEME_PURPOSE + ", SECTIONAADHARACT=" + SECTIONAADHARACT + ", DESIGNATION="
				+ DESIGNATION + ", URL=" + URL + ", approvalDoc=" + Arrays.toString(approvalDoc) + ", UPDATEDDATE="
				+ UPDATEDDATE + ", DEREGISTRATIONAPPROVALDATE=" + DEREGISTRATIONAPPROVALDATE
				+ ", DEREGISTRATIONAPPROVALDOC=" + Arrays.toString(DEREGISTRATIONAPPROVALDOC) + ", DEACTIVATIONTXNID="
				+ DEACTIVATIONTXNID + "]";
	}
	public SubauaLicencekeyUpdation(String tID, String sUBAUA_CODE, String sUBAUA_LK, String sUBAUA_CERFILE,
			Date fROMDATE, Date tODATE, String oRGNAME, String dEPARTMENT, String oTHERDEPART, String aCTIVE,
			String oFFICERNAME, String pHONE, String eMAIL, String iS_REG_WITH_UID, String mPOCNAME, String mPOCEMAIL,
			String mPOCCONTACT, String mPOCDESIGNATION, String aPPROVALDATE, String sCHEME_PURPOSE,
			String sECTIONAADHARACT, String dESIGNATION, String uRL, byte[] approvalDoc, String uPDATEDDATE,
			String dEREGISTRATIONAPPROVALDATE, byte[] dEREGISTRATIONAPPROVALDOC, String dEACTIVATIONTXNID) {
		super();
		TID = tID;
		SUBAUA_CODE = sUBAUA_CODE;
		SUBAUA_LK = sUBAUA_LK;
		SUBAUA_CERFILE = sUBAUA_CERFILE;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
		ORGNAME = oRGNAME;
		DEPARTMENT = dEPARTMENT;
		OTHERDEPART = oTHERDEPART;
		ACTIVE = aCTIVE;
		OFFICERNAME = oFFICERNAME;
		PHONE = pHONE;
		EMAIL = eMAIL;
		IS_REG_WITH_UID = iS_REG_WITH_UID;
		MPOCNAME = mPOCNAME;
		MPOCEMAIL = mPOCEMAIL;
		MPOCCONTACT = mPOCCONTACT;
		MPOCDESIGNATION = mPOCDESIGNATION;
		APPROVALDATE = aPPROVALDATE;
		SCHEME_PURPOSE = sCHEME_PURPOSE;
		SECTIONAADHARACT = sECTIONAADHARACT;
		DESIGNATION = dESIGNATION;
		URL = uRL;
		this.approvalDoc = approvalDoc;
		UPDATEDDATE = uPDATEDDATE;
		DEREGISTRATIONAPPROVALDATE = dEREGISTRATIONAPPROVALDATE;
		DEREGISTRATIONAPPROVALDOC = dEREGISTRATIONAPPROVALDOC;
		DEACTIVATIONTXNID = dEACTIVATIONTXNID;
	}
	
	}
