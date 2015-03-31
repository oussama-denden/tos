package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;

/**
 * classe de test de la methode {@link DraftServiceImpl#associerAuteur(String, com.nordnet.opale.business.Auteur)}.
 * 
 * @author akram-moncer
 * 
 */
public class AssocierAuteurTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AssocierAuteurTest.class);

	/**
	 * {@link DraftService}.
	 */
	@Autowired
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * tester le cas valide d'association d'un auteur au {@link Draft}.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-auteur.xml" })
	public void associerAuteurValide() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/associerAuteur.json");
			draftService.associerAuteur("REF-DRAFT", auteur);
			Draft draft = draftService.getDraftByReference("REF-DRAFT");
			assertNotNull(draft.getAuteur());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester la cas d'association d'un auteur a un Draft qui n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-auteur.xml" })
	public void associerAuteurAvecDraftNonExiste() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/associerAuteur.json");
			draftService.associerAuteur("00000000", auteur);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester la cas d'association d'un auteur sans indiquer le champs qui.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/associer-auteur.xml" })
	public void associerAuteurSansQui() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/associerAuteur.json");
			auteur.setQui(null);
			draftService.associerAuteur("REF-DRAFT", auteur);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
