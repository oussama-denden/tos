package com.nordnet.opale.business.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * label du choix.
	 */
	private String label;

	/**
	 * liste des {@link Tarif}.
	 */
	private List<Tarif> tarifs = new ArrayList<>();

	/**
	 * configuration du choix.
	 */
	private String configuration;

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
	 * @return {@link #configuration}.
	 */
	public String getConfiguration() {
		return configuration;
	}

	/**
	 * 
	 * @param configuration
	 *            {@link #configuration}.
	 */
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	/**
	 * transforme la {@link List} de tarif en un objet {@link Map}.
	 * 
	 * @return {@link Map<string, Tarif>}.
	 */
	public Map<String, Tarif> getTarifsMap() {
		Map<String, Tarif> map = new HashMap<>();
		for (Tarif tarif : tarifs) {
			map.put(tarif.getIdTarif(), tarif);
		}
		return map;
	}

}
