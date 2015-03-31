package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.Constants;

/**
 * Classe de test pour la methode
 * {@link DraftService#modifierLigne(String, String, com.nordnet.opale.business.DraftLigneInfo)} .
 * 
 * @author akram-moncer
 * 
 */
public class ModifierLigneTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ModifierLigneTest.class);

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
	 * Mofifier une ligne valide.
	 * 
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/modifier-ligne-draft.xml" })
	@Transactional
	public void testerModifierLigneValide() throws JsonParseException, JsonMappingException, IOException {
		DraftLigneInfo draftLigneInfo =
				draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/modifierLignes.json");
		try {
			draftService.modifierLigne("REF-DRAFT-1", "00000001", draftLigneInfo);

			Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(draft.getDraftLignes().size()));
			DraftLigne draftLigne = draft.getDraftLignes().get(Constants.ZERO);
			assertEquals(Double.valueOf(Constants.DEUX), Double.valueOf(draftLigne.getDraftLigneDetails().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 * 
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/modifier-ligne-draft.xml" })
	public void testerModifierLignePourDraftNonExist() throws JsonParseException, JsonMappingException, IOException {
		DraftLigneInfo draftLigneInfo =
				draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/modifierLignes.json");
		try {
			draftService.modifierLigne("REF-DRAFT-2", "00000001", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 * 
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/modifier-ligne-draft.xml" })
	public void testerModifierLigneNonExist() throws JsonParseException, JsonMappingException, IOException {
		DraftLigneInfo draftLigneInfo =
				draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/modifierLignes.json");
		try {
			draftService.modifierLigne("REF-DRAFT-1", "00000002", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.39", e.getErrorCode());
		}
	}

	/**
	 * Tester le cas de modifier une ligne avec une offre non valid.
	 * 
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/modifier-ligne-draft.xml" })
	public void testModifierLigneAvecOffreNonValide() throws JsonParseException, JsonMappingException, IOException {
		DraftLigneInfo draftLigneInfo =
				draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/modifierLignes.json");
		draftLigneInfo.getOffre().setReferenceOffre(null);
		try {
			draftService.modifierLigne("REF-DRAFT-1", "00000001", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		}
	}
}
