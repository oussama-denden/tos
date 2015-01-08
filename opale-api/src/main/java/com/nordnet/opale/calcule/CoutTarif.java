package com.nordnet.opale.calcule;

import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
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
	private DraftLigneDetail draftLigneDetail;

	/**
	 * ligne du draft.
	 */
	private DraftLigne draftLigne;

	/**
	 * draft
	 */
	private Draft draft;

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
	private ReductionRepository reductionRepository;

	/**
	 * constructeur par defaut.
	 */

	public CoutTarif(Tarif tarif, String segmentTVA, DraftLigneDetail draftLigneDetail, DraftLigne draftLigne,
			Draft draft, boolean isLigne, boolean isDetail, ReductionRepository reductionRepository) {
		this.tarif = tarif;
		this.segmentTVA = segmentTVA;
		this.draftLigneDetail = draftLigneDetail;
		this.draftLigne = draftLigne;
		this.draft = draft;
		this.isLigne = isLigne;
		this.isDetail = isDetail;
		this.reductionRepository = reductionRepository;
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

		if (draft == null || tarif == null || reductionRepository == null || (isLigne && draftLigne == null)
				|| (isDetail && draftLigneDetail == null)) {
			return null;
		} else {

			DetailCout detailCout = new DetailCout();
			double coutComptantHT = 0d;
			double coutComptantTTC = 0d;
			double tva = VatClient.getValeurTVA(tarif.getTva(), segmentTVA);

			if (tarif.isRecurrent()) {
				Plan normal =
						new Plan(tarif.getPrix(), VatClient.appliquerTVA(tarif.getPrix(), tarif.getTva(), segmentTVA));
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

			coutComptantTTC = VatClient.appliquerTVA(coutComptantHT, tarif.getTva(), segmentTVA);
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
					reductionRepository.findReductionLigneFrais(draft.getReference(), draftLigne.getReference(),
							frais.getIdFrais(), tarif.getIdTarif());
		} else if (isDetail) {
			reduction =
					reductionRepository.findReductionLigneDetailleFrais(draft.getReference(),
							draftLigne.getReference(), draftLigneDetail.getReferenceChoix(), frais.getIdFrais(),
							tarif.getIdTarif());

		}
		reductionTTC =
				ReductionUtil.calculeReductionComptant(
						VatClient.appliquerTVA(frais.getMontant(), tarif.getTva(), segmentTVA), reduction);
		reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

		reductionComptantHT = reductionHT;
		reductionComptantTTC = reductionTTC;
	}

}
