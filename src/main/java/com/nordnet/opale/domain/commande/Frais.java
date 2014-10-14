package com.nordnet.opale.domain.commande;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.nordnet.opale.business.FraisInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.enums.TypeFrais;

/**
 * Classe represente les frais dans une {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "frais")
@Entity
public class Frais {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du tarif.
	 */
	@NotNull
	private String reference;

	/**
	 * label du frais.
	 */
	private String label;

	/**
	 * {@link TypeFrais}.
	 */
	@Enumerated(EnumType.STRING)
	private TypeFrais typeFrais;

	/**
	 * montant du frais.
	 */
	private Double montant;

	/**
	 * politique.
	 */
	private String politique;

	/**
	 * politique index.
	 */
	private String politiqueIndex;

	/**
	 * constructeur par defaut.
	 */
	public Frais() {
	}

	/**
	 * creation d'un frais a partir de la trame du catalogue.
	 * 
	 * @param refFrais
	 *            reference du frais.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public Frais(String refFrais, TrameCatalogue trameCatalogue) {
		com.nordnet.opale.business.catalogue.Frais frais = trameCatalogue.getFraisMap().get(refFrais);
		this.reference = frais.getReference();
		this.label = frais.getLabel();
		this.typeFrais = frais.getTypeFrais();
		this.montant = frais.getMontant();
		// this.politique
		// this.politiqueIndex
	}

	/**
	 * 
	 * @return {@link #id}.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            {@link #id}.
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return {@link #politique}.
	 */
	public String getPolitique() {
		return politique;
	}

	/**
	 * 
	 * @param politique
	 *            {@link #politique}.
	 */
	public void setPolitique(String politique) {
		this.politique = politique;
	}

	/**
	 * 
	 * @return {@link #politiqueIndex}.
	 */
	public String getPolitiqueIndex() {
		return politiqueIndex;
	}

	/**
	 * 
	 * @param politiqueIndex
	 *            {@link #politiqueIndex}.
	 */
	public void setPolitiqueIndex(String politiqueIndex) {
		this.politiqueIndex = politiqueIndex;
	}

	/**
	 * recuperer le frais business partir du frais doamin
	 * 
	 * @return {@link FraisInfo}
	 */
	public FraisInfo tofraisInfo() {
		FraisInfo fraisInfo = new FraisInfo();
		fraisInfo.setReference(reference);
		fraisInfo.setLabel(label);
		fraisInfo.setMontant(montant);
		fraisInfo.setType(typeFrais);
		return fraisInfo;
	}

}
