package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.draft.test.generator.ReductionInfoGenrator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link ReductionService#ajouterReductionDetailLigne(com.nordnet.opale.domain.draft.DraftLigneDetail, String, String, com.nordnet.opale.business.ReductionInfo) .
 * @author mahjoub-MARZOUGUI
 *
 */
public class AjouterReductionDetailLigneTest extends GlobalTestCase {

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("reductionService")
	private ReductionService reductionService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("reductionInfoGenrator")
	private ReductionInfoGenrator reductionInfoGenrator;

	/**
	 * {@link DraftLigneDetailRepository}.
	 */
	@SpringBean("draftLigneDetailRepository")
	private DraftLigneDetailRepository draftLigneDetailRepository;

	/**
	 * ajout reduction valide a un detaille ligne du draft.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajout-reduction.xml" })
	public void ajoutReductiondetailLigneValide() throws OpaleException {

		ReductionInfo reductionInfo = reductionInfoGenrator.getReductionInfo();
		DraftLigneDetail draftLigneDetail =
				draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef("REF-DRAFT-1", "REF-LIGNE-1", "kitsat");
		reductionService.ajouterReductionDetailLigne(draftLigneDetail, "REF-DRAFT-1", "REF-LIGNE-1", reductionInfo);
		Reduction reduction =
				reductionService.findReductionDetailLigneDraftSansFrais("REF-DRAFT-1", "REF-LIGNE-1", "kitsat");
		assertNotNull(reduction);

	}

	/**
	 * ajout reduction invalide a une ligne du draft.
	 * 
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajout-reduction.xml" })
	public void ajoutReductiondetailLigneInValide() {

		ReductionInfo reductionInfo = reductionInfoGenrator.getReductionInfo();

		DraftLigneDetail draftLigneDetail =
				draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef("REF-DRAFT-1", "REF-LIGNE-1", "jet");
		try {
			reductionService.ajouterReductionDetailLigne(draftLigneDetail, "REF-DRAFT-1", "REF-LIGNE-1", reductionInfo);
			fail("unexpected state");
		} catch (OpaleException exception) {
			assertEquals(exception.getErrorCode(), "5.1.2");

		}

	}

}