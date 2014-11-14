package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;

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
 *  Classe de test de la methode.
 * {@link ReductionService#ajouterReductionFraisLigneDetaille(String, String, com.nordnet.opale.domain.draft.DraftLigneDetail, String, com.nordnet.opale.business.ReductionInfo)
 * @author mahjoub-MARZOUGUI
 *
 */
public class AjouterReductionFraisLigneDetailleTest extends GlobalTestCase {

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
	 * teser l'ajout du reduction a un frais du detaille ligne.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajout-reduction.xml" })
	public void testAjouterReductionFraisLigneDetaille() throws OpaleException {
		ReductionInfo reductionInfo = reductionInfoGenrator.getReductionInfo();
		DraftLigneDetail draftLigneDetail =
				draftLigneDetailRepository.findByRefDraftAndRefLigneAndRef("REF-DRAFT-1", "REF-LIGNE-1", "kitsat");
		reductionService.ajouterReductionFraisLigneDetaille("REF-DRAFT-1", "REF-LIGNE-1", draftLigneDetail,
				"REF-FRAIS-1", reductionInfo);
		Reduction reduction =
				reductionService.findReductionDetailLigneDraftFrais("REF-DRAFT-1", "REF-LIGNE-1", "kitsat",
						"mensuel_jet10_base", "REF-FRAIS-1");

		assertNotNull(reduction);

	}
}
