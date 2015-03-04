package com.nordnet.opale.cron;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.common.alert.ws.client.SendAlert;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.validator.CommandeValidator;

/**
 * Cron pour transforme les commandes en contrats.
 * 
 * @author Oussama Denden
 * 
 */
public class TransformerEnContrat extends QuartzJobBean {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(TransformerEnContrat.class);

	/**
	 * draft service. {@link CommandeService}.
	 */
	private CommandeService commandeService;

	/**
	 * {@link SendAlert}
	 */
	@Autowired
	private SendAlert sendAlert;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("Cron: Transformer Commande En Contrat");

		List<String> Referencecommandes = commandeService.getReferenceCommandeNonAnnuleEtNonTransformes();

		try {
			for (String reference : Referencecommandes) {
				LOGGER.info(" traittement du commande " + reference);

				Commande commande = commandeService.getCommandeByReference(reference);
				CommandeValidator.isExiste(reference, commande);

				if (commandeService.validerCommandeEnTransformationAutomatique(commande)) {
					LOGGER.info("TransformerEnContrat: contrat valide " + commande.getReference());
					Auteur auteur = new Auteur();
					auteur.setQui(Constants.INTERNAL_USER);
					try {
						commandeService.transformeEnContrat(commande, auteur);
					} catch (JSONException exception) {
						LOGGER.error("erreur lors de la transformation des commandes en contrats", exception);

						try {
							sendAlert.send(System.getProperty(Constants.PRODUCT_ID),
									"Erreur dans le cron Transformer Commande En Contrat ", "cause: "
											+ exception.getCause().getLocalizedMessage(), exception.getMessage());
						} catch (Exception e) {
							LOGGER.error("fail to send alert", e);
						}
					}
				}
			}

		} catch (OpaleException ex) {
			LOGGER.error("erreur lors de la transformation des commandes en contrats", ex);

			try {
				sendAlert.send(System.getProperty(Constants.PRODUCT_ID),
						"Erreur dans le cron Transformer Commande En Contrat ", "cause: "
								+ ex.getCause().getLocalizedMessage(), ex.getMessage());
			} catch (Exception e) {
				LOGGER.error("fail to send alert", e);
			}
		}

	}

	/**
	 * @param commandeService
	 *            {@link #commandeService}.
	 */
	public void setCommandeService(CommandeService commandeService) {
		this.commandeService = commandeService;
	}
}
