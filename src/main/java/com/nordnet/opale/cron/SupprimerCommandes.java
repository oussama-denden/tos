package com.nordnet.opale.cron;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

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
	private CommandeService commandeService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("Cron: Supprimer commandes");

		List<Commande> commandes = commandeService.getCommandeNonAnnuleEtNonTransformes();
		for (Commande commande : commandes) {
			try {
				if (checkCommandeActive(commande.getReference())) {
					LOGGER.info("annulation automatique du commande :" + commande.getReference());
					commande.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());
					commandeService.save(commande);
				}
			} catch (OpaleException | ParseException ex) {
				LOGGER.error("Error : " + ex.getMessage());
			}
		}

	}

	/**
	 * verifer si une commande et eligible pour suppression.
	 * 
	 * @param refCommande
	 *            reference du commande
	 * @return true si la commande et eligible pour suppression.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @throws ParseException
	 *             {@link ParseException}
	 * 
	 */
	private Boolean checkCommandeActive(String refCommande) throws OpaleException, ParseException {
		LocalDate dateJour = LocalDate.fromDateFields(PropertiesUtil.getInstance().getDateDuJour());
		Date dateAction = Utils.parseDate(commandeService.getRecentDate(refCommande));
		LocalDate dateDerniereAction = LocalDate.fromDateFields(dateAction);
		Integer delaiInactive = PropertiesUtil.getInstance().getDureeInactive();
		if (dateDerniereAction.plusDays(delaiInactive).isBefore(dateJour)) {
			return true;
		}

		return false;

	}

	/**
	 * set the commande service.
	 * 
	 * @param commandeService
	 *            the new {@link CommandeService}
	 */
	public void setCommandeService(CommandeService commandeService) {
		this.commandeService = commandeService;
	}

}
