package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.SignatureInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * tester la methode {@link SignatureService#transmettreSignature(String, com.nordnet.opale.business.SignatureInfo)}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class SignerCommandeDirectTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SignerCommandeTest.class);

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
	 * tester la creation et la transmission d'une signature.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void testSignerCommandeDirectValide() throws OpaleException {

		Commande commande = null;
		Signature signature = null;
		try {
			commande = commandeService.getCommandeByReference("REF-COMMANDE-3");
			assertNotNull(commande);

			SignatureInfo signatureInfo = SignatureInfoGenerator.getSignatureInfo();
			commandeService.signerCommande(commande.getReference(), null, signatureInfo);
			// recuperer la signature une autre fois pour verifer l'ajout des informaitons.
			signature = signatureService.getSignatureByReferenceCommande("REF-COMMANDE-3");
			assertTrue(signature.isSigne());

		} catch (Exception exception) {
			LOGGER.error("erreur dans la transmission du signature  :" + exception.getMessage());
			fail(exception.getMessage());
		}

	}

	/**
	 * tester la creation et la transmission d'une signature pour une commande qui est deja signe.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * @throws JSONException
	 *             {@link JSONException}.
	 */
	public void testTransmettreSignatureInvalide() throws OpaleException, JSONException {
		Commande commande = null;
		try {
			commande = commandeService.getCommandeByReference("REF-COMMANDE-4");
			assertNotNull(commande);

			SignatureInfo signatureInfo = SignatureInfoGenerator.getSignatureInfo();
			commandeService.signerCommande(commande.getReference(), null, signatureInfo);
			fail("comportement inattendu");
		} catch (OpaleException exception) {
			assertEquals(exception.getErrorCode(), "4.1.3");
		}
	}
}
