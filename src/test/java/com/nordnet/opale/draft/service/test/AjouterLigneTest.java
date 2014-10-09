package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.draft.service.DraftService;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode {@link DraftService#ajouterLigne(String, com.nordnet.opale.business.DraftLigneInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class AjouterLigneTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(AjouterLigneTest.class);

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * Tester le cas de l'ajout valid d'une ligne valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajouter-ligne-draft.xml" })
	public void testAjouterLigneValide() {
		DraftLigneInfo draftLigneInfo = DraftInfoGenerator.getDraftLigneInfo();
		try {
			draftService.ajouterLigne("REF-DRAFT-1", draftLigneInfo);
			Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(draft.getDraftLignes().size()));
			assertEquals(Double.valueOf(Constants.TROIS),
					Double.valueOf(draft.getDraftLignes().get(Constants.ZERO).getDraftLigneDetails().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
