package com.nordnet.opale.enums;

/**
 * Enumeration qui definie le mode de signature.
 * 
 * <ul>
 * <li><b>{@link #CREATION}</b></li>
 * 
 * </ul>
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public enum ModeSignature {

	/**
	 * signature de mode OPEN_TRUST .
	 */
	OPEN_TRUST;

	/**
	 * Cette methode retourne {@link ModeSignature} a partir d'un String.
	 * 
	 * @param modeSignature
	 *            le mode de signature
	 * @return {@link ModeSignature}
	 */
	public static ModeSignature fromSting(String modeSignature) {
		switch (modeSignature) {
		case "OPEN_TRUST":
			return OPEN_TRUST;
		}
		return null;
	}

}
