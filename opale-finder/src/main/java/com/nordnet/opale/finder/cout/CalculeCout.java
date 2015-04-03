package com.nordnet.opale.finder.cout;

import com.nordnet.opale.finder.business.Cout;
import com.nordnet.opale.finder.exception.OpaleException;

/**
 * classe abstract qui est le parent du tous les classe qui calcule le cout.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public abstract class CalculeCout {

	/**
	 * cout comptant TTC.
	 */
	double coutComptantTTC;

	/**
	 * cout recurrent ttc.
	 */
	double coutRecurrentTTC;

	/**
	 * reduction TTC.
	 */
	double reductionTTC;

	/**
	 * reduction recurrente.
	 */
	double reductionRecurrentTTC;

	/**
	 * reduction comptant.
	 */
	double reductionComptantTTC;

	/**
	 * cout Comptant Reduit.
	 */
	double coutComptantReduitTTC;

	/**
	 * cout recurrent reduit.
	 */
	double coutRecurentReduitTTC;

	/**
	 * 
	 * @return {@link #cout}
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public abstract Cout getCout() throws OpaleException;

	/**
	 * constructeur par defaut.
	 */
	public CalculeCout() {

	}

	/*
	 * getter and setters
	 */

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public double getCoutComptantTTC() throws OpaleException {
		if (getCout() == null) {
			return 0;
		}
		return getCout().getCoutComptantTTC();
	}

	/**
	 * 
	 * @param coutComptantTTC
	 *            {@link #coutComptantTTC}
	 */
	public void setCoutComptantTTC(double coutComptantTTC) {
		this.coutComptantTTC = coutComptantTTC;
	}

	/**
	 * 
	 * @return {@link #coutRecurrentTTC}
	 * 
	 */
	public double getCoutRecurrentTTC() {
		return coutRecurrentTTC;
	}

	/**
	 * 
	 * @param coutRecurrentTTC
	 *            {@link #coutRecurrentTTC}
	 */
	public void setCoutRecurrentTTC(double coutRecurrentTTC) {
		this.coutRecurrentTTC = coutRecurrentTTC;
	}

	/**
	 * 
	 * @return {@link #reductionTTC}
	 */
	public double getReductionTTC() {
		return reductionTTC;
	}

	/**
	 * 
	 * @param reductionTTC
	 *            {@link #reductionTTC}
	 */
	public void setReductionTTC(double reductionTTC) {
		this.reductionTTC = reductionTTC;
	}

	/**
	 * 
	 * @return {@link #reductionRecurrentTTC}
	 */
	public double getReductionRecurrentTTC() {
		return reductionRecurrentTTC;
	}

	/**
	 * 
	 * @param reductionRecurrentTTC
	 *            {@link #reductionRecurrentTTC}
	 */
	public void setReductionRecurrentTTC(double reductionRecurrentTTC) {
		this.reductionRecurrentTTC = reductionRecurrentTTC;
	}

	/**
	 * 
	 * @return {@link #reductionComptantTTC}
	 */
	public double getReductionComptantTTC() {
		return reductionComptantTTC;
	}

	/**
	 * 
	 * @param reductionComptantTTC
	 *            {@link #reductionComptantTTC}
	 */
	public void setReductionComptantTTC(double reductionComptantTTC) {
		this.reductionComptantTTC = reductionComptantTTC;
	}

	/**
	 * 
	 * @return {@link #coutComptantReduitTTC}
	 */
	public double getCoutComptantReduitTTC() {
		return coutComptantReduitTTC;
	}

	/**
	 * 
	 * @param coutComptantReduitTTC
	 *            {@link #coutComptantReduitTTC}
	 */
	public void setCoutComptantReduitTTC(double coutComptantReduitTTC) {
		this.coutComptantReduitTTC = coutComptantReduitTTC;
	}

	/**
	 * 
	 * @return {@link #coutRecurentReduitTTC}
	 */
	public double getCoutRecurentReduitTTC() {
		return coutRecurentReduitTTC;
	}

	/**
	 * 
	 * @param coutRecurentReduitTTC
	 *            {@link #coutRecurentReduitTTC}
	 */
	public void setCoutRecurentReduitTTC(double coutRecurentReduitTTC) {
		this.coutRecurentReduitTTC = coutRecurentReduitTTC;
	}

}
