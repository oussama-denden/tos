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
	 * cout comptantHT.
	 */
	double coutComptantHT;

	/**
	 * cout comptant TTC.
	 */
	double coutComptantTTC;

	/**
	 * cout recurrent HT.
	 */
	double coutRecurrentHT;

	/**
	 * cout recurrent ttc.
	 */
	double coutRecurrentTTC;

	/**
	 * reduction hors taxe;
	 */
	double reductionHT;

	/**
	 * reduction TTC.
	 */
	double reductionTTC;

	/**
	 * reduction recurrente;
	 */
	double reductionRecurrentHT;

	/**
	 * reduction recurrente;
	 */
	double reductionRecurrentTTC;

	/**
	 * reduction comptant;
	 */
	double reductionComptantHT;

	/**
	 * reduction comptant;
	 */
	double reductionComptantTTC;

	/**
	 * cout Comptant Reduit
	 */
	double coutComptantReduitTTC;

	/**
	 * cout Comptant Reduit
	 */
	double coutComptantReduitHT;

	/**
	 * cout recurrent reduit.
	 */
	double coutRecurentReduitTTC;

	/**
	 * cout recurrent reduit.
	 */
	double coutRecurentReduitHT;

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
	 * @return {@link #cout}
	 * @throws OpaleException
	 */
	public Cout getCout() throws OpaleException {
		return cout;
	}

	/**
	 * 
	 * @param cout
	 *            {@link #cout}
	 */
	public void setCout(Cout cout) {
		this.cout = cout;
	}

	/**
	 * 
	 * @return {@link #coutComptantHT}
	 * @throws OpaleException
	 */
	public double getCoutComptantHT() throws OpaleException {
		if (getCout() == null) {
			return 0;
		} else
			return getCout().getCoutComptantHT();
	}

	/**
	 * 
	 * @param coutComptantHT
	 *            {@link #coutComptantHT}
	 */
	public void setCoutComptantHT(double coutComptantHT) {
		this.coutComptantHT = coutComptantHT;
	}

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 * @throws OpaleException
	 */
	public double getCoutComptantTTC() throws OpaleException {
		if (getCout() == null) {
			return 0;
		} else
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
	 * @return {@link #coutRecurrentHTT}
	 * @throws OpaleException
	 */
	public double getCoutRecurrentHT() throws OpaleException {
		return coutRecurrentHT;
	}

	/**
	 * 
	 * @param coutRecurrentHT
	 *            {@link #coutRecurrentHT}
	 * @throws OpaleException
	 */
	public void setCoutRecurrentHT(double coutRecurrentHT) throws OpaleException {
		this.coutRecurrentHT = coutRecurrentHT;
	}

	/**
	 * 
	 * @return {@link #coutRecurrentTTC}
	 * @throws OpaleException
	 */
	public double getCoutRecurrentTTC() throws OpaleException {
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
	 * @return {@link #reductionHT}
	 */
	public double getReductionHT() {
		return reductionHT;
	}

	/**
	 * 
	 * @param reductionHT
	 *            {@link #reductionHT}
	 */
	public void setReductionHT(double reductionHT) {
		this.reductionHT = reductionHT;
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
	 * @return {@link #reductionRecurrentHT}
	 */
	public double getReductionRecurrentHT() {
		return reductionRecurrentHT;
	}

	/**
	 * 
	 * @param reductionRecurrentHT
	 *            {@link #reductionRecurrentHT}
	 */
	public void setReductionRecurrentHT(double reductionRecurrentHT) {
		this.reductionRecurrentHT = reductionRecurrentHT;
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
	 * @return {@link #reductionComptantHT}
	 */
	public double getReductionComptantHT() {
		return reductionComptantHT;
	}

	/**
	 * 
	 * @param reductionComptantHT
	 *            {@link #reductionComptantTTC}
	 */
	public void setReductionComptantHT(double reductionComptantHT) {
		this.reductionComptantHT = reductionComptantHT;
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
	 * @return {@link #coutComptantReduitHT}
	 */
	public double getCoutComptantReduitHT() {
		return coutComptantReduitHT;
	}

	/**
	 * 
	 * @param coutComptantReduitHT
	 *            {@link #coutComptantReduitHT}
	 */
	public void setCoutComptantReduitHT(double coutComptantReduitHT) {
		this.coutComptantReduitHT = coutComptantReduitHT;
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

	/**
	 * 
	 * @return {@link #coutRecurentReduitHT}
	 */
	public double getCoutRecurentReduitHT() {
		return coutRecurentReduitHT;
	}

	/**
	 * 
	 * @param coutRecurentReduitHT
	 *            {@link #coutRecurentReduitHT}
	 */
	public void setCoutRecurentReduitHT(double coutRecurentReduitHT) {
		this.coutRecurentReduitHT = coutRecurentReduitHT;
	}

}
