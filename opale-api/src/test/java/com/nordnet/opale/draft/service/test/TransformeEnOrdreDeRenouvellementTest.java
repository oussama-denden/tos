package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;

/**
 * tester la methode {@link CommandeService#transformeEnOrdereRenouvellement(String)}.
 * 
 * @author Oussama Denden
 * 
 */
public class TransformeEnOrdreDeRenouvellementTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(TransformeEnOrdreDeRenouvellementTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * tester la methode transforme En oredre de renouvellement.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/transforme-en-ordre-renouvellement.xml" })
	public void testTransformeEnContratValide() throws OpaleException {

		Commande commande = commandeService.getCommandeByReference("00000004");
		assertNotNull(commande);

		try {
			for (CommandeLigne ligne : commande.getCommandeLignes()) {
				assertNotNull(ligne.getReferenceContrat());
				commandeService.transformeEnOrdereRenouvellement(commande, ligne);
			}
		} catch (JSONException exception) {
			LOGGER.error("erreur dans la transmission de commande en contrat  :" + exception.getMessage());
			fail(exception.getMessage());
		}
	}
}
