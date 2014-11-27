package com.nordnet.opale.finder.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.dao.CommandeDao;
import com.nordnet.opale.finder.exception.OpaleException;

/**
 * L'implementation de service {@link CommandeService}.
 * 
 * @author anisselmane.
 * 
 */
@Service("commandeService")
public class CommandeServiceImpl implements CommandeService {

	/**
	 * Declaration du log.
	 */
	private static final Log LOGGER = LogFactory.getLog(CommandeServiceImpl.class);

	/**
	 * Commande DAO.
	 */
	@Autowired
	CommandeDao commandeDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Commande> findByIdClient(String idClient) throws OpaleException {
		LOGGER.info("Entrer methode findByIdClient");
		Date d1 = new Date();
		List<Commande> commandes = commandeDao.findByIdClient(idClient);
		Date d2 = new Date();
		LOGGER.info("Find All Orders in " + (d2.getTime() - d1.getTime()) + "ms");
		LOGGER.info("Fin methode findByIdClient");
		return commandes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Commande> findByIdClient(int numPage, int nombreligne, String idClient) throws OpaleException {
		LOGGER.info("Entrer methode findByIdClient");
		Date d1 = new Date();

		List<Commande> commandes = commandeDao.findByIdClient(numPage, nombreligne, idClient);

		Date d2 = new Date();
		LOGGER.info("Find " + nombreligne + "  Orders in " + (d2.getTime() - d1.getTime()) + "ms");
		LOGGER.info("Fin methode findByIdClient");
		return commandes;

	}

}
