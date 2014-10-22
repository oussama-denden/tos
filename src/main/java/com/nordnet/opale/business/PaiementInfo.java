package com.nordnet.opale.business;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.enums.deserializer.ModePaiementDeserializer;
import com.nordnet.opale.enums.deserializer.TypePaiementDeserializer;

/**
 * classe pour transferer les info de paiement.
 * 
 * @author akram-moncer
 * 
 */
@JsonInclude(Include.NON_NULL)
public class PaiementInfo {

	/**
	 * l'auteur.
	 */
	private Auteur auteur;

	/**
	 * reference de paiement.
	 */
	private String reference;

	/**
	 * {@link ModePaiement}.
	 */
	@JsonDeserialize(using = ModePaiementDeserializer.class)
	private ModePaiement modePaiement;

	/**
	 * montant de paiement.
	 */
	private Double montant;

	/**
	 * info paiement.
	 */
	private String infoPaiement;

	/**
	 * {@link TypePaiement}.
	 */
	@JsonDeserialize(using = TypePaiementDeserializer.class)
	private TypePaiement typePaiement;

	/**
	 * date d'intention de paiement.
	 */
	private Date timestampIntention;

	/**
	 * date de paiement.
	 */
	private Date timestampPaiement;

	/**
	 * constructeur par defaut.
	 */
	public PaiementInfo() {
	}

	public PaiementInfo(ModePaiement modePaiement, Double montant, String infoPaiement, TypePaiement typePaiement,
			Date timestampIntention, Date timestampPaiement) {
		super();
		this.modePaiement = modePaiement;
		this.montant = montant;
		this.infoPaiement = infoPaiement;
		this.typePaiement = typePaiement;
		this.timestampIntention = timestampIntention;
		this.timestampPaiement = timestampPaiement;
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
	 * 
	 * @return {@link ModePaiement}.
	 */
	public ModePaiement getModePaiement() {
		return modePaiement;
	}

	/**
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 */
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}

	/**
	 * 
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * 
	 * @param montant
	 *            {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * 
	 * @return {@link #infoPaiement}.
	 */
	public String getInfoPaiement() {
		return infoPaiement;
	}

	/**
	 * 
	 * @param infoPaiement
	 *            {@link #infoPaiement}.
	 */
	public void setInfoPaiement(String infoPaiement) {
		this.infoPaiement = infoPaiement;
	}

	/**
	 * 
	 * @return {@link #typePaiement}.
	 */
	public TypePaiement getTypePaiement() {
		return typePaiement;
	}

	/**
	 * 
	 * @param typePaiement
	 *            {@link TypePaiement}.
	 */
	public void setTypePaiement(TypePaiement typePaiement) {
		this.typePaiement = typePaiement;
	}

	/**
	 * 
	 * @return {@link #timestampIntention}.
	 */
	public Date getTimestampIntention() {
		return timestampIntention;
	}

	/**
	 * 
	 * @param timestampIntention
	 *            {@link #timestampIntention}.
	 */
	public void setTimestampIntention(Date timestampIntention) {
		this.timestampIntention = timestampIntention;
	}

	/**
	 * 
	 * @return {@link #timestampPaiement}.
	 */
	public Date getTimestampPaiement() {
		return timestampPaiement;
	}

	/**
	 * 
	 * @param timestampPaiement
	 *            {@link #timestampPaiement}.
	 */
	public void setTimestampPaiement(Date timestampPaiement) {
		this.timestampPaiement = timestampPaiement;
	}

}
