package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;

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
	 * reference de l'offre.
	 */
	private String referenceOffre;

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
	 * difference entre cout TTC et cout HT.
	 */
	private Double montantTVA;

	/**
	 * difference entre cout TTC et cout HT.
	 */
	private Double montantTVAReduit;

	/**
	 * Taux TVA.
	 */
	private Double tauxTVA;

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
	 * @return {@link #referenceOffre}.
	 */
	public String getReferenceOffre() {
		return referenceOffre;
	}

	/**
	 * @param referenceOffre
	 *            {@link #referenceOffre}.
	 */
	public void setReferenceOffre(String referenceOffre) {
		this.referenceOffre = referenceOffre;
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

	/**
	 * @return {@link #montantTVA}.
	 */
	public Double getMontantTVA() {
		return montantTVA;
	}

	/**
	 * @param montantTVA
	 *            {@link #montantTVA}.
	 */
	public void setMontantTVA(Double montantTVA) {
		this.montantTVA = Utils.round(montantTVA, Constants.DEUX);
	}

	/**
	 * @return {@link #montantTVAReduit}.
	 */
	public Double getMontantTVAReduit() {
		return montantTVAReduit;
	}

	/**
	 * @param montantTVAReduit
	 *            {@link #montantTVAReduit}.
	 */
	public void setMontantTVAReduit(Double montantTVAReduit) {
		this.montantTVAReduit = Utils.round(montantTVAReduit, Constants.DEUX);
	}

	/**
	 * @return {@link #tauxTVA}.
	 */
	public Double getTauxTVA() {
		return tauxTVA;
	}

	/**
	 * @param tauxTVA
	 *            {@link #tauxTVA}.
	 */
	public void setTauxTVA(Double tauxTVA) {
		this.tauxTVA = tauxTVA;
	}

}