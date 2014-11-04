package com.nordnet.opale.business.commande;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
import com.nordnet.opale.deserializer.ModeFacturationDeserializer;
import com.nordnet.opale.deserializer.ModePaiementDeserializer;
import com.nordnet.opale.deserializer.TypeProduitDeserializer;
import com.nordnet.opale.deserializer.TypeTVADeserializer;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.TypeProduit;
import com.nordnet.opale.enums.TypeTVA;

/**
 * Cette classe regroupe les informations qui definissent un {@link ElementContractuel}.
 * 
 * @author Denden-OUSSAMA
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementContractuel {

	/**
	 * Le champ "BillingGroup", lorsqu'il doit être rempli, le sera avec le numéro de la commande.
	 */
	private String numeroCommande;

	/**
	 * adresse de facturation.
	 */
	private String idAdrFacturation;

	/**
	 * adresse de livraison.
	 */
	private String idAdrLivraison;

	/**
	 * referenceProduit(reference de produit auquel ce ContratABO sera associe).
	 */
	private String referenceProduit;

	/**
	 * le ProductId que sera utilisé dans saphir.
	 */
	private String idProduit;

	/**
	 * Numero de l'element contractuel.
	 */
	private Integer numEC;

	/**
	 * Numero de l'element contractuel parent.
	 */
	private Integer numECParent;

	/**
	 * le type de produit associe a l'element contractuel il peut etre BIEN/SERVICE.
	 */
	@JsonDeserialize(using = TypeProduitDeserializer.class)
	private TypeProduit typeProduit;

	/**
	 * engagement par exmple 24 mois.
	 */
	private Integer engagement;

	/**
	 * duree du sous contrat en mois.
	 */
	private Integer duree;

	/**
	 * montant a payer.
	 */
	private Double montant;

	/** The type tva. {@link TypeTVA}. */
	@JsonDeserialize(using = TypeTVADeserializer.class)
	private TypeTVA typeTVA;

	/**
	 * La période de facturation.
	 */
	private Integer periodicite;

	/** The mode paiement. {@link ModePaiement}. */
	@JsonDeserialize(using = ModePaiementDeserializer.class)
	private ModePaiement modePaiement;

	/**
	 * reference de mode paiement.
	 */
	private String referenceModePaiement;

	/** The mode facturation. {@link ModeFacturation}. */
	@JsonDeserialize(using = ModeFacturationDeserializer.class)
	private ModeFacturation modeFacturation;

	/**
	 * liste des frais.
	 */
	private Set<Frais> frais = new HashSet<Frais>();

	/**
	 * constructeur par defaut.
	 */
	public ElementContractuel() {

	}

	@Override
	public String toString() {
		return "ElementContractuel [numeroCommande=" + numeroCommande + ", idAdrFacturation=" + idAdrFacturation
				+ ", idAdrLivraison=" + idAdrLivraison + ", referenceProduit=" + referenceProduit + ", idProduit="
				+ idProduit + ", numEC=" + numEC + ", numECParent=" + numECParent + ", typeProduit=" + typeProduit
				+ ", engagement=" + engagement + ", duree=" + duree + ", montant=" + montant + ", typeTVA=" + typeTVA
				+ ", periodicite=" + periodicite + ", modePaiement=" + modePaiement + ", referenceModePaiement="
				+ referenceModePaiement + ", modeFacturation=" + modeFacturation + ", frais=" + frais + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ElementContractuel)) {
			return false;
		}
		ElementContractuel rhs = (ElementContractuel) obj;
		return new EqualsBuilder().append(idAdrFacturation, rhs.idAdrFacturation)
				.append(idAdrLivraison, rhs.idAdrLivraison).append(numEC, rhs.numEC).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(43, 11).append(referenceProduit).toHashCode();
	}

	/* Getters & Setters */

	/**
	 * @return {@link #numeroCommande}.
	 */
	public String getNumeroCommande() {
		return numeroCommande;
	}

	/**
	 * @param numeroCommande
	 *            {@link #numeroCommande}.
	 */
	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}

	/**
	 * Gets the reference produit.
	 * 
	 * @return {@link #referenceProduit}.
	 */
	public String getReferenceProduit() {
		return referenceProduit;
	}

	/**
	 * Sets the reference produit.
	 * 
	 * @param referenceProduit
	 *            the new reference produit {@link #referenceProduit}.
	 */
	public void setReferenceProduit(String referenceProduit) {
		this.referenceProduit = referenceProduit;
	}

	/**
	 * 
	 * @return {@link #idProduit}.
	 */
	public String getIdProduit() {
		return idProduit;
	}

	/**
	 * 
	 * @param idProduit
	 *            {@link #idProduit}.
	 */
	public void setIdProduit(String idProduit) {
		this.idProduit = idProduit;
	}

	/**
	 * 
	 * @return {@link #numEC}.
	 */
	public Integer getNumEC() {
		return numEC;
	}

	/**
	 * 
	 * @param numEC
	 *            {@link #numEC}.
	 */
	public void setNumEC(Integer numEC) {
		this.numEC = numEC;
	}

	/**
	 * 
	 * @return {@link #numECParent}.
	 */
	public Integer getNumECParent() {
		return numECParent;
	}

	/**
	 * 
	 * @param numECParent
	 *            {@link #numECParent}
	 */
	public void setNumECParent(Integer numECParent) {
		this.numECParent = numECParent;
	}

	/**
	 * Gets the type produit.
	 * 
	 * @return {@link #typeProduit}
	 */
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

	/**
	 * Sets the type produit.
	 * 
	 * @param typeProduit
	 *            the new type produit {@link #typeProduit}
	 */
	public void setTypeProduit(TypeProduit typeProduit) {
		this.typeProduit = typeProduit;
	}

	/**
	 * Gets the engagement.
	 * 
	 * @return {@link #engagement}.
	 */
	public Integer getEngagement() {
		return engagement;
	}

	/**
	 * Sets the engagement.
	 * 
	 * @param engagement
	 *            the new engagement {@link #engagement}.
	 */
	public void setEngagement(Integer engagement) {
		this.engagement = engagement;
	}

	/**
	 * Gets the duree.
	 * 
	 * @return {@link #duree}.
	 */
	public Integer getDuree() {
		return duree;
	}

	/**
	 * Sets the duree.
	 * 
	 * @param duree
	 *            the new duree {@link #duree}.
	 */
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	/**
	 * Gets the montant.
	 * 
	 * @return {@link #montant}.
	 */
	public Double getMontant() {
		return montant;
	}

	/**
	 * Sets the montant.
	 * 
	 * @param montant
	 *            the new montant {@link #montant}.
	 */
	public void setMontant(Double montant) {
		this.montant = montant;
	}

	/**
	 * Gets the type tva.
	 * 
	 * @return {@link #typeTVA}.
	 */
	public TypeTVA getTypeTVA() {
		return typeTVA;
	}

	/**
	 * Sets the type tva.
	 * 
	 * @param typeTVA
	 *            the new type tva {@link #typeTVA}.
	 */
	public void setTypeTVA(TypeTVA typeTVA) {
		this.typeTVA = typeTVA;
	}

	/**
	 * Gets the periodicite.
	 * 
	 * @return {@link periodicite}.
	 */
	public Integer getPeriodicite() {
		return periodicite;
	}

	/**
	 * Sets the periodicite.
	 * 
	 * @param periodicite
	 *            the new periodicite {@link periodicite}.
	 */
	public void setPeriodicite(Integer periodicite) {
		this.periodicite = periodicite;
	}

	/**
	 * Gets the mode paiement.
	 * 
	 * @return {@link ModePaiement}.
	 */
	public ModePaiement getModePaiement() {
		return modePaiement;
	}

	/**
	 * Sets the mode paiement.
	 * 
	 * @param modePaiement
	 *            the new mode paiement {@link ModePaiement}.
	 */
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}

	/**
	 * Gets the reference mode paiement.
	 * 
	 * @return {@link referenceModePaiement}.
	 */
	public String getReferenceModePaiement() {
		return referenceModePaiement;
	}

	/**
	 * Sets the reference mode paiement.
	 * 
	 * @param referenceModePaiement
	 *            the new reference mode paiement {@link referenceModePaiement}.
	 */
	public void setReferenceModePaiement(String referenceModePaiement) {
		this.referenceModePaiement = referenceModePaiement;
	}

	/**
	 * Gets the frais.
	 * 
	 * @return {@link FraisContrat}.
	 */
	public Set<Frais> getFrais() {
		return frais;
	}

	/**
	 * Sets the frais.
	 * 
	 * @param frais
	 *            the new frais {@link FraisContrat}.
	 */
	public void setFrais(Set<Frais> frais) {
		this.frais = frais;
	}

	/**
	 * Gets the id adr facturation.
	 * 
	 * @return {@link #idAdrFacturation}.
	 */
	public String getIdAdrFacturation() {
		return idAdrFacturation;
	}

	/**
	 * Sets the id adr facturation.
	 * 
	 * @param idAdrFacturation
	 *            the new id adr facturation {@link #idAdrFacturation}.
	 */
	public void setIdAdrFacturation(String idAdrFacturation) {
		this.idAdrFacturation = idAdrFacturation;
	}

	/**
	 * Gets the id adr livraison.
	 * 
	 * @return {@link #idAdrLivraison}.
	 */
	public String getIdAdrLivraison() {
		return idAdrLivraison;
	}

	/**
	 * Sets the id adr livraison.
	 * 
	 * @param idAdrLivraison
	 *            the new id adr livraison {@link #idAdrLivraison}.
	 */
	public void setIdAdrLivraison(String idAdrLivraison) {
		this.idAdrLivraison = idAdrLivraison;
	}

	/**
	 * Gets the mode facturation.
	 * 
	 * @return {@link ModeFacturation}.
	 */
	public ModeFacturation getModeFacturation() {
		return modeFacturation;
	}

	/**
	 * Sets the mode facturation.
	 * 
	 * @param modeFacturation
	 *            the new mode facturation {@link ModeFacturation}.
	 */
	public void setModeFacturation(ModeFacturation modeFacturation) {
		this.modeFacturation = modeFacturation;
	}

	/**
	 * 
	 * @return true si l'element est un parent.
	 */
	public boolean isParent() {
		Optional<Integer> numECParent = Optional.fromNullable(this.numECParent);
		if (numECParent.isPresent()) {
			return true;
		}
		return false;
	}

}
