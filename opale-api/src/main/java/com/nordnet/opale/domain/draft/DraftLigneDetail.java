package com.nordnet.opale.domain.draft;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Tarif;
import com.nordnet.opale.util.Utils;
import com.nordnet.topaze.ws.entity.ElementContractuel;

/**
 * contient les detail d'une {@link DraftLigne} dans un {@link Draft}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "draftlignedetail")
@Entity
@JsonIgnoreProperties({ "id", "sousDraftLigneDetails", "draftLigneDetailParent", "parent" })
public class DraftLigneDetail {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * numero element contractuel, utilise dans topaze.
	 */
	private Integer numEC;

	/**
	 * reference selection.
	 */
	private String referenceSelection;

	/**
	 * reference choix.
	 */
	private String referenceChoix;

	/**
	 * reference tarif.
	 */
	private String referenceTarif;

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
	private List<DraftLigneDetail> sousDraftLigneDetails = new ArrayList<>();

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
		if (!Utils.isStringNullOrEmpty(detail.getReferenceTarif())) {
			this.referenceTarif = detail.getReferenceTarif();
		}
		this.referenceChoix = detail.getReferenceChoix();
		if (!Utils.isStringNullOrEmpty(detail.getConfigurationJson())) {
			this.configurationJson = detail.getConfigurationJson();
		}
	}

	/**
	 * creation d'un DraftLigneDetail a partir d'un {@link CommandeLigneDetail}.
	 * 
	 * @param commandeLigneDetail
	 *            {@link CommandeLigneDetail}.
	 */
	public DraftLigneDetail(CommandeLigneDetail commandeLigneDetail) {
		this.referenceSelection = commandeLigneDetail.getReferenceSelection();
		this.referenceChoix = commandeLigneDetail.getReferenceChoix();
		Tarif tarif = commandeLigneDetail.getTarif();
		this.referenceTarif = tarif != null ? tarif.getReference() : null;
		this.configurationJson = commandeLigneDetail.getConfigurationJson();
	}

	/**
	 * creation draftLigneDetail a partir de l'{@link ElementContractuel}.
	 * 
	 * @param elementContractuel
	 *            {@link ElementContractuel}.
	 * @param offreCatalogue
	 *            {@link OffreCatalogue}.
	 */
	public DraftLigneDetail(ElementContractuel elementContractuel, OffreCatalogue offreCatalogue) {
		this.referenceChoix = elementContractuel.getReferenceProduit();
		this.referenceSelection = offreCatalogue.findReferenceSelection(elementContractuel.getReferenceProduit());
		this.referenceTarif = elementContractuel.getReferenceTarif();
		this.numEC = elementContractuel.getNumEC();
		this.referenceTarif = elementContractuel.getReferenceTarif();
	}

	@Override
	public String toString() {
		return "DraftLigneDetail [id=" + id + ", numEC=" + numEC + ", referenceSelection=" + referenceSelection
				+ ", referenceChoix=" + referenceChoix + ", referenceTarif=" + referenceTarif + ", configurationJson="
				+ configurationJson + "]";
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
	 * @return {@link #numEC}.
	 */
	public Integer getNumEC() {
		return numEC;
	}

	/**
	 * 
	 * @param numEC
	 *            {@link #numEC}.
	 */
	public void setNumEC(Integer numEC) {
		this.numEC = numEC;
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
	 * @return {@link #referenceChoix}.
	 */
	public String getReferenceChoix() {
		return referenceChoix;
	}

	/**
	 * 
	 * @param referenceChoix
	 *            {@link #referenceChoix}.
	 */
	public void setReferenceChoix(String referenceChoix) {
		this.referenceChoix = referenceChoix;
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

	/**
	 * reference du produit parent de draft ligne detail.
	 * 
	 * @return reference produit parent.
	 */
	public String getdependDe() {
		if (draftLigneDetailParent != null) {
			return draftLigneDetailParent.getReferenceChoix();
		}
		return null;
	}

}
