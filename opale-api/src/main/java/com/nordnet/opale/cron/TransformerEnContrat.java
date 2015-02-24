package com.nordnet.opale.cron;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

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
					} catch (JSONException e) {
						LOGGER.error("erreur lors de la transformation des commandes en contrats", e);
					}
				}
			}

		} catch (OpaleException ex) {
			LOGGER.error("erreur lors de la transformation des commandes en contrats", ex);
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
