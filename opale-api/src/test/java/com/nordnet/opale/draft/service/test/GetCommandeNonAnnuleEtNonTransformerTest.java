package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.commande.CommandeServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * classe de test de la methode {@link CommandeServiceImpl#getCommandeNonAnnuleEtNonTransformes()}.
 * 
 * @author akram-moncer
 * 
 */
public class GetCommandeNonAnnuleEtNonTransformerTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupprimerSignatureTest.class);

	/**
	 * {@link CommandeService}.
	 */
	@SpringBean("commandeService")
	private CommandeService commandeService;

	/**
	 * tester la recherche des commande non transforme et non annule.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/get-commande-non-transforme-non-annule.xml" })
	public void testerGetCommandeNonAnnuleEtNonTransformer() {
		try {
			List<Commande> commandes = commandeService.getCommandeNonAnnuleEtNonTransformes();
			Assert.assertEquals(Double.valueOf(Constants.UN), Double.valueOf(commandes.size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
