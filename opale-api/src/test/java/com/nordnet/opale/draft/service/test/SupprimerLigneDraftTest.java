package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.service.draft.DraftService;

/**
 * Classe de test pour la methode {@link DraftService#supprimerLigneDraft(String, String, DeleteInfo)} .
 * 
 * @author anisselmane.
 * 
 */
public class SupprimerLigneDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupprimerLigneDraftTest.class);

	/**
	 * {@link DraftService}.
	 */
	@Autowired
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * {@link DraftLigneRepository}.
	 */
	@Autowired
	private DraftLigneRepository draftLigneRepository;

	/**
	 * Supprimer ligne draft valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/annuler-draft.xml" })
	@Transactional
	public void testerSupprimerLigneDraftValide() {

		try {
			draftService.supprimerLigneDraft("00000001", "00000001",
					draftInfoGenerator.getObjectFromJsonFile(DeleteInfo.class, "./requests/supprimerLigneDraft.json"));
			draftLigneRepository.flush();
			Draft draft = draftService.getDraftByReference("00000001");
			assertEquals(0, draft.getDraftLignes().size());

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Supprimer une ligne draft quand lla ligne draft est inexistante.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml" })
	public void GivenLigneDraftNotExistsWhenSupprimerLigneDraftThenFail() {

		try {
			draftService.supprimerLigneDraft("00000001", "00000002",
					draftInfoGenerator.getObjectFromJsonFile(DeleteInfo.class, "./requests/supprimerLigneDraft.json"));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.39", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Supprimer une ligne draft quand le draft est inexistant.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml" })
	public void GivenDraftNotExistsWhenSupprimerLigneDraftThenFail() {

		try {
			draftService.supprimerLigneDraft("00000002", "00000001",
					draftInfoGenerator.getObjectFromJsonFile(DeleteInfo.class, "./requests/supprimerLigneDraft.json"));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.39", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
