package com.nordnet.opale.service.paiement;

import java.util.List;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;

/**
 * La service PaiementService va contenir tous les operations sur les {@link Paiement}.
 * 
 * @author akram-moncer
 * 
 */
public interface PaiementService {

	/**
	 * chercher un paiement par reference commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return {@link Paiement}.
	 */
	public List<Paiement> getPaiementByReferenceCommande(String referenceCommande);

	/**
	 * chercher un paiement par sa reference.
	 * 
	 * @param reference
	 *            reference paiement.
	 * @return {@link Paiement}.
	 */
	public Paiement getPaiementByReference(String reference);

	/**
	 * calculer le montant comptant payer pour une commande.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return montant total paye.
	 */
	public Double montantComptantPaye(String referenceCommande);

	/**
	 * chercher l'intention de paiement associe a la commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return {@link Paiement}.
	 */
	public Paiement getIntentionPaiement(String referenceCommande);

	/**
	 * ajouter une intention de paiement pour une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @return {@link Paiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Paiement ajouterIntentionPaiement(String referenceCommande, PaiementInfo paiementInfo) throws OpaleException;

	/**
	 * tester si le paiement est possible avant de faire l'appel de la methode 'effectuerPaiement'.
	 * 
	 * @param referencePaiement
	 *            reference {@link Paiement}.
	 * @param referenceCommade
	 *            reference commande.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public void isEffectuerPaiementPossible(String referencePaiement, String referenceCommade, PaiementInfo paiementInfo)
			throws OpaleException;

	/**
	 * effectuer un paiement. Si la reference de paiement est null, un {@link Paiement} sera cree.
	 * 
	 * @param referencePaiement
	 *            reference paiement.
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @param paiementInfo
	 *            {@link PaiementInfo}.
	 * @param typePaiement
	 *            {@link TypePaiement}.
	 * @return {@link Paiement} ou null si le paiement existe deja.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Paiement effectuerPaiement(String referencePaiement, String referenceCommande, PaiementInfo paiementInfo,
			TypePaiement typePaiement) throws OpaleException;

	/**
	 * retourner la liste des paiement comptant d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @param isAnnule
	 *            si annule
	 * @return liste {@link Paiement}.
	 */
	public List<Paiement> getListePaiementComptant(String referenceCommande, boolean isAnnule);

	/**
	 * retourner le paiement recurrent d'une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @param isAnnule
	 *            si annule
	 * @return {@link Paiement}.
	 */
	public List<Paiement> getPaiementRecurrent(String referenceCommande, boolean isAnnule);

	/**
	 * Applique une suppression physiquement un intention et logique pour un paiement.
	 * 
	 * @param refCommande
	 *            reference commande
	 * @param refPaiement
	 *            reference paiement
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public void supprimer(String refCommande, String refPaiement) throws OpaleException;

	/**
	 * retourner la liste des paiements en cours d une commande selon le type de paiement.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * 
	 * @param typePaiement
	 *            {@link TypePaiement}
	 * @return Liste de {@link Paiement}
	 */
	List<Paiement> getPaiementEnCours(String referenceCommande, TypePaiement typePaiement);

	/**
	 * retourner la liste des paiements en cours d une commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return Liste de {@link Paiement}
	 */
	List<Paiement> getPaiementEnCours(String referenceCommande);

}
