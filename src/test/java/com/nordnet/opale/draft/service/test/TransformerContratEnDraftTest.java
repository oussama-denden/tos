package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftValidationInfo;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.business.commande.Contrat;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.service.draft.DraftServiceImpl;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;
import com.nordnet.opale.util.Constants;

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
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * tester le cas valide de la transformation d'un {@link Contrat} en {@link Draft}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/transformer-contrat-en-draft.xml" })
	public void testerTransformerContratEnDraftValide() {
		try {
			assertEquals(Double.valueOf(Constants.ZERO), Double.valueOf(draftService.findAllDraft().size()));
			TrameCatalogue trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
							"./requests/transformerContratEnDraft.json");
			Object object = draftService.transformerContratEnDraft("00000001", trameCatalogue);
			assertTrue(object instanceof Draft);
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(draftService.findAllDraft().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester le cas non valide de la transformation d'un {@link Contrat} en {@link Draft}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/transformer-contrat-en-draft.xml" })
	public void testerTransformerContratEnDraftNonValide() {
		try {
			TrameCatalogue trameCatalogue =
					draftInfoGenerator.getObjectFromJsonFile(TrameCatalogue.class,
							"./requests/transformerContratEnDraft.json");
			Object object = draftService.transformerContratEnDraft("00000002", trameCatalogue);
			assertTrue(object instanceof DraftValidationInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
