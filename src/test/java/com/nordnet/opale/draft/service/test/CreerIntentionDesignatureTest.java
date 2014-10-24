package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.SignatureInfoGenerator;
import com.nordnet.opale.enums.ModeSignature;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * tester la methode {@link SignatureService#signerCommande(String, com.nordnet.opale.business.AjoutSignatureInfo)}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CreerIntentionDesignatureTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(CreerIntentionDesignatureTest.class);

	/**
	 * {@link SignatureService}.
	 */
	@SpringBean("signatureService")
	private SignatureService signatureService;

	/**
	 * {@link CommandeService}.
	 */
	@SpringBean("commandeService")
	private CommandeService commandeService;

	/**
	 * tester la signautre d'une commande avec des informations valides.
	 * 
	 * @throws JSONException
	 *             {@link JSONException}.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void testCreerIntentionDesignatureValide() throws OpaleException, JSONException {
		Commande commande = null;
		Signature signature = null;
		try {
			commande = commandeService.getCommandeByReference("REF-COMMANDE-1");
			assertNotNull(commande);
			AjoutSignatureInfo ajoutSignatureInfo = SignatureInfoGenerator.getAjoutSignatureInfo();
			Object response = commandeService.creerIntentionDeSignature(commande.getReference(), ajoutSignatureInfo);
			assertNotNull(response);

			signature = signatureService.getSignatureByReferenceCommande("REF-COMMANDE-1");
			assertNotNull(signature);
			assertNotNull(signature.getReferenceCommande());
			assertEquals(signature.getMode(), ModeSignature.OPEN_TRUST);
		} catch (Exception ex) {
			LOGGER.error("erreur dans l'ajout de signature  :" + ex.getMessage());
			fail(ex.getMessage());
		}

	}
}
