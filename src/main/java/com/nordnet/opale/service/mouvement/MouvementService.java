package com.nordnet.opale.service.mouvement;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.paiement.Paiement;

/**
 * contient les service d'envoi des mouvements vers saphir.
 * 
 * @author akram-moncer
 * 
 */
public interface MouvementService {

	/**
	 * Envoie d'un mouvement vers saphir pour chaque {@link Paiement} reçue.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @param paiement
	 *            {@link Paiement}.
	 */
	public void envoieMouvement(Commande commande, Paiement paiement);

}
