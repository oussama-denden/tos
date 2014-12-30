package com.nordnet.opale.calcule;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.Choice;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.validator.DraftValidator;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutLigneDetailDraft extends CalculeCout {

	/**
	 * detaille du ligne draft.
	 */
	private final DraftLigneDetail draftLigneDetail;

	/**
	 * calcule info.
	 */
	private final TransformationInfo transformationInfo;

	/**
	 * ligne du draft.
	 */
	private final DraftLigne draftLigne;

	/**
	 * draft
	 */
	private final Draft draft;

	/**
	 * constructeur avec param.
	 * 
	 * @param draftLigneDetail
	 *            {@link DraftLigneDetail}
	 * 
	 * @param transformationInfo
	 *            {@link TransformationInfo}
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}
	 * 
	 * @param draft
	 *            {@link Draft}
	 */
	public CoutLigneDetailDraft(DraftLigneDetail draftLigneDetail, TransformationInfo transformationInfo,
			DraftLigne draftLigne, Draft draft) {
		this.draftLigneDetail = draftLigneDetail;
		this.transformationInfo = transformationInfo;
		this.draftLigne = draftLigne;
		this.draft = draft;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		DetailCout detailCoutTarif = null;
		String segmentTVA = null;
		if (transformationInfo.getClientInfo() != null && transformationInfo.getClientInfo().getFacturation() != null) {
			DraftValidator.isIndicatifTVAValide(transformationInfo.getClientInfo().getFacturation());
			segmentTVA = transformationInfo.getClientInfo().getFacturation().getTva();
		} else if (draft.getClientAFacturer() != null) {
			segmentTVA = draft.getClientAFacturer().getTva();
		}
		DetailCatalogue detailCatalogue = null;
		Choice choice = null;
		Tarif tarif = null;
		TrameCatalogue trameCatalogue = transformationInfo.getTrameCatalogue();
		OffreCatalogue offreCatalogue = trameCatalogue.getOffreMap().get(draftLigne.getReferenceOffre());
		detailCatalogue = offreCatalogue.getDetailsMap().get(draftLigneDetail.getReferenceSelection());
		choice = detailCatalogue.getChoice(draftLigneDetail.getReferenceChoix());
		tarif = choice.getTarifsMap().get(draftLigneDetail.getReferenceTarif());
		if (tarif != null) {
			CoutTarif coutTarif = new CoutTarif(tarif, segmentTVA);
			detailCoutTarif = (DetailCout) coutTarif.getCout();
			// reductionHT +=
			// calculerReductionLignetETDetail(draft.getReference(), draftLigne.getReference(),
			// draftLigneDetail.getReferenceChoix(), detailCoutTarif.getCoutTotal(), reduction,
			// detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan()
			// : Constants.ZERO, tarif, false);
		}
		return detailCoutTarif;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoutTTC() throws OpaleException {
		return getCout().getCoutComptantTTC();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoutHT() throws OpaleException {
		return getCout().getCoutComptantHT();
	}

}
