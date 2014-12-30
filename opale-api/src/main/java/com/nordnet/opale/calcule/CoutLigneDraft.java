package com.nordnet.opale.calcule;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
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
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.validator.DraftValidator;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutLigneDraft extends CalculeCout {

	/**
	 * le draft.
	 */
	private Draft draft;

	/**
	 * calcule info.
	 */
	private TransformationInfo transformationInfo;

	/**
	 * la ligne du draft.
	 */
	private DraftLigne draftLigne;

	/**
	 * constructeur par defaut.
	 */
	public CoutLigneDraft() {

	}

	/**
	 * constructeur avec param.
	 * 
	 * @param draft
	 *            {@link #draft}
	 * 
	 * @param transformationInfo
	 *            {@link #draftLigne}
	 * 
	 * @param draftLigne
	 *            {@link #transformationInfo}
	 */
	public CoutLigneDraft(Draft draft, TransformationInfo transformationInfo, DraftLigne draftLigne) {
		this.draft = draft;
		this.transformationInfo = transformationInfo;
		this.draftLigne = draftLigne;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {
		String segmentTVA = null;
		if (transformationInfo.getClientInfo() != null && transformationInfo.getClientInfo().getFacturation() != null) {
			DraftValidator.isIndicatifTVAValide(transformationInfo.getClientInfo().getFacturation());
			segmentTVA = transformationInfo.getClientInfo().getFacturation().getTva();
		} else if (draft.getClientAFacturer() != null) {
			segmentTVA = draft.getClientAFacturer().getTva();
		}
		DetailCout detailCout = new DetailCout();
		double coutComptantHT = 0d;
		double coutComptantTTC = 0d;
		double reductionHT = 0d;
		double reductionTTC = 0d;
		detailCout.setNumero(draftLigne.getReference());
		detailCout.setLabel(draftLigne.getReferenceOffre());
		double tarifHT = 0d;
		double tarifTTC = 0d;
		Integer frequence = null;
		Tarif tarif = null;
		DetailCatalogue detailCatalogue = null;
		Choice choice = null;
		TrameCatalogue trameCatalogue = transformationInfo.getTrameCatalogue();
		OffreCatalogue offreCatalogue = trameCatalogue.getOffreMap().get(draftLigne.getReferenceOffre());
		for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
			CoutLigneDetailDraft coutLigneDetailDraft =
					new CoutLigneDetailDraft(draftLigneDetail, transformationInfo, draftLigne, draft);
			if (tarif != null) {
				DetailCout detailCoutTarif = (DetailCout) coutLigneDetailDraft.getCout();
				coutComptantHT += detailCout.getCoutComptantHT();
				coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
				tarifHT +=
						detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
								.getNormal().getTarifHT() : 0d;
				tarifTTC +=
						detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
								.getNormal().getTarifTTC() : 0d;
				frequence = tarif.getFrequence();
				// reductionHT +=
				// calculerReductionLignetETDetail(draft.getReference(), draftLigne.getReference(),
				// draftLigneDetail.getReferenceChoix(), detailCoutTarif.getCoutTotal(), reduction,
				// detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan()
				// : Constants.ZERO, tarif, false);
			}
		}

		tarif = offreCatalogue.getTarifsMap().get(draftLigne.getReferenceTarif());
		if (tarif != null) {
			CoutTarif coutTarif = new CoutTarif(tarif, segmentTVA);
			DetailCout detailCoutTarif = (DetailCout) coutTarif.getCout();
			coutComptantHT += detailCoutTarif.getCoutComptantHT();
			coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
			tarifHT +=
					detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
							.getNormal().getTarifHT() : 0d;
			tarifTTC +=
					detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
							.getNormal().getTarifTTC() : 0d;
			frequence = tarif.getFrequence();
			// reduction +=
			// calculerReductionLignetETDetail(draft.getReference(), draftLigne.getReference(),
			// draftLigne.getReference(), coutTotal, reduction, plan, tarif, true);
		}
		Plan normal = new Plan(tarifHT, tarifTTC);
		Plan reduit = new Plan(reductionTTC, reductionHT);
		detailCout.setCoutRecurrent(new CoutRecurrent(frequence, normal, reduit));
		detailCout.setCoutComptantHT(coutComptantHT);
		detailCout.setCoutComptantTTC(coutComptantTTC);
		detailCout.setReductionHT(reductionHT);
		return detailCout;
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
