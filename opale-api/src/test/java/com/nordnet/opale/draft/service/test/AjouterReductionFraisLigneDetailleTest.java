package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.reduction.ReductionService;

/**
 *  Classe de test de la methode.
 * {@link ReductionService#ajouterReductionFraisLigneDetaille(String, String, com.nordnet.opale.domain.draft.DraftLigneDetail, String, com.nordnet.opale.business.ReductionInfo)
 * @author mahjoub-MARZOUGUI
 *
 */
public class AjouterReductionFraisLigneDetailleTest extends GlobalTestCase {

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
	 * {@link DraftLigneDetailRepository}.
	 */
	@Autowired
	private DraftLigneDetailRepository draftLigneDetailRepository;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * teser l'ajout du reduction a un frais du detaille ligne.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajout-reduction.xml" })
	public void testAjouterReductionFraisLigneDetaille() throws OpaleException {

		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/ajouterReduction.json");
			DraftLigneDetail draftLigneDetail =
					draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef("REF-DRAFT-1", "REF-LIGNE-1", "kitsat");
			reductionService.ajouterReductionFraisLigneDetaille("REF-DRAFT-1", "REF-LIGNE-1", draftLigneDetail,
					"REF-FRAIS-1", reductionInfo);
			Reduction reduction =
					reductionService.findReductionDetailLigneDraftFrais("REF-DRAFT-1", "REF-LIGNE-1", "kitsat",
							"mensuel_jet10_base", "REF-FRAIS-1");

			assertNotNull(reduction);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
