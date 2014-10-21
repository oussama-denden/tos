package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.deserializer.ModePaiementDeserializer;

/**
 * classe pour transferer les info de paiement.
 * 
 * @author akram-moncer
 * 
 */
public class PaiementInfo {

	/**
	 * {@link ModePaiement}.
	 */
	@JsonDeserialize(using = ModePaiementDeserializer.class)
	private ModePaiement modePaiement;

	/**
	 * montant de paiement.
	 */
	private Double montant;

	/**
	 * info paiement.
	 */
	private String infoPaiement;

	/**
	 * l'auteur.
	 */
	private Auteur auteur;

	/**
	 * constructeur par defaut.
	 */
	public PaiementInfo() {
	}

	/**
	 * 
	 * @return {@link ModePaiement}.
	 */
	public ModePaiement getModePaiement() {
		return modePaiement;
	}

	/**
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 */
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}

	/**
	 * 
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * 
	 * @param montant
	 *            {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * 
	 * @return {@link #infoPaiement}.
	 */
	public String getInfoPaiement() {
		return infoPaiement;
	}

	/**
	 * 
	 * @param infoPaiement
	 *            {@link #infoPaiement}.
	 */
	public void setInfoPaiement(String infoPaiement) {
		this.infoPaiement = infoPaiement;
	}

	/**
	 * get the auteur.
	 * 
	 * @return {@link AuteurInfo}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * set the auteur.
	 * 
	 * @param auteur
	 *            the new {@link AuteurInfo}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}
}
