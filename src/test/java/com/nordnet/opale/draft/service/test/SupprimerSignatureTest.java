package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.signature.SignatureService;
import com.nordnet.opale.test.utils.OpaleMultiSchemaXmlDataSetFactory;

/**
 * Classe de test de la methode.
 * {@link SignatureService#supprimer(String, String, com.nordnet.opale.business.Auteur)
 * @author mahjoub-MARZOUGUI
 *
 */
public class SupprimerSignatureTest extends GlobalTestCase {

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
	 * tester la suppression d'une signature.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void supprimerIntentionSignatureValide() throws OpaleException {
		Signature signatureAvantSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-3");
		assertNotNull(signatureAvantSuppression);
		Auteur auteur = new Auteur();
		auteur.setQui("test");
		signatureService.supprimer("REF-COMMANDE-6", "REF-SIGNATURE-3", auteur);
		Signature signatureApresSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-3");
		assertNull(signatureApresSuppression);

	}

	/**
	 * tester la suppression d'une signature.
	 * 
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	@Test
	@DataSet(factory = OpaleMultiSchemaXmlDataSetFactory.class, value = { "/dataset/signer-commande.xml" })
	public void supprimerSignatureValide() throws OpaleException {
		Signature signatureAvantSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-4");
		assertNotNull(signatureAvantSuppression);
		Auteur auteur = new Auteur();
		auteur.setQui("test");
		signatureService.supprimer("REF-COMMANDE-5", "REF-SIGNATURE-4", auteur);
		Signature signatureApresSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-4");
		assertNotNull(signatureApresSuppression);
		assertNotNull(signatureApresSuppression.getDateAnnulation());

	}
}
