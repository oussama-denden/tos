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
	 * reference selection.
	 */
	private String referenceSelection;

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
	 * constructeur par defaut.
	 */
	public CommandeLigneDetail() {
	}

	@Override
	public String toString() {
		return "CommandeLigneDetail [id=" + id + ", referenceSelection=" + referenceSelection + ", modePaiement="
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

}