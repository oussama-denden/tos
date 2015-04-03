package com.nordnet.opale.domain.commande;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.google.common.base.Optional;
import com.nordnet.opale.business.DetailCommandeLigneInfo;
import com.nordnet.opale.business.OffreCatalogueInfo;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.TypeProduit;
import com.nordnet.opale.util.Constants;
import com.nordnet.topaze.ws.entity.Contrat;
import com.nordnet.topaze.ws.entity.ContratPreparationInfo;
import com.nordnet.topaze.ws.entity.Produit;

/**
 * Classe represente une ligne (offre) dans la {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "commandeligne")
@Entity
public class CommandeLigne {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference de la ligne.
	 */
	private String reference;

	/**
	 * numero de la ligne dans la commande.
	 */
	private Integer numero;

	/**
	 * numero element contractuel, utilise dans topaze.
	 */
	private Integer numEC;

	/**
	 * reference de l'offre.
	 */
	private String referenceOffre;

	/**
	 * reference du contrat.
	 */
	@Index(columnNames = "referenceContrat", name = "index_commandeLigne_referenceOffre")
	private String referenceContrat;

	/**
	 * label de l'offre dans le catalogue.
	 */
	@Index(columnNames = "label", name = "index_commandeLigne_label")
	private String label;

	/**
	 * gamme de l'offre.
	 */
	private String gamme;

	/**
	 * secteur.
	 */
	private String secteur;

	/**
	 * {@link TypeProduit}.
	 */
	@Enumerated(EnumType.STRING)
	private TypeProduit typeProduit;

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
	 * liste des {@link CommandeLigneDetail} associe au ligne dans la commande.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "commandeLigneId")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CommandeLigneDetail> commandeLigneDetails = new ArrayList<CommandeLigneDetail>();

	/**
	 * liste des {@link Tarif} associe a la ligne de commande.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tarifId")
	private Tarif tarif;

	/**
	 * Le geste effectue.
	 */
	@Enumerated(EnumType.STRING)
	private Geste geste;

	/**
	 * Cause de non transformation.
	 */
	@Type(type = "text")
	private String causeNonTransformation;

	/**
	 * date de transformation du commande vers contrat.
	 */
	private Date dateTransformationContrat;

	/**
	 * @return {@link #dateTransformationContrat}.
	 */
	public Date getDateTransformationContrat() {
		return dateTransformationContrat;
	}

	/**
	 * @param dateTransformationContrat
	 *            {@link #dateTransformationContrat}.
	 */
	public void setDateTransformationContrat(Date dateTransformationContrat) {
		this.dateTransformationContrat = dateTransformationContrat;
	}

	/**
	 * constructeur par defaut.
	 */
	public CommandeLigne() {
	}

	/**
	 * creation d'une ligne de commande a partir du {@link DraftLigne} et de {@link TrameCatalogue}.
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}.
	 * @param offreCatalogue
	 *            {@link OffreCatalogue}.
	 */
	public CommandeLigne(DraftLigne draftLigne, OffreCatalogue offreCatalogue) {
		this.numEC = draftLigne.getNumEC();
		this.referenceOffre = draftLigne.getReferenceOffre();
		this.referenceContrat = draftLigne.getReferenceContrat();
		this.gamme = offreCatalogue.getGamme();
		this.secteur = offreCatalogue.getSecteur();
		this.label = offreCatalogue.getLabel();
		this.typeProduit = TypeProduit.fromString(offreCatalogue.getType().name());
		this.modeFacturation = offreCatalogue.getModeFacturation();
		this.auteur = draftLigne.getAuteur();
		this.dateCreation = draftLigne.getDateCreation();
		this.tarif = new Tarif(offreCatalogue.getTarifsMap().get(draftLigne.getReferenceTarif()));
		this.geste = draftLigne.getGeste() == null ? Geste.VENTE : draftLigne.getGeste();
		DetailCatalogue detailCatalogue = null;
		for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
			detailCatalogue = offreCatalogue.getDetailsMap().get(detail.getReferenceSelection());
			CommandeLigneDetail commandeLigneDetail = new CommandeLigneDetail(detail, detailCatalogue);
			addCommandeLigneDetail(commandeLigneDetail);
		}
		creerArborescence(draftLigne.getDraftLigneDetails(), this.commandeLigneDetails);
	}

	@Override
	public String toString() {
		return "CommandeLigne [id=" + id + ", numero=" + numero + ", referenceOffre=" + referenceOffre + ", label="
				+ label + ", gamme=" + gamme + ", secteur=" + secteur + ", modeFacturation=" + modeFacturation
				+ ", dateCreation=" + dateCreation + ", auteur=" + auteur + "]";
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
	 *            {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #numero}.
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 *            {@link #numero}.
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
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
	 * @return {@link #referenceContrat}.
	 */
	public String getReferenceContrat() {
		return referenceContrat;
	}

	/**
	 * @param referenceContrat
	 *            {@link #referenceContrat}.
	 */
	public void setReferenceContrat(String referenceContrat) {
		this.referenceContrat = referenceContrat;
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
	 * @return {@link #gamme}.
	 */
	public String getGamme() {
		return gamme;
	}

	/**
	 * 
	 * @param gamme
	 *            {@link #gamme}.
	 */
	public void setGamme(String gamme) {
		this.gamme = gamme;
	}

	/**
	 * 
	 * @return {@link #secteur}.
	 */
	public String getSecteur() {
		return secteur;
	}

	/**
	 * 
	 * @param secteur
	 *            {@link #secteur}.
	 */
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	/**
	 * 
	 * @return {@link #typeProduit}.
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
	 * @return {@link #commandeLigneDetails}.
	 */
	public List<CommandeLigneDetail> getCommandeLigneDetails() {
		return commandeLigneDetails;
	}

	/**
	 * 
	 * @param commandeLigneDetails
	 *            {@link #commandeLigneDetails}.
	 */
	public void setCommandeLigneDetails(List<CommandeLigneDetail> commandeLigneDetails) {
		this.commandeLigneDetails = commandeLigneDetails;
	}

	/**
	 * 
	 * @return {@link Tarif}.
	 */
	public Tarif getTarif() {
		return tarif;
	}

	/**
	 * 
	 * @param tarif
	 *            {@link Tarif}.
	 */
	public void setTarif(Tarif tarif) {
		this.tarif = tarif;
	}

	/**
	 * @return {@link #geste}
	 */
	public Geste getGeste() {
		return geste;
	}

	/**
	 * 
	 * @param geste
	 *            {@link #geste}
	 */
	public void setGeste(Geste geste) {
		this.geste = geste;
	}

	/**
	 * 
	 * @return {@link #causeNonTransformation}
	 */
	public String getCauseNonTransformation() {
		return causeNonTransformation;
	}

	/**
	 * 
	 * @param causeNonTransformation
	 *            {@link #causeNonTransformation}
	 */
	public void setCauseNonTransformation(String causeNonTransformation) {
		this.causeNonTransformation = causeNonTransformation;
	}

	/**
	 * ajouter un {@link CommandeLigneDetail} a ligne de commande.
	 * 
	 * @param commandeLigneDetail
	 *            {@link CommandeLigneDetail}.
	 */
	public void addCommandeLigneDetail(CommandeLigneDetail commandeLigneDetail) {
		this.commandeLigneDetails.add(commandeLigneDetail);
	}

	/**
	 * recuperer l'offre business a parot de chaque ligne de commande.
	 * 
	 * @return {@link OffreCatalogueInfo}
	 */
	public OffreCatalogueInfo toOffreCatalogueInfo() {
		OffreCatalogueInfo offreCatalogueInfo = new OffreCatalogueInfo();
		offreCatalogueInfo.setReference(referenceOffre);
		offreCatalogueInfo.setLabel(label);
		offreCatalogueInfo.setGamme(gamme);
		offreCatalogueInfo.setSecteur(secteur);
		offreCatalogueInfo.setTypeProduit(typeProduit);
		offreCatalogueInfo.setModeFacturation(modeFacturation);

		offreCatalogueInfo.setTarif(tarif.toTarifInfo());

		List<DetailCommandeLigneInfo> detailCommandeLigneInfos = new ArrayList<DetailCommandeLigneInfo>();
		for (CommandeLigneDetail commandeLigneDetail : commandeLigneDetails) {
			detailCommandeLigneInfos.add(commandeLigneDetail.toDetailCommandeLigneInfo());
		}
		offreCatalogueInfo.setDetails(detailCommandeLigneInfos);

		return offreCatalogueInfo;

	}

	/**
	 * verifie si le commandeLigne est recurrent ou non.
	 * 
	 * @return true si le commandeLigne est recurrent.
	 */
	public boolean isRecurrent() {

		if (tarif.isRecurrent()) {
			return true;
		}
		for (CommandeLigneDetail commandeLigneDetail : commandeLigneDetails) {
			if (commandeLigneDetail.isRecurrent()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Transformer un {@link CommandeLigne} en un {@link ContratPreparationInfo} .
	 * 
	 * @param referenceCommande
	 *            reference du commande.
	 * @param qui
	 *            {@link Auteur#getQui()}.
	 * @param paiement
	 *            {@link Paiement}
	 * @return {@link ContratPreparationInfo}.
	 */
	public ContratPreparationInfo toContratPreparationInfo(String referenceCommande, String qui, List<Paiement> paiement) {
		ContratPreparationInfo contrat = new ContratPreparationInfo();

		contrat.setUser(qui);
		List<Produit> produits = new ArrayList<>();
		produits.add(toProduitParent(referenceCommande, paiement));
		numEC = Constants.UN;
		for (CommandeLigneDetail ligneDetail : commandeLigneDetails) {
			Integer numECParent = null;
			Integer numEC = commandeLigneDetails.indexOf(ligneDetail) + Constants.DEUX;
			if (ligneDetail.getCommandeLigneDetailParent() != null) {
				numECParent = commandeLigneDetails.indexOf(ligneDetail.getCommandeLigneDetailParent()) + Constants.DEUX;
			} else {
				numECParent = Constants.UN;
			}

			produits.add(ligneDetail.toProduit(referenceCommande, numEC, numECParent, modeFacturation, paiement));
			ligneDetail.setNumEC(numEC);
		}
		contrat.setProduits(produits);

		return contrat;
	}

	/**
	 * creer l'arborescence entre les {@link CommandeLigneDetail}.
	 * 
	 * @param draftDetails
	 *            liste des {@link DraftLigneDetail}.
	 * @param commandeDetails
	 *            liste des {@link CommandeLigneDetail}.
	 */
	private void creerArborescence(List<DraftLigneDetail> draftDetails, List<CommandeLigneDetail> commandeDetails) {
		Map<String, CommandeLigneDetail> commandeLigneDetailMap = new HashMap<String, CommandeLigneDetail>();
		for (CommandeLigneDetail commandeLigneDetail : commandeDetails) {
			commandeLigneDetailMap.put(commandeLigneDetail.getReferenceChoix(), commandeLigneDetail);
		}

		for (DraftLigneDetail draftLigneDetail : draftDetails) {
			if (!draftLigneDetail.isParent()) {
				CommandeLigneDetail commandeLigneDetail =
						commandeLigneDetailMap.get(draftLigneDetail.getReferenceChoix());
				CommandeLigneDetail commandeLigneDetailParent =
						commandeLigneDetailMap.get(draftLigneDetail.getDraftLigneDetailParent().getReferenceChoix());
				commandeLigneDetail.setCommandeLigneDetailParent(commandeLigneDetailParent);
			}
		}
	}

	/**
	 * transfometion de la commande ligne en {@link Produit} parent dans le {@link Contrat} a prepare.
	 * 
	 * @param referenceCommande
	 *            reference de la {@link Commande}.
	 * @param paiement
	 *            {@link Paiement}
	 * @return {@link Produit}.
	 */
	private Produit toProduitParent(String referenceCommande, List<Paiement> paiement) {

		Produit produitParent = new Produit();
		produitParent.setLabel(label);
		produitParent.setNumEC(Constants.UN);
		produitParent.setNumeroCommande(referenceCommande);
		produitParent.setTypeProduit(com.nordnet.topaze.ws.enums.TypeProduit.fromString(typeProduit != null
				? typeProduit.name() : ""));
		produitParent.setReference(referenceOffre);
		produitParent.setReferenceTarif(tarif.getReference());
		if (tarif != null) {
			produitParent.setPrix(tarif.toPrix(modeFacturation, paiement));
		}
		return produitParent;
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
		if (!(obj instanceof CommandeLigne || obj instanceof DraftLigne)) {
			return false;
		}

		if (obj instanceof CommandeLigne) {
			CommandeLigne commmandeLigne = (CommandeLigne) obj;
			return new EqualsBuilder().append(referenceOffre, commmandeLigne.referenceOffre)
					.append(modeFacturation, commmandeLigne.modeFacturation).isEquals();
		} else {
			DraftLigne draftLigne = (DraftLigne) obj;
			return new EqualsBuilder().append(referenceOffre, draftLigne.getReferenceOffre()).isEquals();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(id).append(referenceOffre).append(modeFacturation).toHashCode();
	}

	/**
	 * verifier si la ligne commande a ete transforme en contrat ou non.
	 * 
	 * @return true si la commande a ete transformer en contrat.
	 */
	public boolean isTransformerEnContrat() {
		if (Optional.fromNullable(dateTransformationContrat).isPresent()) {
			return true;
		}
		return false;
	}
}
