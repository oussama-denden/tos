package com.nordnet.opale.business;

import java.util.List;

/**
 * Cette classe regroupe les informations qui definissent un {@link DetailCommandeLigneInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class DetailCommandeLigneInfo {

	/**
	 * le reference
	 */
	private String reference;

	/**
	 * label
	 */
	private String label;

	/**
	 * le tarif
	 */
	private List<TarifInfo> tarif;

	/**
	 * constructeur par defaut
	 */
	public DetailCommandeLigneInfo() {

	}

	/**
	 * get the reference
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * set the reference
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * get the label
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * set the label
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * get the tarif
	 * 
	 * @return {@link TarifInfo}
	 */
	public List<TarifInfo> getTarif() {
		return tarif;
	}

	/**
	 * set the tarif
	 * 
	 * @param tarif
	 *            the new {@link TarifInfo}
	 */
	public void setTarif(List<TarifInfo> tarif) {
		this.tarif = tarif;
	}

}
