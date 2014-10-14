package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.service.commande.CommandeService;

/**
 * classe de test de la methode
 * {@link CommandeService#paiementComptant(String, com.nordnet.opale.business.PaiementInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class PaiementComptantTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(PaiementComptantTest.class);

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

	public void testerPaiementComptantValide() {
		try {
			// PaiementInfo paiementInfo =
			// draftInfoGenerator.getObjectFromJsonFile(PaiementInfo.class, "./requests/paiementComptant.json");
			// commandeService.paiementComptant(refCommande, paiementInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
