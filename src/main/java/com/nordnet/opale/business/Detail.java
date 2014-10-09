package com.nordnet.opale.business;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.deserializer.ModePaiementDeserializer;
import com.nordnet.opale.util.Utils;

/**
 * contient les details d'une offre.
 * 
 * @author akram-moncer
 * 
 */
public class Detail {

	/**
	 * reference selection.
	 */
	private String referenceSelection;

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
	 * definit la relation de dependance entre les detail d'une offre.
	 */
	private String dependDe;

	/**
	 * constructeur par defaut.
	 */
	public Detail() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Detail rhs = (Detail) obj;
		return new EqualsBuilder().append(reference, rhs.reference).isEquals();
	}

	/**
	 * 
	 * @return {@link #referenceSelection}.
	 */
	public String getReferenceSelection() {
		return referenceSelection;
	}

	/**
	 * 
	 * @param referenceSelection
	 *            {@link #referenceSelection}.
	 */
	public void setReferenceSelection(String referenceSelection) {
		this.referenceSelection = referenceSelection;
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
	 * 
	 * @return {@link #dependDe}.
	 */
	public String getDependDe() {
		return dependDe;
	}

	/**
	 * 
	 * @param dependDe
	 *            {@link #dependDe}.
	 */
	public void setDependDe(String dependDe) {
		this.dependDe = dependDe;
	}

	/**
	 * Tester si un element est un parent.
	 * 
	 * @return true si l'element detail est un parent
	 */
	public boolean isParent() {
		if (Utils.isStringNullOrEmpty(dependDe)) {
			return true;
		}

		return false;
	}

}
