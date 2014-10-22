package com.nordnet.opale.domain.signature;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.enums.ModeSignature;
import com.nordnet.opale.enums.deserializer.ModeSignatureDeserialiser;

/**
 * Classi qui groupe les informations de signature.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Table(name = "signature")
@Entity
public class Signature {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * le mode de signature.
	 */
	@JsonDeserialize(using = ModeSignatureDeserialiser.class)
	@Enumerated(EnumType.STRING)
	private ModeSignature mode;

	/**
	 * reference de signature.
	 */
	private String reference;

	/**
	 * l'id de signature.
	 */
	private String idSignature;

	/**
	 * footprint de signature.
	 */
	@Column(columnDefinition = "TINYTEXT")
	private String footprint;

	/**
	 * date de signature.
	 */
	private Long timestampSignature;

	/**
	 * l auteur du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * contrcteur par defaut.
	 */
	public Signature() {

	}

	/* Getters and Setters */

	/**
	 * get the id.
	 * 
	 * @return {@link #id}
	 */
	public int getId() {
		return id;
	}

	/**
	 * set the id.
	 * 
	 * @param id
	 *            the new {@link #id}
	 */
	public void setId(int id) {
		this.id = id;
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
	 * get the reference.
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * set the reference.
	 * 
	 * @param reference
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
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
	public Long getTimestampSignature() {
		return timestampSignature;
	}

	/**
	 * set the timestamp.
	 * 
	 * @param timestampSignature
	 *            set the new {@link #timestampSignature}
	 */
	public void setTimestampSignature(Long timestampSignature) {
		this.timestampSignature = timestampSignature;
	}

	/**
	 * 
	 * @return {@link #auteur}.
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link #auteur}.
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * verfier si la signature associe a une commande est signe.
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean isSigne() {
		return (mode != null && idSignature != null && timestampSignature != null);
	}

	/**
	 * transformer signature en business .
	 * 
	 * @return {@link SignatureInfo}
	 */
	public SignatureInfo toSignatureInfo() {
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setMode(mode);
		signatureInfo.setIdSignature(idSignature);
		signatureInfo.setFootprint(footprint);
		signatureInfo.setTimestamp(timestampSignature);

		return signatureInfo;
	}

}
