package com.nordnet.opale.service.signature;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.AjoutSignatureInfo;
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

		Commande commande = commandeService.getCommandeByReferenceDraft(refCommande);
		CommandeValidator.checkCommandeExiste(refCommande, commande);
		String signatureReference = null;
		if (commande.getReferenceSignature() == null) {
			signatureReference = creerSignature(ajoutSignatureInfo, commande);
		} else {
			Signature signature = signatureRepository.findByReference(commande.getReference());
			SignatureValidator.checkSignatureComplete(refCommande, signature);
			signature.setMode(ajoutSignatureInfo.getMode());
			signatureRepository.save(signature);
			signatureReference = signature.getReference();

		}
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("signatureReference", signatureReference);
		return jsonResponse.toString();

	}

	/**
	 * creer un signature et ajouter la reference au commande.
	 * 
	 * @param ajoutSignatureInfo
	 *            {@link AjoutSignatureInfo}
	 * @param commande
	 *            {@link Commande}
	 * @return {@link String}
	 */
	private String creerSignature(AjoutSignatureInfo ajoutSignatureInfo, Commande commande) {
		Signature signature = new Signature();
		signature.setMode(ajoutSignatureInfo.getMode());
		signature.setReference(keygenService.getNextKey(Signature.class));
		signatureRepository.save(signature);
		commande.setReferenceSignature(signature.getReference());
		commandeRepository.save(commande);
		return signature.getReference();
	}

}
