package com.nordnet.opale.calcule;

import java.util.List;

import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.util.Constants;
import com.nordnet.topaze.ws.enums.TypeValeur;

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
	public static double calculeReductionRecurrent(double montant, Reduction reduction, Integer frequence) {

		double montantParMois = 0;
		if (reduction == null || montant == 0) {
			return 0;
		} else if (reduction.getTypeValeur().equals(TypeValeur.MOIS)) {

			if (frequence != null && frequence.intValue() != Constants.ZERO) {

				montantParMois = montant / frequence;
				if (reduction.getValeur() < frequence) {

					return reduction.getValeur() * montantParMois;
				}

			} else {
				return frequence * montantParMois;
			}

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
	 * @param TVA
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
	 * 
	 * @param reduction
	 *            reductin TTC
	 * @param TVA
	 *            valeur de tva
	 * @return reduction HT
	 */
	public static double caculerCoutReduitHT(double coutTTC, double tva) {
		if (tva == Constants.ZERO) {
			return 0;
		}
		return coutTTC - ((coutTTC * tva) / 100);
	}

	/**
	 * ajouter le cout recurrent gloable au trame du cout.
	 * 
	 * @param coutRecurrents
	 *            liste du cout recurrent.
	 * @param coutRecurrent
	 *            cout recurrent a ajoute
	 */
	public static void ajouterCoutRecurrent(List<CoutRecurrent> coutRecurrents, CoutRecurrent coutRecurrent) {
		int index = -1;
		if (coutRecurrents != null && coutRecurrents.size() == Constants.ZERO && coutRecurrent != null) {
			coutRecurrents.add(coutRecurrent);
		}

		else {
			index = coutRecurrents.indexOf(coutRecurrent);

			if (index == -1) {
				coutRecurrents.add(coutRecurrent);
			}

			else {

				CoutRecurrent coutRecurrentAdditonne =
						addiotionnerDeuxCoutRecurrent(coutRecurrents.get(index), coutRecurrent);

				coutRecurrents.remove(index);
				coutRecurrents.add(coutRecurrentAdditonne);
			}
		}
	}

	/**
	 * additionner deux couts recurrents.
	 * 
	 * @param coutRecurrentAncient
	 *            cout recurrent deja existant dans la liste des couts recurrents
	 * @param coutRecurrentNouveau
	 *            cout recurrent nouveau
	 */
	private static CoutRecurrent addiotionnerDeuxCoutRecurrent(CoutRecurrent coutRecurrentAncient,
			CoutRecurrent coutRecurrentNouveau) {

		CoutRecurrent coutRecurrentSomme = coutRecurrentAncient.clone();

		if (coutRecurrentAncient.getNormal() != null && coutRecurrentNouveau.getNormal() != null) {
			coutRecurrentSomme.getNormal().setTarifHT(
					coutRecurrentAncient.getNormal().getTarifHT() + coutRecurrentNouveau.getNormal().getTarifHT());
			coutRecurrentSomme.getNormal().setTarifTTC(
					coutRecurrentAncient.getNormal().getTarifTTC() + coutRecurrentNouveau.getNormal().getTarifTTC());
			coutRecurrentSomme.getNormal().setTarifTva(
					coutRecurrentAncient.getNormal().getTarifTva() + coutRecurrentNouveau.getNormal().getTarifTva());
		}

		if (coutRecurrentAncient.getReduit() != null && coutRecurrentNouveau.getReduit() != null) {
			coutRecurrentSomme.getReduit().setTarifHT(
					coutRecurrentAncient.getReduit().getTarifHT() + coutRecurrentNouveau.getReduit().getTarifHT());
			coutRecurrentSomme.getReduit().setTarifTTC(
					coutRecurrentAncient.getReduit().getTarifTTC() + coutRecurrentNouveau.getReduit().getTarifTTC());
			coutRecurrentSomme.getReduit().setTarifTva(
					coutRecurrentAncient.getReduit().getTarifTva() + coutRecurrentNouveau.getReduit().getTarifTva());
		}

		return coutRecurrentSomme;
	}

}
