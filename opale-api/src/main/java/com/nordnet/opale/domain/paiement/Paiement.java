package com.nordnet.opale.domain.paiement;

import java.util.Date;

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
import org.hibernate.validator.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.serializer.DateSerializer;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * Represente un paiement d'une {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "paiement")
@Entity
@JsonIgnoreProperties({ "id", "intension", "paye", "reference" })
public class Paiement {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du tarif.
	 */
	@NotNull
	@Index(columnNames = "reference", name = "index_paiement_reference")
	private String reference;

	/**
	 * reference commande.
	 */
	@Index(columnNames = "referenceCommande", name = "index_paiement_referenceCommande")
	private String referenceCommande;

	/**
	 * {@link ModePaiement}.
	 */
	@Enumerated(EnumType.STRING)
	private ModePaiement modePaiement;

	/**
	 * le montant paye.
	 */
	private Double montant;

	/**
	 * id paiement.
	 */
	private String idPaiement;

	/**
	 * {@link TypePaiement}.
	 */
	@Enumerated(EnumType.STRING)
	private TypePaiement typePaiement;

	/**
	 * date d'intention de paiement.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date timestampIntention;

	/**
	 * date de paiement.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date timestampPaiement;

	/**
	 * l'auteur.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * date annulation.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date dateAnnulation;

	/**
	 * date creation.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date dateCreation;

	/**
	 * constructeur par defaut.
	 */
	public Paiement() {
	}

	/**
	 * creer un paiement a partir d'un {@link PaiementInfoRecurrent}.
	 * 
	 * @param paiementInfo
	 *            {@link PaiementInfoRecurrent}.
	 */
	public Paiement(PaiementInfo paiementInfo) {
		this.modePaiement = paiementInfo.getModePaiement();
		this.montant = paiementInfo.getMontant();
		if (paiementInfo instanceof PaiementInfoComptant) {
			this.idPaiement = ((PaiementInfoComptant) paiementInfo).getReferenceModePaiement();
		} else {
			this.idPaiement = ((PaiementInfoRecurrent) paiementInfo).getRum();
		}

	}

	@Override
	public String toString() {
		return "Paiement [id=" + id + ", reference=" + reference + ", referenceCommande=" + referenceCommande
				+ ", modePaiement=" + modePaiement + ", montant=" + montant + ", typePaiement=" + typePaiement + "]";
	}

	/**
	 * 
	 * @return {@link #id}.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            {@link #id}.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return {@link #reference}.
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #referenceCommande}.
	 */
	public String getReferenceCommande() {
		return referenceCommande;
	}

	/**
	 * 
	 * @param referenceCommande
	 *            {@link #referenceCommande}.
	 */
	public void setReferenceCommande(String referenceCommande) {
		this.referenceCommande = referenceCommande;
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
	 * @return {@link TypePaiement}.
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
	 * @return {@link #idPaiement}
	 */
	public String getIdPaiement() {
		return idPaiement;
	}

	/**
	 * 
	 * @param idPaiement
	 *            {@link #idPaiement}
	 */
	public void setIdPaiement(String idPaiement) {
		this.idPaiement = idPaiement;
	}

	/**
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
	 * 
	 * @return {@link #dateAnnulation}
	 */
	public Date getDateAnnulation() {
		return dateAnnulation;
	}

	/**
	 * 
	 * @param dateAnnulation
	 *            {@link #dateAnnulation}
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
	 * verifier si le payment est encore un intension ou pas.
	 * 
	 * @return true si le payment est une intension.
	 */
	public boolean isIntention() {
		Optional<Date> timestampPaiementOP = Optional.fromNullable(timestampPaiement);
		if (timestampPaiementOP.isPresent()) {
			return false;
		}

		return true;
	}

	/**
	 * verifier si le paiement a ete paye ou non.
	 * 
	 * @return true si le paiement a ete paye.
	 */
	public Boolean isPaye() {
		return !isIntention();
	}

	/**
	 * Verifier si le pâiement est annule.
	 * 
	 * @return true si le paiement est annule.
	 */
	public Boolean isAnnule() {
		return (dateAnnulation != null);
	}

	/**
	 * mapping paiement to business.
	 * 
	 * @return {@link PaiementInfoRecurrent}
	 */
	public PaiementInfoRecurrent fromPaiementToPaiementInfoRecurrent() {
		PaiementInfoRecurrent paiementInfo = new PaiementInfoRecurrent();
		paiementInfo.setAuteur(auteur.toAuteurBusiness());
		paiementInfo.setModePaiement(modePaiement);
		paiementInfo.setMontant(montant);
		paiementInfo.setTimestampIntention(timestampIntention);
		paiementInfo.setTimestampPaiement(timestampPaiement);
		paiementInfo.setReference(reference);
		paiementInfo.setRum(idPaiement);

		return paiementInfo;

	}

	/**
	 * mapping paiement to business.
	 * 
	 * @return {@link PaiementInfoRecurrent}
	 */
	public PaiementInfoComptant fromPaiementToPaiementInfoComptant() {
		PaiementInfoComptant paiementInfo = new PaiementInfoComptant();
		paiementInfo.setAuteur(auteur.toAuteurBusiness());
		paiementInfo.setModePaiement(modePaiement);
		paiementInfo.setMontant(montant);
		paiementInfo.setTimestampIntention(timestampIntention);
		paiementInfo.setTimestampPaiement(timestampPaiement);
		paiementInfo.setReference(reference);
		paiementInfo.setReferenceModePaiement(idPaiement);

		return paiementInfo;

	}

	/**
	 * methode appel avant la persistance du paiement.
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
