package com.nordnet.opale.domain.draft;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Optional;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.util.Constants;

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
	 * code auteur.
	 */
	private String codePartenaire;

	/**
	 * Le geste effectue.
	 */
	@Enumerated(EnumType.STRING)
	private Geste geste;

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
			this.clientAFacturer.setAuteur(clientAFacturer.getAuteur());

		}
		Client clientSouscripteur = commande.getClientSouscripteur();
		if (clientSouscripteur != null) {
			this.clientSouscripteur =
					new Client(clientSouscripteur.getClientId(), clientSouscripteur.getAdresseId(),
							clientSouscripteur.getAuteur());
		}
		Client clientALivrer = commande.getClientALivrer();
		if (clientALivrer != null) {
			this.clientALivrer =
					new Client(clientALivrer.getClientId(), clientALivrer.getAdresseId(), clientALivrer.getAuteur());
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
	public Draft(Contrat contrat, TrameCatalogue trameCatalogue) {
		Auteur auteur = new Auteur(trameCatalogue.getAuteur());
		this.auteur = auteur;
		Client clientAFacturer =
				new Client(contrat.getIdClient(), contrat.getSousContrats().get(Constants.ZERO).getIdAdrFacturation(),
						auteur);
		this.clientAFacturer = clientAFacturer;
		Client clientALivrer =
				new Client(contrat.getIdClient(), contrat.getSousContrats().get(Constants.ZERO).getIdAdrLivraison(),
						auteur);
		this.clientALivrer = clientALivrer;

		Client clientSouscripteur = new Client(contrat.getIdClient(), null);
		this.clientSouscripteur = clientSouscripteur;

		addLigne(new DraftLigne(contrat, trameCatalogue));
	}

	@Override
	public String toString() {
		return "Draft [id=" + id + ", reference=" + reference + ", referenceExterne=" + referenceExterne
				+ ", dateAnnulation=" + dateAnnulation + ", dateTransformationCommande=" + dateTransformationCommande
				+ ", commandeSource=" + commandeSource + ", codePartenaire=" + codePartenaire + ", geste=" + geste
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

		draftJsonObject.put("refrence", reference);
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
}
