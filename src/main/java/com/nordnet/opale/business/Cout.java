package com.nordnet.opale.business;

/**
 * contient les couts d'une commande.
 * 
 * @author akram-moncer
 * 
 */
public class Cout {

	/**
	 * cout total du commande/draft.
	 */
	private Double coutTotal;

	/**
	 * constructeur par defaut.
	 */
	public Cout() {
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

}
