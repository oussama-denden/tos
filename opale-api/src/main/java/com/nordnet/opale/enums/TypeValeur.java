package com.nordnet.opale.enums;

/**
 * Enumeration qi definit le type de valeur pour reduction.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public enum TypeValeur {

	/**
	 * type montant.
	 */
	MONTANT,

	/**
	 * type pourcentage.
	 */
	POURCENTAGE,

	/**
	 * type mois.
	 */
	MOIS;

	/**
	 * Cette methode retourne {@link TypeValeur} a partir d'un String.
	 * 
	 * @param typeValeur
	 *            le type de valeur.
	 * @return {@link TypeValeur}.
	 */
	public static TypeValeur fromSting(String typeValeur) {
		switch (typeValeur) {
		
		case "MONTANT":
			return MONTANT;
		case "POURCENTAGE":
			return POURCENTAGE;
		case "MOIS":
			return MOIS;
		}
		return null;
	}

}
