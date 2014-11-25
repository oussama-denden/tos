package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.commande.CommandeServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * Classe de test de la methode
 * {@link CommandeServiceImpl#supprimerPaiement(String, String, com.nordnet.opale.business.Auteur)}.
 * 
 * @author akram
 * 
 */
public class SupprimerPaiementTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupprimerPaiementTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@SpringBean("commandeService")
	private CommandeService commandeService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * tester le cas de suppression d'un paiement valide.
	 * 
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-list-paiement.xml" })
	public void testerSupprimerPaiementValide() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			CommandePaiementInfo commandePaiementInfo = commandeService.getListeDePaiement("REF-COMMANDE", false);
			assertTrue((commandePaiementInfo.getRecurrent().size() + commandePaiementInfo.getComptant().size()) == Constants.QUATRE);
			commandeService.supprimerPaiement("REF-COMMANDE", "REF-PAIEMENT-1", auteur);
			commandePaiementInfo = commandeService.getListeDePaiement("REF-COMMANDE", false);
			assertTrue((commandePaiementInfo.getRecurrent().size() + commandePaiementInfo.getComptant().size()) == Constants.TROIS);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de suppression d'un paiement pour une commande qui n'existe pas.
	 * 
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-list-paiement.xml" })
	public void testerSupprimerPaiementAvecCommandeNonExiste() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			commandeService.supprimerPaiement("REF-COMMANDE", "REF-PAIEMENT-1", auteur);
		} catch (OpaleException e) {
			Assert.assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
