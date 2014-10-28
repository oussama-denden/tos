package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * classe de test de la methode
 * {@link CommandeService#getListePaiementComptant(String)}.
 * 
 * @author akram-moncer
 * 
 */
public class GetListPaiementComptantTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(GetListPaiementComptantTest.class);

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
	 * Tester le cas valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-list-paiement-comptant.xml" })
	public void testerGetListPaiementComptantValide() {
		try {
			List<Paiement> paiementsComptant = commandeService.getListePaiementComptant("00000001", false);
			List<Paiement> paiementsTotal = paiementService.getPaiementByReferenceCommande("00000001");
			assertEquals(Double.valueOf(Constants.TROIS), Double.valueOf(paiementsTotal.size()));
			assertEquals(Double.valueOf(Constants.DEUX), Double.valueOf(paiementsComptant.size()));
			for (Paiement paiement : paiementsComptant) {
				Assert.assertEquals(TypePaiement.COMPTANT, paiement.getTypePaiement());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * Tester le cas valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-list-paiement-comptant.xml" })
	public void testerGetListPaiementComptantAvecCommandeNonExiste() {
		try {
			commandeService.getListePaiementComptant("00000002", false);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}
}
