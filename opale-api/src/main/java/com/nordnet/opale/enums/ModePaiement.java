package com.nordnet.opale.enums;

import com.nordnet.opale.deserializer.ModePaiementDeserializer;

/**
 * Enumeration que definit le type de {@link ModePaiement}.
 * <p>
 * C'est la maniere dont le client va payer le produit qu'il a acquis. Soit de payer par :
 * </p>
 * <ul>
 * <li><b>Virement</b></li>
 * <li><b>Cheque</b></li>
 * <li><b>sous forme de prelevement par mois</b></li>
 * <li><b>3 fois sans frais</b></li>
 * </ul>
 * 
 * @author akram-moncer
 * 
 */
public enum ModePaiement {

	/**
	 * Mode de payement sous forme de prelevement par mois.
	 */
	SEPA,

	/**
	 * Mode de payement par virement.
	 */
	CB,

	/**
	 * Mode de payement par cheque.
	 */
	CHEQUE,

	/**
	 * Mondat administrative.
	 */
	FACTURE,

	/**
	 * Mode de payement en 3 fois sans frais.
	 */
	TROIS_FOIS_SANS_FRAIS;

	/**
	 * Cette methode sera utiliser par le {@link ModePaiementDeserializer} pour faire la deserialisation.
	 * 
	 * @param modePaiement
	 *            on retourne null si la valeur de string n'est pas l'un de valeur deja defini.
	 * @return null si la valeur de string n'est pas l'un de valeur deja defini.
	 */
	public static ModePaiement fromString(final String modePaiement) {
		switch (modePaiement) {
		case "SEPA":
			return SEPA;
		case "CB":
			return CB;
		case "CHEQUE":
			return CHEQUE;
		case "FACTURE":
			return FACTURE;
		case "TROIS_FOIS_SANS_FRAIS":
			return TROIS_FOIS_SANS_FRAIS;
		}
		return null;
	}

}
