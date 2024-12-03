package com.gov.Authmis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REMAINDER_DETAILS")
public class SetReminderForServerConfigEntity {
	/**
	SRNO                   NOT NULL NUMBER  
	STATUS                          NUMBER(10)    
	DEACTIVATED_BY                  VARCHAR2(100) 
	DEACTIVATED_DATE                DATE          
	REASON_OF_DEACTIVATION          VARCHAR2(250)
	**/
	@Id
	@Column(name = "SRNO")
	private Long SRNO;
	@Column(name = "STATUS")
	private Long STATUS;
	@Column(name = "DEACTIVATED_BY")
	private String DEACTIVATED_BY;
	@Column(name = "DEACTIVATED_DATE")
	private Date DEACTIVATED_DATE;
	@Column(name = "REASON_OF_DEACTIVATION")
	private String REASON_OF_DEACTIVATION;
	
	public Long getSRNO() {
		return SRNO;
	}
	public void setSRNO(Long sRNO) {
		SRNO = sRNO;
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
	@Override
	public String toString() {
		return "SetRemainderForServerConfigEntity [SRNO=" + SRNO + ", STATUS=" + STATUS + ", DEACTIVATED_BY="
				+ DEACTIVATED_BY + ", DEACTIVATED_DATE=" + DEACTIVATED_DATE + ", REASON_OF_DEACTIVATION="
				+ REASON_OF_DEACTIVATION + "]";
	}
	public SetReminderForServerConfigEntity(Long sRNO, Long sTATUS, String dEACTIVATED_BY, Date dEACTIVATED_DATE,
			String rEASON_OF_DEACTIVATION) {
		super();
		SRNO = sRNO;
		STATUS = sTATUS;
		DEACTIVATED_BY = dEACTIVATED_BY;
		DEACTIVATED_DATE = dEACTIVATED_DATE;
		REASON_OF_DEACTIVATION = rEASON_OF_DEACTIVATION;
	}
	public SetReminderForServerConfigEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
