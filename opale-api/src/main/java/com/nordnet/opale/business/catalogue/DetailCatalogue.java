package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.TypeProduitDeserializer;
import com.nordnet.opale.enums.TypeProduit;

/**
 * Classe contenant les info d'un detail dans le catalogue.
 * 
 * @author akram-moncer
 * 
 */
public class DetailCatalogue {

	/**
	 * reference selection.
	 */
	private String referenceSelection;

	/**
	 * label du frais.
	 */
	private String label;

	/**
	 * required.
	 */
	private boolean required;

	/**
	 * type de selection.
	 */
	private String selectionType;

	/**
	 * Type de produit (SERVICE,Bien).
	 */
	@JsonDeserialize(using = TypeProduitDeserializer.class)
	private TypeProduit type;

	/**
	 * liste des {@link Choice} associe au detail du catalogue.
	 */
	private List<Choice> choices = new ArrayList<>();

	/**
	 * la reference de l'element parent.
	 */
	private String dependDe;

	/**
	 * constructeur par defaut.
	 */
	public DetailCatalogue() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetailCatalogue other = (DetailCatalogue) obj;
		if (referenceSelection == null) {
			if (other.referenceSelection != null)
				return false;
		} else if (!referenceSelection.equals(other.referenceSelection))
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
		return new HashCodeBuilder(43, 11).append(referenceSelection).toHashCode();
	}

	/**
	 * 
	 * @return {@link #referenceSelection}.
	 */
	public String getReferenceSelection() {
		return referenceSelection;
	}

	/**
	 * 
	 * @param referenceSelection
	 *            {@link #referenceSelection}.
	 */
	public void setReferenceSelection(String referenceSelection) {
		this.referenceSelection = referenceSelection;
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
	 * @return {@link #required}.
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * 
	 * @param required
	 *            {@link #required}.
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * 
	 * @return {@link #selectionType}.
	 */
	public String getSelectionType() {
		return selectionType;
	}

	/**
	 * 
	 * @param selectionType
	 *            {@link #selectionType}.
	 */
	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	/**
	 * 
	 * @return {@link TypeProduit}.
	 */
	public TypeProduit getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            {@link TypeProduit}.
	 */
	public void setType(TypeProduit type) {
		this.type = type;
	}

	/**
	 * 
	 * @return {@link #choices}.
	 */
	public List<Choice> getChoices() {
		return choices;
	}

	/**
	 * 
	 * @param choices
	 *            {@link #choices}.
	 */
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	/**
	 * 
	 * @return {@link #dependDe}.
	 */
	public String getDependDe() {
		return dependDe;
	}

	/**
	 * 
	 * @param dependDe
	 *            {@link #dependDe}.
	 */
	public void setDependDe(String dependDe) {
		this.dependDe = dependDe;
	}

	/**
	 * transfomer la {@link List} de choice en un objet {@link Map}.
	 * 
	 * @return {@link Map<String, Choice>}
	 */
	public Map<String, Choice> getChoiceMap() {
		Map<String, Choice> choiceMap = new HashMap<>();
		for (Choice choice : choices) {
			choiceMap.put(choice.getReference(), choice);
		}

		return choiceMap;
	}

	/**
	 * trouver le {@link Choice} a partir de la referenceChoix.
	 * 
	 * @param refrenceChoix
	 *            reference du choix.
	 * @return {@link Choice}.
	 */
	public Choice getChoice(String refrenceChoix) {
		return getChoiceMap().get(refrenceChoix);
	}

	/**
	 * creation d'une {@link Map} avec la referenceChoix en cle et la referenceSelection en valeur, sert lors de la
	 * recherche de la referenceSelection a partir de la referenceChoix.
	 * 
	 * @return une {@link Map} avec la referenceChoix en cle et la referenceSelection en valeur
	 */
	public Map<String, String> getReferenceChoixReferenceSelectionMap() {
		Map<String, String> referenceChoixReferenceSelectionMap = new HashMap<>();
		for (Choice choice : choices) {
			referenceChoixReferenceSelectionMap.put(choice.getReference(), referenceSelection);
		}
		return referenceChoixReferenceSelectionMap;
	}

}
