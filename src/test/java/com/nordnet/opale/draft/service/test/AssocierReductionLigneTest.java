package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link DraftServiceImpl#associerReductionLigne(String, String, com.nordnet.opale.business.ReductionInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class AssocierReductionLigneTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AssocierReductionLigneTest.class);

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
	 * tester le cas d'associaion d'une reduction au {@link DraftLigne}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void associerReductionLigneValide() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			String result = (String) draftService.associerReductionLigne("REF-DRAFT", "REF-LIGNE", reductionInfo);
			assertTrue(result.contains("referenceReduction"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'associaion d'une reduction au {@link DraftLigne} pour un draft qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void associerReductionLigneAvecDraftNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReductionLigne("00000000", "REF-LIGNE", reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'associaion d'une reduction au {@link DraftLigne} qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void associerReductionLigneAvecLigneNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReductionLigne("REF-DRAFT", "00000000", reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
