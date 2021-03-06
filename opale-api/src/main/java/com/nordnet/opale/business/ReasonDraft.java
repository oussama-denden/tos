package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.domain.draft.Draft;

/**
 * classe qui represente la cause de l'erreur de validation du {@link Draft}.
 * 
 * @author akram-moncer
 * 
 */
public class ReasonDraft {

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
	 * les valeur des champs non valide.
	 */
	private List<String> values = new ArrayList<>();

	/**
	 * constructeur par defaut.
	 */
	public ReasonDraft() {
	}

	/**
	 * 
	 * @param source
	 *            {@link #source}.
	 * @param error
	 *            {@link #error}
	 * @param errorMessage
	 *            {@link #errorMessage}.
	 * @param values
	 *            {@link #values}.
	 */
	public ReasonDraft(String source, String error, String errorMessage, List<String> values) {
		super();
		this.source = source;
		this.error = error;
		this.errorMessage = errorMessage;
		this.values = values;
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

	/**
	 * 
	 * @return {@link #values}.
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * 
	 * @param values
	 *            {@link #values}.
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

}
