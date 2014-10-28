package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode
 * {@link DraftService#transformerEnCommande(String, com.nordnet.opale.business.TransformationInfo)}
 * .
 * 
 * @author akram-moncer
 * 
 */
public class TransformerEnCommandeTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ValiderDraftTest.class);

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("draftService")
	private DraftService draftService;

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
	 * Tester le de validation d'un {@link Draft} valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/transformer-en-commande.xml" })
	public void testTransformerEnCommandeValide() {
		try {
			TransformationInfo transformationInfo = draftInfoGenerator.getObjectFromJsonFile(TransformationInfo.class,
					"./requests/transformerEnCommande.json");
			draftService.transformerEnCommande("REF-DRAFT-1", transformationInfo);
			Commande commande = commandeService.getCommandeByReferenceDraft("REF-DRAFT-1");
			assertNotNull(commande);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(commande.getCommandeLignes().size()));
			assertEquals(Double.valueOf(Constants.DEUX),
					Double.valueOf(commande.getCommandeLignes().get(Constants.ZERO).getCommandeLigneDetails().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
