package com.nordnet.opale.draft.service;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.draft.business.AuteurInfo;
import com.nordnet.opale.draft.business.DraftReturn;
import com.nordnet.opale.draft.domain.Adresse;
import com.nordnet.opale.draft.domain.Auteur;
import com.nordnet.opale.draft.domain.Draft;
import com.nordnet.opale.draft.repository.DraftRepository;

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
	 * {@link AvenantRepository}.
	 */
	@Autowired
	private DraftRepository draftRepository;

	/**
	 * {@inheritDoc}
	 */
	public Draft getDraftByReference(String reference) {

		return draftRepository.findByReference(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public void supprimerDraft(String reference) {

		Draft draft = getDraftByReference(reference);

		draftRepository.delete(draft);
	}

	/**
	 * {@inheritDoc}
	 */
	public DraftReturn creerDraft(AuteurInfo auteurInfo) {

		LOGGER.info("Enter methode creerDraft");
		Auteur auteur = new Auteur();
		auteur.setCanal(auteurInfo.getAuteur().getCanal());
		auteur.setCode(auteurInfo.getAuteur().getCode());
		auteur.setIp(auteurInfo.getAuteur().getIp().getIp());
		auteur.setQui(auteurInfo.getAuteur().getQui());
		auteur.setTimestamp(auteurInfo.getAuteur().getIp().getTs());
		Adresse adresse = new Adresse();
		adresse.setCodeAdresse("codeAdresse");

		Draft draft = new Draft();
		draft.setAdresse(adresse);
		draft.setAuteur(auteur);
		draft.setCodeClient("codeClient");
		draft.setReference(UUID.randomUUID().toString().substring(0, 8));

		draftRepository.save(draft);

		DraftReturn draftReturn = new DraftReturn();
		draftReturn.setReference(draft.getReference());
		LOGGER.info("Fin methode creerDraft");
		return draftReturn;
	}

}
