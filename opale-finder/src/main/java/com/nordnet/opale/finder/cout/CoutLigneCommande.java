package com.nordnet.opale.finder.cout;

import com.nordnet.opale.finder.business.CommandeLigne;
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
public class CoutLigneCommande extends CalculeCout {

	/**
	 * {@link CommandeLigne}.
	 */
	private CommandeLigne commandeLigne;

	/**
	 * {@link #segmentTVA}.
	 */
	private String segmentTVA;

	/**
	 * reference du commande parent.
	 */
	private String referenceCommande;

	/**
	 * {@link reductionDao}.
	 */
	private ReductionDao reductionDao;

	/**
	 * valeur de tva.
	 */
	private double tva;

	/**
	 * constructeur par defaut.
	 */
	public CoutLigneCommande() {

	}

	/**
	 * constructeur avec parametres.
	 * 
	 * @param referenceCommande
	 *            reference commande
	 * @param commandeLigne
	 *            {@link CommandeLigne}
	 * @param segmentTVA
	 *            {@link #segmentTVA}
	 * @param reductionDao
	 *            {@link reductionDao}
	 */
	public CoutLigneCommande(String referenceCommande, CommandeLigne commandeLigne, String segmentTVA,
			ReductionDao reductionDao) {
		this.referenceCommande = referenceCommande;
		this.commandeLigne = commandeLigne;
		this.segmentTVA = segmentTVA;
		this.reductionDao = reductionDao;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("null")
	@Override
	public Cout getCout() throws OpaleException {

		DetailCout detailCout = new DetailCout();
		double tarifTTC = 0d;
		Tarif tarif = null;

		if (commandeLigne.getDetailCommandeLignes() != null) {
			for (DetailCommandeLigne commandeLigneDetail : commandeLigne.getDetailCommandeLignes()) {
				tarif = commandeLigneDetail.getTarif();

				if (tarif != null) {
					CoutLigneDetailCommande coutLigneDetailCommande =
							new CoutLigneDetailCommande(commandeLigneDetail, referenceCommande,
									commandeLigne.getReference(), tarif, segmentTVA, reductionDao);

					DetailCout detailCoutTarif = (DetailCout) coutLigneDetailCommande.getCout();
					coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
					tarifTTC += detailCoutTarif.getCoutRecurrent().getPrix();

				}
			}
		}

		tarif = commandeLigne.getTarif();
		if (tarif != null) {

			tva = VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA);

			CoutTarif coutTarifCommande =
					new CoutTarif(tarif, segmentTVA, null, commandeLigne.getReference(), referenceCommande, true,
							false, reductionDao);

			DetailCout detailCoutTarif = (DetailCout) coutTarifCommande.getCout();
			coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
			tarifTTC += detailCoutTarif.getCoutRecurrent().getPrix();

			Reduction reductionECParent =
					reductionDao.findReductionECParent(referenceCommande, commandeLigne.getReference(),
							tarif.getReference());
			// calculer la reduction sur le tarif de ligne.
			calculerReductionECParent(reductionECParent, detailCoutTarif);
		}

		// trouver les reduction liees aux lignes.
		Reduction reductionLigne =
				reductionDao.findReductionLigneSanFrais(referenceCommande, commandeLigne.getReference());

		// recuperer les reductions recurrentes liees au draft
		Reduction reductionDraft = reductionDao.findReduction(referenceCommande);

		// calculer la reduction de ligne.
		calculerReductionLigne(reductionLigne, coutComptantTTC, tarifTTC, false);

		// calculer la reduction recurrente du draft sur la ligne.
		calculerReductionLigne(reductionDraft, coutComptantTTC, tarifTTC, true);

		coutRecurentReduitTTC = (tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0;

		coutComptantReduitTTC =
				(coutComptantTTC - reductionComptantTTC) > 0 ? coutComptantTTC - reductionComptantTTC : 0;
		CoutRecurrent coutRecurrent = new CoutRecurrent();
		coutRecurrent.setEngagement(tarif.getEngagement());
		coutRecurrent.setFrais(tarif.getFrais());
		coutRecurrent.setFrequence(tarif.getFrequence());
		coutRecurrent.setPrix(coutRecurentReduitTTC);
		coutRecurrent.setTVA(VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA));
		detailCout.setCoutRecurrent(coutRecurrent);

		detailCout.setCoutComptantTTC(coutComptantReduitTTC);

		return detailCout;
	}

	/**
	 * calculer la reduction pour un tarif de l'offre.
	 * 
	 * @param reductionECParent
	 *            reduction associe au tarif de la ligne .
	 * 
	 * @param detailCoutTarif
	 *            detail cout du tarif du ligne.
	 */
	private void calculerReductionECParent(Reduction reductionECParent, DetailCout detailCoutTarif) {

		double tarifTTC = detailCoutTarif.getCoutRecurrent().getPrix();
		double reduction = 0d;

		if (reductionECParent != null && reductionECParent.isReductionRecurrente()) {
			reduction = ReductionUtil.calculeReductionRecurrent(tarifTTC - reductionRecurrentTTC, reductionECParent);

			reductionTTC += reduction;

			reductionRecurrentTTC += reduction;

		}

		if (reductionECParent != null && reductionECParent.isReductionComptant()) {
			reduction =
					ReductionUtil.calculeReductionComptant(detailCoutTarif.getCoutComptantTTC() - reductionComptantTTC,
							reductionECParent);

			reductionTTC += reduction;

			reductionComptantTTC += reduction;
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
	 * @param isReductionDraft
	 *            indique si la reduction est associe a un draft.
	 */
	private void calculerReductionLigne(Reduction reductionLigne, double coutComptant, double coutRecurrent,
			boolean isReductionDraft) {

		double reduction = 0d;

		if (reductionLigne != null && reductionLigne.isReductionRecurrente() && !isReductionDraft) {

			reduction = ReductionUtil.calculeReductionRecurrent(coutRecurrent - reductionRecurrentTTC, reductionLigne);

			reductionTTC += reduction;

			reductionRecurrentTTC += reduction;

		}

		if (reductionLigne != null && reductionLigne.isReductionComptant() && !isReductionDraft) {
			reduction = ReductionUtil.calculeReductionComptant(coutComptant - reductionComptantTTC, reductionLigne);

			reductionTTC += reduction;

			reductionComptantTTC += reduction;
		}

		if (reductionLigne != null && reductionLigne.isReductionRecurrente() && isReductionDraft) {

			reduction += ReductionUtil.calculeReductionRecurrent(coutRecurrent - reductionRecurrentTTC, reductionLigne);

			reductionTTC += reduction;

			reductionRecurrentTTC += reduction;
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

}
