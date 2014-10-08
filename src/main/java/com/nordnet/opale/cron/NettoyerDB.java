package com.nordnet.opale.cron;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.draft.service.DraftService;
import com.nordnet.opale.exception.OpaleException;

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

		List<Draft> draftsAnnule = new ArrayList<Draft>();

		draftsAnnule = draftService.findDraftAnnule();

		for (Draft draft : draftsAnnule) {
			try {
				draftService.supprimerDraft(draft.getReference());
			} catch (OpaleException e) {

				LOGGER.error(e.getMessage());
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
