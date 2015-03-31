package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.TrameCatalogueInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;
import com.nordnet.topaze.ws.entity.Contrat;

/**
 * classe de test de la methode
 * {@link DraftServiceImpl#transformerContratEnDraft(String, com.nordnet.opale.business.catalogue.TrameCatalogue)}.
 * 
 * @author akram-moncer
 * 
 */
public class TransformerContratEnDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(TransformerContratEnDraftTest.class);

	/**
	 * {@link DraftService}.
	 */
	@Autowired
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * tester le cas valide de la transformation d'un {@link Contrat} en {@link Draft}.
	 */
	// @Test
	// @DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/transformer-contrat-en-draft.xml"
	// })
	// public void testerTransformerContratEnDraftValide() {
	// try {
	// int size = draftService.findAllDraft().size();
	// TrameCatalogue trameCatalogue =
	// draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
	// "./requests/transformerContratEnDraft.json");
	// Object object = draftService.transformerContratEnDraft("00000001", trameCatalogue);
	// assertTrue(object instanceof Draft);
	// size++;
	// assertEquals(Double.valueOf(size), Double.valueOf(draftService.findAllDraft().size()));
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage());
	// fail(e.getMessage());
	// }
	// }

	/**
	 * tester le cas non valide de la transformation d'un {@link Contrat} en {@link Draft}.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml" })
	public void testerTransformerContratEnDraftNonValide() {
		try {
			TrameCatalogueInfo trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogueInfo.class,
							"./requests/transformerContratEnDraft.json");
			draftService.transformerContratEnDraft("00000002", trameCatalogue);
		} catch (OpaleException e) {
			assertEquals("1.1.30", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
