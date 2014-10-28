package com.nordnet.opale.business;

import com.nordnet.opale.domain.commande.Commande;

/**
 * classe qui represente la cause de l'erreur de validation du {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
public class ReasonCommande {

	/**
	 * source de l'erreur.
	 */
	private String source;

	/**
	 * code erreur.
	 */
	private String error;

	/**
	 * message d'erreur.
	 */
	private String errorMessage;

	/**
	 * constructeur par defaut.
	 */
	public ReasonCommande() {
	}

	/**
	 * 
	 * @param source
	 *            {@link #source}.
	 * @param error
	 *            {@link #error}
	 * @param errorMessage
	 *            {@link #errorMessage}.
	 */
	public ReasonCommande(String source, String error, String errorMessage) {
		super();
		this.source = source;
		this.error = error;
		this.errorMessage = errorMessage;
	}

	/**
	 * 
	 * @return {@link #source}.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 
	 * @param source
	 *            {@link #source}.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 
	 * @return {@link #error}
	 */
	public String getError() {
		return error;
	}

	/**
	 * 
	 * @param error
	 *            {@link #error}.
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * 
	 * @return {@link #errorMessage}.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 
	 * @param errorMessage
	 *            {@link #errorMessage}.
	 * 
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
