package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Infos lignes pour {@link InfosBonCommande}.
 * 
 * @author Oussama Denden
 * 
 */
public class InfosLignePourBonCommande {

	/**
	 * Label de ligne.
	 */
	private String label;

	/**
	 * Reference du contrat.
	 */
	private String referenceContrat;

	/**
	 * Pour l'antivirus le quantite est toujours 1.
	 */
	private Integer quantite = 1;

	/**
	 * Prix HT.
	 */
	private Double prixHT;

	/**
	 * Prix TTC.
	 */
	private Double prixTTC;

	/**
	 * {@link InfosReductionPourBonCommande}.
	 */
	private List<InfosReductionPourBonCommande> reductions = new ArrayList<InfosReductionPourBonCommande>();

	/**
	 * constructeur par defaut.
	 */
	public InfosLignePourBonCommande() {

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
	 * @return {@link #referenceContrat}.
	 */
	public String getReferenceContrat() {
		return referenceContrat;
	}

	/**
	 * @param referenceContrat
	 *            {@link #referenceContrat}.
	 */
	public void setReferenceContrat(String referenceContrat) {
		this.referenceContrat = referenceContrat;
	}

	/**
	 * @return {@link #quantite}.
	 */
	public Integer getQuantite() {
		return quantite;
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

	/**
	 * @return {@link #reductions}.
	 */
	public List<InfosReductionPourBonCommande> getReductions() {
		return reductions;
	}

	/**
	 * @param reductions
	 *            {@link #reductions}.
	 */
	public void setReductions(List<InfosReductionPourBonCommande> reductions) {
		this.reductions = reductions;
	}

}