package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.PaiementInfoComptant;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link CommandeService#payerIntentionPaiement(String, String, com.nordnet.opale.business.PaiementInfoRecurrent)}.
 * 
 * @author akram-moncer
 * 
 */
public class PayerIntentionPaiementTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(PayerIntentionPaiementTest.class);

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
	 * tester le cas de paiement d'une intention valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/payer-intention.xml" })
	public void testerPayerIntentionPaiementValide() {
		try {
			Paiement paiement = paiementService.getPaiementByReference("00000001");
			Assert.assertNull(paiement.getMontant());
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/payerIntentionPaiement.json");
			commandeService.payerIntentionPaiement("00000001", "00000001", paiementInfo);
			Assert.assertEquals(Double.valueOf(Constants.CINQ), paiement.getMontant());
			assertNotNull(paiement.getTimestampPaiement());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas de paiement d'une intention avec une commande qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/payer-intention.xml" })
	public void testerPayerIntentionPaiementAvecCommandeNonExiste() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/payerIntentionPaiement.json");
			commandeService.payerIntentionPaiement("00000000", "00000001", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas de paiement d'une intention qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/payer-intention.xml" })
	public void testerPayerIntentionPaiementNonExiste() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/payerIntentionPaiement.json");
			commandeService.payerIntentionPaiement("00000001", "00000000", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("3.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de paiement d'une intention deja paye.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/payer-intention.xml" })
	public void testerPayerIntentionPaiementDejaPaye() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/payerIntentionPaiement.json");
			commandeService.payerIntentionPaiement("00000002", "00000002", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("3.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de paiement d'une intention sans indiquer le mode paiement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/payer-intention.xml" })
	public void testerPayerIntentionPaiementAvecPaiementInfoSansModePaiement() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/payerIntentionPaiement.json");
			paiementInfo.setModePaiement(null);
			commandeService.payerIntentionPaiement("00000001", "00000001", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de paiement d'une intention sans indiquer le mode paiement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/payer-intention.xml" })
	public void testerPayerIntentionPaiementAvecPaiementInfoSansMontant() {
		try {
			PaiementInfoComptant paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoComptant.class,
							"./requests/payerIntentionPaiement.json");
			paiementInfo.setMontant(null);
			commandeService.payerIntentionPaiement("00000001", "00000001", paiementInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
