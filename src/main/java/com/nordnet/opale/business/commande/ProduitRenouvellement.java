package com.nordnet.opale.business.commande;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.TypeProduit;
import com.nordnet.opale.enums.deserializer.TypeProduitDeserializer;

/**
 * Cette classe regroupe les informations qui definissent un {@link produitRenouvellement}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ProduitRenouvellement {

	/**
	 * reference du produit.
	 */
	private String referenceProduit;

	/**
	 * numero element contractuelle.
	 */
	private Integer numEC;

	/**
	 * prix de renouvellement.
	 */
	private PrixRenouvellemnt prix;

	/**
	 * le nom de {@link Produit} par exemple: Pack Sat Max 10Go.
	 */
	private String label;

	/**
	 * Le champ "BillingGroup", lorsqu'il doit être rempli, le sera avec le
	 * numéro de la commande.
	 */
	private String numeroCommande;

	/** The type produit. {@link TypeProduit} . */
	@JsonDeserialize(using = TypeProduitDeserializer.class)
	private TypeProduit typeProduit;

	/**
	 * le numero de element contractuel parent.
	 */
	private Integer numECParent;
	
	/**
	 * un EL peut etre remboursable ou non.
	 */
	private Boolean remboursable;

	/**
	 * constructeur par defaut.
	 */
	public ProduitRenouvellement() {

	}

	/* Getters & Setters */

	/**
	 * 
	 * @return {@link #referenceProduit}
	 */
	public String getReferenceProduit() {
		return referenceProduit;
	}

	/**
	 * 
	 * @param referenceProduit
	 *            the new reference produit {@link #referenceProduit}
	 */
	public void setReferenceProduit(String referenceProduit) {
		this.referenceProduit = referenceProduit;
	}

	/**
	 * 
	 * @return {@link #numEC}
	 */
	public Integer getNumEC() {
		return numEC;
	}

	/**
	 * 
	 * @param numEC
	 *            the new numEC {@link #numEC}
	 */
	public void setNumEC(Integer numEC) {
		this.numEC = numEC;
	}

	/**
	 * 
	 * @return {@link #prix}
	 */
	public PrixRenouvellemnt getPrix() {
		return prix;
	}

	/**
	 * 
	 * @param prix
	 *            the new prix {@link #prix}
	 */
	public void setPrix(PrixRenouvellemnt prix) {
		this.prix = prix;
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
	 * @return {@link #numeroCommande}
	 */
	public String getNumeroCommande() {
		return numeroCommande;
	}

	/**
	 * 
	 * @param numeroCommande
	 *            the new {@link #numeroCommande}
	 */
	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}

	/**
	 * 
	 * @return {@link #typeProduit}
	 */
	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

	/**
	 * @param typeProduit
	 *            the new {@link #typeProduit}
	 */
	public void setTypeProduit(TypeProduit typeProduit) {
		this.typeProduit = typeProduit;
	}

	/**
	 * 
	 * @return {@link #numECParent}
	 */
	public Integer getNumECParent() {
		return numECParent;
	}

	/**
	 * 
	 * @param numECParent
	 *            the new {@link #numECParent}
	 */
	public void setNumECParent(Integer numECParent) {
		this.numECParent = numECParent;
	}
	
	/**
	 * 
	 * @return indique que l'element est remboursable ou non.
	 */
	public Boolean isRemboursable() {
		return remboursable;
	}

	/**
	 * 
	 * @param remboursable
	 *            remboursable.
	 */
	public void setRemboursable(Boolean remboursable) {
		this.remboursable = remboursable;
	}

}
