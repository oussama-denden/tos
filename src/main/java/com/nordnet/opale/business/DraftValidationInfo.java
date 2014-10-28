package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.domain.draft.Draft;

/**
 * contient les resultats de la validation du {@link Draft} avec la {@link CatalogueTrame}.
 * 
 * @author akram-moncer
 * 
 */
public class DraftValidationInfo {

	/**
	 * liste des cause des erreur lors de la validation d'un {@link Draft}.
	 */
	private List<ReasonDraft> reasons = new ArrayList<ReasonDraft>();

	/**
	 * constructeur par default.
	 */
	public DraftValidationInfo() {
	}

	/**
	 * 
	 * @param reasons
	 *            {@link #reasons}.
	 */
	public DraftValidationInfo(List<ReasonDraft> reasons) {
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
	public List<ReasonDraft> getReasons() {
		return reasons;
	}

	/**
	 * 
	 * @param reasons
	 *            {@link #reasons}.
	 */
	public void setReasons(List<ReasonDraft> reasons) {
		this.reasons = reasons;
	}

	/**
	 * Ajouter une {@link ReasonDraft} de non validation.
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
		ReasonDraft reason = new ReasonDraft(source, error, errorMessage, values);
		reasons.add(reason);
	}

}
