package com.nordnet.opale.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nordnet.opale.enums.TypeFrais;

/**
 * Cette classe regroupe les informations qui definissent un {@link Frais}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class FraisInfo {

	/**
	 * id frais.
	 */
	@JsonProperty("id_frais")
	private String idFrais;

	/**
	 * le label.
	 */
	private String label;

	/**
	 * le montant.
	 */
	private Double montant;

	/**
	 * le type de frais.
	 */
	private TypeFrais typeFrais;

	/**
	 * constructeur par defaut.
	 */
	public FraisInfo() {

	}

	/* Getters and Setters */

	/**
	 * 
	 * @return {@link #idFrais}.
	 */
	public String getIdFrais() {
		return idFrais;
	}

	/**
	 * 
	 * @param idFrais
	 *            {@link #idFrais}.
	 */
	public void setIdFrais(String idFrais) {
		this.idFrais = idFrais;
	}

	/**
	 * get the label.
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * set the label.
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * get the montant.
	 * 
	 * @return {@link #montant}
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * set the montant.
	 * 
	 * @param montant
	 *            the new {@link #montant}
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * 
	 * @return {@link #typeFrais}.
	 */
	public TypeFrais getTypeFrais() {
		return typeFrais;
	}

	/**
	 * 
	 * @param typeFrais
	 *            {@link #typeFrais}.
	 */
	public void setTypeFrais(TypeFrais typeFrais) {
		this.typeFrais = typeFrais;
	}

}
