package com.nordnet.opale.service.signature;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.signature.Signature;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.commande.CommandeRepository;
import com.nordnet.opale.repository.signature.SignatureRepository;
import com.nordnet.opale.service.commande.CommandeService;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.validator.CommandeValidator;
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
	 * {@inheritDoc}
	 * 
	 * 
	 */
	@Override
	public Object signerCommande(String refCommande, AjoutSignatureInfo ajoutSignatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode signerCommande");

		Commande commande = commandeService.getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		String signatureReference = null;
		if (commande.getReferenceSignature() == null) {
			signatureReference = creerSignature(ajoutSignatureInfo, null, commande);
		} else {
			Signature signature = signatureRepository.findByReference(commande.getReferenceSignature());
			SignatureValidator.checkSignatureComplete(refCommande, signature, true);
			signature.setMode(ajoutSignatureInfo.getMode());
			signatureRepository.save(signature);
			signatureReference = signature.getReference();

		}
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("signatureReference", signatureReference);
		return jsonResponse.toString();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transmettreSignature(String refCommande, String refSignature, SignatureInfo signatureInfo)
			throws OpaleException {

		LOGGER.info("Debut methode transmettreSignature");

		Commande commande = commandeService.getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);

		Signature signature = signatureRepository.findByReference(refSignature);
		SignatureValidator.checkSignatureExiste(signature, refSignature, refCommande);
		SignatureValidator.checkSignatureComplete(refCommande, signature, false);
		SignatureValidator.validerSignature(signatureInfo);
		ajouterSignature(signature, signatureInfo);
		LOGGER.info("Fin methode transmettreSignature");

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws JSONException
	 */
	@Override
	public Object transmettreSignature(String refCommande, SignatureInfo signatureInfo)
			throws OpaleException, JSONException {

		LOGGER.info("Debut methode transmettreSignature");

		Commande commande = commandeService.getCommandeByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		String signatureReference = null;
		if (commande.getReferenceSignature() == null) {
			signatureReference = creerSignature(null, signatureInfo, commande);
		} else {

			Signature signature = signatureRepository.findByReference(commande.getReferenceSignature());
			SignatureValidator.checkSignatureComplete(refCommande, signature, true);
			SignatureValidator.validerSignature(signatureInfo);
			signatureReference = ajouterSignature(signature, signatureInfo);
		}
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("signatureReference", signatureReference);
		return jsonResponse.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SignatureInfo getSignature(String refCommande) throws OpaleException {

		LOGGER.info("Debut methode  getSignature");

		Commande commande = commandeRepository.findByReference(refCommande);
		CommandeValidator.isExiste(refCommande, commande);
		if (commande.getReferenceSignature() != null) {
			Signature signature = signatureRepository.findByReference(commande.getReferenceSignature());
			SignatureValidator.checkSignatureExiste(signature, null, refCommande);
			return signatureRepository.findByReference(commande.getReferenceSignature()).toSignatureInfo();
		}
		return null;
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

		if (signature.getMode() != null) {
			signature.setMode(signatureInfo.getMode());
		}
		signature.setIdSignature(signatureInfo.getIdSignature());
		signature.setFootprint(signatureInfo.getFootprint());
		signature.setTimestamp(signatureInfo.getTimestamp());
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
	 * @param commande
	 *            {@link Commande}
	 * @return {@link String}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private String creerSignature(AjoutSignatureInfo ajoutSignatureInfo, SignatureInfo signatureInfo, Commande commande)
			throws OpaleException {

		LOGGER.info("Debut methode privee creerSignature");

		Signature signature = new Signature();
		if (ajoutSignatureInfo != null) {
			signature.setMode(ajoutSignatureInfo.getMode());
		} else if (signatureInfo != null) {
			SignatureValidator.validerSignature(signatureInfo);
			signature.setMode(signatureInfo.getMode());
			signature.setIdSignature(signatureInfo.getIdSignature());
			signature.setFootprint(signatureInfo.getFootprint());
			signature.setTimestamp(signatureInfo.getTimestamp());
		}
		signature.setReference(keygenService.getNextKey(Signature.class));
		signatureRepository.save(signature);
		commande.setReferenceSignature(signature.getReference());
		commandeRepository.save(commande);
		return signature.getReference();
	}

}
