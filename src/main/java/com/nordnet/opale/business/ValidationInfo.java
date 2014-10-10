package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.domain.Draft;

/**
 * contient les resultats de la validation du {@link Draft} avec la {@link CatalogueTrame}.
 * 
 * @author akram-moncer
 * 
 */
public class ValidationInfo {

	/**
	 * liste des cause des erreur lors de la validation d'un {@link Draft}.
	 */
	private List<Reason> reasons = new ArrayList<Reason>();

	/**
	 * constructeur par default.
	 */
	public ValidationInfo() {
	}

	/**
	 * 
	 * @param reasons
	 *            {@link #reasons}.
	 */
	public ValidationInfo(List<Reason> reasons) {
		super();
		this.reasons = reasons;
	}

	/**
	 * 
	 * @return {@link #valide}.
	 */
	public boolean isValide() {
		if (reasons.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return {@link #reasons}.
	 */
	public List<Reason> getReasons() {
		return reasons;
	}

	/**
	 * 
	 * @param reasons
	 *            {@link #reasons}.
	 */
	public void setReasons(List<Reason> reasons) {
		this.reasons = reasons;
	}

	/**
	 * Ajouter une {@link Reason} de non validation.
	 * 
	 * @param source
	 *            source de l'erreur.
	 * @param error
	 *            code erreur.
	 * @param errorMessage
	 *            message d'erreur.
	 * @param values
	 *            {@link #values}.
	 */
	public void addReason(String source, String error, String errorMessage, List<String> values) {
		Reason reason = new Reason(source, error, errorMessage, values);
		reasons.add(reason);
	}

}
