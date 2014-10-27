package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test pour la methode {@link DraftService#creerDraft(DraftInfo)} .
 * 
 * @author anisselmane.
 * 
 */
public class CreerDraftTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CreerDraftTest.class);

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
	 * Creer un draft incomplet.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-draft.xml" })
	public void testerCreerDraftValide() {

		try {
			draftService.creerDraft(draftInfoGenerator.getObjectFromJsonFile(DraftInfo.class,
					"./requests/creerDraftIncomplet.json"));

			Draft draft = draftService.getDraftByReference("00000001");
			assertEquals("00000001", draft.getReference());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Creer un draft incomplet.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-draft.xml" })
	public void testerCreerDraftCompletValide() {

		try {
			draftService.creerDraft(draftInfoGenerator.getObjectFromJsonFile(DraftInfo.class,
					"./requests/creerDraftComplet.json"));

			Draft draft = draftService.getDraftByReference("00000001");
			assertEquals("00000001", draft.getReference());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Creer un draft incomplet.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-draft.xml" })
	public void GivenWithoutClientIdWhenCreerDraftCompletThenFail() {

		try {
			draftService.creerDraft(draftInfoGenerator.getObjectFromJsonFile(DraftInfo.class,
					"./requests/creerDraftCompletWithoutClientId.json"));

			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.5", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Creer un draft incomplet.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/creer-draft.xml" })
	public void GivenWithoutCodeWhenCreerDraftCompletThenFail() {

		try {
			draftService.creerDraft(draftInfoGenerator.getObjectFromJsonFile(DraftInfo.class,
					"./requests/creerDraftCompletWithoutCode.json"));

			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
