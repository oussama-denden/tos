package com.nordnet.opale.business;

import java.util.List;

/**
 * Contient les info de l auteur.
 * 
 * @author anisselmane.
 * 
 */
public class DraftInfo {

	/**
	 * L auteur {@link Auteur}.
	 */
	private Auteur auteur;

	/**
	 * 
	 */
	private List<DraftLigneInfo> lignes;

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
	public DraftInfo() {

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

	/**
	 * 
	 * @return {@link #lignes}
	 */
	public List<DraftLigneInfo> getLignes() {
		return lignes;
	}

	/**
	 * 
	 * @param lignes
	 *            {@link #lignes}
	 */
	public void setLignes(List<DraftLigneInfo> lignes) {
		this.lignes = lignes;
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
