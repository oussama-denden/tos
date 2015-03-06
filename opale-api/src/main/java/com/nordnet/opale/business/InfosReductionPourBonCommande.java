/**
 * 
 */
package com.nordnet.opale.business;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;

/**
 * Infos Reduction pour {@link InfosBonCommande}.
 * 
 * @author Oussama Denden
 * 
 */
@JsonIgnoreProperties({ "reference" })
public class InfosReductionPourBonCommande {

	/**
	 * Reference de reduction dans le catalogue.
	 */
	private String referenceCatalogue;

	/**
	 * Label du reduction.
	 */
	private String label;

	/**
	 * Montant HT.
	 */
	private Double prixHT;

	/**
	 * Montant TTC.
	 */
	private Double prixTTC;

	/**
	 * reference reduction.
	 */
	private String reference;

	/**
	 * constructeur par defaut.
	 */
	public InfosReductionPourBonCommande() {

	}

	/**
	 * @return {@link #referenceCatalogue}.
	 */
	public String getReferenceCatalogue() {
		return referenceCatalogue;
	}

	/**
	 * @param referenceCatalogue
	 *            {@link #referenceCatalogue}.
	 */
	public void setReferenceCatalogue(String referenceCatalogue) {
		this.referenceCatalogue = referenceCatalogue;
	}

	/**
	 * @return {@link #label}.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            {@link #label}.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return {@link #prixHT}.
	 */
	public Double getPrixHT() {
		return prixHT;
	}

	/**
	 * @param prixHT
	 *            {@link #prixHT}.
	 */
	public void setPrixHT(Double prixHT) {
		this.prixHT = Utils.round(prixHT, Constants.DEUX);
	}

	/**
	 * @return {@link #prixTTC}.
	 */
	public Double getPrixTTC() {
		return prixTTC;
	}

	/**
	 * @param prixTTC
	 *            {@link #prixTTC}.
	 */
	public void setPrixTTC(Double prixTTC) {
		this.prixTTC = Utils.round(prixTTC, Constants.DEUX);
	}

	/**
	 * 
	 * @return reference reduction
	 * 
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof InfosReductionPourBonCommande)) {
			return false;
		}
		InfosReductionPourBonCommande rhs = (InfosReductionPourBonCommande) obj;
		return new EqualsBuilder().append(reference, rhs.reference).isEquals();
	}

}