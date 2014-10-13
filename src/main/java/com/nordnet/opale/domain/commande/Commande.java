package com.nordnet.opale.domain.commande;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CommandeLigneInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Client;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;

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
	 * L adresse du draft.
	 */
	@Embedded
	private Client client;

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
		this.client = draft.getClient();
		this.auteur = draft.getAuteur();
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			CommandeLigne commandeLigne = new CommandeLigne(draftLigne, trameCatalogue);
			addLigne(commandeLigne);
		}
	}

	@Override
	public String toString() {
		return "Commande [id=" + id + ", reference=" + reference + ", client=" + client + ", auteur=" + auteur
				+ ", dateCreation=" + dateCreation + "]";
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
	 * @return {@link #client}.
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * 
	 * @param client
	 *            {@link #client}.
	 */
	public void setClient(Client client) {
		this.client = client;
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
	 * recuperer commande business a paritr de command domain
	 * 
	 * @return
	 */
	public CommandeInfo toCommandInfo() {
		CommandeInfo commandeInfo = new CommandeInfo();
		commandeInfo.setAuteur(auteur.toAuteurBusiness());
		List<CommandeLigneInfo> lignes = new ArrayList<CommandeLigneInfo>();

		for (CommandeLigne commandeLigne : commandeLignes) {
			CommandeLigneInfo commandeLigneInfo = new CommandeLigneInfo();
			// commandeLigneInfo.setNumero(reference);
			commandeLigneInfo.setAuteur(commandeLigne.getAuteur().toAuteurBusiness());
			commandeLigneInfo.setOffre(commandeLigne.toOffreCatalogueInfo());
			lignes.add(commandeLigneInfo);

		}
		commandeInfo.setLignes(lignes);
		return commandeInfo;

	}

}
