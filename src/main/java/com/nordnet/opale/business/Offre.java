package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.deserializer.ModeFacturationDeserializer;
import com.nordnet.opale.enums.deserializer.ModePaiementDeserializer;

/**
 * classe business contien les information necassaire pour la creation d'une ligne draft.
 * 
 * @author akram-moncer
 * 
 */
public class Offre {

	/**
	 * reference de la ligne dans le draft.
	 */
	private String reference;

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
		return "Offre [reference=" + reference + ", referenceTarif=" + referenceTarif + ", modePaiement="
				+ modePaiement + ", modeFacturation=" + modeFacturation + ", details=" + details + "]";
	}

	/**
	 * 
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
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
