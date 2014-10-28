package com.nordnet.opale.enums;

/**
 * Enumeration qui definie le type de FRAIS.
 * <p>
 * Deux types de Frais:
 * </p>
 * <ul>
 * <li><b>{@link #CREATION}</b></li>
 * <li><b>{@link #RESILIATION}</b></li>
 * </ul>
 * 
 * @author akram-moncer
 * 
 */
public enum TypeFrais {

	/**
	 * Frais de Type CREATION.
	 */
	CREATION,

	/**
	 * FRAIS DE TYPE RESILIATION.
	 */
	RESILIATION;

	/**
	 * Cette methode retourne {@link TypeFrais} a partir d'un String.
	 * 
	 * @param typeFrais
	 *            le type de prix.
	 * @return {@link TypeFrais}.
	 */
	public static TypeFrais fromSting(String typeFrais) {
		switch (typeFrais) {
		case "CREATION":
			return CREATION;
		case "RESILIATION":
			return RESILIATION;
		}
		return null;
	}

}
