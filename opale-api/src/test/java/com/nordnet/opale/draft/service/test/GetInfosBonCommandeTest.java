package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.InfosBonCommande;
import com.nordnet.opale.business.InfosLignePourBonCommande;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.enums.Geste;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * Classe de test de la methode {@link CommandeService#getInfosBonCommande(String)} .
 * 
 * @author akram-moncer
 * 
 */
public class GetInfosBonCommandeTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(GetInfosBonCommandeTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * tester chercher commande valide.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/get-infos-boncommande.xml" })
	public void testerGetInfosBonCommandeValide() {
		try {

			InfosBonCommande infosBonCommande = commandeService.getInfosBonCommande("00000004");
			assertEquals(infosBonCommande.getReference(), "00000004");
			assertEquals(infosBonCommande.getRefClient(), "1002");
			assertEquals(infosBonCommande.getGeste(), Geste.VENTE);
			assertEquals(infosBonCommande.getFrequence(), new Integer(12));
			assertEquals(infosBonCommande.getMontantTVA(), new Double(13.96));
			assertEquals(infosBonCommande.getMoyenDePaiement(), ModePaiement.SEPA);
			assertEquals(infosBonCommande.getReferencePaiement(), "R1234567");
			assertEquals(infosBonCommande.getPrixReduitHT(), new Double(15.27));
			assertEquals(infosBonCommande.getPrixReduitTTC(), new Double(18.32));
			assertEquals(infosBonCommande.getPrixTotalHT(), new Double(69.8));
			assertEquals(infosBonCommande.getPrixTotalTTC(), new Double(83.76));
			assertEquals(infosBonCommande.getTauxTVA(), new Double(20));
			assertEquals(infosBonCommande.getTrancheTarifaire(), "A");
			assertEquals(new Integer(infosBonCommande.getLignes().size()), new Integer(1));
			InfosLignePourBonCommande lignePourBonCommande = infosBonCommande.getLignes().get(0);
			assertNotNull(lignePourBonCommande);
			assertEquals(lignePourBonCommande.getLabel(), "jet_surf10");
			assertEquals(lignePourBonCommande.getReferenceContrat(), "00000004");
			assertEquals(lignePourBonCommande.getPrixHT(), new Double(69.8));
			assertEquals(lignePourBonCommande.getPrixTTC(), new Double(83.76));
			assertEquals(lignePourBonCommande.getQuantite(), new Integer(1));
			assertEquals(new Integer(lignePourBonCommande.getReductions().size()), new Integer(4));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * tester le cas valide d'association d'une reduction au frais associe au ligne detail pour un draft qui n'existe
	 * pas.
	 */
	@Test
	public void testerGetInfosBonCommandeAvecCommandeNonExiste() {
		try {
			commandeService.getInfosBonCommande("REF_CMD");
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}
}