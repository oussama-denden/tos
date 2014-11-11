package com.nordnet.opale.exception;

/**
 * La classe d exception pour opale.
 * 
 * @author anisselmane.
 * 
 */
public class OpaleException extends Exception {

	/**
	 * Serialization token.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Code d'erreur.
	 */
	private final String errorCode;

	/**
	 * constructeur avec message d'erreur et code d'erreur..
	 * 
	 * @param s
	 *            message d'erreur.
	 * @param errorCode
	 *            code d'erreur.
	 */
	public OpaleException(String s, String errorCode) {
		super(s);
		this.errorCode = errorCode;
	}

	/**
	 * constructeur avec message d'erreur et l'exception.
	 * 
	 * @param message
	 *            message de l'erreur
	 * @param cause
	 *            {@link Throwable}
	 */
	public OpaleException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = null;
	}

	/**
	 * @return code d'erreur.
	 */
	public String getErrorCode() {
		return errorCode;
	}

}
