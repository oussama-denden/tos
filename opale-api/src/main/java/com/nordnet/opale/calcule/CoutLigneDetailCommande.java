package com.nordnet.opale.calcule;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.InfosReductionPourBonCommande;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.commande.Tarif;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.vat.client.VatClient;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutLigneDetailCommande extends CalculeCout {

	/**
	 * {@link CommandeLigneDetail}
	 */
	private CommandeLigneDetail commandeLigneDetail;

	/**
	 * refrence commande.
	 */
	private String referenceCommande;

	/**
	 * reference commande ligne.
	 */
	private String referenceCommandeLigne;

	/**
	 * {@link Tarif}
	 */
	private Tarif tarif;
	/**
	 * {@link #segmentTVA}
	 */
	private String segmentTVA;

	/**
	 * {@link ReductionRepository}
	 */
	private ReductionService reductionService;

	/**
	 * {@link InfosReductionPourBonCommande}
	 */
	private InfosReductionPourBonCommande infosReductionPourBonCommande;

	/**
	 * constructeur par defaut.
	 */
	public CoutLigneDetailCommande() {

	}

	/**
	 * constructeur avec parametre
	 * 
	 * @param commandeLigneDetail
	 *            {@link CommandeLigneDetail}
	 * @param tarif
	 *            {@link Tarif}
	 * @param reductionRepository
	 *            {@link ReductionRepository}
	 */
	public CoutLigneDetailCommande(CommandeLigneDetail commandeLigneDetail, String referenceCommande,
			String referenceCommandeLigne, Tarif tarif, String segmentTVA, ReductionService reductionService) {
		this.commandeLigneDetail = commandeLigneDetail;
		this.referenceCommande = referenceCommande;
		this.referenceCommandeLigne = referenceCommandeLigne;
		this.tarif = tarif;
		this.segmentTVA = segmentTVA;
		this.reductionService = reductionService;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		Plan reduit = null;
		double tva = VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA);

		CoutTarif coutTarifCommande =
				new CoutTarif(tarif, segmentTVA, commandeLigneDetail.getReferenceChoix(), referenceCommandeLigne,
						referenceCommande, false, true, reductionService);

		DetailCout detailCout = (DetailCout) coutTarifCommande.getCout();

		Reduction reduction =
				reductionService.findReductionDetailLigneDraftSansFrais(referenceCommande, referenceCommandeLigne,
						commandeLigneDetail.getReferenceChoix());

		double tarifHT =
				detailCout.getCoutRecurrent() != null ? detailCout.getCoutRecurrent().getNormal().getTarifHT() : 0d;
		double tarifTTC =
				detailCout.getCoutRecurrent() != null ? detailCout.getCoutRecurrent().getNormal().getTarifTTC() : 0d;

		calculerReductionLigneDetail(reduction, detailCout.getCoutComptantTTC(), tarifTTC, tva, detailCout,
				tarif.getFrequence());

		detailCout.setReductionHT(reductionHT);
		detailCout.setReductionTTC(reductionTTC);

		// plan reduit
		reduit =
				new Plan((tarifHT - reductionRecurrentHT) > 0 ? tarifHT - reductionRecurrentHT : 0,
						(tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0);
		detailCout.getCoutRecurrent().setReduit(reduit);

		coutRecurentReduitTTC = (tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0;
		coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitTTC, tva);

		coutComptantReduitTTC =
				(detailCout.getCoutComptantTTC() - reductionComptantTTC) > 0 ? detailCout.getCoutComptantTTC()
						- reductionComptantTTC : 0;
		coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

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
	 * @param detailCoutTarif
	 */
	private void calculerReductionLigneDetail(Reduction reductionLigneDetail, double coutComptant,
			double coutRecurrent, double tva, DetailCout detailCoutTarif, Integer frequence) {

		double reduction = 0d;

		if (reductionLigneDetail != null && reductionLigneDetail.isreductionRecurrente()) {

			reduction = ReductionUtil.calculeReductionRecurrent(coutRecurrent, reductionLigneDetail, frequence);

			reductionTTC += reduction + detailCoutTarif.getReductionTTC();
			reductionHT = ReductionUtil.caculerReductionHT(reduction, tva) + detailCoutTarif.getReductionHT();

			reductionRecurrentTTC += reduction;
			reductionRecurrentHT = ReductionUtil.caculerReductionHT(reductionRecurrentTTC, tva);
			creerInfosReductionBonCommande(reductionLigneDetail, tva, reduction);

		}

		if (reductionLigneDetail != null && reductionLigneDetail.isreductionComptant()) {
			reduction =
					ReductionUtil.calculeReductionComptant(coutComptant - reductionComptantTTC, reductionLigneDetail);

			reductionTTC += reduction + detailCoutTarif.getReductionTTC();
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

			reductionComptantTTC += reduction;
			reductionComptantHT = ReductionUtil.caculerReductionHT(reductionComptantTTC, tva);
			creerInfosReductionBonCommande(reductionLigneDetail, tva, reduction);

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

	/**
	 * 
	 * @return {@link InfosReductionPourBonCommande}
	 */
	public InfosReductionPourBonCommande getInfosReductionPourBonCommande() {
		return infosReductionPourBonCommande;
	}

	/**
	 * 
	 * @param infosReductionPourBonCommande
	 *            {@link InfosReductionPourBonCommande}
	 */
	public void setInfosReductionPourBonCommande(InfosReductionPourBonCommande infosReductionPourBonCommande) {
		this.infosReductionPourBonCommande = infosReductionPourBonCommande;
	}

	/**
	 * ajouter une reduction au liste des reduction ligne.
	 * 
	 * @param reduction
	 *            reduction a joute.
	 * @param tva
	 *            taux du tva.
	 * @param prixHT
	 *            valeur du reduction HT.
	 */
	private void creerInfosReductionBonCommande(Reduction reduction, double tva, double prixHT) {
		if (infosReductionPourBonCommande != null) {
			infosReductionPourBonCommande = new InfosReductionPourBonCommande();
			infosReductionPourBonCommande.setReference(reduction.getReference());
			infosReductionPourBonCommande.setLabel(reduction.getLabel());
			infosReductionPourBonCommande.setReferenceCatalogue(reduction.getCodeCatalogueReduction());
			infosReductionPourBonCommande.setPrixTTC(prixHT);
			infosReductionPourBonCommande.setPrixHT(ReductionUtil.caculerReductionHT(prixHT, tva));
		}
	}

}
