package com.nordnet.opale.finder.service;

import java.util.List;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeInfo;
import com.nordnet.opale.finder.exception.OpaleException;

/**
 * La service ContratService va contenir tous les operations en rapport avec la commande.
 * 
 * @author anisselmane.
 * 
 */
public interface CommandeService {

	/**
	 * Recuperer la liste des commandes.
	 * 
	 * @param idClient
	 *            l id du client
	 * 
	 * @return liste de {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public List<Commande> findByIdClient(String idClient) throws OpaleException;

	/**
	 * Recuperer la liste des commandes par reference commande.
	 * 
	 * @param referenceCommande
	 *            reference Commande
	 * 
	 * @return liste de {@link CommandeInfo}.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public CommandeInfo findByReferenceCommande(String referenceCommande) throws OpaleException;

}
