package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.ModePaiementDeserializer;

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
	 * liste des {@link Detail} associe a une offre.
	 */
	List<Detail> details = new ArrayList<Detail>();

	/**
	 * constructeur par defaut.
	 */
	public Offre() {
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

	/**
	 * convertir une {@link Offre} en {@link DraftLigne}.
	 * 
	 * @return {@link DraftLigne}.
	 */
	public DraftLigne toDraftLigne() {
		DraftLigne draftLigne = new DraftLigne();
		draftLigne.setReference(reference);
		draftLigne.setReferenceTarif(referenceTarif);
		draftLigne.setModePaiement(modePaiement);
		for (Detail detail : details) {
			draftLigne.addDraftLigneDetail(detail.toDraftLineDetail());
		}

		return draftLigne;
	}

}
