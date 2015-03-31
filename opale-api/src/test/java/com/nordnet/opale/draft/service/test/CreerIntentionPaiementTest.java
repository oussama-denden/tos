package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * Classe de test de la methode
 * {@link CommandeService#creerIntentionPaiement(String, com.nordnet.opale.business.PaiementInfoRecurrent)}.
 * 
 * @author akram-moncer
 * 
 */
public class CreerIntentionPaiementTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CreerIntentionPaiementTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * {@link PaiementService}.
	 */
	@Autowired
	private PaiementService paiementService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * Tester le cas d'ajout d'une intention de paiement valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementValide() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/creerIntentionPaiement.json");
			Paiement paiement = commandeService.creerIntentionPaiement("00000001", paiementInfo);
			assertEquals("00000001", paiement.getReference());
			assertNotNull(paiement.getTimestampIntention());
			assertNull(paiement.getTimestampPaiement());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * creer un intention de paiement alors qu'une autre existe.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementAvecAutreExiste() {
		try {
			Paiement paiement = paiementService.getIntentionPaiement("00000004");
			assertEquals(ModePaiement.FACTURE, paiement.getModePaiement());
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/creerIntentionPaiement.json");
			paiement = commandeService.creerIntentionPaiement("00000004", paiementInfo);
			assertEquals(ModePaiement.CB, paiement.getModePaiement());
			assertNotNull(paiement.getTimestampIntention());
			assertNull(paiement.getTimestampPaiement());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas d'ajout d'une intention de paiement avec une commande qui n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementAvecCommandeNonExiste() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/creerIntentionPaiement.json");
			commandeService.creerIntentionPaiement("00000002", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas d'ajout d'une intention de paiement valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementSansModePaiement() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/creerIntentionPaiement.json");
			paiementInfo.setModePaiement(null);
			commandeService.creerIntentionPaiement("00000001", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
