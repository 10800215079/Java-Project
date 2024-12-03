package com.gov.Authmis.model;

public class ErrorCodeModel {
	
	private String fromdate;
	
	private String enddate;
	
	private String Error_code;
	
	private String error_message;
	
	private String error_count;
	
	private String error_percent;

	public ErrorCodeModel(String fromdate, String enddate, String error_code, String error_message, String error_count,
			String error_percent) {
		super();
		this.fromdate = fromdate;
		this.enddate = enddate;
		Error_code = error_code;
		this.error_message = error_message;
		this.error_count = error_count;
		this.error_percent = error_percent;
	}

	public ErrorCodeModel() {
		super();
	}

	public String getFromdate() {
		return fromdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public String getError_code() {
		return Error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public String getError_count() {
		return error_count;
	}

	public String getError_percent() {
		return error_percent;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setError_code(String error_code) {
		Error_code = error_code;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public void setError_count(String error_count) {
		this.error_count = error_count;
	}

	public void setError_percent(String error_percent) {
		this.error_percent = error_percent;
	}

	@Override
	public String toString() {
		return "ErrorCodeModel [fromdate=" + fromdate + ", enddate=" + enddate + ", Error_code=" + Error_code
				+ ", error_message=" + error_message + ", error_count=" + error_count + ", error_percent="
				+ error_percent + "]";
	}

	

	
	

}
