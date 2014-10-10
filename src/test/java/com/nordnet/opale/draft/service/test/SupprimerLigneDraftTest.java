package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DeleteInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.DraftLigneRepository;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test pour la methode
 * {@link DraftService#supprimerLigneDraft(String, String, DeleteInfo)} .
 * 
 * @author anisselmane.
 * 
 */
public class SupprimerLigneDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupprimerLigneDraftTest.class);

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
	 * {@link DraftLigneRepository}.
	 */
	@SpringBean("draftLigneRepository")
	private DraftLigneRepository draftLigneRepository;

	/**
	 * Supprimer ligne draft valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/annuler-draft.xml" })
	public void testerSupprimerLigneDraftValide() {

		try {
			draftService.supprimerLigneDraft("00000001", "00000001",
					draftInfoGenerator.getObjectFromJsonFile(DeleteInfo.class, "./requests/supprimerLigneDraft.json"));
			draftLigneRepository.flush();
			Draft draft = draftService.getDraftByReference("00000001");
			assertEquals(0, draft.getDraftLignes().size());

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Supprimer une ligne draft quand lla ligne draft est inexistante.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-draft.xml" })
	public void GivenLigneDraftNotExistsWhenSupprimerLigneDraftThenFail() {

		try {
			draftService.supprimerLigneDraft("00000001", "00000002",
					draftInfoGenerator.getObjectFromJsonFile(DeleteInfo.class, "./requests/supprimerLigneDraft.json"));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Supprimer une ligne draft quand le draft est inexistant.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-draft.xml" })
	public void GivenDraftNotExistsWhenSupprimerLigneDraftThenFail() {

		try {
			draftService.supprimerLigneDraft("00000002", "00000001",
					draftInfoGenerator.getObjectFromJsonFile(DeleteInfo.class, "./requests/supprimerLigneDraft.json"));
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
