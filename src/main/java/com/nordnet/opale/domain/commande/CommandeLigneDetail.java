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
import javax.persistence.Table;

import com.nordnet.opale.business.DetailCommandeLigneInfo;
import com.nordnet.opale.business.TarifInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.enums.ModePaiement;

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
	 * reference produit.
	 */
	private String referenceProduit;

	/**
	 * {@link ModePaiement}.
	 */
	@Enumerated(EnumType.STRING)
	private ModePaiement modePaiement;

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
	private List<CommandeLigneDetail> sousCommandeLigneDetails = new ArrayList<CommandeLigneDetail>();

	/**
	 * Liste des {@link Tarif} associe.
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "commandeLigneDetailId")
	private List<Tarif> tarifs = new ArrayList<Tarif>();

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
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public CommandeLigneDetail(DraftLigneDetail detail, TrameCatalogue trameCatalogue) {
		this.referenceProduit = detail.getReference();
		this.modePaiement = detail.getModePaiement();
		this.configurationJson = detail.getConfigurationJson();
	}

	@Override
	public String toString() {
		return "CommandeLigneDetail [id=" + id + ", referenceSelection=" + referenceProduit + ", modePaiement="
				+ modePaiement + ", configurationJson=" + configurationJson + "]";
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
	 * @return {@link #referenceProduit}.
	 */
	public String getReferenceProduit() {
		return referenceProduit;
	}

	/**
	 * 
	 * @param referenceProduit
	 *            {@link #referenceProduit}.
	 */
	public void setReferenceProduit(String referenceProduit) {
		this.referenceProduit = referenceProduit;
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
	 * 
	 * @return {@link #tarifs}.
	 */
	public List<Tarif> getTarifs() {
		return tarifs;
	}

	/**
	 * 
	 * @param tarifs
	 *            {@link #tarifs}.
	 */
	public void setTarifs(List<Tarif> tarifs) {
		this.tarifs = tarifs;
	}

	/**
	 * ajouter un tarif.
	 * 
	 * @param tarif
	 *            {@link Tarif}.
	 */
	public void addTarif(Tarif tarif) {
		this.tarifs.add(tarif);
	}

	/**
	 * recuperer commande ligne business a paritr de command ligne domain
	 * 
	 * @return {@link DetailCommandeLigneInfo}
	 */
	public DetailCommandeLigneInfo toDetailCommandeLigneInfo() {
		DetailCommandeLigneInfo detailCommandeLigneInfo = new DetailCommandeLigneInfo();
		detailCommandeLigneInfo.setReference(referenceProduit);
		// detailCommandeLigneInfo.setLabel(label);
		List<TarifInfo> tarifInfos = new ArrayList<TarifInfo>();

		for (Tarif tarif : tarifs) {
			tarifInfos.add(tarif.toTarifInfo());
		}
		detailCommandeLigneInfo.setTarif(tarifInfos);

		return detailCommandeLigneInfo;

	}

}
