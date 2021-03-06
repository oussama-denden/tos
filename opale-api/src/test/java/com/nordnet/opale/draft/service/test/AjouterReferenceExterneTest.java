package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class AjouterReferenceExterneTest extends GlobalTestCase {

	/**
	 * The log of the class.
	 */
	private static final Logger LOGGER = Logger.getLogger(AjouterReferenceExterneTest.class);

	/**
	 * service de draft.
	 */
	@Autowired
	private DraftService draftService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * Test ajouter reference externe valid.
	 * 
	 * @throws OpaleException
	 *             the opale exception
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred. {@link OpaleException}
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/ajouter-reference-externe.xml" })
	public void TestAjouterReferenceExterneValid()
			throws OpaleException, JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Debut methode :::TestAjouterReferenceExterneValid");
		ReferenceExterneInfo referenceExterneInfo =
				draftInfoGenerator.getObjectFromJsonFile(ReferenceExterneInfo.class,
						"./requests/ajouterReferenceExterne.json");
		draftService.ajouterReferenceExterne("REF-DRAFT-1", referenceExterneInfo);
		Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
		assertNotNull(draft.getReferenceExterne());
		assertEquals(draft.getReferenceExterne(), "REF-DRAFT-EXTERNE-1");

	}
}
