package com.gov.Authmis.model;

public class QueryBuilder {
	
	private String buildQuery;

	public String getBuildQuery() {
		return buildQuery;
	}

	public void setBuildQuery(String buildQuery) {
		this.buildQuery = buildQuery;
	}
	

	public QueryBuilder() {
		super();
	}

	public QueryBuilder(String buildQuery) {
		super();
		this.buildQuery = buildQuery;
	}

	@Override
	public String toString() {
		return "QueryBuilder [buildQuery=" + buildQuery + "]";
	}
	
	

}
