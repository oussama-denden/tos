package com.nordnet.opale.finder.util;

import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.business.TypeValeur;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ReductionUtil {

	/**
	 * 
	 * @param montant
	 *            montant comptant pour appliquer la reduction.
	 * @param reduction
	 *            reduction associee.
	 * @return la reduction calculee.
	 */
	public static double calculeReductionComptant(double montant, Reduction reduction) {
		if (reduction == null || montant == 0) {
			return 0;
		} else if (reduction.getTypeValeur().equals(TypeValeur.MONTANT) && reduction.getValeur() > montant) {
			return 0;
		} else if (reduction.getTypeValeur().equals(TypeValeur.MONTANT)) {
			return reduction.getValeur().doubleValue();
		} else if (reduction.getTypeValeur().equals(TypeValeur.POURCENTAGE)) {
			return ((montant * reduction.getValeur()) / 100);
		}
		return 0;

	}

	/**
	 * 
	 * @param montant
	 *            montant recurrent pour appliquer la reduction.
	 * @param reduction
	 *            reduction associee.
	 * @return la reduction calculee.
	 */
	public static double calculeReductionRecurrent(double montant, Reduction reduction) {
		if (reduction == null || montant == 0) {
			return 0;
		} else if (reduction.getTypeValeur().equals(TypeValeur.MOIS)) {
			return montant;
		} else if (reduction.getTypeValeur().equals(TypeValeur.POURCENTAGE)) {
			return ((montant * reduction.getValeur()) / 100);
		}
		return 0;

	}

	/**
	 * 
	 * @param reduction
	 *            reductin TTC
	 * @param tva
	 *            valeur de tva
	 * @return reduction HT
	 */
	public static double caculerReductionHT(double reduction, double tva) {
		if (tva == Constants.ZERO) {
			return 0;
		}
		return ((reduction / (100 + tva)) * 100);
	}

	/**
	 * caculer cout reduit HT.
	 * 
	 * @param coutTTC
	 *            reductin TTC
	 * @param tva
	 *            valeur de tva
	 * @return reduction HT
	 */
	public static double caculerCoutReduitHT(double coutTTC, double tva) {
		if (tva == Constants.ZERO) {
			return 0;
		}
		return coutTTC - ((coutTTC * tva) / 100);
	}

}