package com.nordnet.opale.business;

/**
 * Cette classe regroupe les informations qui definissent un {@link ClientInfo}.
 * 
 * @author anisselmane.
 * 
 */
public class ClientInfo {

	/**
	 * L auteur.
	 */
	private Auteur auteur;

	/**
	 * Le client souscripteur.
	 */
	private Client souscripteur;

	/**
	 * Le client pour livraison.
	 */
	private Client livraison;

	/**
	 * Le client pour la facturation.
	 */
	private Client facturation;

	/**
	 * constructeur par defaut.
	 */
	public ClientInfo() {

	}

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
	 * 
	 * @return {@link Client}
	 */
	public Client getSouscripteur() {
		return souscripteur;
	}

	/**
	 * 
	 * @param souscripteur
	 *            {@link Client}
	 */
	public void setSouscripteur(Client souscripteur) {
		this.souscripteur = souscripteur;
	}

	/**
	 * 
	 * @return {@link Client}
	 */
	public Client getLivraison() {
		return livraison;
	}

	/**
	 * 
	 * @param livraison
	 *            {@link Client}
	 */
	public void setLivraison(Client livraison) {
		this.livraison = livraison;
	}

	/**
	 * 
	 * @return {@link Client}
	 */
	public Client getFacturation() {
		return facturation;
	}

	/**
	 * 
	 * @param facturation
	 *            {@link Client}
	 */
	public void setFacturation(Client facturation) {
		this.facturation = facturation;
	}

}
