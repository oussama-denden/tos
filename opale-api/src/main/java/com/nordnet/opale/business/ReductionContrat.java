package com.nordnet.opale.business;

import java.util.Date;

import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.enums.TypeValeur;

/**
 * objet business qui represente la reduction a envoyer vers topaze.
 * 
 * @author akram-moncer
 * 
 */
public class ReductionContrat {

	/**
	 * reference reduction.
	 */
	private String referenceReduction;

	/**
	 * titre de la reduction.
	 */
	private String titre;

	/**
	 * date debut de la reduction.
	 */
	private Date dateDebut;

	/**
	 * date fin de la reduction.
	 */
	private Date dateFin;

	/**
	 * nombre maximal d'utilisation de la reduction.
	 */
	private Integer nbUtilisationMax;

	/**
	 * valeur de la reduction.
	 */
	private Double valeur;

	/**
	 * type de {@link #valeur}.
	 */
	private TypeValeur typeValeur;

	/**
	 * {@link TypeFrais}.
	 */
	private TypeFrais typeFrais;

	/**
	 * {@link TypeReduction}.
	 */
	private TypeReduction typeReduction;

	/**
	 * constructeur par defaut.
	 */
	public ReductionContrat() {
	}

	/**
	 * creation d'une reduction pour le contrat a partir d'un {@link Reduction}.
	 * 
	 * @param reduction
	 *            {@link Reduction}.
	 */
	public ReductionContrat(Reduction reduction) {
		this.titre = reduction.getLabel();
		this.dateDebut = reduction.getDateDebut();
		this.dateFin = reduction.getDateFin();
		this.nbUtilisationMax = reduction.getNbUtilisationMax();
		this.valeur = reduction.getValeur();
		this.typeValeur = reduction.getTypeValeur();
		this.referenceReduction = reduction.getReferenceReduction();
	}

	@Override
	public String toString() {
		return "ReductionContrat [titre=" + titre + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", nbUtilisationMax=" + nbUtilisationMax + ", valeur=" + valeur + ", typeValeur=" + typeValeur
				+ ", typeFrais=" + typeFrais + ", typeReduction=" + typeReduction + "]";
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
	 * @return {@link #titre}.
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * 
	 * @param titre
	 *            {@link #titre}.
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * 
	 * @return {@link #dateDebut}.
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * 
	 * @param dateDebut
	 *            {@link #dateDebut}.
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * 
	 * @return {@link #dateFin}.
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * 
	 * @param dateFin
	 *            {@link #dateFin}.
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
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
	 *            {@link #nbUtilisationMax}.
	 */
	public void setNbUtilisationMax(Integer nbUtilisationMax) {
		this.nbUtilisationMax = nbUtilisationMax;
	}

	/**
	 * 
	 * @return {@link #valeur}.
	 */
	public Double getValeur() {
		return valeur;
	}

	/**
	 * 
	 * @param valeur
	 *            {@link #valeur}.
	 */
	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

	/**
	 * 
	 * @return {@link #typeValeur}.
	 */
	public TypeValeur getTypeValeur() {
		return typeValeur;
	}

	/**
	 * 
	 * @param typeValeur
	 *            {@link #typeValeur}.
	 */
	public void setTypeValeur(TypeValeur typeValeur) {
		this.typeValeur = typeValeur;
	}

	/**
	 * 
	 * @return {@link TypeFrais}.
	 */
	public TypeFrais getTypeFrais() {
		return typeFrais;
	}

	/**
	 * 
	 * @param typeFrais
	 *            {@link TypeFrais}.
	 */
	public void setTypeFrais(TypeFrais typeFrais) {
		this.typeFrais = typeFrais;
	}

	/**
	 * 
	 * @return {@link TypeReduction}.
	 */
	public TypeReduction getTypeReduction() {
		return typeReduction;
	}

	/**
	 * 
	 * @param typeReduction
	 *            {@link TypeReduction}.
	 */
	public void setTypeReduction(TypeReduction typeReduction) {
		this.typeReduction = typeReduction;
	}

}
