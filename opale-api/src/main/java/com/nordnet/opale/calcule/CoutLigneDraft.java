package com.nordnet.opale.calcule;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.validator.DraftValidator;
import com.nordnet.opale.vat.client.VatClient;

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
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private ReductionService reductionService;

	/**
	 * valeur du tva .
	 */
	private double tva;

	/**
	 * constructeur par defaut.
	 */
	public CoutLigneDraft() {

	}

	/**
	 * constructeur avec parametres.
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
	public CoutLigneDraft(Draft draft, TransformationInfo transformationInfo, DraftLigne draftLigne,
			ReductionService reductionService) {
		this.draft = draft;
		this.transformationInfo = transformationInfo;
		this.draftLigne = draftLigne;
		this.reductionService = reductionService;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (draft == null || transformationInfo == null || reductionService == null || draftLigne == null) {
			return null;
		} else {
			String segmentTVA = null;
			DetailCout detailCout = new DetailCout();
			double coutComptantHT = 0d;
			double coutComptantTTC = 0d;
			double tarifHT = 0d;
			double tarifTTC = 0d;
			Integer frequence = null;
			Choice choice = null;
			Tarif tarif = null;
			DetailCatalogue detailCatalogue = null;
			String label = draftLigne.getReferenceOffre();

			if (transformationInfo.getClientInfo() != null
					&& transformationInfo.getClientInfo().getFacturation() != null) {
				DraftValidator.isIndicatifTVAValide(transformationInfo.getClientInfo().getFacturation());
				segmentTVA = transformationInfo.getClientInfo().getFacturation().getTva();
			} else if (draft.getClientAFacturer() != null) {
				segmentTVA = draft.getClientAFacturer().getTva();
			}
			detailCout.setNumero(draftLigne.getReference());
			detailCout.setLabel(draftLigne.getReferenceOffre());
			TrameCatalogue trameCatalogue = transformationInfo.getTrameCatalogue();
			OffreCatalogue offreCatalogue = trameCatalogue.getOffreMap().get(draftLigne.getReferenceOffre());

			for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
				detailCatalogue = offreCatalogue.getDetailsMap().get(draftLigneDetail.getReferenceSelection());
				choice = detailCatalogue.getChoice(draftLigneDetail.getReferenceChoix());
				tarif = choice.getTarifsMap().get(draftLigneDetail.getReferenceTarif());

				if (tarif != null) {

					CoutLigneDetailDraft coutLigneDetailDraft =
							new CoutLigneDetailDraft(draftLigneDetail, transformationInfo, draftLigne, draft,
									reductionService);

					DetailCout detailCoutTarif = (DetailCout) coutLigneDetailDraft.getCout();
					coutComptantHT += detailCoutTarif.getCoutComptantHT();
					coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
					tarifHT +=
							detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
									.getTarifHT() : 0d;
					tarifTTC +=
							detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
									.getTarifTTC() : 0d;
					frequence = tarif.getFrequence();

					reductionHT += detailCoutTarif.getReductionHT();
					reductionTTC += detailCoutTarif.getReductionTTC();

					reductionRecurrentHT += coutLigneDetailDraft.getReductionRecurrentHT();
					reductionRecurrentTTC += coutLigneDetailDraft.getReductionRecurrentTTC();

					reductionComptantHT += coutLigneDetailDraft.getReductionComptantHT();
					reductionComptantTTC += coutLigneDetailDraft.getReductionComptantTTC();
				}
			}

			tarif = offreCatalogue.getTarifsMap().get(draftLigne.getReferenceTarif());
			if (tarif != null) {
				tva = VatClient.getValeurTVA(tarif.getTva(), segmentTVA);

				CoutTarif coutTarif =
						new CoutTarif(tarif.toTarifDomain(), segmentTVA, null, draftLigne.getReference(),
								draft.getReference(), true, false, reductionService);

				DetailCout detailCoutTarif = (DetailCout) coutTarif.getCout();
				coutComptantHT += detailCoutTarif.getCoutComptantHT();
				coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
				tarifHT +=
						detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
								.getTarifHT() : 0d;
				tarifTTC +=
						detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
								.getTarifTTC() : 0d;
				frequence = tarif.getFrequence();

				reductionRecurrentHT += coutTarif.getReductionRecurrentHT();
				reductionRecurrentTTC += coutTarif.getReductionRecurrentTTC();

				reductionComptantHT += coutTarif.getReductionComptantHT();
				reductionComptantTTC += coutTarif.getReductionComptantTTC();

				Reduction reductionECParent =
						reductionService.findReductionECParent(draft.getReference(), draftLigne.getReference(),
								tarif.getIdTarif());
				// calculer la reduction sur le tarif de ligne.
				calculerReductionECParent(reductionECParent, detailCoutTarif, tva);

			}

			// trouver les reduction liees aux lignes.
			Reduction reductionLigne =
					reductionService.findReductionLigneDraftSansFrais(draft.getReference(), draftLigne.getReference());

			// recuperer les reductions recurrentes liees au draft
			Reduction reductionDraft = reductionService.findReduction(draft.getReference());

			// calculer la reduction de ligne.
			calculerReductionLigne(reductionLigne, coutComptantTTC, tarifTTC, tva, false);

			// calculer la reduction recurrente du draft sur la ligne.
			calculerReductionLigne(reductionDraft, coutComptantTTC, tarifTTC, tva, true);

			Plan normal = new Plan(tarifHT, tarifTTC);
			Plan reduit =
					new Plan((tarifHT - reductionRecurrentHT) > 0 ? tarifHT - reductionRecurrentHT : 0,
							(tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0);
			detailCout.setCoutRecurrent(new CoutRecurrent(frequence, normal, reduit));
			detailCout.setCoutComptantHT(coutComptantHT);
			detailCout.setCoutComptantTTC(coutComptantTTC);
			detailCout.setReductionHT(reductionHT);
			detailCout.setReductionTTC(reductionTTC);
			detailCout.setTva(tva);
			detailCout.setLabel(label);

			coutRecurentReduitTTC = (tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0;
			coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitTTC, tva);

			coutComptantReduitTTC =
					(coutComptantTTC - reductionComptantTTC) > 0 ? coutComptantTTC - reductionComptantTTC : 0;
			coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

			return detailCout;
		}
	}

	/**
	 * calculer la reduction pour un tarif de l'offre.
	 * 
	 * @param reductionECParent
	 *            reduction associe au tarif de la ligne .
	 * 
	 * @param detailCoutTarif
	 *            detail cout du tarif du ligne.
	 * @param tva
	 *            valeur de tva.
	 */
	private void calculerReductionECParent(Reduction reductionECParent, DetailCout detailCoutTarif, double tva) {

		double tarifTTC =
				detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
						.getTarifTTC() : 0d;
		double reduction = 0d;

		reductionHT += detailCoutTarif.getReductionHT();
		reductionTTC += detailCoutTarif.getReductionTTC();

		if (reductionECParent != null && reductionECParent.isreductionRecurrente()) {
			reduction = ReductionUtil.calculeReductionRecurrent(tarifTTC - reductionRecurrentTTC, reductionECParent);

			reductionTTC += reduction;
			reductionHT = ReductionUtil.caculerReductionHT(reduction, tva);

			reductionRecurrentTTC += reduction;
			reductionRecurrentHT = ReductionUtil.caculerReductionHT(reduction, tva);

		}

		if (reductionECParent != null && reductionECParent.isreductionComptant()) {
			reduction =
					ReductionUtil.calculeReductionComptant(detailCoutTarif.getCoutComptantTTC() - reductionComptantTTC,
							reductionECParent);

			reductionTTC += reduction;
			reductionHT += ReductionUtil.caculerReductionHT(reduction, tva);

			reductionComptantTTC += reduction;
			reductionComptantHT = ReductionUtil.caculerReductionHT(reduction, tva);
		}

	}

	/**
	 * calculer reduction associe a une ligne.
	 * 
	 * @param reductionLigne
	 *            reduction pour unr ligne.
	 * @param coutComptant
	 *            cout comptant pour une ligne.
	 * @param coutRecurrent
	 *            cout recurrent pour unr ligne.
	 * @param tva
	 *            valeur de tva.
	 * @param isReductionDraft
	 *            indique si la reduction est associe a un draft.
	 */
	private void calculerReductionLigne(Reduction reductionLigne, double coutComptant, double coutRecurrent,
			double tva, boolean isReductionDraft) {

		double reduction = 0d;

		if (reductionLigne != null && reductionLigne.isreductionRecurrente() && !isReductionDraft) {

			reduction = ReductionUtil.calculeReductionRecurrent(coutRecurrent - reductionRecurrentTTC, reductionLigne);

			reductionTTC += reduction;
			reductionHT += ReductionUtil.caculerReductionHT(reductionTTC, tva);

			reductionRecurrentTTC += reduction;
			reductionRecurrentHT = ReductionUtil.caculerReductionHT(reductionRecurrentTTC, tva);

		}

		if (reductionLigne != null && reductionLigne.isreductionComptant() && !isReductionDraft) {
			reduction = ReductionUtil.calculeReductionComptant(coutComptant - reductionComptantTTC, reductionLigne);

			reductionTTC += reduction;
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

			reductionComptantTTC += reduction;
			reductionComptantHT = ReductionUtil.caculerReductionHT(reductionComptantTTC, tva);
		}

		if (reductionLigne != null && reductionLigne.isreductionRecurrente() && isReductionDraft) {

			reduction += ReductionUtil.calculeReductionRecurrent(coutRecurrent - reductionRecurrentTTC, reductionLigne);

			reductionTTC += reduction;
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

			reductionRecurrentTTC += reduction;
			reductionRecurrentHT = ReductionUtil.caculerReductionHT(reductionRecurrentTTC, tva);
		}

	}

	/**
	 * 
	 * @return {@link #tva}
	 */
	public double getTva() {
		return tva;
	}

	/**
	 * 
	 * @param tva
	 *            {@link #tva}
	 */
	public void setTva(double tva) {
		this.tva = tva;
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
