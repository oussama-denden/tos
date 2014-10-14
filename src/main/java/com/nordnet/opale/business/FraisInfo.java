package com.nordnet.opale.business;

import com.nordnet.opale.enums.TypeFrais;

/**
 * Cette classe regroupe les informations qui definissent un {@link Frais}
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class FraisInfo {

	/**
	 * le reference
	 */
	private String reference;

	/**
	 * le label
	 */
	private String label;

	/**
	 * le montant
	 */
	private Double montant;

	/**
	 * le type de frais
	 */
	private TypeFrais type;

	/**
	 * constructeur par defaut
	 */
	public FraisInfo() {

	}

	/* Getters and Setters */

	/**
	 * get the reference
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * set the reference
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * get the label
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * set the label
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * get the montant
	 * 
	 * @return {@link #montant}
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * set the montant
	 * 
	 * @param montant
	 *            the new {@link #montant}
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * get the type
	 * 
	 * @return {@link TypeFrais}
	 */
	public TypeFrais getType() {
		return type;
	}

	/**
	 * set the type
	 * 
	 * @param type
	 *            the new {@link TypeFrais}
	 */
	public void setType(TypeFrais type) {
		this.type = type;
	}

}
