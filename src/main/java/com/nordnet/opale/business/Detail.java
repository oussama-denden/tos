package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.domain.DraftLigneDetail;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.ModePaiementDeserializer;

/**
 * contient les details d'une offre.
 * 
 * @author akram-moncer
 * 
 */
public class Detail {

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
	 * configuration json.
	 */
	private String configurationJson;

	/**
	 * constructeur par defaut.
	 */
	public Detail() {
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
	 * @return {@link #configurationJson}.
	 */
	public String getConfigurationJson() {
		return configurationJson;
	}

	/**
	 * 
	 * @param configurationJson
	 *            {@link #configurationJson}.
	 */
	public void setConfigurationJson(String configurationJson) {
		this.configurationJson = configurationJson;
	}

	/**
	 * Convertir un {@link Detail} en {@link DraftLigneDetail}.
	 * 
	 * @return {@link DraftLigneDetail}.
	 */
	public DraftLigneDetail toDraftLineDetail() {
		DraftLigneDetail draftLigneDetail = new DraftLigneDetail();
		draftLigneDetail.setReference(reference);
		draftLigneDetail.setReferenceTarif(referenceTarif);
		draftLigneDetail.setModePaiement(modePaiement);
		draftLigneDetail.setConfigurationJson(configurationJson);

		return draftLigneDetail;
	}

}
