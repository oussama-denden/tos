package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.CommandeValidationInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.test.utils.Constants;

/**
 * Classe de test de la methode {@link CommandeService#validerCommande(String)}.
 * 
 * @author akram-moncer
 * 
 */
public class ValiderCommandeTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ValiderCommandeTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * tester le cas de validation d'une commande valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-commande.xml" })
	public void testerValiderCommandeValide() {

		try {
			CommandeValidationInfo validationInfo = commandeService.validerCommande("00000001");
			assertEquals(true, validationInfo.isValide());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * tester le cas de validation d'une commande annule.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-commande.xml" })
	public void testerValiderCommandeAnnule() {
		try {
			CommandeValidationInfo validationInfo = commandeService.validerCommande("00000002");
			assertEquals(false, validationInfo.isValide());
			assertEquals("2.1.3", validationInfo.getReasons().get(Constants.ZERO).getError());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de validation d'une commande non valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/valider-commande.xml" })
	public void testerValiderCommandeNonValide() {
		try {
			CommandeValidationInfo validationInfo = commandeService.validerCommande("00000004");
			assertEquals(false, validationInfo.isValide());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
