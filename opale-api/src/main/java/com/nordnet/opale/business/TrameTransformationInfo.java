package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

/**
 * trame pour la transfomation de plusieurs contrat en draft d'un seule coup.
 * 
 * @author akram-moncer
 * 
 */
public class TrameTransformationInfo extends TrameCatalogueInfo {

	/**
	 * liste des references contrat.
	 */
	private List<String> referencesContrat = new ArrayList<String>();

	/**
	 * contructeur par defaut.
	 */
	public TrameTransformationInfo() {

	}

	/**
	 * 
	 * @return {@link #referencesContrat}.
	 */
	public List<String> getReferencesContrat() {
		return referencesContrat;
	}

	/**
	 * 
	 * @param referencesContrat
	 *            {@link #referencesContrat}.
	 */
	public void setReferencesContrat(List<String> referencesContrat) {
		this.referencesContrat = referencesContrat;
	}

}
