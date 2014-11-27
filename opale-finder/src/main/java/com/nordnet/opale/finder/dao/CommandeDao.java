package com.nordnet.opale.finder.dao;

import java.util.List;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.exception.OpaleException;

/**
 * 
 * @author anisselmane.
 * 
 */
public interface CommandeDao {

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
	 * recuperer commandes par page.
	 * 
	 * @param pageNo
	 *            numero de page
	 * @param pageSize
	 *            nombre de ligne
	 * @param idClient
	 *            l id du client
	 * @return liste de {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public List<Commande> findByIdClient(final int pageNo, final int pageSize, String idClient) throws OpaleException;

}
