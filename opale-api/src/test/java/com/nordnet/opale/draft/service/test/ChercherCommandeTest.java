package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.CommandeInfo;
import com.nordnet.opale.business.CriteresCommande;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.commande.CommandeServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * classe de test de la methode
 * {@link CommandeServiceImpl#chercherCommande(com.nordnet.opale.business.CriteresCommande)}.
 * 
 * @author akram-moncer
 * 
 */
public class ChercherCommandeTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ChercherCommandeTest.class);

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
	 * tester chercher commande valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/chercher-commande.xml" })
	public void testerChercherCommande() {
		try {

			CriteresCommande criteresCommande =
					draftInfoGenerator
							.getObjectFromJsonFile(CriteresCommande.class, "./requests/chercherCommande.json");
			List<CommandeInfo> commandeInfos = commandeService.chercherCommande(criteresCommande);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

}
