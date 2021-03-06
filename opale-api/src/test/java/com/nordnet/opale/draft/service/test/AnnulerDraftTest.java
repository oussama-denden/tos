package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.Auteur;
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
public class AnnulerDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AnnulerDraftTest.class);

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
	 * 
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/annuler-draft.xml" })
	public void testerAnnulerDraftValide() throws JsonParseException, JsonMappingException, IOException {
		Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
		try {
			draftService.annulerDraft("00000001", auteur);

			Draft draft = draftService.getDraftByReference("00000001");
			assertEquals("00000001", draft.getReference());
			assertNotNull(draft.getDateAnnulation());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Annuler un draft inexistant.
	 * 
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml" })
	public void GivenDraftNotExistsWhenAnnulerDraftThenFail()
			throws JsonParseException, JsonMappingException, IOException {

		Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
		try {
			draftService.annulerDraft("00000002", auteur);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
