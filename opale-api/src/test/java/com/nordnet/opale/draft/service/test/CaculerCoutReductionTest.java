package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * * class de test de la methode
 * {@link DraftServiceImpl#calculerCout(String, com.nordnet.opale.business.catalogue.TrameCatalogue)}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CaculerCoutReductionTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CaculerCoutReductionTest.class);

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
	 * cas de calcule du cout d'un {@link Reduction} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-draft-reduction.xml" })
	public void calculerCoutReductionValide() {
		try {
			TransformationInfo calculInfo =
					draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
							"./requests/calculerCoutReductionDraft.json");
			Object object = draftService.calculerCout("Dra-00000001", calculInfo);
			assertTrue(object instanceof Cout);
			Cout cout = (Cout) object;
			assertEquals(new Double(50), new Double(cout.getCoutComptantHT()));
			assertEquals(new Double(55), new Double(cout.getCoutComptantTTC()));

			assertEquals(new Double(22.79), new Double(cout.getReductionTTC()));
			assertEquals(new Double(20.72), new Double(cout.getReductionHT()));

			assertEquals(new Double(55), new Double(cout.getDetails().get(0).getCoutComptantTTC()));
			assertEquals(new Double(50), new Double(cout.getDetails().get(0).getCoutComptantHT()));

			assertEquals(new Double(18.78), new Double(cout.getDetails().get(0).getReductionTTC()));
			assertEquals(new Double(17.07), new Double(cout.getDetails().get(0).getReductionHT()));

			assertEquals(new Double(14.3), new Double(cout.getDetails().get(0).getCoutRecurrent().getNormal()
					.getTarifTTC()));
			assertEquals(new Double(13), new Double(cout.getDetails().get(0).getCoutRecurrent().getNormal()
					.getTarifHT()));

			assertEquals(new Double(14.3), new Double(cout.getDetails().get(0).getCoutRecurrent().getNormal()
					.getTarifTTC()));
			assertEquals(new Double(13), new Double(cout.getDetails().get(0).getCoutRecurrent().getNormal()
					.getTarifHT()));

			assertEquals(Constants.TROIS, cout.getDetails().get(0).getCoutRecurrent().getFrequence());

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * cas de calcule pour un {@link Draft} qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-draft.xml" })
	public void calculerCoutDraftNonExiste() {
		try {
			TransformationInfo calculInfo =
					draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
							"./requests/calculerCoutDraft.json");
			draftService.calculerCout("Dra-00000000", calculInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * cas de calcule pour un {@link Draft} non valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-draft.xml" })
	public void calculerCoutDraftNonValide() {
		try {
			TransformationInfo calculInfo =
					draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
							"./requests/calculerCoutDraft.json");
			Object object = draftService.calculerCout("Dra-00000002", calculInfo);
			assertTrue(object instanceof DraftValidationInfo);
			DraftValidationInfo validationInfo = (DraftValidationInfo) object;
			assertFalse(validationInfo.isValide());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
