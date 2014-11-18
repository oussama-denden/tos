package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.CodePartenaireInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

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
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * tester le cas valide d'association du code partenaire.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-code-partenaire.xml" })
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
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-code-partenaire.xml" })
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
