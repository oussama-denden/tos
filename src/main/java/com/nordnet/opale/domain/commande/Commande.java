package com.nordnet.opale.domain.commande;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.google.common.base.Optional;
import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandeLigneInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;

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
	 * listes des {@link CommandeLigne} de la commande.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "commandeId")
	List<CommandeLigne> commandeLignes = new ArrayList<CommandeLigne>();

	/**
	 * la reference signature du client associe au commande.
	 */
	private String referenceSignature;

	/**
	 * si commande paye, paye=true.
	 */
	private boolean paye;

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
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public Commande(Draft draft, TrameCatalogue trameCatalogue) {

		this.clientAFacturer = draft.getClientAFacturer();
		this.clientALivrer = draft.getClientALivrer();
		this.clientSouscripteur = draft.getClientSouscripteur();

		this.auteur = draft.getAuteur();
		this.referenceDraft = draft.getReference();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			CommandeLigne commandeLigne = new CommandeLigne(draftLigne, trameCatalogue);
			commandeLigne.setNumero(this.commandeLignes.size());
			creerArborescence(draftLigne.getDraftLigneDetails(), commandeLigne.getCommandeLigneDetails());
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
	 * ajouter une ligne a la commande.
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 */
	public void addLigne(CommandeLigne commandeLigne) {
		this.commandeLignes.add(commandeLigne);
	}

	/**
	 * get the reference de signature.
	 * 
	 * @return {@link #referenceSignature}
	 */
	public String getReferenceSignature() {
		return referenceSignature;
	}

	/**
	 * set the referense de signature.
	 * 
	 * @param referenceSignature
	 *            the new {@link #referenceSignature}
	 */
	public void setReferenceSignature(String referenceSignature) {
		this.referenceSignature = referenceSignature;
	}

	/**
	 * 
	 * @return {@link #paye}
	 */
	public boolean isPaye() {
		return paye;
	}

	/**
	 * 
	 * @param paye
	 *            #pay
	 */
	public void setPaye(boolean paye) {
		this.paye = paye;
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
			commandeLigneDetailMap.put(commandeLigneDetail.getReferenceProduit(), commandeLigneDetail);
		}

		for (DraftLigneDetail draftLigneDetail : draftDetails) {
			if (!draftLigneDetail.isParent()) {
				CommandeLigneDetail commandeLigneDetail =
						commandeLigneDetailMap.get(draftLigneDetail.getReferenceSelection());
				CommandeLigneDetail commandeLigneDetailParent =
						commandeLigneDetailMap
								.get(draftLigneDetail.getDraftLigneDetailParent().getReferenceSelection());
				commandeLigneDetail.setCommandeLigneDetailParent(commandeLigneDetailParent);
			}
		}
	}

	/**
	 * recuperer commande business a paritr de command domain.
	 * 
	 * @return {@link CommandeInfo}
	 */
	public CommandeInfo toCommandInfo() {
		CommandeInfo commandeInfo = new CommandeInfo();
		commandeInfo.setAuteur(auteur.toAuteurBusiness());
		List<CommandeLigneInfo> lignes = new ArrayList<CommandeLigneInfo>();

		for (CommandeLigne commandeLigne : commandeLignes) {
			CommandeLigneInfo commandeLigneInfo = new CommandeLigneInfo();
			commandeLigneInfo.setNumero(commandeLigne.getNumero());
			commandeLigneInfo.setAuteur(commandeLigne.getAuteur().toAuteurBusiness());
			commandeLigneInfo.setOffre(commandeLigne.toOffreCatalogueInfo());
			lignes.add(commandeLigneInfo);

		}
		commandeInfo.setLignes(lignes);
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
	 * retourner si la commande est signe ou non.
	 * 
	 * @return true si la commande est signe.
	 */
	public boolean isSigne() {
		Optional<String> referenceSignatureOp = Optional.fromNullable(referenceSignature);
		return referenceSignatureOp.isPresent();
	}
}
