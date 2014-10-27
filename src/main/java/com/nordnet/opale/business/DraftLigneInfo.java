package com.nordnet.opale.business;

/**
 * classe business contient les info d'une ligne draft.
 * 
 * @author akram-moncer
 * 
 */
public class DraftLigneInfo {

	/**
	 * {@link Auteur}.
	 */
	private Auteur auteur;

	/**
	 * {@link Offre}.
	 */
	private Offre offre;

	/**
	 * constructeur par defaut.
	 */
	public DraftLigneInfo() {
	}

	/**
	 * 
	 * @return {@link Auteur}.
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link Auteur}.
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * 
	 * @return {@link Offre}.
	 */
	public Offre getOffre() {
		return offre;
	}

	/**
	 * 
	 * @param offre
	 *            {@link Offre}.
	 */
	public void setOffre(Offre offre) {
		this.offre = offre;
	}

}
