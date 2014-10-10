package com.nordnet.opale.domain.commande;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;

/**
 * Classe represente une ligne (offre) dans la {@link Commande}.
 * 
 * @author akram
 * 
 */
public class CommandeLigne {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference de la ligne de draft.
	 */
	private String reference;

	/**
	 * reference de l'offre.
	 */
	private String referenceOffre;

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
	 * constructeur par defaut.
	 */
	public CommandeLigne() {
	}

	@Override
	public String toString() {
		return "CommandeLigne [id=" + id + ", reference=" + reference + ", referenceOffre=" + referenceOffre
				+ ", label=" + label + ", gamme=" + gamme + ", secteur=" + secteur + ", modePaiement=" + modePaiement
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
	 * @return {@link #reference}
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

}
