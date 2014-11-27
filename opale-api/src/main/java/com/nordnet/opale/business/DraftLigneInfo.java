package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.GesteDeserializer;
import com.nordnet.opale.enums.Geste;

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
	 * Le geste effectue.
	 */
	@JsonDeserialize(using = GesteDeserializer.class)
	private Geste geste;

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
	 * @return {@link #geste}
	 */
	public Geste getGeste() {
		return geste;
	}

	/**
	 * 
	 * @param geste
	 *            {@link #geste}
	 */
	public void setGeste(Geste geste) {
		this.geste = geste;
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
