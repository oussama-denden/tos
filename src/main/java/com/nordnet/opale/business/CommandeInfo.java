package com.nordnet.opale.business;

import java.util.List;

/**
 * Cette classe regroupe les informations qui definissent un {@link CommandeInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CommandeInfo {

	/**
	 * l'auteur.
	 */
	private Auteur auteur;

	/**
	 * la liste de ligne de la commande.
	 */
	private List<CommandeLigneInfo> lignes;

	/**
	 * constructeur par defaut.
	 */
	public CommandeInfo() {

	}

	/* Getters and Setters */

	/**
	 * get the auteur.
	 * 
	 * @return {@link Auteur}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * set the auteur.
	 * 
	 * @param auteur
	 *            the new {@link Auteur}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * get lignes.
	 * 
	 * @return {@link List<CommandeLigneInfo>}
	 */
	public List<CommandeLigneInfo> getLignes() {
		return lignes;
	}

	/**
	 * set the lignes.
	 * 
	 * @param lignes
	 *            the new {@link List<CommandeLigneInfo>}
	 */
	public void setLignes(List<CommandeLigneInfo> lignes) {
		this.lignes = lignes;
	}

}
