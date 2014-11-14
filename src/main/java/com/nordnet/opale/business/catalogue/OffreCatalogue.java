package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.ModeFacturationDeserializer;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.TypeProduit;

/**
 * Classe reprensente une offre dans le catalogue.
 * 
 * @author akram-moncer
 * 
 */
public class OffreCatalogue {

	/**
	 * reference offre.
	 */
	private String reference;

	/**
	 * la gemme de l'offre.
	 */
	private String gamme;

	/**
	 * le famille de l'offre.
	 */
	private String famille;

	/**
	 * label de l'offre.
	 */
	private String label;

	/**
	 * {@link TypeProduit}.
	 */
	private TypeProduit nature;

	/**
	 * {@link ModeFacturation}.
	 */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * liste des reference des {@link Tarif} associe a l'offre.
	 */
	private List<String> tarifs = new ArrayList<String>();

	/**
	 * les tranches de tarification.
	 */
	private List<String> tranches = new ArrayList<String>();

	/**
	 * liste des {@link DetailCatalogue} associe a l'offre.
	 */
	private List<DetailCatalogue> details = new ArrayList<DetailCatalogue>();

	/**
	 * constructeur par defaut.
	 */
	public OffreCatalogue() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(Object obj)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OffreCatalogue other = (OffreCatalogue) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(reference).toHashCode();
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
	 * @return {@link #gamme}.
	 */
	public String getGamme() {
		return gamme;
	}

	/**
	 * 
	 * @param gamme
	 *            {@link #gamme}.
	 */
	public void setGamme(String gamme) {
		this.gamme = gamme;
	}

	/**
	 * 
	 * @return {@link #famille}.
	 */
	public String getFamille() {
		return famille;
	}

	/**
	 * 
	 * @param famille
	 *            {@link #famille}.
	 */
	public void setFamille(String famille) {
		this.famille = famille;
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
	 * @return {@link #nature}.
	 */
	public TypeProduit getNature() {
		return nature;
	}

	/**
	 * 
	 * @param nature
	 *            {@link #nature}.
	 */
	public void setNature(TypeProduit nature) {
		this.nature = nature;
	}

	/**
	 * 
	 * @return {@link ModeFacturation}.
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * 
	 * @return {@link #tarifs}.
	 */
	public List<String> getTarifs() {
		return tarifs;
	}

	/**
	 * 
	 * @param tarifs
	 *            {@link #tarifs}.
	 */
	public void setTarifs(List<String> tarifs) {
		this.tarifs = tarifs;
	}

	/**
	 * 
	 * @return {@link #tranches}.
	 */
	public List<String> getTranches() {
		return tranches;
	}

	/**
	 * 
	 * @param tranches
	 *            {@link #tranches}.
	 */
	public void setTranches(List<String> tranches) {
		this.tranches = tranches;
	}

	/**
	 * 
	 * @return {@link #details}.
	 */
	public List<DetailCatalogue> getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details
	 *            {@link #details}.
	 */
	public void setDetails(List<DetailCatalogue> details) {
		this.details = details;
	}

	/**
	 * transformer la {@link List} en un objet {@link Map}.
	 * 
	 * @return {@link Map<String, DetailCatalogue>}
	 */
	public Map<String, DetailCatalogue> getDetailsMap() {
		Map<String, DetailCatalogue> detailsMap = new HashMap<String, DetailCatalogue>();
		for (DetailCatalogue detailCatalogue : details) {
			detailsMap.put(detailCatalogue.getReferenceSelection(), detailCatalogue);
		}

		return detailsMap;
	}

}
