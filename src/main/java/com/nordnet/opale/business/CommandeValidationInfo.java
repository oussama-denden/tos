package com.nordnet.opale.business;

import java.security.cert.CertPathValidatorException.Reason;
import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.domain.commande.Commande;

/**
 * contient les resultats de la validation du {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
public class CommandeValidationInfo {

	/**
	 * liste des cause des erreur lors de la validation d'un {@link Commande}.
	 */
	private List<ReasonCommande> reasons = new ArrayList<ReasonCommande>();

	/**
	 * constructeur par default.
	 */
	public CommandeValidationInfo() {
	}

	/**
	 * 
	 * @param reasons
	 *            {@link #reasons}.
	 */
	public CommandeValidationInfo(List<ReasonCommande> reasons) {
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
	public List<ReasonCommande> getReasons() {
		return reasons;
	}

	/**
	 * 
	 * @param reasons
	 *            {@link #reasons}.
	 */
	public void setReasons(List<ReasonCommande> reasons) {
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
	 */
	public void addReason(String source, String error, String errorMessage) {
		ReasonCommande reason = new ReasonCommande(source, error, errorMessage);
		reasons.add(reason);
	}

}
