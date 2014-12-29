package com.nordnet.opale.finder.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Cette classe regroupe les informations qui definissent un {@link Frais}.
 * 
 * @author anisselmane.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Frais {

	/**
	 * le reference.
	 */
	private String reference;

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
	private String type;

	/**
	 * constructeur par defaut.
	 */
	public Frais() {

	}

	/* Getters and Setters */

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
	 * @return {@link #montant}
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * 
	 * @param montant
	 *            the new {@link #montant}
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * 
	 * @return {@link TypeFrais}
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            the new {@link TypeFrais}
	 */
	public void setType(String type) {
		this.type = type;
	}

}
