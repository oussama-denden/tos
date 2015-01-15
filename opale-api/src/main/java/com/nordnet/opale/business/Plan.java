package com.nordnet.opale.business;

/**
 * contient des info sur le plan normale et reduit de paiement d'une offre.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class Plan {

	/**
	 * tarif hors tax.
	 */
	private double tarifHT;

	/**
	 * tarif avec taxt.
	 */
	private double tarifTTC;

	/**
	 * constructeur par defaut.
	 */
	public Plan() {

	}

	/**
	 * 
	 * @param tarifHT
	 *            {@link #tarifHT}
	 * @param tarifTTC
	 *            {@link #tarifTTC}
	 */
	public Plan(double tarifHT, double tarifTTC) {
		this.tarifHT = tarifHT;
		this.tarifTTC = tarifTTC;
	}

	/**
	 * 
	 * @return {@link #tarifHT}
	 */
	public double getTarifHT() {
		return tarifHT;
	}

	/**
	 * 
	 * @param tarifHT
	 *            {@link #tarifHT}
	 */
	public void setTarifHT(double tarifHT) {
		this.tarifHT = tarifHT;
	}

	/**
	 * 
	 * @return {@link #tarifTTC}
	 */
	public double getTarifTTC() {
		return tarifTTC;
	}

	/**
	 * 
	 * @param tarifTTC
	 *            {@link #tarifTTC}
	 */
	public void setTarifTTC(double tarifTTC) {
		this.tarifTTC = tarifTTC;
	}

}
