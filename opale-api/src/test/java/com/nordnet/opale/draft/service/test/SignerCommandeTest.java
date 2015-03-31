package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.SignatureInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.signature.SignatureService;

/**
 * tester la methode
 * {@link SignatureService#transmettreSignature(String, String, com.nordnet.opale.business.SignatureInfo)}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class SignerCommandeTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SignerCommandeTest.class);

	/**
	 * {@link SignatureService}.
	 */
	@Autowired
	private SignatureService signatureService;

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * tester la methode transmettre une signature avec des inforamtions valides.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/signer-commande.xml" })
	public void testSignerCommandeValide() throws OpaleException {
		Commande commande = null;
		Signature signature = null;
		try {
			commande = commandeService.getCommandeByReference("REF-COMMANDE-2");
			assertNotNull(commande);

			SignatureInfo signatureInfo = SignatureInfoGenerator.getSignatureInfo();
			commandeService.signerCommande(commande.getReference(), "REF-SIGNATURE-1", signatureInfo);
			// recuperer la signature une autre fois pour verifer l'ajout des informations.
			signature = signatureService.getSignatureByReferenceCommande(commande.getReference());
			assertTrue(signature.isSigne());

		} catch (Exception exception) {
			LOGGER.error("erreur dans la transmission du signature  :" + exception.getMessage());
			fail(exception.getMessage());
		}

	}

	/**
	 * tester la transmission d'une signature a une commande qui possed deja un signature signée.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public void testTranmsttreSifnatureAvecSignatureComplete() throws OpaleException, JSONException {
		Commande commande = null;
		Signature signature = null;
		try {
			commande = commandeService.getCommandeByReference("REF-COMMANDE-3");
			assertNotNull(commande);
			signature = signatureService.getSignatureByReference("REF-SIGNATURE-2");
			assertNotNull(signature);

			SignatureInfo signatureInfo = SignatureInfoGenerator.getSignatureInfo();
			commandeService.signerCommande(commande.getReference(), signature.getReference(), signatureInfo);
			fail("comportement inattendu");

		} catch (OpaleException exception) {
			assertEquals(exception.getErrorCode(), "4.1.3");
		}
	}

}
