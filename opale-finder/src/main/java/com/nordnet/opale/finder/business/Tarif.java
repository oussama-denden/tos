package com.nordnet.opale.finder.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe regroupe les informations qui definissent un {@link Tarif}.
 * 
 * @author anisselmane.
 * 
 */
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
	private List<Frais> frais = new ArrayList<Frais>();

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
		this.frais.add(frais);
	}

}
