package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.domain.DraftLigneDetail;
import com.nordnet.opale.draft.service.DraftService;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.test.utils.Constants;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test pour la methode
 * {@link DraftService#modifierLigne(String, String, com.nordnet.opale.business.DraftLigneInfo)}.
 * 
 * @author akram-moncer
 * 
 */
public class ModifierLigne extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(ModifierLigne.class);

	/**
	 * {@link DraftService}.
	 */
	@SpringBean("draftService")
	private DraftService draftService;

	/**
	 * 
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
}
