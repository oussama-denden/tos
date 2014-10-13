package com.nordnet.opale.service.commande;

import javax.activation.CommandInfo;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;

/**
 * Contient les operation sur les {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
public interface CommandeService {

	/**
	 * sauver un {@link Commande} dans la base de données.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 */
	public void save(Commande commande);

	/**
	 * recuperer une commande
	 * 
	 * @param refCommande
	 *            {@link String}
	 * 
	 * @return {@link CommandInfo}
	 * 
	 * @throws OpaleException
	 * @{@link OpaleException}
	 */
	public CommandeInfo getCommande(String refCommande) throws OpaleException;

}
