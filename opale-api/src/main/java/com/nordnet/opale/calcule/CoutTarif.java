package com.nordnet.opale.calcule;

import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.domain.commande.Frais;
import com.nordnet.opale.domain.commande.Tarif;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.vat.client.VatClient;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutTarif extends CalculeCout {

	/**
	 * tarif.
	 */
	private Tarif tarif;

	/**
	 * segement TVA.
	 */
	private String segmentTVA;

	/**
	 * detaille du ligne draft.
	 */
	private String referenceDraftLigneDetail;

	/**
	 * ligne du draft.
	 */
	private String referenceDraftLigne;

	/**
	 * draft
	 */
	private String referenceDraft;

	/**
	 * indique si on va calculer le tarif d'une ligne.
	 */
	private boolean isLigne;

	/**
	 * indique si on va calculer le tarif d'un detaille ligne.
	 * 
	 */
	private boolean isDetail;

	/**
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private ReductionService reductionService;

	/**
	 * constructeur par defaut.
	 */

	public CoutTarif(Tarif tarif, String segmentTVA, String referenceDraftLigneDetail, String referenceDraftLigne,
			String referenceDraft, boolean isLigne, boolean isDetail, ReductionService reductionService) {
		this.tarif = tarif;
		this.segmentTVA = segmentTVA;
		this.referenceDraftLigneDetail = referenceDraftLigneDetail;
		this.referenceDraftLigne = referenceDraftLigne;
		this.referenceDraft = referenceDraft;
		this.isLigne = isLigne;
		this.isDetail = isDetail;
		this.reductionService = reductionService;
	}

	public CoutTarif() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (referenceDraft == null || tarif == null || reductionService == null
				|| (isLigne && referenceDraftLigne == null) || (isDetail && referenceDraftLigneDetail == null)) {
			return null;
		} else {

			DetailCout detailCout = new DetailCout();
			double coutComptantHT = 0d;
			double coutComptantTTC = 0d;
			double tva = VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA);

			if (tarif.isRecurrent()) {
				Plan normal =
						new Plan(tarif.getPrix(), VatClient.appliquerTVA(tarif.getPrix(), tarif.getTypeTVA(),
								segmentTVA));
				detailCout.setCoutRecurrent(new CoutRecurrent(tarif.getFrequence(), normal, null));
				// detailCout.setPlan(new Plan(tarif.getFrequence(), tarif.getPrix(),
				// VatClient.appliquerTVA(tarif.getPrix(),
				// tarif.getTva(), segmentTVA)));
			}

			else

			{

				coutComptantHT += tarif.getPrix();
			}

			for (Frais frais : tarif.getFrais()) {

				if (frais.getTypeFrais() == TypeFrais.CREATION) {

					coutComptantHT += frais.getMontant();

					// application du reduction.
					calculerReductionTarif(frais, tva);
				}
			}

			coutComptantTTC = VatClient.appliquerTVA(coutComptantHT, tarif.getTypeTVA(), segmentTVA);
			coutComptantReduitTTC = coutComptantTTC - reductionTTC;
			coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);
			detailCout.setCoutComptantHT(coutComptantHT);
			detailCout.setCoutComptantTTC(coutComptantTTC);
			detailCout.setReductionHT(reductionHT);
			detailCout.setReductionTTC(reductionTTC);

			return detailCout;
		}
	}

	/**
	 * calculer la reduction associee a un tarif.
	 * 
	 * @param frais
	 *            frais creation du tarif.
	 * @param tva
	 *            valeur de tva.
	 * @throws OpaleException
	 */
	private void calculerReductionTarif(Frais frais, double tva) throws OpaleException {
		Reduction reduction = null;
		if (isLigne) {
			reduction =
					reductionService.findReductionlLigneDraftFrais(referenceDraft, referenceDraftLigne,
							tarif.getReference(), frais.getReference());
		} else if (isDetail) {
			reduction =
					reductionService.findReductionDetailLigneDraftFrais(referenceDraft, referenceDraftLigneDetail,
							referenceDraftLigne, frais.getReference(), tarif.getReference());

		}
		reductionTTC =
				ReductionUtil.calculeReductionComptant(
						VatClient.appliquerTVA(frais.getMontant(), tarif.getTypeTVA(), segmentTVA), reduction);
		reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

		reductionComptantHT = reductionHT;
		reductionComptantTTC = reductionTTC;
	}

}
