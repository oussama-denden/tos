package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.topaze.ws.enums.TypeValeur;

/**
 * Classe de test de la methode.
 * {@link ReductionService#ajouterReductionFraisLigne(String, com.nordnet.opale.domain.draft.DraftLigne, String, com.nordnet.opale.business.ReductionInfo)
 * @author mahjoub-MARZOUGUI
 *
 */
public class AjouterReductionFraisLigneTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AjouterLignesTest.class);

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("reductionService")
	private ReductionService reductionService;

	/**
	 * {@link DraftLigneDetailRepository}.
	 */
	@SpringBean("draftLigneRepository")
	private DraftLigneRepository draftLigneRepository;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * ajout reduction valide a un frais ligne du draft.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajout-reduction.xml" })
	public void testajouterReductionFraisLigneValide() throws OpaleException {

		try {
			ReductionInfo reductionInfo =
					draftInfoGenerator.getObjectFromJsonFile(ReductionInfo.class, "./requests/ajouterReduction.json");
			DraftLigne draftLigne = draftLigneRepository.findByRefDraftAndRef("REF-DRAFT-1", "REF-LIGNE-1");
			reductionService.ajouterReductionFraisLigne("REF-DRAFT-1", draftLigne, "REF-FRAIS-1", reductionInfo);
			Reduction reduction =
					reductionService.findReductionlLigneDraftFrais("REF-DRAFT-1", "REF-LIGNE-1", "annuel_jet10_base",
							"REF-FRAIS-1");
			assertNotNull(reduction);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

}
