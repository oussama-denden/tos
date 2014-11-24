package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.ModeFacturationDeserializer;
import com.nordnet.opale.deserializer.TypeProduitDeserializer;
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
	 * secteur.
	 */
	private String secteur;

	/**
	 * label de l'offre.
	 */
	private String label;

	/**
	 * {@link TypeProduit}.
	 */
	@JsonDeserialize(using = TypeProduitDeserializer.class)
	private TypeProduit type;

	/**
	 * {@link ModeFacturation}.
	 */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * liste des {@link Tarif}.
	 */
	private List<Tarif> tarifs = new ArrayList<Tarif>();

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
	 * @return {@link #secteur}.
	 */
	public String getSecteur() {
		return secteur;
	}

	/**
	 * 
	 * @param secteur
	 *            {@link #secteur}.
	 */
	public void setSecteur(String secteur) {
		this.secteur = secteur;
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
	public TypeProduit getType() {
		return type;
	}

	/**
	 * 
	 * @param nature
	 *            {@link #nature}.
	 */
	public void setType(TypeProduit type) {
		this.type = type;
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
	public List<Tarif> getTarifs() {
		return tarifs;
	}

	/**
	 * 
	 * @param tarifs
	 *            {@link #tarifs}.
	 */
	public void setTarifs(List<Tarif> tarifs) {
		this.tarifs = tarifs;
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

	/**
	 * retourner la referenceSelection associe a une referenceChoix donnee.
	 * 
	 * @param referenceChoix
	 *            reference du Choix.
	 * @return referenceSelection.
	 */
	public String findReferenceSelection(String referenceChoix) {
		Map<String, String> refChoixRefSelectionMap = new HashMap<String, String>();
		for (DetailCatalogue detailCatalogue : details) {
			refChoixRefSelectionMap.putAll(detailCatalogue.getReferenceChoixReferenceSelectionMap());
		}
		return refChoixRefSelectionMap.get(referenceChoix);
	}

	/**
	 * transforme la {@link List} de tarif en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, Tarif>}.
	 */
	public Map<String, Tarif> getTarifsMap() {
		Map<String, Tarif> map = new HashMap<String, Tarif>();
		for (Tarif tarif : tarifs) {
			map.put(tarif.getIdTarif(), tarif);
		}
		return map;
	}

}
