package com.nordnet.opale.cron;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.service.commande.CommandeService;

/**
 * Cron responsable de supprimer automatiquement les commandes inactives.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class SupprimerCommandes extends QuartzJobBean {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(NettoyerDB.class);

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("Cron: Supprimer commandes");

		List<Commande> commandes = commandeService.getCommandeNonAnnuleEtNonTransformes();

	}

	/**
	 * verifer si une commande et eligible pour suppression.
	 * 
	 * @param commande
	 *            {@link Commande}
	 * @return true si la commande et eligible pour suppression.
	 */
	private Boolean checkCommandeActive(Commande commande) {

		return false;

	}

}
