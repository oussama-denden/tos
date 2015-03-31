package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.CodePartenaireInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;

/**
 * classe de test de la methode
 * {@link DraftService#associerCodePartenaire(String, com.nordnet.opale.business.CodePartenaireInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class AssocierCodePartenaireTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AssocierCodePartenaireTest.class);

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
	 * tester le cas valide d'association du code partenaire.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-code-partenaire.xml" })
	public void testerAssocierCodePartenaireValide() {
		try {
			Draft draft = draftService.getDraftByReference("00000001");
			assertNull(draft.getCodePartenaire());
			CodePartenaireInfo codePartenaireInfo =
					draftInfoGenerator.getObjectFromJsonFile(CodePartenaireInfo.class,
							"./requests/associerCodePartenaire.json");
			draftService.associerCodePartenaire("00000001", codePartenaireInfo);
			draft = draftService.getDraftByReference("00000001");
			assertNotNull(draft.getCodePartenaire());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'association du code partenaire a un draft qui n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-code-partenaire.xml" })
	public void testerAssocierCodePartenaireAvecDraftNonExist() {
		try {
			CodePartenaireInfo codePartenaireInfo =
					draftInfoGenerator.getObjectFromJsonFile(CodePartenaireInfo.class,
							"./requests/associerCodePartenaire.json");
			draftService.associerCodePartenaire("00000000", codePartenaireInfo);
			fail("Unexpected Exception");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
