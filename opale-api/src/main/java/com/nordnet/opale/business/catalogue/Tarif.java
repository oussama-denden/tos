package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
import com.nordnet.common.valueObject.constants.VatType;
import com.nordnet.opale.deserializer.VATTypeDeserializer;

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
	@JsonProperty("id_tarif")
	private String idTarif;

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
	 * {@link VatType}.
	 */
	@JsonDeserialize(using = VATTypeDeserializer.class)
	private VatType tva;

	/**
	 * la liste des reference des {@link Frais} associe au tarif.
	 */
	private List<Frais> frais = new ArrayList<Frais>();

	/**
	 * constructeur par defaut.
	 */
	public Tarif() {
	}

	@Override
	public String toString() {
		return "Tarif [idTarif=" + idTarif + ", prix=" + prix + ", duree=" + duree + ", frequence=" + frequence
				+ ", engagement=" + engagement + ", frais=" + frais + "]";
	}

	/**
	 * 
	 * @return {@link #idTarif}.
	 */
	public String getIdTarif() {
		return idTarif;
	}

	/**
	 * 
	 * @param idTarif
	 *            {@link #idTarif}.
	 */
	public void setIdTarif(String idTarif) {
		this.idTarif = idTarif;
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
	 * @return {@link VatType}.
	 */
	public VatType getTva() {
		return tva;
	}

	/**
	 * 
	 * @param tva
	 *            {@link VatType}.
	 */
	public void setTva(VatType tva) {
		this.tva = tva;
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

	/**
	 * transforme la {@link List} de frais en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, Frais>}.
	 */
	public Map<String, Frais> getFraisMap() {
		Map<String, Frais> map = new HashMap<String, Frais>();
		for (Frais frais : this.frais) {
			map.put(frais.getIdFrais(), frais);
		}
		return map;
	}

}
