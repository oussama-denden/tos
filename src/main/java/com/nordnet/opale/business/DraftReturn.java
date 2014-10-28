package com.nordnet.opale.business;

/**
 * Cette classe regroupe les informations retournees apres la creation d un
 * draft.
 * 
 * @author anisselmane.
 * 
 */

public class DraftReturn {

	/**
	 * La reference draft.
	 */
	private String reference;

	/**
	 * constructeur par defaut.
	 */
	public DraftReturn() {

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

}
