package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 *  * Classe de test de la methode {@link SignatureService#getSignatures(String, Boolean).
 *  
 * @author mahjoub-MARZOUGUI
 *
 */
public class GetSignaturesTest extends GlobalTestCase {

	/**
	 * Declaration du log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupprimerSignatureTest.class);

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
	 * teste get liste de signature.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void getSignaturesNonAnnulesTest() {
		try {
			List<SignatureInfo> signatures = commandeService.getSignature("REF-COMMANDE-7", false);
			assertEquals(signatures.size(), 1);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * teste get liste de signature.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void getSignaturesAnnulesTest() {
		try {
			List<SignatureInfo> signatures = commandeService.getSignature("REF-COMMANDE-7", true);
			assertEquals(signatures.size(), 3);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * teste get liste de signature pour une commande qui n'existe pas.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void getSignaturesAvecCommandeNonExiste() {
		try {
			commandeService.getSignature("00000000", true);
		} catch (OpaleException e) {
			Assert.assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

}
