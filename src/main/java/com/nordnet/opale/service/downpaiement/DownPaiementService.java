package com.nordnet.opale.service.downpaiement;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;

/**
 * contient les service d'envoi des mouvements vers saphir.
 * 
 * @author akram-moncer
 * 
 */
public interface DownPaiementService {

	/**
	 * Envoie d'un paiement vers saphir pour chaque {@link Paiement} reçue.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @param paiement
	 *            {@link Paiement}.
	 */
	public void envoiePaiement(Commande commande, Paiement paiement);

}
