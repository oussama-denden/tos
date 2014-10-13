package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
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
	 * {@link DraftInfoGenerator}.
	 */
	@SpringBean("draftInfoGenerator")
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * Tester le cas de l'ajout valid d'une ligne valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajouter-ligne-draft.xml" })
	public void testAjouterLigneValide() {
		try {
			DraftLigneInfo draftLigneInfo =
					draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/ajouterLigne.json");
			draftService.ajouterLigne("REF-DRAFT-1", draftLigneInfo);
			Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(draft.getDraftLignes().size()));
			assertEquals(Double.valueOf(Constants.DEUX),
					Double.valueOf(draft.getDraftLignes().get(Constants.ZERO).getDraftLigneDetails().size()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajouter-ligne-draft.xml" })
	public void testAjouterLigneAvecDraftNonExist() {
		try {
			DraftLigneInfo draftLigneInfo =
					draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/ajouterLigne.json");
			draftService.ajouterLigne("REF-DRAFT-2", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas d'ajout avec une offre non valid.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/ajouter-ligne-draft.xml" })
	public void testAjouterLigneAvecOffreNonValide() {
		try {
			DraftLigneInfo draftLigneInfo =
					draftInfoGenerator.getObjectFromJsonFile(DraftLigneInfo.class, "./requests/ajouterLigne.json");
			draftLigneInfo.getOffre().setReferenceOffre(null);
			draftService.ajouterLigne("REF-DRAFT-1", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
