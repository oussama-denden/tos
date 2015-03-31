package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.Constants;

/**
 * Classe de test de la methode {@link DraftService#ajouterLignes(String, java.util.List)} .
 * 
 * @author akram-moncer
 * 
 */
public class AjouterLignesTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AjouterLignesTest.class);

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
	 * Tester le cas de l'ajout valid d'une ligne valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajouter-ligne-draft.xml" })
	@Transactional
	public void testAjouterLignesValide() {
		try {
			DraftLigneInfo[] draftLignesInfo =
					draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo[].class, "./requests/ajouterLignes.json");
			draftService.ajouterLignes("REF-DRAFT-1", Arrays.asList(draftLignesInfo));
			Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(draft.getDraftLignes().size()));
			assertEquals(Double.valueOf(Constants.DEUX),
					Double.valueOf(draft.getDraftLignes().get(Constants.ZERO).getDraftLigneDetails().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/emptyDB.xml", "/dataset/ajouter-ligne-draft.xml" })
	public void testAjouterLignesAvecDraftNonExist() {
		try {
			DraftLigneInfo[] draftLignesInfo =
					draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo[].class, "./requests/ajouterLignes.json");
			draftService.ajouterLignes("REF-DRAFT-2", Arrays.asList(draftLignesInfo));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas d'ajout avec une offre non valid.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/emptyDB.xml", "/dataset/ajouter-ligne-draft.xml" })
	public void testAjouterLignesAvecOffreNonValide() {
		try {
			DraftLigneInfo[] draftLignesInfo =
					draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo[].class, "./requests/ajouterLignes.json");
			draftLignesInfo[Constants.ZERO].getOffre().setReferenceOffre(null);
			draftService.ajouterLignes("REF-DRAFT-1", Arrays.asList(draftLignesInfo));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
