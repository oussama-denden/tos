package com.nordnet.opale.finder.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nordnet.opale.finder.util.Utils;

/**
 * Cette classe regroupe les informations qui definissent un {@link CoutRecurrent}.
 * 
 * @author anisselmane.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CoutRecurrent {

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
	 * Le tva.
	 */
	private double tva;

	/**
	 * la liste des reference des {@link Frais} associe au tarif.
	 */
	private List<Frais> frais;

	/**
	 * constructeur par defaut.
	 */
	public CoutRecurrent() {
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
		this.prix = Utils.arroundiNombre(prix);
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
	 * @return {@link #TVA}
	 */
	public double getTVA() {
		return tva;
	}

	/**
	 * 
	 * @param tva
	 *            {@link #TVA}
	 */
	public void setTVA(double tva) {
		this.tva = tva;
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

}
