package com.nordnet.opale.business.commande;


/**
 * Cette classe regroupe les informations qui definissent un {@link PolitiqueValidation}.
 * 
 * @author anisselmane.
 * 
 */
public class PolitiqueValidation {

	/**
	 * frais de création.
	 */
	private Boolean fraisCreation;

	/**
	 * Indiquer si le test 'isPackagerCreationPossible' doit etre effectuer ou non lors de l'envoi des services a
	 * packager.
	 */
	private Boolean checkIsPackagerCreationPossible;

	/**
	 * Instantiates a new politique validation.
	 */
	public PolitiqueValidation() {
		super();
	}

	/* Getters & Setters */

	/**
	 * Checks if is frais creation.
	 * 
	 * @return true, if is frais creation
	 */
	public Boolean isFraisCreation() {
		return fraisCreation;
	}

	/**
	 * Sets the frais creation.
	 * 
	 * @param fraisCreation
	 *            the new frais creation
	 */
	public void setFraisCreation(Boolean fraisCreation) {
		this.fraisCreation = fraisCreation;
	}

	/**
	 * 
	 * @return {@link #checkIsPackagerCreationPossible}.
	 */
	public Boolean getCheckIsPackagerCreationPossible() {
		return checkIsPackagerCreationPossible;
	}

	/**
	 * 
	 * @param checkIsPackagerCreationPossible
	 *            {@link #checkIsPackagerCreationPossible}.
	 */
	public void setCheckIsPackagerCreationPossible(Boolean checkIsPackagerCreationPossible) {
		this.checkIsPackagerCreationPossible = checkIsPackagerCreationPossible;
	}

}
