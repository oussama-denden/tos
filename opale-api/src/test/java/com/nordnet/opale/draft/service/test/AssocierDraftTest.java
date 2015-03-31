package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.ClientInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;

/**
 * Classe de test pour la methode {@link DraftService#annulerDraft(String)} .
 * 
 * @author anisselmane.
 * 
 */
public class AssocierDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AssocierDraftTest.class);

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
	 * annuler draft valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-draft.xml" })
	public void testerAssocierDraftValide() {

		try {
			draftService.associerClient("00000001",
					draftInfoGenerator.getObjectFromJsonFile(ClientInfo.class, "./requests/associerDraft.json"));

			Draft draft = draftService.getDraftByReference("00000001");
			assertNotNull(draft.getClientAFacturer());
			assertEquals("client-1245", draft.getClientAFacturer().getClientId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Creer un draft incomplet.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-draft.xml" })
	public void GivenClientWithoutClientIdWhenAssocierDraftThenFail() {

		try {
			draftService.associerClient("00000001", draftInfoGenerator.getObjectFromJsonFile(ClientInfo.class,
					"./requests/associerDraftWithoutClientId.json"));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.5", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Annuler un draft inexistant.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-draft.xml" })
	public void GivenDraftNotExistsWhenAssocierDraftThenFail() {

		try {
			draftService.associerClient("00000002",
					draftInfoGenerator.getObjectFromJsonFile(ClientInfo.class, "./requests/associerDraft.json"));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
