package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.service.draft.DraftService;

/**
 * Classe de test pour la methode {@link DraftService#supprimerDraft(String)} .
 * 
 * @author anisselmane.
 * 
 */
public class SupprimerDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupprimerDraftTest.class);

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
	public void testerSupprimerDraftValide() {

		try {
			draftService.supprimerDraft("00000001");
			draftLigneRepository.flush();
			Draft draft = draftService.findDraftByReference("00000001");
			assertNull(draft);

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
	public void GivenLigneDraftNotExistsWhenSupprimerDraftThenFail() {

		try {
			draftService.supprimerDraft("00000002");
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
