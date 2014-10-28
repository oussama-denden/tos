package com.nordnet.opale.domain.draft;

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
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Optional;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;

/**
 * Cette classe regroupe les informations qui definissent un {@link Draft}.
 * 
 * @author anisselmane.
 * 
 */
@Entity
@Table(name = "draft")
@JsonIgnoreProperties({ "id", "annule", "transforme" })
public class Draft {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du draft.
	 */
	@NotNull
	private String reference;

	/**
	 * l auteur du draft.
	 */
	@Embedded
	private Auteur auteur;

	/**
	 * reference externe du draft.
	 */
	private String referenceExterne;

	/**
	 * La date d annulation du draft.
	 */
	private Date dateAnnulation;

	/**
	 * date de transformation du draft en {@link Commande}.
	 */
	private Date dateTransformationCommande;

	/**
	 * reference de la commande source du draft.
	 */
	private String commandeSource;

	/**
	 * la list des {@link DraftLigne} associe au draft.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "draftId")
	private List<DraftLigne> draftLignes = new ArrayList<DraftLigne>();

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
	 * constructeur par defaut.
	 */
	public Draft() {

	}

	/**
	 * creation d'un draft a partir d'un {@link Commande}.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 */
	public Draft(Commande commande) {
		this.commandeSource = commande.getReference();
		this.auteur = commande.getAuteur();
		Client clientAFacturer = commande.getClientAFacturer();
		if (clientAFacturer != null) {
			// this.clientAFacturer = new Client(clientAFacturer.getClientId(),
			// clientALivrer.getAdresseId());
			this.clientAFacturer = new Client();
			this.clientAFacturer.setAdresseId(clientAFacturer.getAdresseId());
			this.clientAFacturer.setClientId(clientAFacturer.getClientId());

		}
		Client clientSouscripteur = commande.getClientSouscripteur();
		if (clientSouscripteur != null) {
			this.clientSouscripteur = new Client(clientSouscripteur.getClientId(), clientSouscripteur.getAdresseId());
		}
		Client clientALivrer = commande.getClientALivrer();
		if (clientALivrer != null) {
			this.clientALivrer = new Client(clientALivrer.getClientId(), clientALivrer.getAdresseId());
		}
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			addLigne(new DraftLigne(commandeLigne));
		}
	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", reference=" + reference + "]";
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
	 * get reference externe.
	 * 
	 * @return {@link #referenceExterne}
	 */
	public String getReferenceExterne() {
		return referenceExterne;

	}

	/**
	 * set reference externe.
	 * 
	 * @param referenceExterne
	 *            the new {@link #referenceExterne}
	 */
	public void setReferenceExterne(String referenceExterne) {
		this.referenceExterne = referenceExterne;

	}

	/**
	 * 
	 * @return {@link #dateAnnulation}.
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
	 * @return {@link #dateTransformationCommande}.
	 */
	public Date getDateTransformationCommande() {
		return dateTransformationCommande;
	}

	/**
	 * 
	 * @param dateTransformationCommande
	 *            {@link #dateTransformationCommande}.
	 */
	public void setDateTransformationCommande(Date dateTransformationCommande) {
		this.dateTransformationCommande = dateTransformationCommande;
	}

	/**
	 * 
	 * @return {@link #commandeSource}.
	 */
	public String getCommandeSource() {
		return commandeSource;
	}

	/**
	 * 
	 * @param commandeSource
	 *            {@link #commandeSource}.
	 */
	public void setCommandeSource(String commandeSource) {
		this.commandeSource = commandeSource;
	}

	/**
	 * 
	 * @return List {@link DraftLigne}.
	 */
	public List<DraftLigne> getDraftLignes() {
		return draftLignes;
	}

	/**
	 * 
	 * @param draftLignes
	 *            List {@link DraftLigne}.
	 */
	public void setDraftLignes(List<DraftLigne> draftLignes) {
		this.draftLignes = draftLignes;
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
	 * ajouter une {@link DraftLigne} au draft.
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}.
	 */
	public void addLigne(DraftLigne draftLigne) {
		this.draftLignes.add(draftLigne);
	}

	/**
	 * tester si le draft est annule.
	 * 
	 * @return true si le draft est annule.
	 */
	public Boolean isAnnule() {
		Optional<Date> dateAnnulationOp = Optional.fromNullable(dateAnnulation);
		return dateAnnulationOp.isPresent();
	}

	/**
	 * tester si le draft a ete transforme en {@link Commande}.
	 * 
	 * @return true si le draft a ete transforme en draft.
	 */
	public Boolean isTransforme() {
		Optional<Date> dateTransfromationOp = Optional.fromNullable(dateTransformationCommande);
		return dateTransfromationOp.isPresent();
	}

}
