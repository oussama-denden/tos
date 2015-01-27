package com.nordnet.opale.domain.signature;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.deserializer.ModeSignatureDeserialiser;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.enums.ModeSignature;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

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
	@Index(columnNames = "reference", name = "index_signature_reference")
	private String reference;

	/**
	 * refrence du commande.
	 */
	@Index(columnNames = "referenceCommande", name = "index_signature_referenceCommande")
	private String referenceCommande;

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
	private Date timestampSignature;

	/**
	 * date de l'inention de signature.
	 */
	private Date timestampIntention;

	/**
	 * l auteur du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * la date d'annulation.
	 */
	private Date dateAnnulation;

	/**
	 * date creation.
	 */
	private Date dateCreation;

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
	 * get the reference commande.
	 * 
	 * @return {@link #referenceCommande}
	 */
	public String getReferenceCommande() {
		return referenceCommande;
	}

	/**
	 * set the reference commande.
	 * 
	 * @param referenceCommande
	 *            the new {@link #referenceCommande}
	 */
	public void setReferenceCommande(String referenceCommande) {
		this.referenceCommande = referenceCommande;
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
	public Date getTimestampSignature() {
		return timestampSignature;
	}

	/**
	 * set the timestamp.
	 * 
	 * @param timestampSignature
	 *            set the new {@link #timestampSignature}
	 */
	public void setTimestampSignature(Date timestampSignature) {
		this.timestampSignature = timestampSignature;
	}

	/**
	 * 
	 * @return {@link #timestampIntention}
	 */
	public Date getTimestampIntention() {
		return timestampIntention;
	}

	/**
	 * set the date de l'intention.
	 * 
	 * @param timestampIntention
	 *            the new {@link #dateAnnulation}
	 */
	public void setTimestampIntention(Date timestampIntention) {
		this.timestampIntention = timestampIntention;
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
	 * get date d'annulation.
	 * 
	 * @return {@link #dateAnnulation}
	 */
	public Date getDateAnnulation() {
		return dateAnnulation;
	}

	/**
	 * set date d'annulation.
	 * 
	 * @param dateAnnulation
	 *            the new {@link #dateAnnulation}
	 */
	public void setDateAnnulation(Date dateAnnulation) {
		this.dateAnnulation = dateAnnulation;
	}

	/**
	 * 
	 * @return {@link #dateCreation}
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * 
	 * @param dateCreation
	 *            {@link #dateCreation}
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
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
	 * Verifer si la signature est annule.
	 * 
	 * @return return true si la signature est annule.
	 */
	public Boolean isAnnule() {
		return dateAnnulation != null;

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
		signatureInfo.setAuteur(auteur.toAuteurBusiness());

		return signatureInfo;
	}

	/**
	 * methode appel avant la persistance de la signature.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@PrePersist
	@PreUpdate
	public void prePersist() throws OpaleException {
		if (auteur != null && auteur.getIp() != null && auteur.getIp().getTs() == null) {
			auteur.getIp().setTs(PropertiesUtil.getInstance().getDateDuJour());
		}
	}

}
