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
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * class de test de la methode
 * {@link DraftServiceImpl#calculerCout(String, com.nordnet.opale.business.catalogue.TrameCatalogue)}.
 * 
 * @author akram-moncer
 * 
 */
public class CalculerCoutDraft extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CalculerCoutDraft.class);

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
	 * cas de calcule du cout d'un {@link Draft} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-draft.xml" })
	public void calculerCoutDraftValideSansClientAFacture() {
		try {
			TransformationInfo calculInfo =
					draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
							"./requests/calculerCoutDraft.json");
			Object object = draftService.calculerCout("Dra-00000001", calculInfo);
			assertTrue(object instanceof Cout);
			Cout cout = (Cout) object;
			assertEquals(new Double(119.8), new Double(cout.getCoutTotal()));
			assertEquals(new Double(143.76), new Double(cout.getCoutTotalTTC()));
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(cout.getDetails().size()));
			assertEquals(new Double(119.8), new Double(cout.getDetails().get(0).getCoutTotal()));
			assertEquals(new Double(143.76), new Double(cout.getDetails().get(0).getCoutTotalTTC()));
			assertEquals(new Double(34.9), new Double(cout.getDetails().get(0).getPlan().getPlan()));
			assertEquals(new Double(41.88), new Double(cout.getDetails().get(0).getPlan().getPlanTTC()));
			assertEquals(Constants.UN, cout.getDetails().get(0).getPlan().getFrequence());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * cas de calcule du cout d'un {@link Draft} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-draft.xml" })
	public void calculerCoutDraftValideAvecClientAFacture() {
		try {
			TransformationInfo calculInfo =
					draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
							"./requests/calculerCoutDraft.json");
			Object object = draftService.calculerCout("Dra-00000003", calculInfo);
			assertTrue(object instanceof Cout);
			Cout cout = (Cout) object;
			assertEquals(new Double(119.8), new Double(cout.getCoutTotal()));
			assertEquals(new Double(143.76), new Double(cout.getCoutTotalTTC()));
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(cout.getDetails().size()));
			assertEquals(new Double(119.8), new Double(cout.getDetails().get(0).getCoutTotal()));
			assertEquals(new Double(143.76), new Double(cout.getDetails().get(0).getCoutTotalTTC()));
			assertEquals(new Double(143.76), new Double(cout.getCoutTotalTTC()));
			assertEquals(new Double(34.9), new Double(cout.getDetails().get(0).getPlan().getPlan()));
			assertEquals(new Double(41.88), new Double(cout.getDetails().get(0).getPlan().getPlanTTC()));
			assertEquals(Constants.UN, cout.getDetails().get(0).getPlan().getFrequence());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * cas de calcule du cout d'un {@link Draft} pour un client donne.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-draft.xml" })
	public void calculerCoutDraftPourClientDonne() {
		try {
			TransformationInfo calculInfo =
					draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
							"./requests/calculerCoutDraftPourClientDonne.json");
			Object object = draftService.calculerCout("Dra-00000001", calculInfo);
			assertTrue(object instanceof Cout);
			Cout cout = (Cout) object;
			assertEquals(new Double(119.8), new Double(cout.getCoutTotal()));
			assertEquals(new Double(129.983), new Double(cout.getCoutTotalTTC()));
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(cout.getDetails().size()));
			assertEquals(new Double(119.8), new Double(cout.getDetails().get(0).getCoutTotal()));
			assertEquals(new Double(129.983), new Double(cout.getDetails().get(0).getCoutTotalTTC()));
			assertEquals(new Double(34.9), new Double(cout.getDetails().get(0).getPlan().getPlan()));
			assertEquals(new Double(37.8665), new Double(cout.getDetails().get(0).getPlan().getPlanTTC()));
			assertEquals(Constants.UN, cout.getDetails().get(0).getPlan().getFrequence());
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
