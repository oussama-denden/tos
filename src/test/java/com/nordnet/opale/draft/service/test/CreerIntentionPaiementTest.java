package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.PaiementInfo;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link CommandeService#creerIntentionPaiement(String, com.nordnet.opale.business.PaiementInfo)}.
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
	@SpringBean("commandeService")
	private CommandeService commandeService;

	/**
	 * {@link PaiementService}.
	 */
	@SpringBean("paiementService")
	private PaiementService paiementService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * Tester le cas d'ajout d'une intention de paiement valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementValide() {
		try {
			PaiementInfo paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfo.class,
							"./requests/creerIntentionPaiement.json");
			Paiement paiement = commandeService.creerIntentionPaiement("00000001", paiementInfo);
			assertEquals("00000001", paiement.getReference());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * creer un intention de paiement alors qu'une autre existe.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementAvecAutreExiste() {
		try {
			Paiement paiement = paiementService.getIntentionPaiement("00000004");
			assertEquals(ModePaiement.FACTURE, paiement.getModePaiement());
			PaiementInfo paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfo.class,
							"./requests/creerIntentionPaiement.json");
			paiement = commandeService.creerIntentionPaiement("00000004", paiementInfo);
			assertEquals(ModePaiement.CB, paiement.getModePaiement());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas d'ajout d'une intention de paiement avec une commande qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementAvecCommandeNonExiste() {
		try {
			PaiementInfo paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfo.class,
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
	 * Tester le cas d'ajout d'une intention de paiement avec une commande paye totalement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-intention-paiement.xml" })
	public void testerCreerIntentionPaiementAvecCommandePayeTotalement() {
		try {
			PaiementInfo paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfo.class,
							"./requests/creerIntentionPaiement.json");
			commandeService.creerIntentionPaiement("00000003", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.11", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
