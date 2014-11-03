package com.nordnet.opale.business.catalogue;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
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
	private Integer frequence;

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
		return "Tarif [reference=" + reference + ", prix=" + prix + ", duree=" + duree + ", frequence=" + frequence
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
	 * @return {@link #frequence}.
	 */
	public Integer getFrequence() {
		return frequence;
	}

	/**
	 * 
	 * @param frequence
	 *            {@link #frequence}.
	 */
	public void setFrequence(Integer frequence) {
		this.frequence = frequence;
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

	/**
	 * verifie si le tarif est recurrent ou non.
	 * 
	 * @return true si le tarif est recurrent.
	 */
	public boolean isRecurrent() {
		Optional<Integer> dureeOp = Optional.fromNullable(duree);
		Optional<Integer> frequenceOp = Optional.fromNullable(frequence);
		if ((!dureeOp.isPresent() && frequenceOp.isPresent())
				|| (frequenceOp.isPresent() && dureeOp.isPresent() && frequence < duree)) {
			return true;
		}
		return false;
	}

}
