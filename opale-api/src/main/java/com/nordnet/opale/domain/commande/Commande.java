package com.nordnet.opale.domain.commande;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

import com.google.common.base.Optional;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandeLigneInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.PropertiesUtil;

/**
 * Classe qui represente la commande.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "commande")
@Entity
public class Commande {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference de la commande.
	 */
	@NotNull
	@Index(columnNames = "reference", name = "index_commande")
	private String reference;

	/**
	 * la reference du draft de la commande.
	 */
	private String referenceDraft;

	/**
	 * Le client souscripteur {@link Client}.
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "clientSouscripteurId", nullable = true)
	private Client clientSouscripteur;

	/**
	 * Le client a livraer {@link Client}.
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "clientALivrerId", nullable = true)
	private Client clientALivrer;

	/**
	 * Le client a facturer {@link Client}.
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "clientAFacturerId", nullable = true)
	private Client clientAFacturer;

	/**
	 * l auteur du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * date creation de la commande.
	 */
	private Date dateCreation;

	/**
	 * date de transformation du commande vers contrat.
	 */
	private Date dateTransformationContrat;

	/**
	 * date d'annulation de la commande.
	 */
	private Date dateAnnulation;

	/**
	 * listes des {@link CommandeLigne} de la commande.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "commandeId")
	List<CommandeLigne> commandeLignes = new ArrayList<CommandeLigne>();

	/**
	 * code auteur.
	 */
	private String codePartenaire;

	/**
	 * constructeur par defaut.
	 */
	public Commande() {
	}

	/**
	 * creation d'une commande a partir du draft et de la trame catalogue.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param transformationInfo
	 *            {@link TransformationInfo}.
	 */
	public Commande(Draft draft, TransformationInfo transformationInfo) {

		if (transformationInfo.getAuteur() != null) {
			this.auteur = new Auteur(transformationInfo.getAuteur());
		} else {
			this.auteur = draft.getAuteur();
		}

		this.clientAFacturer =
				new Client(draft.getClientAFacturer().getClientId(), draft.getClientAFacturer().getAdresseId(), draft
						.getClientAFacturer().getTva(), this.auteur);
		this.clientALivrer =
				new Client(draft.getClientALivrer().getClientId(), draft.getClientALivrer().getAdresseId(), draft
						.getClientALivrer().getTva(), this.auteur);
		this.clientSouscripteur =
				new Client(draft.getClientSouscripteur().getClientId(), draft.getClientSouscripteur().getAdresseId(),
						draft.getClientSouscripteur().getTva(), this.auteur);

		this.codePartenaire = draft.getCodePartenaire();
		this.referenceDraft = draft.getReference();
		OffreCatalogue offreCatalogue = null;
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			offreCatalogue = transformationInfo.getTrameCatalogue().getOffreMap().get(draftLigne.getReferenceOffre());
			CommandeLigne commandeLigne = new CommandeLigne(draftLigne, offreCatalogue);
			commandeLigne.setNumero(this.commandeLignes.size());
			addLigne(commandeLigne);
		}
	}

	@Override
	public String toString() {
		return "Commande [id=" + id + ", reference=" + reference + ", referenceDraft=" + referenceDraft
				+ ", clientSouscripteur=" + clientSouscripteur + ", clientALivrer=" + clientALivrer
				+ ", clientAFacturer=" + clientAFacturer + ", auteur=" + auteur + ", dateCreation=" + dateCreation
				+ ", commandeLignes=" + commandeLignes + "]";
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
	 * @return {@link #referenceDraft}.
	 */
	public String getReferenceDraft() {
		return referenceDraft;
	}

	/**
	 * 
	 * @param referenceDraft
	 *            {@link #referenceDraft}.
	 */
	public void setReferenceDraft(String referenceDraft) {
		this.referenceDraft = referenceDraft;
	}

	/**
	 * 
	 * @return {@link Client}
	 */
	public Client getClientSouscripteur() {
		return clientSouscripteur;
	}

	/**
	 * 
	 * @param clientSouscripteur
	 *            {@link Client}.
	 */
	public void setClientSouscripteur(Client clientSouscripteur) {
		this.clientSouscripteur = clientSouscripteur;
	}

	/**
	 * 
	 * @return {@link Client}.
	 */
	public Client getClientALivrer() {
		return clientALivrer;
	}

	/**
	 * 
	 * @param clientALivrer
	 *            {@link Client}.
	 */
	public void setClientALivrer(Client clientALivrer) {
		this.clientALivrer = clientALivrer;
	}

	/**
	 * 
	 * @return {@link Client}.
	 */
	public Client getClientAFacturer() {
		return clientAFacturer;
	}

	/**
	 * 
	 * @param clientAFacturer
	 *            {@link Client}.
	 */
	public void setClientAFacturer(Client clientAFacturer) {
		this.clientAFacturer = clientAFacturer;
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
	 * 
	 * @return {@link #dateAnnulation}
	 */
	public Date getDateAnnulation() {
		return dateAnnulation;
	}

	/**
	 * 
	 * @param dateAnnulation
	 *            {@link #dateAnnulation}.
	 */
	public void setDateAnnulation(Date dateAnnulation) {
		this.dateAnnulation = dateAnnulation;
	}

	/**
	 * 
	 * @return {@link #commandeLignes}.
	 */
	public List<CommandeLigne> getCommandeLignes() {
		return commandeLignes;
	}

	/**
	 * 
	 * @param commandeLignes
	 *            {@link #commandeLignes}.
	 */
	public void setCommandeLignes(List<CommandeLigne> commandeLignes) {
		this.commandeLignes = commandeLignes;
	}

	/**
	 * the code partenaire.
	 * 
	 * @return {@link #codePartenaire}
	 */
	public String getCodePartenaire() {
		return codePartenaire;
	}

	/**
	 * set the code partenaire.
	 * 
	 * @param codePartenaire
	 *            the new {@link #codePartenaire}
	 */
	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
	}

	/**
	 * ajouter une ligne a la commande.
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 */
	public void addLigne(CommandeLigne commandeLigne) {
		this.commandeLignes.add(commandeLigne);
	}

	/**
	 * recuperer commande business a paritr de command domain.
	 * 
	 * @return {@link CommandeInfo}
	 */
	public CommandeInfo toCommandInfo() {
		CommandeInfo commandeInfo = new CommandeInfo();
		commandeInfo.setAuteur(auteur.toAuteurBusiness());
		commandeInfo.setCodePartenaire(codePartenaire);
		List<CommandeLigneInfo> lignes = new ArrayList<CommandeLigneInfo>();

		for (CommandeLigne commandeLigne : commandeLignes) {
			CommandeLigneInfo commandeLigneInfo = new CommandeLigneInfo();
			commandeLigneInfo.setNumero(commandeLigne.getNumero());
			commandeLigneInfo.setAuteur(commandeLigne.getAuteur().toAuteurBusiness());
			commandeLigneInfo.setOffre(commandeLigne.toOffreCatalogueInfo());
			lignes.add(commandeLigneInfo);

		}
		commandeInfo.setLignes(lignes);
		commandeInfo.setAnnule(isAnnule());
		return commandeInfo;

	}

	/**
	 * verifier si la commande a besoin d'un paiement recurrent ou non.
	 * 
	 * @return true si la comande a besoin d'un paiement recurrent.
	 */
	public boolean needPaiementRecurrent() {
		for (CommandeLigne commandeLigne : commandeLignes) {
			if (commandeLigne.isRecurrent())
				return true;
		}
		return false;
	}

	/**
	 * varifier si la commande est annule ou non.
	 * 
	 * @return true si la commande est annule.
	 */
	public boolean isAnnule() {
		Optional<Date> dateAnnulationOptional = Optional.fromNullable(dateAnnulation);
		return dateAnnulationOptional.isPresent();
	}

	/**
	 * verifier si la commande a ete transforme en contrat ou non.
	 * 
	 * @return true si la commande a ete transformer en contrat.
	 */
	public boolean isTransformerEnContrat() {
		if (Optional.fromNullable(dateTransformationContrat).isPresent()) {
			return true;
		}
		return false;
	}

	/**
	 * methode appel avant la persistance du la commande.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@PrePersist
	public void prePersist() throws OpaleException {
		if (auteur != null && auteur.getIp() != null && auteur.getIp().getTs() == null) {
			auteur.getIp().setTs(PropertiesUtil.getInstance().getDateDuJour());
		}
	}

	/**
	 * annuler la commande.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void annuler() throws OpaleException {
		this.dateAnnulation = PropertiesUtil.getInstance().getDateDuJour();
	}

	/**
	 * verifier si la commande contient le geste 'VENTE' parmis c'est ligne.
	 * 
	 * @return true si la commande contient le geste 'VENTE' parmis c'est ligne.
	 */
	public boolean isContientVente() {
		for (CommandeLigne commandeLigne : this.commandeLignes) {
			if (commandeLigne.getGeste() == Geste.VENTE)
				return true;
		}
		return false;
	}

	/**
	 * verifier si la commande contient le geste 'RENOUVELLEMENT' parmis c'est ligne.
	 * 
	 * @return true si la commande contient le geste 'RENOUVELLEMENT' parmis c'est ligne.
	 */
	public boolean isContientRenouvellement() {
		for (CommandeLigne commandeLigne : this.commandeLignes) {
			if (commandeLigne.getGeste() == Geste.RENOUVELLEMENT)
				return true;
		}
		return false;
	}

	/**
	 * verifier si la commande contient le geste 'MIGRATION' parmis c'est ligne.
	 * 
	 * @return true si la commande contient le geste 'MIGRATION' parmis c'est ligne.
	 */
	public boolean isContientMigration() {
		for (CommandeLigne commandeLigne : this.commandeLignes) {
			if (commandeLigne.getGeste() == Geste.MIGRATION)
				return true;
		}
		return false;
	}
}
