package com.nordnet.opale.enums;

import com.nordnet.opale.deserializer.TypePaiementDeserializer;

/**
 * 
 * Enumeration que definit le differents type de paiement.
 * <p>
 * Deux types de paiement :
 * </p>
 * <ul>
 * <li><b>{@link #RECURRENT}</b></li>
 * <li><b>{@link #COMPTANT}</b></li>
 * </ul>
 * 
 * @author akram-moncer
 * 
 */
public enum TypePaiement {

	/**
	 * type de paiement recurrent.
	 */
	RECURRENT,

	/**
	 * type de paiement comptant.
	 */
	COMPTANT;

	/**
	 * Cette methode sera utiliser par le {@link TypePaiementDeserializer} pour faire la deserialisation.
	 * 
	 * @param typePaiement
	 *            le type de paiement.
	 * @return {@link TypePaiement}.
	 */
	public static TypePaiement fromString(final String typePaiement) {
		switch (typePaiement) {
		case "RECURRENT":
			return RECURRENT;
		case "COMPTANT":
			return COMPTANT;
		}
		return null;
	}

}
