package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link DraftService#validerDraft(String, com.nordnet.opale.business.catalogue.TrameCatalogue)}
 * .
 * 
 * @author akram-moncer
 * 
 */
public class ValiderDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ValiderDraftTest.class);

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
	 * Tester le de validation d'un {@link Draft} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/valider-draft.xml" })
	public void testValiderDraftValide() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertTrue(validationInfo.isValide());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/valider-draft.xml" })
	public void testValiderDraftAvecDraftNonExist() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			draftService.validerDraft("REF-DRAFT-2", trameCatalogue);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/valider-draft.xml" })
	public void testValiderDraftAvecOffreNonValide() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-3", trameCatalogue);
			assertEquals(Double.valueOf(Constants.TROIS), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.2", validationInfo.getReasons().get(Constants.DEUX).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/valider-draft.xml" })
	public void testValiderDraftAvecDetailOffreNonValide() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-4", trameCatalogue);
			assertEquals(Double.valueOf(Constants.TROIS), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.3", validationInfo.getReasons().get(Constants.DEUX).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} possedant un bien et qui n'a
	 * pas de client livraison.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/GivenDraftBienWithoutClientLivraisonWhenValiderDraftFail.xml" })
	public void givenDraftBienWithoutClientLivraisonWhenValiderDraftFail() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.14", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} possedant un bien et qui n'a
	 * pas de client livraison.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/GivenDraftServiceWithoutClientLivraisonWhenValiderDraftValid.xml" })
	public void givenDraftServiceWithoutClientLivraisonWhenValiderDraftValid() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.ZERO), Double.valueOf(validationInfo.getReasons().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} qui a de client livraison sans
	 * adresse id.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/GivenClientLivraisonWithoutAdresseIdWhenValiderDraftFail.xml" })
	public void givenClientLivraisonWithoutAdresseIdWhenValiderDraftFail() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.17", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} qui n'a pas de client
	 * facturation.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/GivenDraftWithoutClientFacturationWhenValiderDraftFail.xml" })
	public void givenDraftWithoutClientFacturationWhenValiderDraftFail() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.12", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} qui n'a pas de client
	 * souscripteur.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/GivenDraftWithoutClientSouscripteurWhenValiderDraftFail.xml" })
	public void givenDraftWithoutClientSouscripteurWhenValiderDraftFail() {
		try {
			TrameCatalogue trameCatalogue = draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
					"./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.13", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
