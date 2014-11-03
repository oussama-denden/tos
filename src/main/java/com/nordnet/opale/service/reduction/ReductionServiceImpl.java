package com.nordnet.opale.service.reduction;

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
	 * {@link DraftLigneDetailRepository}
	 */
	@Autowired
	private DraftLigneDetailRepository draftLigneDetailRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReduction(String refDraft, ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReduction ");

		ReductionValidator.chekReductionValide(reductionInfo, Constants.DRAFT);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
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

		DraftLigne draftLigne = draftLigneRepository.findByReference(refLigne);
		ReductionValidator.chekReductionValide(reductionInfo, draftLigne);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceLigne(refLigne);
		reductionRepository.save(reduction);
		return reduction.getReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String ajouterReductionFraisLigneDetaille(String refDraft, DraftLigneDetail draftLigneDetail,
			String refFrais,
			ReductionInfo reductionInfo) throws OpaleException {

		LOGGER.info("Debut methode ajouterReductionFrais ");

		ReductionValidator.chekReductionValide(reductionInfo, Constants.PRODUIT);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceFrais(refFrais);
		reduction.setReferenceTarif(draftLigneDetail.getReferenceTarif());
		reduction.setReferenceLigneDetail(draftLigneDetail.getReference());
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

		ReductionValidator.chekReductionValide(reductionInfo, draftLigneDetail);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceLigne(refLigne);
		reduction.setReferenceDraft(refDraft);
		reduction.setReferenceLigneDetail(draftLigneDetail.getReference());
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

		ReductionValidator.chekReductionValide(reductionInfo, Constants.FRAIS);
		Reduction reduction = reductionInfo.toDomain();
		reduction.setReference(keygenService.getNextKey(Reduction.class, null));
		reduction.setReferenceFrais(refFrais);
		reduction.setReferenceTarif(draftLigne.getReferenceTarif());
		reduction.setReferenceLigne(draftLigne.getReference());
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

}
