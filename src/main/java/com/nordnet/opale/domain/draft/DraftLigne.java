package com.nordnet.opale.domain.draft;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.business.commande.ElementContractuel;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Tarif;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

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
	 * numero element contractuel, utilise dans topaze.
	 */
	private Integer numEC;

	/**
	 * reference de l'offre.
	 */
	private String referenceOffre;

	/**
	 * reference tarif.
	 */
	private String referenceTarif;

	/**
	 * reference {@link Contrat}.
	 */
	private String referenceContrat;

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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
		for (Detail detail : draftLigneInfo.getOffre().getDetails()) {
			draftLigneDetails.add(new DraftLigneDetail(detail));
		}
	}

	/**
	 * Creation d'un DraftLigne a partir du {@link Contrat} et {@link TrameCatalogue}.
	 * 
	 * @param contrat
	 *            {@link Contrat}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public DraftLigne(Contrat contrat, TrameCatalogue trameCatalogue) {
		Auteur auteur = new Auteur(trameCatalogue.getAuteur());
		this.auteur = auteur;
		ElementContractuel elementContractuelParent = contrat.getParent();
		this.referenceContrat = contrat.getReference();
		this.referenceOffre = elementContractuelParent.getReferenceProduit();
		this.referenceTarif = elementContractuelParent.getReferenceTarif();
		this.numEC = elementContractuelParent.getNumEC();
		OffreCatalogue offreCatalogue = trameCatalogue.getOffreMap().get(this.referenceOffre);
		for (ElementContractuel elementContractuel : contrat.getSousContrats()) {
			if (!elementContractuel.isParent()) {
				addDraftLigneDetail(new DraftLigneDetail(elementContractuel, offreCatalogue));
			}
		}
		creerArboressence(contrat.getSousContrats());
	}

	/**
	 * creation de {@link DraftLigne} a partir d'une {@link draftLigneInfo} et.
	 * 
	 * @param draftLigneInfo
	 *            the draft ligne info
	 * @param auteur
	 *            l auteur {@link Auteur}. {@link DraftLigneInfo}.
	 */
	public DraftLigne(DraftLigneInfo draftLigneInfo, com.nordnet.opale.business.Auteur auteur) {
		this.referenceOffre = draftLigneInfo.getOffre().getReferenceOffre();
		this.referenceTarif = draftLigneInfo.getOffre().getReferenceTarif();
		this.auteur = auteur.toDomain();
		for (Detail detail : draftLigneInfo.getOffre().getDetails()) {
			draftLigneDetails.add(new DraftLigneDetail(detail));
		}
	}

	/**
	 * 
	 * creation d'un draftLigne a partir d'un {@link CommandeLigne}.
	 * 
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 */
	public DraftLigne(CommandeLigne commandeLigne) {
		this.referenceOffre = commandeLigne.getReferenceOffre();

		Tarif tarif = commandeLigne.getTarif();
		this.referenceTarif = tarif != null ? tarif.getReference() : null;
		this.auteur = commandeLigne.getAuteur();

		this.dateCreation = commandeLigne.getDateCreation();
		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			addDraftLigneDetail(new DraftLigneDetail(commandeLigneDetail));
		}
		creerArborescence(commandeLigne.getCommandeLigneDetails());
	}

	@Override
	public String toString() {
		return "DraftLigne [id=" + id + ", reference=" + reference + ", referenceOffre=" + referenceOffre
				+ ", referenceTarif=" + referenceTarif + ", dateCreation=" + dateCreation + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(Object obj)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(referenceOffre).append(referenceTarif).toHashCode();
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
	 * @return {@link #referenceContrat}.
	 */
	public String getReferenceContrat() {
		return referenceContrat;
	}

	/**
	 * 
	 * @param referenceContrat
	 *            {@link #referenceContrat}.
	 */
	public void setReferenceContrat(String referenceContrat) {
		this.referenceContrat = referenceContrat;
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

	/**
	 * creer l'arborescence entre les {@link DraftLigneDetail}.
	 * 
	 * @param commandeDetails
	 *            liste des {@link CommandeLigneDetail}.
	 */
	private void creerArborescence(List<CommandeLigneDetail> commandeDetails) {
		Map<String, DraftLigneDetail> draftLigneDetailsMap = getDraftLigneDetailsMap();

		for (CommandeLigneDetail commandeLigneDetail : commandeDetails) {
			if (!commandeLigneDetail.isParent()) {
				DraftLigneDetail draftLigneDetail = draftLigneDetailsMap.get(commandeLigneDetail.getReferenceChoix());
				DraftLigneDetail draftLigneDetailParent =
						draftLigneDetailsMap
								.get(commandeLigneDetail.getCommandeLigneDetailParent().getReferenceChoix());
				draftLigneDetail.setDraftLigneDetailParent(draftLigneDetailParent);
			}
		}
	}

	/**
	 * creer l'arborescence entre les {@link DraftLigneDetail}.
	 * 
	 * @param elementContractuels
	 *            liste des {@link ElementContractuel}.
	 */
	private void creerArboressence(List<ElementContractuel> elementContractuels) {
		Map<String, DraftLigneDetail> draftLigneDetailsMap = getDraftLigneDetailsMap();
		Map<Integer, ElementContractuel> elementContractuelsMap = new HashMap<Integer, ElementContractuel>();
		for (ElementContractuel elementContractuel : elementContractuels) {
			elementContractuelsMap.put(elementContractuel.getNumEC(), elementContractuel);
		}
		for (ElementContractuel elementContractuel : elementContractuels) {
			/*
			 * definier la relation de dependance entre les detail. pas besoin de definir la relation de dependance
			 * entre ligne et detail vue qu'elle est evidente.
			 */
			if (!elementContractuel.isParent() && elementContractuel.getNumECParent() != this.numEC) {
				ElementContractuel elementContractuelParent =
						elementContractuelsMap.get(elementContractuel.getNumECParent());
				DraftLigneDetail draftLigneDetail = draftLigneDetailsMap.get(elementContractuel.getReferenceProduit());
				DraftLigneDetail draftLigneDetailParent =
						draftLigneDetailsMap.get(elementContractuelParent.getReferenceProduit());
				draftLigneDetail.setDraftLigneDetailParent(draftLigneDetailParent);
			}
		}
	}

	/**
	 * transformer la liste des {@link DraftLigneDetail} en Map.
	 * 
	 * @return map des {@link DraftLigneDetail}.
	 */
	private Map<String, DraftLigneDetail> getDraftLigneDetailsMap() {
		Map<String, DraftLigneDetail> draftLigneDetailsMap = new HashMap<String, DraftLigneDetail>();
		for (DraftLigneDetail draftLigneDetail : this.draftLigneDetails) {
			draftLigneDetailsMap.put(draftLigneDetail.getReferenceChoix(), draftLigneDetail);
		}
		return draftLigneDetailsMap;
	}

	/**
	 * methode appel avant la persistance du draft ligne.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@PrePersist
	public void prePersist() throws OpaleException {
		if (auteur != null && auteur.getTimestamp() == null) {
			auteur.setTimestamp(PropertiesUtil.getInstance().getDateDuJour());
		}
	}
}
