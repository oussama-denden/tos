package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.commande.CommandeServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

/**
 * classe de test de la methode {@link CommandeServiceImpl#calculerCout(String)}.
 * 
 * @author akram-moncer
 * 
 */
public class CalculerCoutCommande extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CalculerCoutCommande.class);

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
	 * cas de calcule du cout d'une {@link Commande} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/calculer-cout-commande.xml" })
	public void calculeCoutCommandeValide() {
		try {
			Cout cout = commandeService.calculerCout("Cmd-00000001");
			assertEquals(new Double(119.8), new Double(cout.getCoutComptantHT()));
			assertEquals(new Double(143.76), new Double(cout.getCoutComptantTTC()));
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(cout.getDetails().size()));
			assertEquals(new Double(119.8), new Double(cout.getDetails().get(0).getCoutComptantHT()));
			assertEquals(new Double(143.76), new Double(cout.getDetails().get(0).getCoutComptantTTC()));
			assertEquals(new Double(34.9), new Double(cout.getDetails().get(0).getCoutRecurrent().getNormal()
					.getTarifHT()));
			assertEquals(new Double(41.88), new Double(cout.getDetails().get(0).getCoutRecurrent().getNormal()
					.getTarifTTC()));
			assertEquals(Constants.UN, cout.getDetails().get(0).getCoutRecurrent().getFrequence());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * calcule du cout pour une {@link Commande} qui n'existe pas.
	 */
	public void calculerCoutCommandeNonExiste() {
		try {
			commandeService.calculerCout("Cmd-00000000");
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
