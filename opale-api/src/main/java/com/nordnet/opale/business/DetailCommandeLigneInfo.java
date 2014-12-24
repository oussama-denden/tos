package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.TypeProduitDeserializer;
import com.nordnet.opale.enums.TypeProduit;

/**
 * Cette classe regroupe les informations qui definissent un {@link DetailCommandeLigneInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class DetailCommandeLigneInfo {

	/**
	 * le reference.
	 */
	private String reference;

	/**
	 * label.
	 */
	private String label;

	/**
	 * {@link TarifInfo}.
	 */
	private TarifInfo tarif;

	/**
	 * {@link TypeProduit}.
	 */
	@JsonDeserialize(using = TypeProduitDeserializer.class)
	private TypeProduit typeProduit;

	/**
	 * constructeur par defaut.
	 */
	public DetailCommandeLigneInfo() {

	}

	/**
	 * get the reference.
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * set the reference.
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
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
	 * 
	 * @return {@link #tarif}.
	 */
	public TarifInfo getTarif() {
		return tarif;
	}

	/**
	 * 
	 * @param tarif
	 *            {@link #tarif}.
	 */
	public void setTarif(TarifInfo tarif) {
		this.tarif = tarif;
	}

	/**
	 * 
	 * @return {@link #typeProduit}.
	 */
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

	/**
	 * 
	 * @param typeProduit
	 *            {@link TypeProduit}.
	 */
	public void setTypeProduit(TypeProduit typeProduit) {
		this.typeProduit = typeProduit;
	}

}
