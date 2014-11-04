package com.nordnet.opale.business;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.deserializer.ModeSignatureDeserialiser;
import com.nordnet.opale.enums.ModeSignature;

/**
 * Classe regroupe les informations necessaires pour valider une signature.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class AjoutSignatureInfo {

	/**
	 * l'auteur.
	 */
	private Auteur auteur;

	/**
	 * mode de signature.
	 */
	@JsonDeserialize(using = ModeSignatureDeserialiser.class)
	private ModeSignature mode;

	/**
	 * date de l'intention.
	 */
	private Date timestamp;

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

	/**
	 * get the auteur.
	 * 
	 * @return {@link AuteurInfo}
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * set the auteur.
	 * 
	 * @param auteur
	 *            the new {@link AuteurInfo}
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * get the timeStamps.
	 * 
	 * @return {@link #timestamp}
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * set the timeStamp.
	 * 
	 * @param timestamp
	 *            the new {@link #timestamp}
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
