package com.nordnet.opale.calcule;

import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.business.TransformationInfo;
import com.nordnet.opale.business.catalogue.Choice;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.business.catalogue.OffreCatalogue;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.validator.DraftValidator;
import com.nordnet.opale.vat.client.VatClient;

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
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private final ReductionRepository reductionRepository;

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
			DraftLigne draftLigne, Draft draft, ReductionRepository reductionRepository) {
		this.draftLigneDetail = draftLigneDetail;
		this.transformationInfo = transformationInfo;
		this.draftLigne = draftLigne;
		this.draft = draft;
		this.reductionRepository = reductionRepository;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (draft == null || transformationInfo == null || reductionRepository == null || draftLigne == null
				|| draftLigneDetail == null) {
			return null;
		} else {

			DetailCout detailCoutTarif = null;
			String segmentTVA = null;
			Plan reduit = null;
			if (transformationInfo.getClientInfo() != null
					&& transformationInfo.getClientInfo().getFacturation() != null) {
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

				double tva = VatClient.getValeurTVA(tarif.getTva(), segmentTVA);
				CoutTarif coutTarif =
						new CoutTarif(tarif, segmentTVA, draftLigneDetail, draftLigne, draft, false, true,
								reductionRepository);
				detailCoutTarif = (DetailCout) coutTarif.getCout();
				Reduction reduction =
						reductionRepository.findReductionLigneDetailleSansFrais(draft.getReference(),
								draftLigne.getReference(), draftLigneDetail.getReferenceChoix());
				double tarifHT = detailCoutTarif.getCoutRecurrent().getNormal().getTarifHT();
				double tarifTTC = detailCoutTarif.getCoutRecurrent().getNormal().getTarifTTC();

				calculerReductionLigneDetail(reduction, detailCoutTarif.getCoutComptantTTC(), tarifTTC, tva,
						detailCoutTarif);
				detailCoutTarif.setReductionHT(reductionHT);
				detailCoutTarif.setReductionTTC(reductionTTC);
				reduit =
						new Plan((tarifHT - reductionRecurrentHT) > 0 ? tarifHT - reductionRecurrentHT : 0,
								(tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0);
				detailCoutTarif.getCoutRecurrent().setReduit(reduit);

				coutRecurentReduitTTC = (tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0;
				coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitTTC, tva);

				coutComptantReduitTTC =
						(detailCoutTarif.getCoutComptantTTC() - reductionComptantTTC) > 0 ? detailCoutTarif
								.getCoutComptantTTC() - reductionComptantTTC : 0;
				coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

			}

			return detailCoutTarif;
		}

	}

	/**
	 * 
	 * @param reductionLigneDetail
	 *            reduction associee au detaille.
	 * @param coutComptant
	 *            cout comptant du detail.
	 * @param coutRecurrent
	 *            cout recurrent du
	 * @param tva
	 * @param detailCoutTarif
	 */
	private void calculerReductionLigneDetail(Reduction reductionLigneDetail, double coutComptant,
			double coutRecurrent, double tva, DetailCout detailCoutTarif) {

		double reduction = 0d;

		if (reductionLigneDetail != null && reductionLigneDetail.isreductionRecurrente()) {

			reduction = ReductionUtil.calculeReductionRecurrent(coutRecurrent, reductionLigneDetail);

			reductionTTC += reduction + detailCoutTarif.getReductionTTC();
			reductionHT = ReductionUtil.caculerReductionHT(reduction, tva) + detailCoutTarif.getReductionHT();

			reductionRecurrentTTC += reduction;
			reductionRecurrentHT = ReductionUtil.caculerReductionHT(reductionRecurrentTTC, tva);

		}

		if (reductionLigneDetail != null && reductionLigneDetail.isreductionComptant()) {
			reduction =
					ReductionUtil.calculeReductionComptant(coutComptant - reductionComptantTTC, reductionLigneDetail);

			reductionTTC += reduction + detailCoutTarif.getReductionTTC();
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

			reductionComptantTTC += reduction;
			reductionComptantHT = ReductionUtil.caculerReductionHT(reductionComptantTTC, tva);
		}

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public double getCoutRecurrentHT() throws OpaleException {
		if (getCout() == null && ((DetailCout) this.getCout()).getCoutRecurrent() == null) {
			return 0;
		} else
			return ((DetailCout) this.getCout()).getCoutRecurrent().getNormal().getTarifHT();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public double getCoutRecurrentTTC() throws OpaleException {
		if (getCout() == null && ((DetailCout) this.getCout()).getCoutRecurrent() == null) {
			return 0;
		} else
			return ((DetailCout) this.getCout()).getCoutRecurrent().getNormal().getTarifTTC();
	}

}
