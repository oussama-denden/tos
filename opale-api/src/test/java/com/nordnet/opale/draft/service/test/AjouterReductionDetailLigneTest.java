package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
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
 * Classe de test de la methode
 * {@link ReductionService#ajouterReductionDetailLigne(com.nordnet.opale.domain.draft.DraftLigneDetail, String, String, com.nordnet.opale.business.ReductionInfo) .
 * @author mahjoub-MARZOUGUI
 *
 */
public class AjouterReductionDetailLigneTest extends GlobalTestCase {

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
	 * ajout reduction valide a un detaille ligne du draft.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajout-reduction.xml" })
	public void ajoutReductiondetailLigneValide() throws OpaleException {

		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/ajouterReduction.json");
			DraftLigneDetail draftLigneDetail =
					draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef("REF-DRAFT-1", "REF-LIGNE-1", "kitsat");
			reductionService.ajouterReductionDetailLigne(draftLigneDetail, "REF-DRAFT-1", "REF-LIGNE-1", reductionInfo);
			Reduction reduction =
					reductionService.findReductionDetailLigneDraftSansFrais("REF-DRAFT-1", "REF-LIGNE-1", "kitsat");
			assertNotNull(reduction);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * ajout reduction invalide a une ligne du draft.
	 * 
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajout-reduction.xml" })
	public void ajoutReductiondetailLigneInValide() {

		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/ajouterReduction.json");

			DraftLigneDetail draftLigneDetail =
					draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef("REF-DRAFT-1", "REF-LIGNE-1", "jet");

			reductionService.ajouterReductionDetailLigne(draftLigneDetail, "REF-DRAFT-1", "REF-LIGNE-1", reductionInfo);
			fail("unexpected state");
		} catch (OpaleException exception) {
			assertEquals(exception.getErrorCode(), "5.1.2");

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

}
