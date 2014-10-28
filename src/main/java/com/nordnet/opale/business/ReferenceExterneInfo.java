package com.nordnet.opale.business;

/**
 * cette classe rassemble les informations necessaires pour attriuber un
 * reference externe a un draft.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ReferenceExterneInfo {

	/**
	 * l'auteur qui invoke l'action.
	 */
	private Auteur auteur;

	/**
	 * la reference externe du draft.
	 */
	private String referenceExterne;

	/**
	 * constructeur par defaut.
	 */
	public ReferenceExterneInfo() {

	}

	/* Getters and Setters */

	/**
	 * 
	 * @return {@link Auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link Auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * the reference externe.
	 * 
	 * @return {@link #referenceExterne}
	 */
	public String getReferenceExterne() {
		return referenceExterne;
	}

	/**
	 * set the reference externe.
	 * 
	 * @param referenceExterne
	 *            the new {@link #referenceExterne}
	 */
	public void setReferenceExterne(String referenceExterne) {
		this.referenceExterne = referenceExterne;
	}

}
