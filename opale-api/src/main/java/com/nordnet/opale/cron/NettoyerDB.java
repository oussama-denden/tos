package com.nordnet.opale.cron;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;

/**
 * cron responsable de nettoyer la base de données des données inutiles.
 * 
 * @author anisselmane.
 * 
 */
public class NettoyerDB extends QuartzJobBean {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(NettoyerDB.class);

	/**
	 * draft service. {@link DraftService}.
	 */
	private DraftService draftService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("Cron: nettoyer DB");

		List<String> referenceDraftsAnnule = new ArrayList<String>();

		referenceDraftsAnnule = draftService.findReferenceDraftAnnule();

		for (String reference : referenceDraftsAnnule) {
			try {
				Draft draft = draftService.getDraftByReference(reference);
				draftService.supprimerDraft(draft.getReference());
			} catch (OpaleException e) {
				LOGGER.error("erreur lors du netoyage de la base de donnees", e);
			}
		}

	}

	/**
	 * 
	 * @param draftService
	 *            {@link DraftService}.
	 */
	public void setDraftService(DraftService draftService) {
		this.draftService = draftService;
	}
}
