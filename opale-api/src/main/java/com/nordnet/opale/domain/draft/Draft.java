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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import com.nordnet.opale.business.TrameCatalogueInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.serializer.DateSerializer;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.topaze.ws.entity.Contrat;

/**
 * Cette classe regroupe les informations qui definissent un {@link Draft}.
 * 
 * @author anisselmane.
 * 
 */
@Entity
@Table(name = "draft")
@JsonIgnoreProperties({ "id", "annule", "transforme", "toJSON", "prePersist" })
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
	@JsonSerialize(using = DateSerializer.class)
	private Date dateAnnulation;

	/**
	 * date de transformation du draft en {@link Commande}.
	 */
	@JsonSerialize(using = DateSerializer.class)
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
	 * code auteur.
	 */
	private String codePartenaire;

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
		this.codePartenaire = commande.getCodePartenaire();
		Client clientAFacturer = commande.getClientAFacturer();
		if (clientAFacturer != null) {
			this.clientAFacturer = new Client();
			this.clientAFacturer.setAdresseId(clientAFacturer.getAdresseId());
			this.clientAFacturer.setClientId(clientAFacturer.getClientId());
			this.clientAFacturer.setTva(clientAFacturer.getTva());
			this.clientAFacturer.setAuteur(clientAFacturer.getAuteur());

		}
		Client clientSouscripteur = commande.getClientSouscripteur();
		if (clientSouscripteur != null) {
			this.clientSouscripteur =
					new Client(clientSouscripteur.getClientId(), clientSouscripteur.getAdresseId(),
							clientSouscripteur.getTva(), clientSouscripteur.getAuteur());
		}
		Client clientALivrer = commande.getClientALivrer();
		if (clientALivrer != null) {
			this.clientALivrer =
					new Client(clientALivrer.getClientId(), clientALivrer.getAdresseId(), clientALivrer.getTva(),
							clientALivrer.getAuteur());
		}
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			addLigne(new DraftLigne(commandeLigne));
		}
	}

	/**
	 * creation du {@link Draft} a partir du {@link Contrat} et de la {@link TrameCatalogue}.
	 * 
	 * @param contrat
	 *            {@link Contrat}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public Draft(Contrat contrat, TrameCatalogueInfo trameCatalogue) {
		Auteur auteur = new Auteur(trameCatalogue.getAuteur());
		this.auteur = auteur;
		Client clientAFacturer =
				new Client(contrat.getIdClient(), contrat.getSousContrats().get(Constants.ZERO).getIdAdrFacturation(),
						null, auteur);
		this.clientAFacturer = clientAFacturer;
		Client clientALivrer =
				new Client(contrat.getIdClient(), contrat.getSousContrats().get(Constants.ZERO).getIdAdrLivraison(),
						null, auteur);
		this.clientALivrer = clientALivrer;

		// TODO verifier comment recuperer l'addresse du client suscripteur.
		Client clientSouscripteur = new Client(contrat.getIdClient(), "", null, auteur);
		this.clientSouscripteur = clientSouscripteur;

		addLigne(new DraftLigne(contrat, trameCatalogue));
	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", reference=" + reference + ", referenceExterne=" + referenceExterne
				+ ", dateAnnulation=" + dateAnnulation + ", dateTransformationCommande=" + dateTransformationCommande
				+ ", commandeSource=" + commandeSource + ", codePartenaire=" + codePartenaire + ", geste=" + "]";
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
	 * associer un {@link Client} au draft a partir des informations d'un {@link com.nordnet.opale.business.Client}.
	 * 
	 * @param client
	 *            {@link com.nordnet.opale.business.Client}.
	 * @param auteur
	 *            {@link com.nordnet.opale.business.Auteur}.
	 */
	public void setClientSouscripteur(com.nordnet.opale.business.Client client, com.nordnet.opale.business.Auteur auteur) {
		if (client != null) {
			if (this.clientSouscripteur != null) {
				this.clientSouscripteur.setAdresseId(client.getAdresseId());
				this.clientSouscripteur.setClientId(client.getClientId());
				this.clientSouscripteur.setTva(client.getTva());
				this.clientSouscripteur.setAuteur(auteur != null ? auteur.toDomain() : null);
			} else {
				this.clientSouscripteur =
						new Client(client.getClientId(), client.getAdresseId(), client.getTva(), auteur != null
								? auteur.toDomain() : null);
			}
		}
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
	 * associer un {@link Client} au draft a partir des informations d'un {@link com.nordnet.opale.business.Client}.
	 * 
	 * @param client
	 *            {@link com.nordnet.opale.business.Client}.
	 * @param auteur
	 *            {@link com.nordnet.opale.business.Auteur}.
	 */
	public void setClientALivrer(com.nordnet.opale.business.Client client, com.nordnet.opale.business.Auteur auteur) {
		if (client != null) {
			if (this.clientALivrer != null) {
				this.clientALivrer.setAdresseId(client.getAdresseId());
				this.clientALivrer.setClientId(client.getClientId());
				this.clientALivrer.setTva(client.getTva());
				this.clientALivrer.setAuteur(auteur != null ? auteur.toDomain() : null);
			} else {
				this.clientALivrer =
						new Client(client.getClientId(), client.getAdresseId(), client.getTva(), auteur != null
								? auteur.toDomain() : null);
			}
		}
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
	 * associer un {@link Client} au draft a partir des informations d'un {@link com.nordnet.opale.business.Client}.
	 * 
	 * @param client
	 *            {@link com.nordnet.opale.business.Client}.
	 * @param auteur
	 *            {@link com.nordnet.opale.business.Auteur}.
	 */
	public void setClientAFacturer(com.nordnet.opale.business.Client client, com.nordnet.opale.business.Auteur auteur) {
		if (client != null) {
			if (this.clientAFacturer != null) {
				this.clientAFacturer.setAdresseId(client.getAdresseId());
				this.clientAFacturer.setClientId(client.getClientId());
				this.clientAFacturer.setTva(client.getTva());
				this.clientAFacturer.setAuteur(auteur != null ? auteur.toDomain() : null);
			} else {
				this.clientAFacturer =
						new Client(client.getClientId(), client.getAdresseId(), client.getTva(), auteur != null
								? auteur.toDomain() : null);
			}
		}
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
	 * Ajouter une {@link DraftLigne} au draft.
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}.
	 */
	public void addLigne(DraftLigne draftLigne) {
		this.draftLignes.add(draftLigne);
	}

	/**
	 * Tester si le draft est annule.
	 * 
	 * @return true si le draft est annule.
	 */
	public Boolean isAnnule() {
		Optional<Date> dateAnnulationOp = Optional.fromNullable(dateAnnulation);
		return dateAnnulationOp.isPresent();
	}

	/**
	 * Tester si le draft a ete transforme en {@link Commande}.
	 * 
	 * @return true si le draft a ete transforme en draft.
	 */
	public Boolean isTransforme() {
		Optional<Date> dateTransfromationOp = Optional.fromNullable(dateTransformationCommande);
		return dateTransfromationOp.isPresent();
	}

	/**
	 * creation d'un resume du draft sur format JSON.
	 * 
	 * @return {@link JSONObject}
	 * 
	 * @throws JSONException
	 *             {@link JSONException}
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject draftJsonObject = new JSONObject();
		JSONArray lignes = new JSONArray();
		JSONArray details = null;
		JSONObject draftLigneJsonObject = null;
		JSONObject offreJsonObject = null;
		JSONObject detailJsonObject = null;
		String referenceContrat = null;

		draftJsonObject.put("reference", reference);
		for (DraftLigne draftLigne : draftLignes) {
			draftLigneJsonObject = new JSONObject();
			offreJsonObject = new JSONObject();
			details = new JSONArray();
			referenceContrat = draftLigne.getReferenceContrat();
			draftLigneJsonObject.put("numero", draftLignes.indexOf(draftLigne) + Constants.UN);
			offreJsonObject.put("reference", draftLigne.getReferenceOffre());
			offreJsonObject.put("tarif", draftLigne.getReferenceTarif());
			draftLigneJsonObject.put("offre", offreJsonObject);
			for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
				detailJsonObject = new JSONObject();
				detailJsonObject.put("referenceSelection", detail.getReferenceSelection());
				detailJsonObject.put("reference", detail.getReferenceChoix());
				detailJsonObject.put("tarif", detail.getReferenceTarif());
				detailJsonObject.put("dependDe", detail.getdependDe());
				details.put(detailJsonObject);
			}
			draftLigneJsonObject.put("details", details);
			lignes.put(draftLigneJsonObject);
		}
		draftJsonObject.put("referenceContrat", referenceContrat);
		draftJsonObject.put("lignes", lignes);

		return draftJsonObject;
	}

	/**
	 * methode appel avant la persistance du draft.
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
}
