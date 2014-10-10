package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.draft.service.DraftService;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;

/**
 * Classe de test pour la methode {@link DraftService#creerDraft(DraftInfo)} .
 * 
 * @author akram-moncer
 * 
 */
public class CreerDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CreerDraftTest.class);

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * Mofifier une ligne valide.
	 */
	@Test
	public void testerCreerDraftValide() {

		try {
			draftService.creerDraft(draftInfoGenerator.getObjectFromJsonFile(DraftInfo.class,
					"./requests/creerDraft.json"));

			Draft draft = draftService.getDraftByReference("00000001");
			assertEquals("00000001", draft.getReference());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
