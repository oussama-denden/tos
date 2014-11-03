package com.nordnet.opale.domain.reduction;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.TypeValeur;

/**
 * Cette classe represente l'entite {@link Reduction}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Table(name = "reduction")
@Entity
public class Reduction {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du reduction.
	 */
	@NotNull
	private String reference;

	/**
	 * reference draft.
	 */
	private String referenceDraft;

	/**
	 * reference ligne draft.
	 */
	private String referenceLigne;

	/**
	 * label du reduction.
	 */
	private String label;

	/**
	 * {@link ModePaiement}.
	 */
	@Enumerated(EnumType.STRING)
	private TypeValeur typeValeur;

	/**
	 * valeur de la reduction.
	 */
	private Double valeur;

	/**
	 * nombre d'utilisationmax.
	 */
	private Integer nbUtilisationMax;

	/**
	 * date de debut.
	 */
	private Date dateDebut;

	/**
	 * date de fin.
	 */
	private Date dateFin;

	/**
	 * reference du fras.
	 */
	private String referenceFrais;

	/**
	 * constructeur par defaut.
	 */
	public Reduction() {
	}

	/**
	 * 
	 * @return {@link #id}
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            the new {@link #id}
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
	 *            the new {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #referenceDraft}
	 */
	public String getReferenceDraft() {
		return referenceDraft;
	}

	/**
	 * 
	 * @return {@link #referenceLigne}
	 */
	public String getReferenceLigne() {
		return referenceLigne;
	}

	/**
	 * 
	 * @param referenceLigne
	 *            the new {@link #referenceLigne}
	 */
	public void setReferenceLigne(String referenceLigne) {
		this.referenceLigne = referenceLigne;
	}

	/**
	 * 
	 * @return {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            the new {@link #label}
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @param referenceDraft
	 *            the new {@link #referenceDraft}
	 */
	public void setReferenceDraft(String referenceDraft) {
		this.referenceDraft = referenceDraft;
	}

	/**
	 * 
	 * @return {@link #typeValeur}
	 */
	public TypeValeur getTypeValeur() {
		return typeValeur;
	}

	/**
	 * 
	 * @param typeValeur
	 *            the new {@link #typeValeur}
	 */
	public void setTypeValeur(TypeValeur typeValeur) {
		this.typeValeur = typeValeur;
	}

	/**
	 * 
	 * @return {@link #valeur}
	 */
	public Double getValeur() {
		return valeur;
	}

	/**
	 * 
	 * @param valeur
	 *            the new {@link #valeur}
	 */
	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

	/**
	 * 
	 * @return {@link #nbUtilisationMax}.
	 */
	public Integer getNbUtilisationMax() {
		return nbUtilisationMax;
	}

	/**
	 * 
	 * @param nbUtilisationMax
	 *            the new {@link #nbUtilisationMax}
	 */
	public void setNbUtilisationMax(Integer nbUtilisationMax) {
		this.nbUtilisationMax = nbUtilisationMax;
	}

	/**
	 * 
	 * @return {@link #dateDebut}
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * 
	 * @param dateDebut
	 *            the new {@link #dateDebut}
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * 
	 * @return {@link #dateFin}
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * 
	 * @param dateFin
	 *            the new {@link #dateFin}
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * 
	 * @return {@link Reduction#referenceFrais}
	 */
	public String getReferenceFrais() {
		return referenceFrais;
	}

	/**
	 * 
	 * @param referenceFrais
	 *            the new {@link #referenceFrais}
	 */
	public void setReferenceFrais(String referenceFrais) {
		this.referenceFrais = referenceFrais;
	}

}
