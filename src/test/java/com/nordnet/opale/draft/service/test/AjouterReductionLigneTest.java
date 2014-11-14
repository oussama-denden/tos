package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.draft.test.generator.ReductionInfoGenrator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link ReductionService#ajouterReductionLigne(String, String, com.nordnet.opale.business.ReductionInfo)} .
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class AjouterReductionLigneTest extends GlobalTestCase {

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
	 * ajout reduction valide a un draft.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajout-reduction.xml" })
	public void testAjoutReductionLigneValide() throws OpaleException {

		ReductionInfo reductionInfo = reductionInfoGenrator.getReductionInfo();
		reductionService.ajouterReductionLigne("REF-DRAFT-1", "REF-LIGNE-1", reductionInfo);
		Reduction reduction = reductionService.findReductionLigneDraftSansFrais("REF-DRAFT-1", "REF-LIGNE-1");
		assertNotNull(reduction);

	}

	/**
	 * ajout reduction invalide a une ligne du draft.
	 * 
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajout-reduction.xml" })
	public void testAjoutReductionLigneInValide() {

		ReductionInfo reductionInfo = reductionInfoGenrator.getReductionInfo();

		try {
			reductionService.ajouterReductionLigne("REF-DRAFT-1", "REF-LIGNE-2", reductionInfo);
			fail("unexpected state");
		} catch (OpaleException exception) {
			assertEquals(exception.getErrorCode(), "5.1.2");

		}

	}
}
