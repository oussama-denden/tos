package com.nordnet.opale.service.paiement;

import java.util.List;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;

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
	 * sauvegarder un {@link Paiement} dans la base de données.
	 * 
	 * @param paiement
	 *            {@link Paiement}.
	 */
	public void save(Paiement paiement);

	/**
	 * calculer le montant total payer pour une {@link Commande}.
	 * 
	 * @param referenceCommande
	 *            reference {@link Commande}.
	 * @return montant total paye.
	 */
	public Double montantPaye(String referenceCommande);

	/**
	 * chercher l'intention de paiement associe a la commande.
	 * 
	 * @param referenceCommande
	 *            reference commande.
	 * @return {@link Paiement}.
	 */
	public Paiement getIntentionPaiement(String referenceCommande);

}
