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
	 * 
	 */
	private ClientInfo client;

	/**
	 * L utilisateur.
	 */
	private String user;

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

	/***
	 * 
	 * @return {@link #client}
	 */
	public ClientInfo getClient() {
		return client;
	}

	/**
	 * 
	 * @param client
	 *            {@link #client}
	 */
	public void setClient(ClientInfo client) {
		this.client = client;
	}

	/**
	 * 
	 * @return {@link #user}.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            {@link #user}.
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
