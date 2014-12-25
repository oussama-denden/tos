package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.PaiementInfoRecurrent;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link CommandeService#paiementDirect(String, com.nordnet.opale.business.PaiementInfoRecurrent)}.
 * 
 * @author akram-moncer
 * 
 */
public class PaiementDirectTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(PaiementDirectTest.class);

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
	 * tester le cas d'un paiement direct valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/paiement-direct.xml" })
	public void testerPaiementDirectValide() {
		try {
			PaiementInfoRecurrent paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoRecurrent.class,
							"./requests/paiementDirect.json");
			List<Paiement> paiements = paiementService.getPaiementByReferenceCommande("00000005");
			assertEquals(Double.valueOf(Constants.ZERO), Double.valueOf(paiements.size()));
			commandeService.paiementDirect("00000005", paiementInfo, TypePaiement.COMPTANT);
			paiements = paiementService.getPaiementByReferenceCommande("00000005");
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(paiements.size()));
			assertNotNull(paiements.get(Constants.ZERO).getTimestampPaiement());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas de paiement direct avec une commande qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/paiement-direct.xml" })
	public void testerPaiementDirectAvecCommandeNonExiste() {
		try {
			PaiementInfoRecurrent paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoRecurrent.class,
							"./requests/paiementDirect.json");
			commandeService.paiementDirect("00000000", paiementInfo, TypePaiement.COMPTANT);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de paiement d'une intention sans indiquer le mode paiement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/paiement-direct.xml" })
	public void testerPayerIntentionPaiementAvecPaiementInfoSansModePaiement() {
		try {
			PaiementInfoRecurrent paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoRecurrent.class,
							"./requests/paiementDirect.json");
			paiementInfo.setModePaiement(null);
			commandeService.paiementDirect("00000005", paiementInfo, TypePaiement.COMPTANT);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de paiement direct sans indiquer le mode paiement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/paiement-direct.xml" })
	public void testerPayerIntentionPaiementAvecPaiementInfoSansMontant() {
		try {
			PaiementInfoRecurrent paiementInfo =
					draftInfoGenerator.getObjectFromJsonFile(PaiementInfoRecurrent.class,
							"./requests/paiementDirect.json");
			paiementInfo.setMontant(null);
			commandeService.paiementDirect("00000005", paiementInfo, TypePaiement.COMPTANT);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
