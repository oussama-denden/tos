package com.nordnet.opale.calcule;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.exception.OpaleException;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public abstract class CalculeCout {

	/**
	 * cout.
	 */
	Cout cout;

	/**
	 * cout TTC.
	 */
	double coutTTC;

	/**
	 * cout HT.
	 */
	double coutHT;

	/**
	 * 
	 * @return {@link #cout}
	 * @throws OpaleException
	 */
	public Cout getCout() throws OpaleException {
		return cout;
	}

	/**
	 * 
	 * @return {@link #coutTTC}
	 * @throws OpaleException
	 */
	public double getCoutTTC() throws OpaleException {
		return coutTTC;
	}

	/**
	 * 
	 * @return {@link #coutHT}
	 * @throws OpaleException
	 */
	public double getCoutHT() throws OpaleException {
		return coutHT;
	}

	/**
	 * 
	 * @param cout
	 *            {@link Cout}
	 */
	public void setCout(Cout cout) {
		this.cout = cout;
	}

	/**
	 * 
	 * @param coutTTC
	 *            {@link #coutTTC}
	 */
	public void setCoutTTC(double coutTTC) {
		this.coutTTC = coutTTC;
	}

	/**
	 * 
	 * @param coutHT
	 *            {@link #coutHT}
	 */
	public void setCoutHT(double coutHT) {
		this.coutHT = coutHT;
	}

}
