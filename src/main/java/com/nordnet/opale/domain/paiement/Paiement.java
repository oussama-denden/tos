package com.nordnet.opale.domain.paiement;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.google.common.base.Optional;
import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.enums.ModePaiement;

/**
 * Represente un paiement d'une {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
@Table(name = "paiement")
@Entity
public class Paiement {

	/**
	 * cle primaire.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * reference du tarif.
	 */
	@NotNull
	private String reference;

	/**
	 * reference commande.
	 */
	private String referenceCommande;

	/**
	 * {@link ModePaiement}.
	 */
	@Enumerated(EnumType.STRING)
	private ModePaiement modePaiement;

	/**
	 * le montant paye.
	 */
	private Double montant;

	/**
	 * info paiement.
	 */
	private String infoPaiement;

	/**
	 * constructeur par defaut.
	 */
	public Paiement() {
	}

	/**
	 * creer un paiement a partir d'un {@link PaiementInfo}.
	 * 
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 */
	public Paiement(PaiementInfo paiementInfo) {
		this.modePaiement = paiementInfo.getModePaiement();
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
	 * @return {@link #referenceCommande}.
	 */
	public String getReferenceCommande() {
		return referenceCommande;
	}

	/**
	 * 
	 * @param referenceCommande
	 *            {@link #referenceCommande}.
	 */
	public void setReferenceCommande(String referenceCommande) {
		this.referenceCommande = referenceCommande;
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
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * 
	 * @param montant
	 *            {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * 
	 * @return {@link #infoPaiement}.
	 */
	public String getInfoPaiement() {
		return infoPaiement;
	}

	/**
	 * 
	 * @param infoPaiement
	 *            {@link #infoPaiement}.
	 */
	public void setInfoPaiement(String infoPaiement) {
		this.infoPaiement = infoPaiement;
	}

	/**
	 * verifier si le payment est encore un intension ou pas.
	 * 
	 * @return true si le payment est une intension.
	 */
	public Boolean isIntension() {
		Optional<Double> montantOP = Optional.fromNullable(montant);
		if (montantOP.isPresent()) {
			return false;
		}

		return true;
	}

	/**
	 * verifier si le paiement a ete paye ou non.
	 * 
	 * @return true si le paiement a ete paye.
	 */
	public Boolean isPaye() {
		return !isIntension();
	}

}
