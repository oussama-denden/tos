package com.nordnet.opale.domain.commande;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Index;

import com.google.common.base.Optional;
import com.nordnet.opale.business.DetailCommandeLigneInfo;
import com.nordnet.opale.business.catalogue.Choice;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.TypeProduit;
import com.nordnet.opale.util.Utils;
import com.nordnet.topaze.ws.entity.Produit;

/**
 * Contient les informations lie a une offre dans la commande.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "commandelignedetail")
@Entity
public class CommandeLigneDetail {

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
	 * {@link TypeProduit}.
	 */
	@Enumerated(EnumType.STRING)
	private TypeProduit typeProduit;

	/**
	 * reference choix.
	 */
	@Index(columnNames = "referenceChoix", name = "index_commandeLigneDetail_referenceChoix")
	private String referenceChoix;

	/**
	 * label du produit.
	 */
	@Index(columnNames = "label", name = "index_commandeLigneDetail_label")
	private String label;

	/**
	 * configuration json.
	 */
	private String configurationJson;

	/**
	 * {@link CommandeLigneDetail}.
	 */
	@ManyToOne
	@JoinColumn(name = "dependDe")
	private CommandeLigneDetail commandeLigneDetailParent;

	/**
	 * les sous {@link CommandeLigneDetail}.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commandeLigneDetailParent")
	private List<CommandeLigneDetail> sousCommandeLigneDetails = new ArrayList<>();

	/**
	 * {@link Tarif}.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tarifId")
	private Tarif tarif;

	/**
	 * constructeur par defaut.
	 */
	public CommandeLigneDetail() {
	}

	/**
	 * creation d'un {@link CommandeLigneDetail}.
	 * 
	 * @param detail
	 *            {@link DraftLigneDetail}.
	 * @param detailCatalogue
	 *            {@link DetailCatalogue}.
	 */
	public CommandeLigneDetail(DraftLigneDetail detail, DetailCatalogue detailCatalogue) {
		this.numEC = detail.getNumEC();
		this.referenceSelection = detail.getReferenceSelection();
		this.typeProduit = TypeProduit.fromString(detailCatalogue.getType().name());
		this.configurationJson = detail.getConfigurationJson();
		Choice choice = detailCatalogue.getChoiceMap().get(detail.getReferenceChoix());
		this.referenceChoix = detail.getReferenceChoix();
		this.label = choice.getLabel();
		if (!Utils.isStringNullOrEmpty(detail.getReferenceTarif())) {
			this.tarif = new Tarif(choice.getTarifsMap().get(detail.getReferenceTarif()));
		}
	}

	@Override
	public String toString() {
		return "CommandeLigneDetail [id=" + id + ", configurationJson=" + configurationJson + "]";
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
	 * @return {@link TypeProduit}.
	 */
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

	/**
	 * 
	 * @param typeProduit
	 *            {@link TypeProduit}.
	 */
	public void setTypeProduit(TypeProduit typeProduit) {
		this.typeProduit = typeProduit;
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
	 * @return {@link #label}.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            {@link #label}.
	 */
	public void setLabel(String label) {
		this.label = label;
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
	 * @return {@link CommandeLigneDetail}.
	 */
	public CommandeLigneDetail getCommandeLigneDetailParent() {
		return commandeLigneDetailParent;
	}

	/**
	 * 
	 * @param commandeLigneDetailParent
	 *            {@link CommandeLigneDetail}.
	 */
	public void setCommandeLigneDetailParent(CommandeLigneDetail commandeLigneDetailParent) {
		this.commandeLigneDetailParent = commandeLigneDetailParent;
	}

	/**
	 * 
	 * @return {@link #sousCommandeLigneDetails}.
	 */
	public List<CommandeLigneDetail> getSousCommandeLigneDetails() {
		return sousCommandeLigneDetails;
	}

	/**
	 * 
	 * @param sousCommandeLigneDetails
	 *            {@link #sousCommandeLigneDetails}.
	 */
	public void setSousCommandeLigneDetails(List<CommandeLigneDetail> sousCommandeLigneDetails) {
		this.sousCommandeLigneDetails = sousCommandeLigneDetails;
	}

	/**
	 * @return {@link Tarif}.
	 */
	public Tarif getTarif() {
		return tarif;
	}

	/**
	 * @param tarif
	 *            {@link Tarif}.
	 */
	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	/**
	 * recuperer commande ligne business a paritr de command ligne domain.
	 * 
	 * @return {@link DetailCommandeLigneInfo}
	 */
	public DetailCommandeLigneInfo toDetailCommandeLigneInfo() {
		DetailCommandeLigneInfo detailCommandeLigneInfo = new DetailCommandeLigneInfo();
		detailCommandeLigneInfo.setReference(referenceChoix);
		detailCommandeLigneInfo.setLabel(label);
		Optional<Tarif> tarifOptional = Optional.fromNullable(tarif);
		if (tarifOptional.isPresent()) {
			detailCommandeLigneInfo.setTarif(tarif.toTarifInfo());
		}
		detailCommandeLigneInfo.setTypeProduit(typeProduit);
		return detailCommandeLigneInfo;

	}

	/**
	 * verifie si le commandeDetailLigne est recurrent ou non.
	 * 
	 * @return true si le commandeDetailLigne est recurrent.
	 */
	public boolean isRecurrent() {
		if (tarif.isRecurrent()) {
			return true;
		}
		return false;
	}

	/**
	 * transfromer un {@link CommandeLigneDetail} vers un {@link Produit}.
	 * 
	 * @param referenceCommande
	 *            refrence du commande.
	 * @param numEC
	 *            le numero d'offre.
	 * @param numECParent
	 *            numero d'offre parent.
	 * @param modeFacturation
	 *            {@link ModeFacturation}.
	 * @param paiement
	 *            {@link Paiement}
	 * @return {@link Produit}.
	 */
	public Produit toProduit(String referenceCommande, int numEC, Integer numECParent, ModeFacturation modeFacturation,
			List<Paiement> paiement) {
		Produit produit = new Produit();
		produit.setLabel(label);
		produit.setNumEC(numEC);
		produit.setNumeroCommande(referenceCommande);
		produit.setTypeProduit(com.nordnet.topaze.ws.enums.TypeProduit.fromString(typeProduit.toString()));
		produit.setReference(referenceChoix);
		Optional<Tarif> tarifOptional = Optional.fromNullable(tarif);
		if (tarifOptional.isPresent()) {
			produit.setReferenceTarif(tarif.getReference());
			produit.setPrix(tarif.toPrix(modeFacturation, paiement));
		}
		produit.setNumECParent(numECParent);
		return produit;
	}

	/**
	 * verifier si un element est parent ou non.
	 * 
	 * @return true si l'element est parent.
	 */
	public boolean isParent() {
		Optional<CommandeLigneDetail> commandeLigneDetailOptional = Optional.fromNullable(commandeLigneDetailParent);
		if (commandeLigneDetailOptional.isPresent()) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof CommandeLigneDetail || obj instanceof DraftLigneDetail)) {
			return false;
		}
		if (obj instanceof CommandeLigneDetail) {
			CommandeLigneDetail rhs = (CommandeLigneDetail) obj;
			return new EqualsBuilder().append(referenceSelection, rhs.referenceSelection)
					.append(referenceChoix, rhs.referenceChoix).isEquals();
		}
		DraftLigneDetail draftLigneDetail = (DraftLigneDetail) obj;
		return new EqualsBuilder().append(referenceSelection, draftLigneDetail.getReferenceSelection())
				.append(referenceChoix, draftLigneDetail.getReferenceChoix()).isEquals();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(id).append(referenceSelection).append(referenceChoix).toHashCode();
	}
}
