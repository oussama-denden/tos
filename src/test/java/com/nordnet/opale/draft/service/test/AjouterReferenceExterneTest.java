package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.draft.service.DraftService;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

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
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * Test ajouter reference externe valid.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajouter-reference-externe.xml" })
	public void TestAjouterReferenceExterneValid() throws OpaleException {
		LOGGER.info("Debut methode :::TestAjouterReferenceExterneValid");
		ReferenceExterneInfo referenceExterneInfo = new ReferenceExterneInfo();
		referenceExterneInfo.setUser("userTest");
		referenceExterneInfo.setReferenceExterne("REF-DRAFT-EXTERNE-1");
		draftService.ajouterReferenceExterne("REF-DRAFT-1", referenceExterneInfo);
		Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
		assertNotNull(draft.getReferenceExterne());
		assertEquals(draft.getReferenceExterne(), "REF-DRAFT-EXTERNE-1");

	}
}
