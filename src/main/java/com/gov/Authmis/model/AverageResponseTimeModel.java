package com.gov.Authmis.model;

public class AverageResponseTimeModel {
	  private String subCode;
	  
	  private String fromdate;
	  
	  private String Enddate;
	  
	  private String CREATED_DATE;
	  private String TOTAL_TRANSACTIONS;
	  private String REQUEST_TYPE;
	  private String AVERAGE_TIME;
	

	public AverageResponseTimeModel() {
		super();
	}

	public AverageResponseTimeModel(String subCode, String fromdate, String enddate, String cREATED_DATE,
			String tOTAL_TRANSACTIONS, String rEQUEST_TYPE, String aVERAGE_TIME) {
		super();
		this.subCode = subCode;
		this.fromdate = fromdate;
		Enddate = enddate;
		CREATED_DATE = cREATED_DATE;
		TOTAL_TRANSACTIONS = tOTAL_TRANSACTIONS;
		REQUEST_TYPE = rEQUEST_TYPE;
		AVERAGE_TIME = aVERAGE_TIME;
	}



	
	@Override
	public String toString() {
		return "AverageResponseTimeModel [subCode=" + subCode + ", fromdate=" + fromdate + ", Enddate=" + Enddate
				+ ", CREATED_DATE=" + CREATED_DATE + ", TOTAL_TRANSACTIONS=" + TOTAL_TRANSACTIONS + ", REQUEST_TYPE="
				+ REQUEST_TYPE + ", AVERAGE_TIME=" + AVERAGE_TIME + "]";
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public String getTOTAL_TRANSACTIONS() {
		return TOTAL_TRANSACTIONS;
	}

	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}

	public String getAVERAGE_TIME() {
		return AVERAGE_TIME;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public void setTOTAL_TRANSACTIONS(String tOTAL_TRANSACTIONS) {
		TOTAL_TRANSACTIONS = tOTAL_TRANSACTIONS;
	}

	public void setREQUEST_TYPE(String rEQUEST_TYPE) {
		REQUEST_TYPE = rEQUEST_TYPE;
	}

	public void setAVERAGE_TIME(String aVERAGE_TIME) {
		AVERAGE_TIME = aVERAGE_TIME;
	}

	public String getSubCode() {
	    return this.subCode;
	  }
	  
	  public void setSubCode(String subCode) {
	    this.subCode = subCode;
	  }
	  
	  public String getFromdate() {
	    return this.fromdate;
	  }
	  
	  public void setFromdate(String fromdate) {
	    this.fromdate = fromdate;
	  }
	  
	  public String getEnddate() {
	    return this.Enddate;
	  }
	  
	  public void setEnddate(String enddate) {
	    this.Enddate = enddate;
	  }
	}
