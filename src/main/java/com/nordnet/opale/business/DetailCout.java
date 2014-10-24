package com.nordnet.opale.business;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
public class DetailCout {

	/**
	 * numero detail coincide avec le numero de ligne dans la commande.
	 */
	public String numero;

	/**
	 * label de l'offre.
	 */
	public String label;

	/**
	 * cout total de l'offre.
	 */
	public Double coutTotal;

	/**
	 * {@link Plan}.
	 */
	private Plan plan;

	/**
	 * constructeur par defaut.
	 */
	public DetailCout() {
	}

	/**
	 * 
	 * @return {@link #numero}.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 *            {@link #numero}.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
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
	 * @return {@link #coutTotal}.
	 */
	public Double getCoutTotal() {
		return coutTotal;
	}

	/**
	 * 
	 * @param coutTotal
	 *            {@link #coutTotal}.
	 */
	public void setCoutTotal(Double coutTotal) {
		this.coutTotal = coutTotal;
	}

	/**
	 * 
	 * @return {@link Plan}.
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * 
	 * @param plan
	 *            {@link Plan}.
	 */
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

}
