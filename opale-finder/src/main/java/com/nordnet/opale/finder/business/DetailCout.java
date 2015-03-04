package com.nordnet.opale.finder.business;

import com.nordnet.opale.finder.util.Utils;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
public class DetailCout extends Cout {

	/**
	 * cout total du commande/draft.
	 */
	private double coutComptantTTC;

	/**
	 * {@link CoutRecurrent}.
	 */
	private CoutRecurrent coutRecurrent;

	/**
	 * constructeur par defaut.
	 */
	public DetailCout() {
	}

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 */
	@Override
	public double getCoutComptantTTC() {
		return coutComptantTTC;
	}

	/**
	 * 
	 * @param coutComptantTTC
	 *            {@link #coutComptantTTC}
	 */
	@Override
	public void setCoutComptantTTC(double coutComptantTTC) {
		this.coutComptantTTC = Utils.arroundiNombre(coutComptantTTC);
	}

	/**
	 * 
	 * @return {@link #coutRecurrent}
	 */
	public CoutRecurrent getCoutRecurrent() {
		return coutRecurrent;
	}

	/**
	 * 
	 * @param coutRecurrent
	 *            {@link #coutRecurrent}
	 */
	public void setCoutRecurrent(CoutRecurrent coutRecurrent) {
		this.coutRecurrent = coutRecurrent;
	}

}
