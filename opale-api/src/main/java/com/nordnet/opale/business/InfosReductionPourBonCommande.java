/**
 * 
 */
package com.nordnet.opale.business;

/**
 * Infos Reduction pour {@link InfosBonCommande}.
 * 
 * @author Oussama Denden
 * 
 */
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
		this.prixHT = prixHT;
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
		this.prixTTC = prixTTC;
	}

}