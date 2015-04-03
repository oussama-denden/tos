package com.nordnet.opale.service.signature;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
import com.nordnet.opale.repository.signature.SignatureRepository;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.service.tracage.TracageService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.spring.ApplicationContextHolder;
import com.nordnet.opale.validator.SignatureValidator;

/**
 * implementation de {@link SignatureService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Service("signatureService")
public class SignatureServiceImpl implements SignatureService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(SignatureServiceImpl.class);

	/**
	 * {@link SignatureRepository}.
	 */
	@Autowired
	private SignatureRepository signatureRepository;

	/**
	 * {@link CommandeRepository}.
	 */
	@Autowired
	private CommandeRepository commandeRepository;

	/**
	 * {@link CommandeService}.
	 */
	@Autowired
	private CommandeService commandeService;

	/**
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

	/**
	 * {@link TracageService}.
	 */
	private TracageService tracageService;

	/**
	 * {@inheritDoc}
	 * 
	 * 
	 */
	@Override
	public Object ajouterIntentionDeSignature(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode signerCommande");
		SignatureValidator.validerAuteur(ajoutSignatureInfo.getAuteur());
		Signature signature = getSignatureByReferenceCommande(refCommande);
		String signatureReference = null;
		if (signature == null) {
			signatureReference = creerSignature(ajoutSignatureInfo, null, refCommande);
		} else {
			SignatureValidator.checkSignatureComplete(signature, true);
			SignatureValidator.checkIfSignatureAnnule(signature);
			signature.setMode(ajoutSignatureInfo.getMode());
			signature.setAuteur(new Auteur(ajoutSignatureInfo.getAuteur()));
			signature.setTimestampIntention(ajoutSignatureInfo.getTimestamp() != null ? ajoutSignatureInfo
					.getTimestamp() : PropertiesUtil.getInstance().getDateDuJour());
			signatureRepository.save(signature);
			signatureReference = signature.getReference();

		}

		getTracage().ajouterTrace(Constants.ORDER, refCommande,
				"Ajouter un intention de signature pour la commande de reference " + refCommande,
				ajoutSignatureInfo.getAuteur());
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("signatureReference", signatureReference);
		return jsonResponse.toString();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws JSONException
	 */
	@Override
	public Object ajouterSignatureCommande(String refCommande, String refSignature, SignatureInfo signatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode ajouterSignatureCommande");
		SignatureValidator.validerAuteur(signatureInfo.getAuteur());
		String referenceSignature = null;
		if (refSignature != null) {
			Signature signature = signatureRepository.findByReferenceAndReferenceCommande(refSignature, refCommande);
			SignatureValidator.checkSignatureExiste(signature, refSignature, refCommande);
			SignatureValidator.checkSignatureComplete(signature, false);
			SignatureValidator.validerSignature(signatureInfo);
			SignatureValidator.checkIfSignatureAnnule(signature);
			ajouterSignature(signature, signatureInfo);
		} else {
			Signature signature = getSignatureByReferenceCommande(refCommande);
			if (signature != null) {
				SignatureValidator.checkSignatureComplete(signature, false);
				SignatureValidator.validerSignature(signatureInfo);
				SignatureValidator.checkIfSignatureAnnule(signature);
				referenceSignature = ajouterSignature(signature, signatureInfo);
			}

			else {
				referenceSignature = creerSignature(null, signatureInfo, refCommande);

			}
		}
		getTracage().ajouterTrace(Constants.ORDER, refCommande, "Signer la commande de reference " + refCommande,
				signatureInfo.getAuteur());
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("signatureReference", referenceSignature);
		return jsonResponse.toString();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SignatureInfo> getSignatures(String refCommande, Boolean afficheAnnule) throws OpaleException {

		LOGGER.info("Debut methode  getSignature");
		List<SignatureInfo> listeDeSignature = new ArrayList<>();
		List<Signature> signaturesAnnules = signatureRepository.getSignaturesAnnules(refCommande);
		Signature signature = signatureRepository.findByReferenceCommande(refCommande);
		SignatureValidator.checkSignatureExiste(signature, null, refCommande);
		listeDeSignature.add(signature.toSignatureInfo());
		if (afficheAnnule && signaturesAnnules != null) {
			for (Signature signatureAnnule : signaturesAnnules) {
				listeDeSignature.add(signatureAnnule.toSignatureInfo());
			}

		}
		return listeDeSignature;
	}

	@Override
	public Signature getSignatureByReference(String refSignature) throws OpaleException {
		return signatureRepository.findByReference(refSignature);
	}

	/**
	 * ajouter les informations d'une signature deja existe.
	 * 
	 * @param signature
	 *            {@link Signature}
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @return {@link String}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private String ajouterSignature(Signature signature, SignatureInfo signatureInfo) throws OpaleException {

		LOGGER.info("Debut methode privee ajouterSignature");
		SignatureValidator.validerAuteur(signatureInfo.getAuteur());
		if (signatureInfo.getMode() != null) {
			signature.setMode(signatureInfo.getMode());
		}
		signature.setIdSignature(signatureInfo.getIdSignature());
		signature.setFootprint(signatureInfo.getFootprint());
		signature.setTimestampSignature(signatureInfo.getTimestamp());
		if (signatureInfo.getAuteur() != null) {
			signature.setAuteur(new Auteur(signatureInfo.getAuteur()));
		}
		signature.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
		signatureRepository.save(signature);

		return signature.getReference();

	}

	/**
	 * creer un signature et ajouter la reference au commande.
	 * 
	 * @param ajoutSignatureInfo
	 *            {@link AjoutSignatureInfo}
	 * @param signatureInfo
	 *            {@link SignatureInfo}
	 * @param refCommande
	 *            reference du commande.
	 * @return {@link String}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private String creerSignature(AjoutSignatureInfo ajoutSignatureInfo, SignatureInfo signatureInfo, String refCommande)
			throws OpaleException {

		LOGGER.info("Debut methode privee creerSignature");

		Signature signature = new Signature();
		Auteur auteur = null;
		if (ajoutSignatureInfo != null) {
			signature.setMode(ajoutSignatureInfo.getMode());
			signature.setTimestampIntention(ajoutSignatureInfo.getTimestamp() != null ? ajoutSignatureInfo
					.getTimestamp() : PropertiesUtil.getInstance().getDateDuJour());
			auteur = ajoutSignatureInfo.getAuteur() != null ? new Auteur(ajoutSignatureInfo.getAuteur()) : null;
		} else if (signatureInfo != null) {
			SignatureValidator.validerSignature(signatureInfo);
			signature.setMode(signatureInfo.getMode());
			signature.setIdSignature(signatureInfo.getIdSignature());
			signature.setFootprint(signatureInfo.getFootprint());
			signature.setTimestampSignature(signatureInfo.getTimestamp());
			auteur = signatureInfo.getAuteur() != null ? new Auteur(signatureInfo.getAuteur()) : null;
		}

		signature.setAuteur(auteur);
		signature.setReference(keygenService.getNextKey(Signature.class));
		signature.setReferenceCommande(refCommande);
		signature.setDateCreation(PropertiesUtil.getInstance().getDateDuJour());
		signatureRepository.save(signature);
		return signature.getReference();
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void supprimer(String refCommande, String refSignature, com.nordnet.opale.business.Auteur auteur)
			throws OpaleException {
		LOGGER.info("Debut methode supprimer");
		SignatureValidator.validerAuteur(auteur);
		Signature signature = signatureRepository.findByReference(refSignature);
		SignatureValidator.checkSignatureExiste(signature, refSignature, refCommande);
		if (!signature.isSigne()) {
			signatureRepository.delete(signature);
			signatureRepository.flush();
			getTracage().ajouterTrace(Constants.ORDER, refCommande,
					"Supprimer l'intention de signature de reference " + refSignature, auteur);
		} else {
			SignatureValidator.checkIfSignatureAnnule(signature);
			signature.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour());
			signatureRepository.save(signature);
		}
		getTracage().ajouterTrace(Constants.ORDER, refCommande, "Supprimer la signature de reference " + refSignature,
				auteur);

		LOGGER.info("Fin methode supprimer");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Signature getSignatureByReferenceCommande(String refCommande) throws OpaleException {
		return signatureRepository.findByReferenceCommande(refCommande);
	}

	/**
	 * Retourn le {@link TracageService}.
	 * 
	 * @return {@link TracageService}
	 */
	public TracageService getTracage() {
		if (tracageService == null) {
			if (System.getProperty("log.useMock").equals("true")) {
				tracageService = (TracageService) ApplicationContextHolder.getBean("tracageServiceMock");
			} else {
				tracageService = (TracageService) ApplicationContextHolder.getBean("tracageService");
			}
		}
		return tracageService;
	}
}
