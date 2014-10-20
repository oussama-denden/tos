package com.nordnet.opale.business.catalogue;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.TypeTVA;
import com.nordnet.opale.enums.deserializer.TypeTVADeserializer;

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
	 * duree.
	 */
	private Integer duree;

	/**
	 * la periodicite en nombre de mois.
	 */
	private Integer periode;

	/**
	 * l'engagement en nombre de mois.
	 */
	private Integer engagement;

	/**
	 * {@link TypeTVA}.
	 */
	@JsonDeserialize(using = TypeTVADeserializer.class)
	private TypeTVA typeTVA;

	/**
	 * la liste des reference des {@link Frais} associe au tarif.
	 */
	private List<String> frais;

	/**
	 * constructeur par defaut.
	 */
	public Tarif() {
	}

	@Override
	public String toString() {
		return "Tarif [reference=" + reference + ", prix=" + prix + ", duree=" + duree + ", periode=" + periode
				+ ", engagement=" + engagement + ", frais=" + frais + "]";
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
	 * @return {@link #duree}.
	 */
	public Integer getDuree() {
		return duree;
	}

	/**
	 * 
	 * @param duree
	 *            {@link #duree}.
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
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
