package com.nordnet.opale.draft.business;

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
	 * L utilisateur.
	 */
	private String user;

	/**
	 * constructeur par defaut.
	 */
	public AuteurInfo() {

	}

	public Auteur getAuteur() {
		return auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
