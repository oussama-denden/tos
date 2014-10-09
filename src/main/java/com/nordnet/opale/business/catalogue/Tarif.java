package com.nordnet.opale.business.catalogue;

import java.util.List;

/**
 * classe contenant les info du tarif.
 * 
 * @author akram-moncer
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
	private Integer periode;

	/**
	 * l'engagement en nombre de mois.
	 */
	private Integer engagement;

	/**
	 * la liste des reference des {@link Frais} associe au tarif.
	 */
	private List<String> frais;

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
	 * @return {@link #periode}.
	 */
	public Integer getPeriode() {
		return periode;
	}

	/**
	 * 
	 * @param periode
	 *            {@link #periode}.
	 */
	public void setPeriode(Integer periode) {
		this.periode = periode;
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
	public List<String> getFrais() {
		return frais;
	}

	/**
	 * 
	 * @param frais
	 *            {@link #frais}.
	 */
	public void setFrais(List<String> frais) {
		this.frais = frais;
	}

}
