package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.OptionTransformation;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode {@link CommandeService#transformerEnDraft(String)}.
 * 
 * @author akram-moncer
 * 
 */
public class TransformerEnDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(TransformerEnDraftTest.class);

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
	 * tester la cas de transformation d'une commande qui n'existe pas en draft.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/transformer-en-draft.xml" })
	public void testerTransformerCommandeNonExiste() {
		try {
			OptionTransformation optionTransformation =
					draftInfoGenerator.getObjectFromJsonFile(OptionTransformation.class,
							"./requests/transformerEnDraft.json");
			commandeService.transformerEnDraft("00000000", optionTransformation);
			fail("Unexpected Error");
		} catch (OpaleException e) {
			assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * tester transformerEnCommandeValide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/transformer-en-draft.xml" })
	public void testerTransformerEnDraftValide() {
		try {
			OptionTransformation optionTransformation =
					draftInfoGenerator.getObjectFromJsonFile(OptionTransformation.class,
							"./requests/transformerEnDraft.json");
			Draft draft = commandeService.transformerEnDraft("00000001", optionTransformation);
			Commande commande = commandeService.getCommandeByReference("00000001");
			assertEquals(commande.getReference(), draft.getCommandeSource());
			assertEquals(commande.getCommandeLignes().size(), draft.getDraftLignes().size());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
