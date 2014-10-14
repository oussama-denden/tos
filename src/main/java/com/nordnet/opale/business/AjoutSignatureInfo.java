package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.ModeSignature;
import com.nordnet.opale.enums.deserializer.ModeSignatureDeserialiser;

/**
 * Classe regroupe les informations necessaires pour valider une signature.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class AjoutSignatureInfo {

	/**
	 * mode de signature.
	 */
	@JsonDeserialize(using = ModeSignatureDeserialiser.class)
	private ModeSignature mode;

	/**
	 * constructeur par defaut.
	 */
	public AjoutSignatureInfo() {
	}

	/* Getters and Setters */

	/**
	 * get the mode .
	 * 
	 * @return {@link #mode}
	 */
	public ModeSignature getMode() {
		return mode;
	}

	/**
	 * set the mode.
	 * 
	 * @param mode
	 *            the new {@link #mode}
	 */
	public void setMode(ModeSignature mode) {
		this.mode = mode;
	}

}
