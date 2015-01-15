package com.nordnet.opale.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.util.Constants;

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
	private double coutComptantHT;

	/**
	 * cout total du commande/draft.
	 */
	private double coutComptantTTC;

	/**
	 * cout totale du reduction.
	 */
	private double reductionHT;

	/**
	 * cout totale du reduction.
	 */
	private double reductionTTC;

	/**
	 * liste des {@link DetailCout}.
	 */
	List<DetailCout> details = new ArrayList<DetailCout>();

	/**
	 * constructeur par defaut.
	 */
	public Cout() {
	}

	/**
	 * 
	 * @return {@link #details}.
	 */
	public List<DetailCout> getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details
	 *            {@link #details}.
	 */
	public void setDetails(List<DetailCout> details) {
		this.details = details;
	}

	/**
	 * ajout d'un {@link DetailCout}.
	 * 
	 * @param detailCout
	 *            {@link DetailCout}.
	 */
	public void addDetail(DetailCout detailCout) {
		details.add(detailCout);
	}

	/**
	 * 
	 * @return {@link #coutComptantHT}
	 */
	public double getCoutComptantHT() {
		return coutComptantHT;
	}

	/**
	 * 
	 * @param coutComptant
	 *            {@link #coutComptantHT}
	 */
	public void setCoutComptantHT(double coutComptantHT) {
		this.coutComptantHT = arroundiNombre(coutComptantHT);
	}

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 */
	public double getCoutComptantTTC() {
		return coutComptantTTC;
	}

	/**
	 * 
	 * @param coutComptantTTC
	 *            {@link #coutComptantTTC}
	 */
	public void setCoutComptantTTC(double coutComptantTTC) {
		this.coutComptantTTC = arroundiNombre(coutComptantTTC);
	}

	/**
	 * 
	 * @return {@link Cout#reductionHT}
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
		this.reductionHT = arroundiNombre(reductionHT);
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
		this.reductionTTC = arroundiNombre(reductionTTC);
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
	public static double arroundiNombre(double value) {

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		bd = bd.setScale(Constants.DEUX, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
