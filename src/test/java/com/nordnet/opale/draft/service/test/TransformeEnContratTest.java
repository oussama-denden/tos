package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * tester la methode
 * {@link CommandeService#transformeEnContrat(String).
 * 
 * @author Oussama Denden
 * 
 */
public class TransformeEnContratTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(TransformeEnContratTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@SpringBean("commandeService")
	private CommandeService commandeService;

	/**
	 * tester la methode transforme En Contrat valide.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/transforme-en-contrat.xml" })
	public void testTransformeEnContratValide() throws OpaleException {

		Commande commande = commandeService.getCommandeByReference("00000004");
		assertNotNull(commande);
		assertNull(commande.getDateTransformationContrat());

		for (CommandeLigne ligne : commande.getCommandeLignes()) {
			assertNull(ligne.getReferenceContrat());
		}

		try {
			Auteur auteur = new Auteur();
			auteur.setQui("test");
			List<String> referencesContrats = commandeService.transformeEnContrat("00000004", auteur);
			assertTrue(referencesContrats.size() == Constants.UN);
			commande = commandeService.getCommandeByReference("00000004");
			assertNotNull(commande.getDateTransformationContrat());
			for (CommandeLigne ligne : commande.getCommandeLignes()) {
				assertNotNull(ligne.getReferenceContrat());
			}
		} catch (JSONException exception) {
			LOGGER.error("erreur dans la transmission de commande en contrat  :" + exception.getMessage());
			fail(exception.getMessage());
		}
	}
}
