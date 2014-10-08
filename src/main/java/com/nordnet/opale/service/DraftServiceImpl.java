package com.nordnet.opale.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.AuteurInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.business.ReferenceExterneInfo;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.Keygen;
import com.nordnet.opale.repository.DraftRepository;
import com.nordnet.opale.repository.KeygenRepository;
import com.nordnet.opale.util.Constants;

/**
 * L'implementation de service {@link DraftService}.
 * 
 * @author anisselmane.
 * 
 */
@Service("draftService")
public class DraftServiceImpl implements DraftService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(DraftServiceImpl.class);

	/**
	 * {@link DraftRepository}.
	 */
	@Autowired
	private DraftRepository draftRepository;

	/**
	 * {@link KeygenRepository}.
	 */
	@Autowired
	private KeygenRepository keygenRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Draft getDraftByReference(String reference) {

		return draftRepository.findByReference(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimerDraft(String reference) {

		Draft draft = getDraftByReference(reference);

		draftRepository.delete(draft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DraftReturn creerDraft(AuteurInfo auteurInfo) {

		LOGGER.info("Enter methode creerDraft");

		Auteur auteur = new Auteur();
		auteur.setCanal(auteurInfo.getAuteur().getCanal());
		auteur.setCode(auteurInfo.getAuteur().getCode());
		auteur.setIp(auteurInfo.getAuteur().getIp().getIp());
		auteur.setQui(auteurInfo.getAuteur().getQui());
		auteur.setTimestamp(auteurInfo.getAuteur().getIp().getTs());

		Draft draft = new Draft();
		draft.setAuteur(auteur);

		Keygen keygen = keygenRepository.findDernier();

		Keygen keygen2 = new Keygen();
		int inc = 0;
		if (keygen != null) {
			draft.setReference(keygen.getReferenceDraft());
			inc = Integer.parseInt(keygen.getReferenceDraft()) + 1;
		} else {
			draft.setReference(Constants.REF_DRAEFT_INIT);
			inc = Integer.parseInt(Constants.REF_DRAEFT_INIT) + 1;
		}
		// draft.setReference(UUID.randomUUID().toString().substring(0, 8));

		draftRepository.save(draft);

		// generer la nouvelle reference draft.

		String newReferenceDraft = String.format("%08d", inc);
		keygen2.setReferenceDraft(newReferenceDraft);
		keygenRepository.save(keygen2);

		DraftReturn draftReturn = new DraftReturn();
		draftReturn.setReference(draft.getReference());

		LOGGER.info("Fin methode creerDraft");
		return draftReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void ajouterReferenceExterne(String referenceDraft, ReferenceExterneInfo referenceExterneInfo) {
		LOGGER.info("Debut methode ajouterReferenceExterne");
		Draft draft = getDraftByReference(referenceDraft);

		draft.setReferenceExterne(referenceExterneInfo.getReferenceExterne());
		draftRepository.save(draft);

		LOGGER.info("Fin methode ajouterReferenceExterne");

	}
}
