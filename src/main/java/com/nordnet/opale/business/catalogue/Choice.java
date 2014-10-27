package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author akram-moncer
 * 
 */
public class Choice {

	/**
	 * reference choice.
	 */
	private String reference;

	/**
	 * label du frais.
	 */
	private String label;

	/**
	 * liste des reference des {@link Tarif} associe au choice.
	 */
	private List<String> tarifs = new ArrayList<String>();

	/**
	 * constructeur par defaut.
	 */
	public Choice() {
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

}
