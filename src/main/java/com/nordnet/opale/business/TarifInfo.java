package com.nordnet.opale.business;

import java.util.List;

/**
 * Cette classe regroupe les informations qui definissent un {@link TarifInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class TarifInfo {

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
	private Integer periodicite;

	/**
	 * l'engagement en nombre de mois.
	 */
	private Integer engagement;

	/**
	 * duree de tarif
	 */
	private Integer duree;

	/**
	 * la liste des reference des {@link Frais} associe au tarif.
	 */
	private List<FraisInfo> frais;

	/**
	 * constructeur par defaut.
	 */
	public TarifInfo() {
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
	public List<FraisInfo> getFrais() {
		return frais;
	}

	/**
	 * 
	 * @param frais
	 *            {@link #frais}.
	 */
	public void setFrais(List<FraisInfo> frais) {
		this.frais = frais;
	}

	/**
	 * 
	 * @return {@link #periodicite}
	 */
	public Integer getPeriodicite() {
		return periodicite;
	}

	/**
	 * 
	 * @param periodicite
	 *            {@link #periodicite}
	 * 
	 */
	public void setPeriodicite(Integer periodicite) {
		this.periodicite = periodicite;
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

}
