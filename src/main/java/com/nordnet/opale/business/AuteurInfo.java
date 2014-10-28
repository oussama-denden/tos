package com.nordnet.opale.business;

/**
 * Contient les info de l auteur.
 * 
 * @author anisselmane.
 * 
 */
public class AuteurInfo {

	/**
	 * L auteur {@link Auteur}.
	 */
	private Auteur auteur;

	/**
	 * constructeur par defaut.
	 */
	public AuteurInfo() {

	}

	/**
	 * 
	 * @return {@link #auteur}.
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link #auteur}.
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}


}
