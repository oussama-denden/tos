package com.nordnet.opale.finder.cout;

import com.nordnet.opale.finder.business.Cout;
import com.nordnet.opale.finder.business.CoutRecurrent;
import com.nordnet.opale.finder.business.DetailCommandeLigne;
import com.nordnet.opale.finder.business.DetailCout;
import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.business.Tarif;
import com.nordnet.opale.finder.dao.ReductionDao;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.util.ReductionUtil;
import com.nordnet.opale.vat.client.VatClient;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutLigneDetailCommande extends CalculeCout {

	/**
	 * {@link CommandeLigneDetail}.
	 */
	private DetailCommandeLigne commandeLigneDetail;

	/**
	 * refrence commande.
	 */
	private String referenceCommande;

	/**
	 * reference commande ligne.
	 */
	private String referenceCommandeLigne;

	/**
	 * {@link Tarif}.
	 */
	private Tarif tarif;
	/**
	 * {@link #segmentTVA}.
	 */
	private String segmentTVA;

	/**
	 * 
	 */
	private ReductionDao reductionDao;

	/**
	 * constructeur par defaut.
	 */
	public CoutLigneDetailCommande() {

	}

	/**
	 * constructeur avec parametre.
	 * 
	 * @param commandeLigneDetail
	 *            {@link CommandeLigneDetail}
	 * @param referenceCommande
	 *            reference commande
	 * @param referenceCommandeLigne
	 *            reference commande ligne
	 * @param tarif
	 *            {@link Tarif}
	 * @param segmentTVA
	 *            segment TVA
	 * @param reductionDao
	 *            {@link reductionDao}
	 */
	public CoutLigneDetailCommande(DetailCommandeLigne commandeLigneDetail, String referenceCommande,
			String referenceCommandeLigne, Tarif tarif, String segmentTVA, ReductionDao reductionDao) {
		this.commandeLigneDetail = commandeLigneDetail;
		this.referenceCommande = referenceCommande;
		this.referenceCommandeLigne = referenceCommandeLigne;
		this.tarif = tarif;
		this.segmentTVA = segmentTVA;
		this.reductionDao = reductionDao;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		double tva = VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA);

		CoutTarif coutTarifCommande =
				new CoutTarif(tarif, segmentTVA, commandeLigneDetail.getReference(), referenceCommandeLigne,
						referenceCommande, false, true, reductionDao);

		DetailCout detailCout = (DetailCout) coutTarifCommande.getCout();
		double tarifTTC = detailCout.getCoutRecurrent().getPrix();

		Reduction reduction =
				reductionDao.findReductionLigneDetailleSansFrais(referenceCommande, referenceCommandeLigne,
						commandeLigneDetail.getReference());

		calculerReductionLigneDetail(reduction, detailCout.getCoutComptantTTC(), tarifTTC, tva, detailCout);

		// detailCout.setReductionTTC(reductionTTC);

		coutRecurentReduitTTC = (tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0;

		coutComptantReduitTTC =
				(detailCout.getCoutComptantTTC() - reductionComptantTTC) > 0 ? detailCout.getCoutComptantTTC()
						- reductionComptantTTC : 0;

		detailCout.setCoutComptantTTC(coutComptantReduitTTC);
		CoutRecurrent coutRecurrent = new CoutRecurrent();
		coutRecurrent.setEngagement(tarif.getEngagement());
		coutRecurrent.setFrais(tarif.getFrais());
		coutRecurrent.setFrequence(tarif.getFrequence());
		coutRecurrent.setPrix(coutRecurentReduitTTC);
		coutRecurrent.setTVA(VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA));
		detailCout.setCoutRecurrent(coutRecurrent);

		return detailCout;
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
	 *            {@link TVA}
	 * @param detailCoutTarif
	 *            {@link DetailCout}
	 */
	private void calculerReductionLigneDetail(Reduction reductionLigneDetail, double coutComptant,
			double coutRecurrent, double tva, DetailCout detailCoutTarif) {

		double reduction = 0d;

		if (reductionLigneDetail != null && reductionLigneDetail.isReductionRecurrente()) {

			reduction = ReductionUtil.calculeReductionRecurrent(coutRecurrent, reductionLigneDetail);

			// reductionTTC += reduction + detailCoutTarif.getReductionTTC();

			reductionRecurrentTTC += reduction;

		}

		if (reductionLigneDetail != null && reductionLigneDetail.isReductionComptant()) {
			reduction =
					ReductionUtil.calculeReductionComptant(coutComptant - reductionComptantTTC, reductionLigneDetail);

			// reductionTTC += reduction + detailCoutTarif.getReductionTTC();

			reductionComptantTTC += reduction;
		}

	}

}
