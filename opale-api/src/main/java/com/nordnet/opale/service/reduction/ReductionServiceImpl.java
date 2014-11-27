package com.nordnet.opale.service.reduction;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nordnet.opale.business.ReductionInfo;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.draft.DraftLigneDetailRepository;
import com.nordnet.opale.repository.draft.DraftLigneRepository;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.keygen.KeygenService;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.validator.ReductionValidator;

/**
 * classe qui implemente {@link ReductionService}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Component("reductionService")
public class ReductionServiceImpl implements ReductionService {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(ReductionServiceImpl.class);

	/**
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private ReductionRepository reductionRepository;

	/**
	 * {@link KeygenService}.
	 */
	@Autowired
	private KeygenService keygenService;

	/**
	 * {@link DraftLigneRepository}.
	 */
	@Autowired
	private DraftLigneRepository draftLigneRepository;

	/**
	 * {@link DraftLigneDetailRepository}.
	 */
	@Autowired
	private DraftLigneDetailRepository draftLigneDetailRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReduction(String refDraft, ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReduction ");

		Reduction reductionDraft = reductionRepository.findReductionDraft(refDraft);
		ReductionValidator.checkReductionDraftExist(refDraft, reductionDraft);
		ReductionValidator.chekReductionValide(reductionInfo, Constants.DRAFT, null);

		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class));
		reduction.setReferenceDraft(refDraft);
		reductionRepository.save(reduction);
		return reduction.getReference();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionLigne(String refDraft, String refLigne, ReductionInfo reductionInfo)
			throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionLigne ");

		Reduction reductionLigne = reductionRepository.findReductionLigneSanFrais(refDraft, refLigne);
		ReductionValidator.checkReductionDraftLigneExist(refDraft, refLigne, reductionLigne);
		DraftLigne draftLigne = draftLigneRepository.findByReference(refLigne);
		ReductionValidator.chekReductionValide(reductionInfo, Constants.LIGNE, draftLigne);

		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigne(refLigne);
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionFraisLigneDetaille(String refDraft, String refLigne,
			DraftLigneDetail draftLigneDetail, String refFrais, ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionFrais ");

		Reduction reductionLigneDetailFrais =
				reductionRepository.findReductionLigneDetailleFrais(refDraft, refLigne,
						draftLigneDetail.getReferenceChoix(), refFrais, draftLigneDetail.getReferenceTarif());
		ReductionValidator.checkReductionDraftLigneDetailFraisExist(refDraft, refLigne,
				draftLigneDetail.getReferenceChoix(), refFrais, reductionLigneDetailFrais);
		ReductionValidator.chekReductionValide(reductionInfo, Constants.PRODUIT, null);

		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigne(refLigne);
		reduction.setReferenceTarif(draftLigneDetail.getReferenceTarif());
		reduction.setReferenceFrais(refFrais);
		reduction.setReferenceLigneDetail(draftLigneDetail.getReferenceChoix());
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionDetailLigne(DraftLigneDetail draftLigneDetail, String refDraft, String refLigne,
			ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionDetailLigne ");

		Reduction reductionLigneDetail =
				reductionRepository.findReductionLigneDetailleSansFrais(refDraft, refLigne,
						draftLigneDetail.getReferenceChoix());
		ReductionValidator.checkReductionDraftLigneDetailExist(refDraft, refLigne,
				draftLigneDetail.getReferenceChoix(), reductionLigneDetail);
		ReductionValidator.chekReductionValide(reductionInfo, Constants.PRODUIT, draftLigneDetail);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class));
		reduction.setReferenceLigne(refLigne);
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigneDetail(draftLigneDetail.getReferenceChoix());
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionFraisLigne(String refDraft, DraftLigne draftLigne, String refFrais,
			ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionFraisLigne ");

		Reduction reductionLigneFrais =
				reductionRepository.findReductionLigneFrais(refDraft, draftLigne.getReference(), refFrais,
						draftLigne.getReferenceTarif());
		ReductionValidator.checkReductionDraftLigneFraisExist(refDraft, draftLigne.getReference(), refFrais,
				reductionLigneFrais);
		ReductionValidator.chekReductionValide(reductionInfo, Constants.FRAIS, null);

		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigne(draftLigne.getReference());
		reduction.setReferenceFrais(refFrais);
		reduction.setReferenceTarif(draftLigne.getReferenceTarif());
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void supprimer(String refReduction) throws OpaleException {
		Reduction reduction = reductionRepository.findByReference(refReduction);
		ReductionValidator.isExiste(reduction, refReduction);
		reductionRepository.delete(reduction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionDraft(String referenceDraft) {
		return reductionRepository.findReductionDraft(referenceDraft);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionLigneDraft(String referenceDraft, String referenceLigne) {
		return reductionRepository.findReductionLigne(referenceDraft, referenceLigne);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Reduction> findReductionDetailLigneDraft(String referenceDraft, String referenceLigne,
			String referenceLigneDetail) {
		return reductionRepository.findReductionLigneDetaille(referenceDraft, referenceLigne, referenceLigneDetail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Reduction reduction) {
		reductionRepository.save(reduction);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionDetailLigneDraftFrais(String referenceDraft, String refLigne,
			String referenceLigneDetail, String referenceTarif, String referenceFrais) {
		return reductionRepository.findReductionLigneDetailleFrais(referenceDraft, refLigne, referenceLigneDetail,
				referenceFrais, referenceTarif);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionlLigneDraftFrais(String referenceDraft, String referenceLigne, String referenceTarif,
			String referenceFrais) {
		return reductionRepository.findReductionLigneFrais(referenceDraft, referenceLigne, referenceFrais,
				referenceTarif);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionLigneDraftSansFrais(String referenceDraft, String referenceLigne) {
		return reductionRepository.findReductionLigneSanFrais(referenceDraft, referenceLigne);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Reduction findReductionDetailLigneDraftSansFrais(String referenceDraft, String referenceLigne,
			String referenceLigneDetail) {
		return reductionRepository.findReductionLigneDetailleSansFrais(referenceDraft, referenceLigne,
				referenceLigneDetail);
	}

	@Override
	public String ajouterReductionECParent(String refDraft, String refLigne, String refTarif,
			ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionECParent ");

		Reduction reductionLigne = reductionRepository.findReductionECParent(refDraft, refLigne, refTarif);
		ReductionValidator.checkReductionDraftLigneExist(refDraft, refLigne, reductionLigne);
		DraftLigne draftLigne = draftLigneRepository.findByReference(refLigne);
		ReductionValidator.chekReductionValide(reductionInfo, Constants.ECPARENT, draftLigne);

		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class));
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigne(refLigne);
		reduction.setReferenceTarif(refTarif);
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

}
