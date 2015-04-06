package com.nordnet.opale.finder.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Optional;

/**
 * Cette classe regroupe les informations qui definissent un {@link Tarif}.
 * 
 * @author anisselmane.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Tarif {

	/**
	 * reference tarif.
	 */
	private String reference;

	/**
	 * le prix dans le tarif.
	 */
	private Double prix;

	/**
	 * la periodicite en nombre de mois.
	 */
	private Integer frequence;

	/**
	 * l'engagement en nombre de mois.
	 */
	private Integer engagement;

	/**
	 * duree de tarif.
	 */
	private Integer duree;

	/**
	 * Type TVA.
	 */
	private String typeTVA;

	/**
	 * la liste des reference des {@link Frais} associe au tarif.
	 */
	private List<Frais> frais;

	/**
	 * constructeur par defaut.
	 */
	public Tarif() {
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
	 * @return {@link #prix}.
	 */
	public Double getPrix() {
		return prix;
	}

	/**
	 * 
	 * @param prix
	 *            {@link #prix}.
	 */
	public void setPrix(Double prix) {
		this.prix = prix;
	}

	/**
	 * 
	 * @return {@link #engagement}.
	 */
	public Integer getEngagement() {
		return engagement;
	}

	/**
	 * 
	 * @param engagement
	 *            {@link #engagement}.
	 */
	public void setEngagement(Integer engagement) {
		this.engagement = engagement;
	}

	/**
	 * 
	 * @return {@link #frais}.
	 */
	public List<Frais> getFrais() {
		return frais;
	}

	/**
	 * 
	 * @param frais
	 *            {@link #frais}.
	 */
	public void setFrais(List<Frais> frais) {
		this.frais = frais;
	}

	/**
	 * 
	 * @return {@link #frequence}
	 */
	public Integer getFrequence() {
		return frequence;
	}

	/**
	 * 
	 * @param frequence
	 *            {@link #frequence}
	 * 
	 */
	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
	}

	/**
	 * 
	 * @return {@link #duree}
	 */
	public Integer getDuree() {
		return duree;
	}

	/**
	 * 
	 * @param duree
	 *            {@link #duree}
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	/**
	 * 
	 * @return {@link #typeTVA}
	 */
	public String getTypeTVA() {
		return typeTVA;
	}

	/**
	 * 
	 * @param typeTVA
	 *            {@link #typeTVA}
	 */
	public void setTypeTVA(String typeTVA) {
		this.typeTVA = typeTVA;
	}

	/**
	 * Ajouter un frais.
	 * 
	 * @param frais
	 *            {@link Frais}
	 */
	public void addFrais(Frais frais) {
		if (this.frais == null)
			this.frais = new ArrayList<>();
		this.frais.add(frais);
	}

	/**
	 * verifie si le tarif est recurrent ou non.
	 * 
	 * @return true si le tarif est recurrent.
	 */
	public boolean isRecurrent() {
		Optional<Integer> frequenceOp = Optional.fromNullable(frequence);
		if (frequenceOp.isPresent()) {
			return true;
		}
		return false;
	}

}
