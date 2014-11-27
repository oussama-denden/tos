package com.nordnet.opale.business;

/**
 * Contient les informations de l auteur.
 * 
 * @author anisselmane.
 * 
 */
public class DeleteInfo {

	/**
	 * L utilisateur.
	 */
	private Auteur auteur;

	/**
	 * constructeur par defaut.
	 */
	public DeleteInfo() {

	}

	/**
	 * 
	 * @return {@link #auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link #auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

}
