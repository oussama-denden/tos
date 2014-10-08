package com.nordnet.opale.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;

/**
 * entite qui represente une ligne d'un {@link Draft}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "draftligne")
@Entity
@JsonIgnoreProperties({ "id" })
public class DraftLigne {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference de la ligne de draft.
	 */
	private String reference;

	/**
	 * reference de l'offre.
	 */
	private String referenceOffre;

	/**
	 * reference tarif.
	 */
	private String referenceTarif;

	/**
	 * {@link ModePaiement}.
	 */
	@Enumerated(EnumType.STRING)
	private ModePaiement modePaiement;

	/**
	 * {@link ModeFacturation}.
	 */
	@Enumerated(EnumType.STRING)
	private ModeFacturation modeFacturation;

	/**
	 * date de creation de la ligne.
	 */
	private Date dateCreation;

	/**
	 * l auteur du de la ligne du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * liste des {@link DraftLigneDetail} associe a la ligne d'un draft.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "draftLigneId")
	private List<DraftLigneDetail> draftLigneDetails = new ArrayList<DraftLigneDetail>();

	/**
	 * constructeur par defaut.
	 */
	public DraftLigne() {
	}

	/**
	 * creation de {@link DraftLigne} a partir d'une {@link draftLigneInfo}.
	 * 
	 * @param draftLigneInfo
	 *            {@link DraftLigneInfo}.
	 */
	public DraftLigne(DraftLigneInfo draftLigneInfo) {
		this.referenceOffre = draftLigneInfo.getOffre().getReferenceOffre();
		this.referenceTarif = draftLigneInfo.getOffre().getReferenceTarif();
		this.modePaiement = draftLigneInfo.getOffre().getModePaiement();
		this.modeFacturation = draftLigneInfo.getOffre().getModeFacturation();
		this.auteur = new Auteur(draftLigneInfo.getAuteur());
		for (Detail detail : draftLigneInfo.getOffre().getDetails()) {
			draftLigneDetails.add(new DraftLigneDetail(detail));
		}
	}

	@Override
	public String toString() {
		return "DraftLigne [id=" + id + ", reference=" + reference + ", referenceOffre=" + referenceOffre
				+ ", referenceTarif=" + referenceTarif + ", modePaiement=" + modePaiement + ", modeFacturation="
				+ modeFacturation + ", dateCreation=" + dateCreation + ", auteur=" + auteur + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		DraftLigne rhs = (DraftLigne) obj;
		return new EqualsBuilder().append(id, rhs.id).append(referenceOffre, rhs.referenceOffre)
				.append(referenceTarif, rhs.referenceTarif).isEquals();
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
	 * @return {@link #reference}
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
	 * @return {@link #referenceOffre}.
	 */
	public String getReferenceOffre() {
		return referenceOffre;
	}

	/**
	 * 
	 * @param referenceOffre
	 *            {@link #referenceOffre}.
	 */
	public void setReferenceOffre(String referenceOffre) {
		this.referenceOffre = referenceOffre;
	}

	/**
	 * 
	 * @return {@link #referenceTarif}.
	 */
	public String getReferenceTarif() {
		return referenceTarif;
	}

	/**
	 * 
	 * @param referenceTarif
	 *            {@link #referenceTarif}.
	 */
	public void setReferenceTarif(String referenceTarif) {
		this.referenceTarif = referenceTarif;
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
	 * @return {@link ModeFacturation}.
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * 
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * 
	 * @return {@link #dateCreation}.
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * 
	 * @param dateCreation
	 *            {@link #dateCreation}.
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * 
	 * @return {@link Auteur}.
	 */
	public Auteur getAuteur() {
		return auteur;
	}

	/**
	 * 
	 * @param auteur
	 *            {@link Auteur}.
	 */
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	/**
	 * 
	 * @return {@link #draftLigneDetails}
	 */
	public List<DraftLigneDetail> getDraftLigneDetails() {
		return draftLigneDetails;
	}

	/**
	 * 
	 * @param draftLigneDetails
	 *            {@link #draftLigneDetails}.
	 */
	public void setDraftLigneDetails(List<DraftLigneDetail> draftLigneDetails) {
		this.draftLigneDetails = draftLigneDetails;
	}

	/**
	 * ajouter une {@link DraftLigneDetail} a la list associe a une ligne draft.
	 * 
	 * @param draftLigneDetail
	 *            {@link DraftLigneDetail}.
	 */
	public void addDraftLigneDetail(DraftLigneDetail draftLigneDetail) {
		this.draftLigneDetails.add(draftLigneDetail);
	}

}
