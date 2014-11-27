package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link DraftServiceImpl#associerReductionFraisLigne(String, String, String, com.nordnet.opale.business.ReductionInfo)}
 * .
 * 
 * @author akram-moncer
 * 
 */
public class AssocierReductionFraisLigneTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AssocierReductionFraisLigneTest.class);

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
	 * tester le cas d'association d'une reduction au frais ligne.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void testerAssocierReductionFraisLigneValide() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			String result =
					(String) draftService.associerReductionFraisLigne("REF-DRAFT", "REF-LIGNE", "std_cloture",
							reductionInfo);
			assertTrue(result.contains("referenceReduction"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'association d'une reduction au frais ligne pour un draft qui n'exsite pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void testerAssocierReductionFraisLigneAvecDraftNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReductionFraisLigne("00000000", "REF-LIGNE", "std_cloture", reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'association d'une reduction au frais ligne pour une ligne qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void testerAssocierReductionFraisLigneAvecLigneNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReductionFraisLigne("REF-DRAFT", "00000000", "std_cloture", reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
