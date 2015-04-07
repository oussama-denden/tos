package com.nordnet.opale.finder.business;

import java.util.Date;

/**
 * Cette classe represente l'entite {@link Reduction}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class Reduction {

	/**
	 * reference du reduction.
	 */
	private String reference;

	/**
	 * reference pour les reduction regulieres.
	 */
	private String referenceReduction;

	/**
	 * reference draft.
	 */
	private String referenceDraft;

	/**
	 * reference ligne draft.
	 */
	private String referenceLigne;

	/**
	 * reference detail ligne draft.
	 */
	private String referenceLigneDetail;

	/**
	 * label du reduction.
	 */
	private String label;

	/**
	 * {@link ModePaiement}.
	 */
	private TypeValeur typeValeur;

	/**
	 * valeur de la reduction.
	 */
	private Double valeur;

	/**
	 * nombre d'utilisationmax.
	 */
	private Integer nbUtilisationMax;

	/**
	 * date de debut.
	 */
	private Date dateDebut;

	/**
	 * date de fin.
	 */
	private Date dateFin;

	/**
	 * reference du fras.
	 */
	private String referenceFrais;

	/**
	 * reference du tarif.
	 */
	private String referenceTarif;

	/**
	 * constructeur par defaut.
	 */
	public Reduction() {
	}

	/**
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #referenceReduction}
	 */
	public String getReferenceReduction() {
		return referenceReduction;
	}

	/**
	 * 
	 * @param referenceReduction
	 *            {@link #referenceReduction}
	 */
	public void setReferenceReduction(String referenceReduction) {
		this.referenceReduction = referenceReduction;
	}

	/**
	 * 
	 * @return {@link #referenceDraft}
	 */
	public String getReferenceDraft() {
		return referenceDraft;
	}

	/**
	 * 
	 * @return {@link #referenceLigne}
	 */
	public String getReferenceLigne() {
		return referenceLigne;
	}

	/**
	 * 
	 * @param referenceLigne
	 *            the new {@link #referenceLigne}
	 */
	public void setReferenceLigne(String referenceLigne) {
		this.referenceLigne = referenceLigne;
	}

	/**
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @param referenceDraft
	 *            the new {@link #referenceDraft}
	 */
	public void setReferenceDraft(String referenceDraft) {
		this.referenceDraft = referenceDraft;
	}

	/**
	 * 
	 * @return {@link #typeValeur}
	 */
	public TypeValeur getTypeValeur() {
		return typeValeur;
	}

	/**
	 * 
	 * @param typeValeur
	 *            the new {@link #typeValeur}
	 */
	public void setTypeValeur(TypeValeur typeValeur) {
		this.typeValeur = typeValeur;
	}

	/**
	 * 
	 * @return {@link #valeur}
	 */
	public Double getValeur() {
		return valeur;
	}

	/**
	 * 
	 * @param valeur
	 *            the new {@link #valeur}
	 */
	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

	/**
	 * 
	 * @return {@link #nbUtilisationMax}.
	 */
	public Integer getNbUtilisationMax() {
		return nbUtilisationMax;
	}

	/**
	 * 
	 * @param nbUtilisationMax
	 *            the new {@link #nbUtilisationMax}
	 */
	public void setNbUtilisationMax(Integer nbUtilisationMax) {
		this.nbUtilisationMax = nbUtilisationMax;
	}

	/**
	 * 
	 * @return {@link #dateDebut}
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * 
	 * @param dateDebut
	 *            the new {@link #dateDebut}
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * 
	 * @return {@link #dateFin}
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * 
	 * @param dateFin
	 *            the new {@link #dateFin}
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * 
	 * @return {@link Reduction#referenceFrais}
	 */
	public String getReferenceFrais() {
		return referenceFrais;
	}

	/**
	 * @return {@link #referenceLigneDetail}
	 */
	public String getReferenceLigneDetail() {
		return referenceLigneDetail;
	}

	/**
	 * 
	 * @param referenceFrais
	 *            the new {@link #referenceFrais}
	 */
	public void setReferenceFrais(String referenceFrais) {
		this.referenceFrais = referenceFrais;
	}

	/**
	 * @param referenceLigneDetail
	 *            {@link #referenceLigneDetail}
	 */
	public void setReferenceLigneDetail(String referenceLigneDetail) {
		this.referenceLigneDetail = referenceLigneDetail;
	}

	/**
	 * @return {@link #referenceTarif}
	 */
	public String getReferenceTarif() {
		return referenceTarif;
	}

	/**
	 * 
	 * @param referenceTarif
	 *            the new {@link #referenceTarif}
	 */
	public void setReferenceTarif(String referenceTarif) {
		this.referenceTarif = referenceTarif;
	}

	/**
	 * vérifier si une reduction est sur le cout récurrent.
	 * 
	 * @return true si la reduction est comptante.
	 */
	public boolean isReductionRecurrente() {
		return (typeValeur.equals(TypeValeur.MOIS) || typeValeur.equals(TypeValeur.POURCENTAGE));
	}

	/**
	 * vérifer si une reduction est sur le cout comptant.
	 * 
	 * @return true si la reduction est comptante.
	 */
	public boolean isReductionComptant() {
		return (typeValeur.equals(TypeValeur.MONTANT) || typeValeur.equals(TypeValeur.POURCENTAGE));
	}

}
