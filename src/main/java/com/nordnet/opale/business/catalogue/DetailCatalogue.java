package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.List;

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
	 * type detail.
	 */
	private String type;

	/**
	 * liste des {@link Choice} associe au detail du catalogue.
	 */
	private List<Choice> choices = new ArrayList<Choice>();

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
	 * @return {@link #type}.
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            {@link #type}.
	 */
	public void setType(String type) {
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

}