package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.ModeFacturationDeserializer;
import com.nordnet.opale.deserializer.ModePaiementDeserializer;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;

/**
 * classe business contien les information necassaire pour la creation d'une ligne draft.
 * 
 * @author akram-moncer
 * 
 */
public class Offre {

	/**
	 * reference de l'offre.
	 */
	private String referenceOffre;

	/**
	 * reference tarif.
	 */
	private String referenceTarif;

	/**
	 * {@link ModePaiement}.
	 */
	@JsonDeserialize(using = ModePaiementDeserializer.class)
	private ModePaiement modePaiement;

	/**
	 * {@link ModeFacturation}.
	 */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * liste des {@link Detail} associe a une offre.
	 */
	List<Detail> details = new ArrayList<Detail>();

	/**
	 * constructeur par defaut.
	 */
	public Offre() {
	}

	@Override
	public String toString() {
		return "Offre [referenceOffre=" + referenceOffre + ", referenceTarif=" + referenceTarif + ", modePaiement="
				+ modePaiement + ", modeFacturation=" + modeFacturation + ", details=" + details + "]";
	}

	/**
	 * 
	 * @return {@link #referenceOffre}.
	 */
	public String getReferenceOffre() {
		return referenceOffre;
	}

	/**
	 * 
	 * @param referenceOffre
	 *            {@link #referenceOffre}.
	 */
	public void setReferenceOffre(String referenceOffre) {
		this.referenceOffre = referenceOffre;
	}

	/**
	 * 
	 * @return {@link #referenceTarif}.
	 */
	public String getReferenceTarif() {
		return referenceTarif;
	}

	/**
	 * 
	 * @param referenceTarif
	 *            {@link #referenceTarif}.
	 */
	public void setReferenceTarif(String referenceTarif) {
		this.referenceTarif = referenceTarif;
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
	 * @return {@link ModeFacturation}.
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * 
	 * @return {@link #details}.
	 */
	public List<Detail> getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details
	 *            {@link #details}.
	 */
	public void setDetails(List<Detail> details) {
		this.details = details;
	}

}
