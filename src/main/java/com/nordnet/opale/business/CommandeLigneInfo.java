package com.nordnet.opale.business;

import com.nordnet.opale.business.catalogue.OffreCatalogue;

/**
 * Cette classe regroupe les informations qui definissent un {@link CommandeLigneInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CommandeLigneInfo {

	/**
	 * numero de la ligne
	 */
	private int numero;

	/**
	 * l'auteur
	 */
	private Auteur auteur;

	/**
	 * l'offre
	 */
	private OffreCatalogueInfo offre;

	/**
	 * constructeur par defaut
	 */
	public CommandeLigneInfo() {

	}

	/* Getters and Setters */

	/**
	 * get the numero
	 * 
	 * @return {@link #numero}
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * set the numero
	 * 
	 * @param numero
	 *            the new {@link #numero}
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * get the auteur
	 * 
	 * @return {@link Auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * set the auteur
	 * 
	 * @param auteur
	 *            the new {@link Auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * get the offre
	 * 
	 * @return {@link OffreCatalogue}
	 */
	public OffreCatalogueInfo getOffre() {
		return offre;
	}

	/**
	 * set the offre
	 * 
	 * @param offre
	 *            the new {@link OffreCatalogue}
	 */
	public void setOffre(OffreCatalogueInfo offre) {
		this.offre = offre;
	}

}
