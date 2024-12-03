package com.gov.Authmis.model;

import java.sql.Date;

public class SetReminderForServerConfigModel {

	/**
	 * 
	 * SRNO NOT NULL NUMBER PROJECT_NAME VARCHAR2(100) PERFORM_ACTIVITY
	 * VARCHAR2(500) REMAINDER_DATE DATE CREATED_DATE DATE CREATED_BY VARCHAR2(100)
	 * IP VARCHAR2(100) STATUS NUMBER(10) DEACTIVATED_BY VARCHAR2(100)
	 * DEACTIVATED_DATE DATE REASON_OF_DEACTIVATION VARCHAR2(250)
	 * 
	 */

	private Long SRNO;
	private String PROJECT_NAME;
	private String PERFORM_ACTIVITY;
	private Date REMAINDER_DATE;
	private Long GET_REMAINDER;
	private Date CREATED_DATE;
	private String CREATED_BY;
	private String IP;
	private Long STATUS;
	private String DEACTIVATED_BY;
	private Date DEACTIVATED_DATE;
	private String REASON_OF_DEACTIVATION;

	private String MAIL_TO;
	private String MAIL_CC;

	public Long getSRNO() {
		return SRNO;
	}

	public void setSRNO(Long sRNO) {
		SRNO = sRNO;
	}

	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}

	public void setPROJECT_NAME(String pROJECT_NAME) {
		PROJECT_NAME = pROJECT_NAME;
	}

	public String getPERFORM_ACTIVITY() {
		return PERFORM_ACTIVITY;
	}

	public void setPERFORM_ACTIVITY(String pERFORM_ACTIVITY) {
		PERFORM_ACTIVITY = pERFORM_ACTIVITY;
	}

	public Date getREMAINDER_DATE() {
		return REMAINDER_DATE;
	}

	public void setREMAINDER_DATE(Date rEMAINDER_DATE) {
		REMAINDER_DATE = rEMAINDER_DATE;
	}

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Long getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(Long sTATUS) {
		STATUS = sTATUS;
	}

	public String getDEACTIVATED_BY() {
		return DEACTIVATED_BY;
	}

	public void setDEACTIVATED_BY(String dEACTIVATED_BY) {
		DEACTIVATED_BY = dEACTIVATED_BY;
	}

	public Date getDEACTIVATED_DATE() {
		return DEACTIVATED_DATE;
	}

	public void setDEACTIVATED_DATE(Date dEACTIVATED_DATE) {
		DEACTIVATED_DATE = dEACTIVATED_DATE;
	}

	public String getREASON_OF_DEACTIVATION() {
		return REASON_OF_DEACTIVATION;
	}

	public void setREASON_OF_DEACTIVATION(String rEASON_OF_DEACTIVATION) {
		REASON_OF_DEACTIVATION = rEASON_OF_DEACTIVATION;
	}

	public Long getGET_REMAINDER() {
		return GET_REMAINDER;
	}

	public void setGET_REMAINDER(Long gET_REMAINDER) {
		GET_REMAINDER = gET_REMAINDER;
	}

	public String getMAIL_TO() {
		return MAIL_TO;
	}

	public void setMAIL_TO(String mAIL_TO) {
		MAIL_TO = mAIL_TO;
	}

	public String getMAIL_CC() {
		return MAIL_CC;
	}

	public void setMAIL_CC(String mAIL_CC) {
		MAIL_CC = mAIL_CC;
	}

	@Override
	public String toString() {
		return "SetReminderForServerConfigModel [SRNO=" + SRNO + ", PROJECT_NAME=" + PROJECT_NAME
				+ ", PERFORM_ACTIVITY=" + PERFORM_ACTIVITY + ", REMAINDER_DATE=" + REMAINDER_DATE + ", GET_REMAINDER="
				+ GET_REMAINDER + ", CREATED_DATE=" + CREATED_DATE + ", CREATED_BY=" + CREATED_BY + ", IP=" + IP
				+ ", STATUS=" + STATUS + ", DEACTIVATED_BY=" + DEACTIVATED_BY + ", DEACTIVATED_DATE=" + DEACTIVATED_DATE
				+ ", REASON_OF_DEACTIVATION=" + REASON_OF_DEACTIVATION + ", MAIL_TO=" + MAIL_TO + ", MAIL_CC=" + MAIL_CC
				+ "]";
	}

	public SetReminderForServerConfigModel(Long sRNO, String pROJECT_NAME, String pERFORM_ACTIVITY, Date rEMAINDER_DATE,
			Long gET_REMAINDER, Date cREATED_DATE, String cREATED_BY, String iP, Long sTATUS, String dEACTIVATED_BY,
			Date dEACTIVATED_DATE, String rEASON_OF_DEACTIVATION, String mAIL_TO, String mAIL_CC) {
		super();
		SRNO = sRNO;
		PROJECT_NAME = pROJECT_NAME;
		PERFORM_ACTIVITY = pERFORM_ACTIVITY;
		REMAINDER_DATE = rEMAINDER_DATE;
		GET_REMAINDER = gET_REMAINDER;
		CREATED_DATE = cREATED_DATE;
		CREATED_BY = cREATED_BY;
		IP = iP;
		STATUS = sTATUS;
		DEACTIVATED_BY = dEACTIVATED_BY;
		DEACTIVATED_DATE = dEACTIVATED_DATE;
		REASON_OF_DEACTIVATION = rEASON_OF_DEACTIVATION;
		MAIL_TO = mAIL_TO;
		MAIL_CC = mAIL_CC;
	}

	public SetReminderForServerConfigModel() {
		super();
		// TODO Auto-generated constructor stub
	}




}
