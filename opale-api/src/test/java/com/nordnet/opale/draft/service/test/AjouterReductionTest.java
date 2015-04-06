package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.reduction.ReductionService;

/**
 * Classe de test de la methode
 * {@link ReductionService#ajouterReduction(String, com.nordnet.opale.business.ReductionInfo)} .
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class AjouterReductionTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AjouterLignesTest.class);

	/**
	 * {@link DraftService}.
	 */
	@Autowired
	private ReductionService reductionService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * ajout reduction valide a un draft.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajout-reduction.xml" })
	public void testAjoutReductionValide() {
		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/ajouterReduction.json");
			reductionService.ajouterReduction("REF-DRAFT-1", reductionInfo);
			Reduction reduction = reductionService.findReduction("REF-DRAFT-1");
			assertNotNull(reduction);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

}
