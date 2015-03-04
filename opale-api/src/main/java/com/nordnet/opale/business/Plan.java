package com.nordnet.opale.business;

import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.Utils;

/**
 * contient des info sur le plan normale et reduit de paiement d'une offre.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class Plan implements Cloneable {

	/**
	 * tarif hors tax.
	 */
	private double tarifHT;

	/**
	 * tarif avec taxt.
	 */
	private double tarifTTC;

	/**
	 * difference entre tarif TTC et tarif HT.
	 */
	private double tarifTva;

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
		setTarifHT(tarifHT);
		setTarifTTC(tarifTTC);
		setTarifTva(tarifTTC > tarifHT ? tarifTTC - tarifHT : 0d);
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
		this.tarifHT = Utils.round(tarifHT, Constants.DEUX);
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
		this.tarifTTC = Utils.round(tarifTTC, Constants.DEUX);
	}

	/**
	 * 
	 * @return {@link #tarifTva}
	 */
	public double getTarifTva() {
		return tarifTva;
	}

	/**
	 * 
	 * @param tarifTva
	 *            {@link #tarifTva}
	 */
	public void setTarifTva(double tarifTva) {
		this.tarifTva = Utils.round(tarifTva, Constants.DEUX);
		;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Plan clone() {
		Plan plan = new Plan();

		plan.setTarifHT(tarifHT);
		plan.setTarifTTC(tarifTTC);

		return plan;
	}

}
