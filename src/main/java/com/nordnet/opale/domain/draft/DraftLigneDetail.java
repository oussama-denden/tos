package com.nordnet.opale.domain.draft;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.enums.ModePaiement;

/**
 * contient les detail d'une {@link DraftLigne} dans un {@link Draft}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "draftlignedetail")
@Entity
@JsonIgnoreProperties({ "id" })
public class DraftLigneDetail {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference selection.
	 */
	private String referenceSelection;

	/**
	 * reference de la ligne dans le draft.
	 */
	private String reference;

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
	 * configuration json.
	 */
	private String configurationJson;

	/**
	 * {@link DraftLigneDetail}.
	 */
	@ManyToOne
	@JoinColumn(name = "dependDe")
	private DraftLigneDetail draftLigneDetailParent;

	/**
	 * list des sous {@link DraftLigneDetail}.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "draftLigneDetailParent")
	private List<DraftLigneDetail> sousDraftLigneDetails = new ArrayList<DraftLigneDetail>();

	/**
	 * constructeur par defaut.
	 */
	public DraftLigneDetail() {
	}

	/**
	 * creation de {@link DraftLigneDetail} a partir de {@link Detail}.
	 * 
	 * @param detail
	 *            {@link Detail}.
	 */
	public DraftLigneDetail(Detail detail) {
		this.referenceSelection = detail.getReferenceSelection();
		this.reference = detail.getReference();
		this.referenceTarif = detail.getReferenceTarif();
		this.modePaiement = detail.getModePaiement();
		this.configurationJson = detail.getConfigurationJson();
	}

	@Override
	public String toString() {
		return "DraftLigneDetail [id=" + id + ", reference=" + reference + ", referenceTarif=" + referenceTarif
				+ ", modePaiement=" + modePaiement + ", configurationJson=" + configurationJson + "]";
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
	 * @return {@link #referenceSelection}.
	 */
	public String getReferenceSelection() {
		return referenceSelection;
	}

	/**
	 * 
	 * @param referenceSelection
	 *            {@link #referenceSelection}.
	 */
	public void setReferenceSelection(String referenceSelection) {
		this.referenceSelection = referenceSelection;
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
	 * @return {@link #configurationJson}.
	 */
	public String getConfigurationJson() {
		return configurationJson;
	}

	/**
	 * 
	 * @param configurationJson
	 *            {@link #configurationJson}.
	 */
	public void setConfigurationJson(String configurationJson) {
		this.configurationJson = configurationJson;
	}

	/**
	 * 
	 * @return {@link DraftLigneDetail}.
	 */
	public DraftLigneDetail getDraftLigneDetailParent() {
		return draftLigneDetailParent;
	}

	/**
	 * 
	 * @param draftLigneDetailParent
	 *            {@link DraftLigneDetail}.
	 */
	public void setDraftLigneDetailParent(DraftLigneDetail draftLigneDetailParent) {
		this.draftLigneDetailParent = draftLigneDetailParent;
	}

	/**
	 * 
	 * @return {@link #sousDraftLigneDetails}.
	 */
	public List<DraftLigneDetail> getSousDraftLigneDetails() {
		return sousDraftLigneDetails;
	}

	/**
	 * 
	 * @param sousDraftLigneDetails
	 *            {@link #sousDraftLigneDetails}.
	 */
	public void setSousDraftLigneDetails(List<DraftLigneDetail> sousDraftLigneDetails) {
		this.sousDraftLigneDetails = sousDraftLigneDetails;
	}

	/**
	 * verifier si un detail du draft est parent ou non.
	 * 
	 * @return true si le detail du draft est un parent.
	 */
	public Boolean isParent() {
		if (draftLigneDetailParent != null) {
			return false;
		}
		return true;
	}

}
