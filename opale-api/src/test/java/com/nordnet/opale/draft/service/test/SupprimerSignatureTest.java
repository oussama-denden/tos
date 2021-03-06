package com.nordnet.opale.draft.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.draft.test.GlobalTestCase;
import com.nordnet.opale.draft.test.generator.DraftInfoGenerator;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.commande.CommandeServiceImpl;
import com.nordnet.opale.service.signature.SignatureService;

/**
 * Classe de test de la methode. {@link CommandeServiceImpl#supprimerSignature(String, String, Auteur)}.
 * 
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
	@Autowired
	private SignatureService signatureService;

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * {@link DraftInfoGenerator}.
	 */
	@Autowired
	private DraftInfoGenerator draftInfoGenerator;

	/**
	 * tester la suppression d'une signature.
	 * 
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/signer-commande.xml" })
	public void supprimerIntentionSignatureValide() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			Signature signatureAvantSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-3");
			assertNotNull(signatureAvantSuppression);
			commandeService.supprimerSignature("REF-COMMANDE-6", "REF-SIGNATURE-3", auteur);
			Signature signatureApresSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-3");
			assertNull(signatureApresSuppression);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * tester la suppression d'une signature.
	 * 
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/signer-commande.xml" })
	public void supprimerSignatureValide() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			Signature signatureAvantSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-4");
			assertNotNull(signatureAvantSuppression);
			commandeService.supprimerSignature("REF-COMMANDE-5", "REF-SIGNATURE-4", auteur);
			Signature signatureApresSuppression = signatureService.getSignatureByReference("REF-SIGNATURE-4");
			assertNotNull(signatureApresSuppression);
			assertNotNull(signatureApresSuppression.getDateAnnulation());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester la suppression d'une signature pour une commande qui n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/signer-commande.xml" })
	public void supprimerSignatureAvecCommandeNonExiste() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			commandeService.supprimerSignature("00000000", "REF-SIGNATURE-4", auteur);
		} catch (OpaleException e) {
			Assert.assertEquals("2.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester la suppression d'une signature qui n'existe pas.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/signer-commande.xml" })
	public void supprimerSignatureAvecSignatureNonExiste() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			commandeService.supprimerSignature("REF-COMMANDE-5", "00000000", auteur);
		} catch (OpaleException e) {
			Assert.assertEquals("4.1.2", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * tester la suppression d'une signature annule.
	 */
	@Test
	@DatabaseSetup(value = { "/dataset/emptyDB.xml", "/dataset/signer-commande.xml" })
	public void supprimerSignatureAnnule() {
		try {
			Auteur auteur = draftInfoGenerator.getObjectFromJsonFile(Auteur.class, "./requests/auteur.json");
			commandeService.supprimerSignature("REF-COMMANDE-7", "REF-SIGNATURE-8", auteur);
		} catch (OpaleException e) {
			Assert.assertEquals("4.1.6", e.getErrorCode());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fail(e.getMessage());
		}
	}
}
