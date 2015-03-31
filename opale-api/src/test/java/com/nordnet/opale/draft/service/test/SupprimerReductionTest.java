package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.reduction.ReductionService;

/**
 *  Classe de test de la methode {@link ReductionService#supprimer(String).
 * @author mahjoub-MARZOUGUI
 *
 */
public class SupprimerReductionTest extends GlobalTestCase {

	/**
	 * {@link DraftService}.
	 */
	@Autowired
	private ReductionService reductionService;

	/**
	 * tester la suppression d'une reduction.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajout-reduction.xml" })
	public void testSupprimerReduction() throws OpaleException {
		Reduction reductionAvant = reductionService.findReduction("REF-DRAFT-3");
		assertNotNull(reductionAvant);
		reductionService.supprimer(reductionAvant.getReference());
		Reduction reductionApres = reductionService.findReduction("REF-DRAFT-3");
		assertNull(reductionApres);
	}

}
