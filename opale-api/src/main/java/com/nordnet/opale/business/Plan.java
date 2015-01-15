package com.nordnet.opale.business;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.nordnet.opale.util.Constants;

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
		this.tarifHT = arroundiNombre(tarifHT);
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
		this.tarifTTC = arroundiNombre(tarifTTC);
	}

	/**
	 * Rounds up a double value.
	 * 
	 * @param value
	 *            double value.
	 * @param places
	 *            the number of decimal places.
	 * @return rounded value.
	 */
	public double arroundiNombre(double value) {

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		bd = bd.setScale(Constants.DEUX, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
