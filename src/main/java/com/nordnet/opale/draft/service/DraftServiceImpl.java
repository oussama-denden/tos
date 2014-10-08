package com.nordnet.opale.draft.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nordnet.opale.business.AuteurInfo;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.DraftReturn;
import com.nordnet.opale.domain.Auteur;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.keygen.service.KeygenService;
import com.nordnet.opale.repository.DraftRepository;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.validator.DraftValidator;

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
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

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

		Auteur auteur = new Auteur(auteurInfo.getAuteur());

		Draft draft = new Draft();
		draft.setAuteur(auteur);

		draft.setReference(keygenService.getNextKey(Draft.class));

		draftRepository.save(draft);

		DraftReturn draftReturn = new DraftReturn();
		draftReturn.setReference(draft.getReference());

		LOGGER.info("Fin methode creerDraft");
		return draftReturn;
	}

	@Override
	public String ajouterLigne(String refDraft, DraftLigneInfo draftLigneInfo) throws OpaleException {

		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);
		Auteur auteur = new Auteur(draftLigneInfo.getAuteur());
		DraftLigne draftLigne = new DraftLigne(draftLigneInfo.getOffre());
		draftLigne.setAuteur(auteur);
		draftLigne.setReference(keygenService.getNextKey(DraftLigne.class));
		draftLigne.setDateCreation(PropertiesUtil.getInstance().getDateDuJour().toDate());
		draft.addLigne(draftLigne);

		draftRepository.save(draft);

		return draftLigne.getReference();
	}

	public void modifierLigne(String refDraft, String refLigne) throws OpaleException {
		Draft draft = draftRepository.findByReference(refDraft);
		DraftValidator.isExistDraft(draft, refDraft);

	}

	/**
	 * {@inheritDoc}
	 */
	public void annulerDraft(String refDraft) throws OpaleException {
		LOGGER.info("Entrer methode annulerDraft");

		Draft draft = draftRepository.findByReference(refDraft);

		DraftValidator.isExistDraft(draft, refDraft);

		draft.setDateAnnulation(PropertiesUtil.getInstance().getDateDuJour().toDate());

		draftRepository.save(draft);

		LOGGER.info("Fin methode annulerDraft");
	}
}
