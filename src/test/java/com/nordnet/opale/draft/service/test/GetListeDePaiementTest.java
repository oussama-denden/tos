package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.CommandePaiementInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.commande.CommandeServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * Classe de test de la methode {@link CommandeServiceImpl#getListeDePaiement(String, boolean)}.
 * 
 * @author akram
 * 
 */
public class GetListeDePaiementTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(GetListeDePaiementTest.class);

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
	 * tester le cas de recuperation de la liste des paiement comptants et recurrents pour une commande.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-list-paiement.xml" })
	public void testerGetListePaiementValide() {
		try {
			CommandePaiementInfo commandePaiementInfo = commandeService.getListeDePaiement("REF-COMMANDE", false);
			assertTrue(commandePaiementInfo.getRecurrent().size() == Constants.DEUX);
			assertTrue(commandePaiementInfo.getRecurrent().size() == Constants.DEUX);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas de recuperation de la liste des paiement comptants et recurrents pour une commande qui n'existe
	 * pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-list-paiement.xml" })
	public void testerGetListePaiementAvecCommandeNonExiste() {
		try {
			commandeService.getListeDePaiement("00000000", false);
		} catch (OpaleException e) {
			Assert.assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
