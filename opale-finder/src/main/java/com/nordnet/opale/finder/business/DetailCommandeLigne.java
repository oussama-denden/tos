package com.nordnet.opale.finder.business;

/**
 * Cette classe regroupe les informations qui definissent un {@link DetailCommandeLigne}.
 * 
 * @author anisselmane.
 * 
 */
public class DetailCommandeLigne {

	/**
	 * le reference.
	 */
	private String reference;

	/**
	 * label.
	 */
	private String label;

	/**
	 * {@link Tarif}.
	 */
	private Tarif tarif;

	/**
	 * constructeur par defaut.
	 */
	public DetailCommandeLigne() {

	}

	/**
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return {@link #tarif}.
	 */
	public Tarif getTarif() {
		return tarif;
	}

	/**
	 * 
	 * @param tarif
	 *            {@link #tarif}.
	 */
	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

}
