package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.TrameCatalogueInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.Constants;

/**
 * Classe de test de la methode
 * {@link DraftService#validerDraft(String, com.nordnet.opale.business.catalogue.TrameCatalogue)} .
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
	@Autowired
	private DraftService draftService;

	{
		System.setProperty("netcatalog.useMock", "true");
	}

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * Tester le de validation d'un {@link Draft} valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	@Transactional
	public void testValiderDraftValide() {

		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
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
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	public void testValiderDraftAvecDraftNonExist() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			draftService.validerDraft("00000000", trameCatalogue);
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
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	@Transactional
	public void testValiderDraftAvecOffreNonValide() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-3", trameCatalogue);
			assertEquals(Double.valueOf(Constants.TROIS), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.2", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	@Transactional
	public void testValiderDraftAvecDetailOffreNonValide() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-4", trameCatalogue);
			assertEquals(Double.valueOf(Constants.TROIS), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.3", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} possedant un bien et qui n'a pas de client livraison.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml",
			"/dataset/GivenDraftBienWithoutClientLivraisonWhenValiderDraftFail.xml" })
	@Transactional
	public void givenDraftBienWithoutClientLivraisonWhenValiderDraftFail() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.14", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} possedant un bien et qui n'a pas de client livraison.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml",
			"/dataset/GivenDraftServiceWithoutClientLivraisonWhenValiderDraftValid.xml" })
	@Transactional
	public void givenDraftServiceWithoutClientLivraisonWhenValiderDraftValid() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} qui a de client livraison sans adresse id.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml",
			"/dataset/GivenClientLivraisonWithoutAdresseIdWhenValiderDraftFail.xml" })
	@Transactional
	public void givenClientLivraisonWithoutAdresseIdWhenValiderDraftFail() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.17", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} qui n'a pas de client facturation.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml",
			"/dataset/GivenDraftWithoutClientFacturationWhenValiderDraftFail.xml" })
	@Transactional
	public void givenDraftWithoutClientFacturationWhenValiderDraftFail() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.12", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le de validation d'un {@link Draft} qui n'a pas de client souscripteur.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml",
			"/dataset/GivenDraftWithoutClientSouscripteurWhenValiderDraftFail.xml" })
	@Transactional
	public void givenDraftWithoutClientSouscripteurWhenValiderDraftFail() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-1", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("1.1.13", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * validation d'un draft avec un tarif ligne non valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	@Transactional
	public void testDraftAvecTarifLigneInvalide() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-5", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.4", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * validation d'un draft avec un tarif associe au detail non valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	@Transactional
	public void testDraftAvecTarifDetailInvalide() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-6", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.5", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * validation d'un draft avec une reference choix invalide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-draft.xml" })
	@Transactional
	public void testDraftAvecChoixInvalide() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class, "./requests/validerDraft.json");
			DraftValidationInfo validationInfo = draftService.validerDraft("REF-DRAFT-7", trameCatalogue);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(validationInfo.getReasons().size()));
			assertEquals("36.3.1.6", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
