package com.nordnet.opale.service.paiement;

import java.util.List;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.ModePaiement;
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
	 * sauvegarder un {@link Paiement} dans la base de données.
	 * 
	 * @param paiement
	 *            {@link Paiement}.
	 */
	public void save(Paiement paiement);

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
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 * @return {@link Paiement}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Paiement ajouterIntentionPaiement(String referenceCommande, ModePaiement modePaiement) throws OpaleException;

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

}
