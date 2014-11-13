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

import com.nordnet.opale.business.DetailCommandeLigneInfo;
import com.nordnet.opale.business.OffreCatalogueInfo;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.business.commande.ContratPreparationInfo;
import com.nordnet.opale.business.commande.Produit;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.util.Constants;

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
	 * numero de la ligne dans la commande.
	 */
	private Integer numero;

	/**
	 * reference de l'offre.
	 */
	private String referenceOffre;

	/**
	 * reference du contrat.
	 */
	private String referenceContrat;

	/**
	 * label de l'offre dans le catalogue.
	 */
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
	 * liste des {@link CommandeLigneDetail} associe au ligne dans la commande.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "commandeLigneId")
	private List<CommandeLigneDetail> commandeLigneDetails = new ArrayList<CommandeLigneDetail>();

	/**
	 * liste des {@link Tarif} associe a la ligne de commande.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tarifId")
	private Tarif tarif;

	/**
	 * la famille de l'offre.
	 */
	private String famille;

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
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public CommandeLigne(DraftLigne draftLigne, TrameCatalogue trameCatalogue) {
		OffreCatalogue offreCatalogue = trameCatalogue.getOffreMap().get(draftLigne.getReferenceOffre());
		this.referenceOffre = draftLigne.getReferenceOffre();
		this.gamme = offreCatalogue.getGamme();
		this.famille = offreCatalogue.getFamille();
		this.modePaiement = draftLigne.getModePaiement();
		this.modeFacturation = draftLigne.getModeFacturation();
		this.auteur = draftLigne.getAuteur();
		this.dateCreation = draftLigne.getDateCreation();
		this.tarif = new Tarif(draftLigne.getReferenceTarif(), trameCatalogue);

		for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
			CommandeLigneDetail commandeLigneDetail = new CommandeLigneDetail(detail, referenceOffre, trameCatalogue);
			addCommandeLigneDetail(commandeLigneDetail);
		}
		creerArborescence(draftLigne.getDraftLigneDetails(), this.commandeLigneDetails);
	}

	@Override
	public String toString() {
		return "CommandeLigne [id=" + id + ", numero=" + numero + ", referenceOffre=" + referenceOffre + ", label="
				+ label + ", gamme=" + gamme + ", secteur=" + secteur + ", modePaiement=" + modePaiement
				+ ", modeFacturation=" + modeFacturation + ", dateCreation=" + dateCreation + ", auteur=" + auteur
				+ "]";
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
	 * ajouter un {@link CommandeLigneDetail} a ligne de commande.
	 * 
	 * @param commandeLigneDetail
	 *            {@link CommandeLigneDetail}.
	 */
	public void addCommandeLigneDetail(CommandeLigneDetail commandeLigneDetail) {
		this.commandeLigneDetails.add(commandeLigneDetail);
	}

	/**
	 * get the famille.
	 * 
	 * @return {@link #famille}
	 */
	public String getFamille() {
		return famille;
	}

	/**
	 * set the famille.
	 * 
	 * @param famille
	 *            the new {@link #famille}
	 */
	public void setFamille(String famille) {
		this.famille = famille;
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
		offreCatalogueInfo.setFammille(secteur);
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
	 * @return {@link ContratPreparationInfo}.
	 */
	public ContratPreparationInfo toContratPreparationInfo(String referenceCommande, String qui) {
		ContratPreparationInfo contrat = new ContratPreparationInfo();

		contrat.setUser(qui);
		List<Produit> produits = new ArrayList<>();
		for (CommandeLigneDetail ligneDetail : commandeLigneDetails) {
			Integer numECParent = null;
			Integer numEC = commandeLigneDetails.indexOf(ligneDetail) + Constants.UN;
			if (ligneDetail.getCommandeLigneDetailParent() != null) {
				numECParent = commandeLigneDetails.indexOf(ligneDetail.getCommandeLigneDetailParent()) + Constants.UN;
			}

			produits.add(ligneDetail.toProduit(referenceCommande, numEC, numECParent, modeFacturation));
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
					.append(modeFacturation, commmandeLigne.modeFacturation)
					.append(modePaiement, commmandeLigne.modePaiement).isEquals();
		} else {
			DraftLigne draftLigne = (DraftLigne) obj;
			return new EqualsBuilder().append(referenceOffre, draftLigne.getReferenceOffre())
					.append(modeFacturation, draftLigne.getModeFacturation())
					.append(modePaiement, draftLigne.getModePaiement()).isEquals();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(id).append(referenceOffre).append(modeFacturation)
				.append(modePaiement).toHashCode();
	}

}
