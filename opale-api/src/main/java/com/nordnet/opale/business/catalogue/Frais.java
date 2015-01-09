package com.nordnet.opale.business.catalogue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.TypeFraisDeserializer;
import com.nordnet.opale.enums.TypeFrais;

/**
 * represente les frais associe a une offre.
 * 
 * @author akram-moncer
 * 
 */
public class Frais {

	/**
	 * id frais.
	 */
	@JsonProperty("id_frais")
	private String idFrais;

	/**
	 * label du frais.
	 */
	private String label;

	/**
	 * montant du frais.
	 */
	private Double montant;

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
	 * changer le frais du catalogue au frais domaine.
	 * 
	 * @return {@link com.nordnet.opale.domain.commande.Frais}
	 */
	public com.nordnet.opale.domain.commande.Frais tofraisDomain() {
		com.nordnet.opale.domain.commande.Frais frais = new com.nordnet.opale.domain.commande.Frais();
		frais.setLabel(label);
		frais.setReference(idFrais);
		frais.setMontant(montant);
		frais.setTypeFrais(typeFrais);
		return frais;
	}

}
