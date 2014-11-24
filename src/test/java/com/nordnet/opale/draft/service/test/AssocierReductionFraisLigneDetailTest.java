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
 * {@link DraftServiceImpl#associerReductionFraisLigneDetail(String, String, String, String, com.nordnet.opale.business.ReductionInfo)}
 * .
 * 
 * @author akram-moncer
 * 
 */
public class AssocierReductionFraisLigneDetailTest extends GlobalTestCase {

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
	 * tester le cas valide d'association d'une reduction au frais associe au ligne detail.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void associerReductionFraisLigneDetailValide() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			String result =
					(String) draftService.associerReductionFraisLigneDetail("REF-DRAFT", "REF-LIGNE", "REF-PRODUIT",
							"std_cloture", reductionInfo);
			assertTrue(result.contains("referenceReduction"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas valide d'association d'une reduction au frais associe au ligne detail pour un draft qui n'existe
	 * pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void associerReductionFraisLigneDetailAvecDraftNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReductionFraisLigneDetail("00000000", "REF-LIGNE", "REF-PRODUIT", "std_cloture",
					reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'associaion d'une reduction au draft ligne pour un detail qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/associer-reduction.xml" })
	public void associerReductionFraisLigneDetailAvecProduitNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReductionFraisLigneDetail("REF-DRAFT", "00000000", "REF-PRODUIT", "std_cloture",
					reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.27", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
