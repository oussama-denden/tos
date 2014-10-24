package com.nordnet.opale.business;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nordnet.opale.enums.ModeSignature;

/**
 * Classi qui groupe les informations de signature.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@JsonInclude(Include.NON_NULL)
public class SignatureInfo {

	/**
	 * l'auteur.
	 */
	private Auteur auteur;

	/**
	 * le mode de signature.
	 */
	private ModeSignature mode;

	/**
	 * l'id de signature.
	 */
	private String idSignature;

	/**
	 * footprint de signature.
	 */
	private String footprint;

	/**
	 * date de signature.
	 */
	private Date timestamp;

	/**
	 * contrcteur par defaut.
	 */
	public SignatureInfo() {

	}

	/* Getters and Setters */

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
	 * get the mode.
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
	 * get the id of signature.
	 * 
	 * @return {@link #idSignature}
	 */
	public String getIdSignature() {
		return idSignature;
	}

	/**
	 * set the id of signature.
	 * 
	 * @param idSignature
	 *            the new {@link #idSignature}
	 */
	public void setIdSignature(String idSignature) {
		this.idSignature = idSignature;
	}

	/**
	 * get the footprint.
	 * 
	 * @return {@link #footprint}
	 */
	public String getFootprint() {
		return footprint;
	}

	/**
	 * set the footprint.
	 * 
	 * @param footprint
	 *            the new {@link #footprint}
	 */
	public void setFootprint(String footprint) {
		this.footprint = footprint;
	}

	/**
	 * get the timestamp.
	 * 
	 * @return {@link #timestamp}
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * set the timestamp.
	 * 
	 * @param timestamp
	 *            set the new {@link #timestamp}
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
