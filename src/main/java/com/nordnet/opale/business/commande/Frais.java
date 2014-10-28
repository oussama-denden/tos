package com.nordnet.opale.business.commande;

import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.util.Utils;

/**
 * Cette classe regroupe les informations qui definissent un {@link Frais}.
 * 
 * @author Denden-OUSSAMA
 * 
 */
public class Frais {

	/**
	 * titre de frais par exmemple: Frais de dossier.
	 */
	private String titre;

	/**
	 * montant de frais.
	 */
	private Double montant;

	/**
	 * Type de frais.
	 */
	private TypeFrais typeFrais;

	/**
	 * l'order d'application des frais de resiliation.
	 */
	private Integer ordre;

	/**
	 * le nombre de mois que le frais de resiliation reste valable ex: pour un {@link #nombreMois} egal 12, si une
	 * resiliation est effectue lors des 12 premier mois ce frais sera applique.
	 */
	private Integer nombreMois;

	/**
	 * constructeur par defaut.
	 */
	public Frais() {

	}

	/* Getters & Setters */

	@Override
	public String toString() {
		return "Frais [montant=" + montant + ", typeFrais=" + typeFrais + ", ordre=" + ordre + ", nombreMois="
				+ nombreMois + "]";
	}

	/**
	 * @return {@link #titre}.
	 */
	public String getTitre() {
		if (Utils.isStringNullOrEmpty(titre)) {
			switch (typeFrais) {
			case CREATION:
				return "Frais Creation";
			case RESILIATION:
				return "Frais Resiliation";
			}
		}
		return titre;
	}

	/**
	 * @param titre
	 *            {@link #titre}.
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Gets the montant.
	 * 
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * Sets the montant.
	 * 
	 * @param montant
	 *            the new montant {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * Gets the type frais.
	 * 
	 * @return {@link #typeFrais}.
	 */
	public TypeFrais getTypeFrais() {
		return typeFrais;
	}

	/**
	 * Sets the type frais.
	 * 
	 * @param typeFrais
	 *            the new type frais {@link #typeFrais}.
	 */
	public void setTypeFrais(TypeFrais typeFrais) {
		this.typeFrais = typeFrais;
	}

	/**
	 * 
	 * @return {@link #ordre}
	 */
	public Integer getOrdre() {
		return ordre;
	}

	/**
	 * @param ordre
	 *            {@link #ordre}.
	 */
	public void setOrdre(Integer ordre) {
		this.ordre = ordre;
	}

	/**
	 * 
	 * @return {@link #nombreMois}
	 */
	public Integer getNombreMois() {
		return nombreMois;
	}

	/**
	 * 
	 * @param nombreMois
	 *            {@link #nombreMois}
	 */
	public void setNombreMois(Integer nombreMois) {
		this.nombreMois = nombreMois;
	}

}
