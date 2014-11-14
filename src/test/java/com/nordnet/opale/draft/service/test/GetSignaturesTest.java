package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
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
	 * teste get liste de signature.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void getSignaturesNonAnnulesTest() throws OpaleException {
		List<SignatureInfo> signatures = signatureService.getSignatures("REF-COMMANDE-7", false);
		assertEquals(signatures.size(), 1);

	}

	/**
	 * teste get liste de signature.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 * 
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void getSignaturesAnnulesTest() throws OpaleException {
		List<SignatureInfo> signatures = signatureService.getSignatures("REF-COMMANDE-7", true);
		assertEquals(signatures.size(), 3);

	}

}
