package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;

/**
 * classe de teste de la methode {@link DraftServiceImpl#associerReduction(String, ReductionInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class AssocierReductionTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AssocierReductionTest.class);

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
	 * tester le cas d'association d'une reduction valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-reduction.xml" })
	public void testerAssocierReductionValide() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			String result = (String) draftService.associerReduction("REF-DRAFT", reductionInfo);
			Assert.assertTrue(result.contains("referenceReduction"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas d'association d'une reduction a un draft qui n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-reduction.xml" })
	public void testerAssocierReductionAvecDraftNonExiste() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/associerReduction.json");
			draftService.associerReduction("00000000", reductionInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
