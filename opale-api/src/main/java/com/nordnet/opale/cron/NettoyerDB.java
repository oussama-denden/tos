package com.nordnet.opale.cron;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nordnet.common.alert.ws.client.SendAlert;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.spring.ApplicationContextHolder;

/**
 * cron responsable de nettoyer la base de donnÃ©es des donnÃ©es inutiles.
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

	private SendAlert sendAlert;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		LOGGER.info("Cron: nettoyer DB");

		List<String> referenceDraftsAnnule = new ArrayList<>();

		referenceDraftsAnnule = draftService.findReferenceDraftAnnule();

		for (String reference : referenceDraftsAnnule) {
			try {
				Draft draft = draftService.getDraftByReference(reference);
				draftService.supprimerDraft(draft.getReference());
			} catch (OpaleException e) {
				LOGGER.error("erreur lors du netoyage de la base de donnees", e);

				try {
					getSendAlert().send(System.getProperty(Constants.PRODUCT_ID), "Erreur dans le cron nettoyer DB ",
							"cause: " + e.getCause().getLocalizedMessage(), e.getMessage());
				} catch (Exception ex) {
					LOGGER.error("fail to send alert", ex);
				}
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
