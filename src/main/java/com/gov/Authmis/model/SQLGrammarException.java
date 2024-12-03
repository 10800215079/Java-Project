package com.gov.Authmis.model;

public class SQLGrammarException  extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4674229356462407286L;

	public SQLGrammarException(String massage) {
        super(massage);
    }
}