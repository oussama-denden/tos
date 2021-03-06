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
	 * code auteur.
	 */
	private String codePartenaire;

	/**
	 * la liste de ligne de la commande.
	 */
	private List<CommandeLigneInfo> lignes;

	/**
	 * un flag pour indiquer si la commande est annule ou pas.
	 */
	private boolean isAnnule;

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
	 * the code partenaire.
	 * 
	 * @return {@link #codePartenaire}
	 */
	public String getCodePartenaire() {
		return codePartenaire;
	}

	/**
	 * set the code partenaire.
	 * 
	 * @param codePartenaire
	 *            the new {@link #codePartenaire}
	 */
	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
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

	/**
	 * 
	 * @return {@link #isAnnule}
	 */
	public boolean isAnnule() {
		return isAnnule;
	}

	/**
	 * 
	 * @param isAnnule
	 *            {@link #isAnnule}.
	 */
	public void setAnnule(boolean isAnnule) {
		this.isAnnule = isAnnule;
	}

}
