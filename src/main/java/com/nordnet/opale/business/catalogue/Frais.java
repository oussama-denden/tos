package com.nordnet.opale.business.catalogue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.enums.TypeTVA;
import com.nordnet.opale.enums.deserializer.TypeFraisDeserializer;
import com.nordnet.opale.enums.deserializer.TypeTVADeserializer;

/**
 * represente les frais associe a une offre.
 * 
 * @author akram-moncer
 * 
 */
public class Frais {

	/**
	 * reference frais.
	 */
	private String reference;

	/**
	 * label du frais.
	 */
	private String label;

	/**
	 * montant du frais.
	 */
	private Double montant;

	/**
	 * {@link TypeTVA}.
	 */
	@JsonDeserialize(using = TypeTVADeserializer.class)
	private TypeTVA typeTVA;

	/**
	 * {@link TypeFrais}.
	 */
	@JsonDeserialize(using = TypeFraisDeserializer.class)
	private TypeFrais typeFrais;

	/**
	 * constructeur par defaut.
	 */
	public Frais() {
	}

	/**
	 * 
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #label}.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            {@link #label}.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * 
	 * @param montant
	 *            {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * 
	 * @return {@link TypeTVA}.
	 */
	public TypeTVA getTypeTVA() {
		return typeTVA;
	}

	/**
	 * 
	 * @param typeTVA
	 *            {@link TypeTVA}.
	 */
	public void setTypeTVA(TypeTVA typeTVA) {
		this.typeTVA = typeTVA;
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

}
