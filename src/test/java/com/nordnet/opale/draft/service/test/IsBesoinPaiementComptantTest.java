package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de teste de la methode {@link CommandeService#isBesoinPaiementComptant(String)}.
 * 
 * @author akram-moncer
 * 
 */
public class IsBesoinPaiementComptantTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(IsBesoinPaiementRecurrentTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@SpringBean("commandeService")
	private CommandeService commandeService;

	/**
	 * tester pour une commande non existe.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/is-besoin-paiement-comptant.xml" })
	public void testerIsBesoinPaiementComptantPourCommandeNonExiste() {
		try {
			commandeService.isBesoinPaiementComptant("00000000");
			fail("unexpected error");
		} catch (OpaleException e) {
			Assert.assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester pour une commande non paye totalement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/is-besoin-paiement-comptant.xml" })
	public void testerIsBesoinPaiementComptantPourCommandeNonPayeTotalement() {
		try {
			boolean isBesoinPaiementComptant = commandeService.isBesoinPaiementComptant("00000001");
			Assert.assertEquals(true, isBesoinPaiementComptant);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester pour une commande paye totalement.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/is-besoin-paiement-comptant.xml" })
	public void testerIsBesoinPaiementComptantPourCommandePayeTotalement() {
		try {
			boolean isBesoinPaiementComptant = commandeService.isBesoinPaiementComptant("00000002");
			Assert.assertEquals(false, isBesoinPaiementComptant);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester pour une commande sans cout comptant.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/is-besoin-paiement-comptant.xml" })
	public void testerIsBesoinPaiementComptantPourCommandeSansCoutComptant() {
		try {
			boolean isBesoinPaiementComptant = commandeService.isBesoinPaiementComptant("00000003");
			Assert.assertEquals(false, isBesoinPaiementComptant);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
