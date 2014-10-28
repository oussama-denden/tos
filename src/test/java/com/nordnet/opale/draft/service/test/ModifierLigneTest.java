package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.draft.DraftService;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test pour la methode
 * {@link DraftService#modifierLigne(String, String, com.nordnet.opale.business.DraftLigneInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class ModifierLigneTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ModifierLigneTest.class);

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * Mofifier une ligne valide.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/modifier-ligne-draft.xml" })
	public void testerModifierLigneValide() {
		DraftLigneInfo draftLigneInfo = DraftInfoGenerator.getDraftLigneInfoModifier();
		try {
			draftService.modifierLigne("REF-DRAFT-1", "00000001", draftLigneInfo);

			Draft draft = draftService.getDraftByReference("REF-DRAFT-1");
			assertEquals(Double.valueOf(Constants.UN), Double.valueOf(draft.getDraftLignes().size()));
			DraftLigne draftLigne = draft.getDraftLignes().get(Constants.ZERO);
			assertEquals(ModePaiement.SEPA, draftLigne.getModePaiement());
			assertEquals(ModeFacturation.PREMIER_MOIS, draftLigne.getModeFacturation());
			assertEquals(Double.valueOf(Constants.QUATRE), Double.valueOf(draftLigne.getDraftLigneDetails().size()));
			for (DraftLigneDetail detail : draftLigne.getDraftLigneDetails()) {
				assertEquals(ModePaiement.SEPA, detail.getModePaiement());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/modifier-ligne-draft.xml" })
	public void testerModifierLignePourDraftNonExist() {
		DraftLigneInfo draftLigneInfo = DraftInfoGenerator.getDraftLigneInfoModifier();
		try {
			draftService.modifierLigne("REF-DRAFT-2", "00000001", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.1", e.getErrorCode());
		}
	}

	/**
	 * Tester le cas ou le draft n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/modifier-ligne-draft.xml" })
	public void testerModifierLigneNonExist() {
		DraftLigneInfo draftLigneInfo = DraftInfoGenerator.getDraftLigneInfoModifier();
		try {
			draftService.modifierLigne("REF-DRAFT-1", "00000002", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("1.1.2", e.getErrorCode());
		}
	}

	/**
	 * Tester le cas de modifier une ligne avec une offre non valid.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/modifier-ligne-draft.xml" })
	public void testModifierLigneAvecOffreNonValide() {
		DraftLigneInfo draftLigneInfo = DraftInfoGenerator.getDraftLigneInfo();
		draftLigneInfo.getOffre().setReferenceOffre(null);
		try {
			draftService.modifierLigne("REF-DRAFT-1", "00000001", draftLigneInfo);
			fail("Unexpected error");
		} catch (OpaleException e) {
			assertEquals("0.1.4", e.getErrorCode());
		}
	}
}
