package com.gov.Authmis.model;

public class OracleDatabaseException  extends RuntimeException{

	 private static final long serialVersionUID=-9079454849611061074L;
	 
	 public OracleDatabaseException() {
		 super();
	 }
	public OracleDatabaseException(final String message) {
		super(message);
	}
}
