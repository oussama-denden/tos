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
import com.google.common.base.Optional;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

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
	 * info paiement.
	 */
	private String infoPaiement;

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
	private Date timestampIntention;

	/**
	 * date de paiement.
	 */
	private Date timestampPaiement;

	/**
	 * l'auteur.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * date annulation.
	 */
	private Date dateAnnulation;

	/**
	 * constructeur par defaut.
	 */
	public Paiement() {
	}

	/**
	 * creer un paiement a partir d'un {@link PaiementInfo}.
	 * 
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 */
	public Paiement(PaiementInfo paiementInfo) {
		this.modePaiement = paiementInfo.getModePaiement();
		this.montant = paiementInfo.getMontant();
		this.infoPaiement = paiementInfo.getInfoPaiement();
		this.idPaiement = paiementInfo.getIdPaiement();
	}

	@Override
	public String toString() {
		return "Paiement [id=" + id + ", reference=" + reference + ", referenceCommande=" + referenceCommande
				+ ", modePaiement=" + modePaiement + ", montant=" + montant + ", infoPaiement=" + infoPaiement
				+ ", typePaiement=" + typePaiement + "]";
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
	 * @return {@link PaiementInfo}
	 */
	public PaiementInfo fromPaiementToPaiementInfo() {
		PaiementInfo paiementInfo = new PaiementInfo();
		paiementInfo.setAuteur(auteur.toAuteurBusiness());
		paiementInfo.setInfoPaiement(infoPaiement);
		paiementInfo.setModePaiement(modePaiement);
		paiementInfo.setMontant(montant);
		paiementInfo.setTimestampIntention(timestampIntention);
		paiementInfo.setTimestampPaiement(timestampPaiement);
		paiementInfo.setReference(reference);
		paiementInfo.setIdPaiement(idPaiement);

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
		if (auteur != null && auteur.getTimestamp() == null) {
			auteur.setTimestamp(PropertiesUtil.getInstance().getDateDuJour());
		}
	}

}