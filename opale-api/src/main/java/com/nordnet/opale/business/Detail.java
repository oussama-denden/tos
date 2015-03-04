package com.nordnet.opale.business;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
	 * reference choix.
	 */
	private String referenceChoix;

	/**
	 * reference tarif.
	 */
	private String referenceTarif;

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
		return new EqualsBuilder().append(referenceChoix, rhs.referenceChoix).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(referenceChoix).toHashCode();
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
	 * @return {@link #referenceChoix}.
	 */
	public String getReferenceChoix() {
		return referenceChoix;
	}

	/**
	 * 
	 * @param referenceChoix
	 *            {@link #referenceChoix}.
	 */
	public void setReferenceChoix(String referenceChoix) {
		this.referenceChoix = referenceChoix;
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
