package com.nordnet.opale.cron;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.common.alert.ws.client.SendAlert;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;
import com.nordnet.opale.util.spring.ApplicationContextHolder;

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
	private final static Logger LOGGER = Logger.getLogger(SupprimerCommandes.class);

	/**
	 * {@link CommandeService}.
	 */
	private CommandeService commandeService;

	/**
	 * 
	 */
	private SendAlert sendAlert;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("Cron: Supprimer commandes");

		List<String> referenceCommandes = commandeService.getReferenceCommandeNonAnnuleEtNonTransformes();
		for (String reference : referenceCommandes) {
			try {
				Commande commande = commandeService.getCommandeByReference(reference);
				if (checkCommandeActive(commande)) {
					LOGGER.info("annulation automatique du commande :" + commande.getReference());
					commande.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());
					commandeService.save(commande);
				}
			} catch (OpaleException | ParseException ex) {
				LOGGER.error("erreur lors de la suppression des commandes inactives", ex);

				try {
					getSendAlert().send(System.getProperty(Constants.PRODUCT_ID),
							"Erreur dans le cron Supprimer commandes ",
							"cause: " + ex.getCause().getLocalizedMessage(), ex.getMessage());
				} catch (Exception e) {
					LOGGER.error("fail to send alert", e);
				}
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
	private Boolean checkCommandeActive(Commande commande) throws OpaleException, ParseException {
		LocalDate dateJour = LocalDate.fromDateFields(PropertiesUtil.getInstance().getDateDuJour());
		Date dateAction = Utils.parseDate(commandeService.getRecentDate(commande.getReference()));
		LocalDate dateDerniereAction = LocalDate.fromDateFields(dateAction);
		LocalDate dateCreationCommande =
				commande.getDateCreation() != null ? LocalDate.fromDateFields(commande.getDateCreation()) : null;
		Integer delaiInactive = PropertiesUtil.getInstance().getDureeInactive();
		if ((dateCreationCommande != null && dateCreationCommande.plusDays(delaiInactive).isBefore(dateJour) && dateCreationCommande
				.isAfter(dateDerniereAction))
				|| (dateCreationCommande != null && dateCreationCommande.isBefore(dateDerniereAction) && dateDerniereAction
						.plusDays(delaiInactive).isBefore(dateJour))) {
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

	/**
	 * Get send alert.
	 * 
	 * @return {@link #sendAlert}
	 */
	private SendAlert getSendAlert() {
		if (this.sendAlert == null) {
			if (System.getProperty("alert.useMock").equals("true")) {
				sendAlert = (SendAlert) ApplicationContextHolder.getBean("sendAlertMock");
			} else {
				sendAlert = (SendAlert) ApplicationContextHolder.getBean("sendAlert");
			}
		}
		return sendAlert;
	}

}
