package com.nordnet.opale.calcule;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
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
public class CoutLigneCommande extends CalculeCout {

	/**
	 * {@link CommandeLigne}
	 */
	private CommandeLigne commandeLigne;

	/**
	 * {@link #segmentTVA}
	 */
	private String segmentTVA;

	/**
	 * reference du commande parent.
	 */
	private String referenceCommande;

	/**
	 * {@link ReductionRepository}
	 */
	private ReductionService reductionService;

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
	 * @param commandeLigne
	 *            {@link CommandeLigne}
	 * @param segmentTVA
	 *            {@link #segmentTVA}
	 * @param reductionRepository
	 *            {@link ReductionRepository}
	 */
	public CoutLigneCommande(String refenrenceCommande, CommandeLigne commandeLigne, String segmentTVA,
			ReductionService reductionService) {
		this.referenceCommande = refenrenceCommande;
		this.commandeLigne = commandeLigne;
		this.segmentTVA = segmentTVA;
		this.reductionService = reductionService;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		DetailCout detailCout = new DetailCout();
		String numero = String.valueOf(commandeLigne.getNumero());
		String label = commandeLigne.getReferenceOffre();
		double tarifHT = 0d;
		double tarifTTC = 0d;
		Integer frequence = null;
		com.nordnet.opale.domain.commande.Tarif tarif = null;

		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			tarif = commandeLigneDetail.getTarif();

			if (tarif != null) {
				CoutLigneDetailCommande coutLigneDetailCommande =
						new CoutLigneDetailCommande(commandeLigneDetail, referenceCommande,
								commandeLigne.getReferenceOffre(), tarif, segmentTVA, reductionService);

				DetailCout detailCoutTarif = (DetailCout) coutLigneDetailCommande.getCout();
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

				reductionRecurrentHT += coutLigneDetailCommande.getReductionRecurrentHT();
				reductionRecurrentTTC += coutLigneDetailCommande.getReductionRecurrentTTC();

				reductionComptantHT += coutLigneDetailCommande.getReductionComptantHT();
				reductionComptantTTC += coutLigneDetailCommande.getReductionComptantTTC();
			}
		}

		tarif = commandeLigne.getTarif();
		if (tarif != null) {

			tva = VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA);

			CoutTarif coutTarifCommande =
					new CoutTarif(tarif, segmentTVA, null, commandeLigne.getReferenceOffre(), referenceCommande, true,
							false, reductionService);

			DetailCout detailCoutTarif = (DetailCout) coutTarifCommande.getCout();
			coutComptantHT += detailCoutTarif.getCoutComptantHT();
			coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
			tarifHT +=
					detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
							.getTarifHT() : 0d;
			tarifTTC +=
					detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
							.getTarifTTC() : 0d;
			frequence = tarif.getFrequence();

			reductionRecurrentHT += coutTarifCommande.getReductionRecurrentHT();
			reductionRecurrentTTC += coutTarifCommande.getReductionRecurrentTTC();

			reductionComptantHT += coutTarifCommande.getReductionComptantHT();
			reductionComptantTTC += coutTarifCommande.getReductionComptantTTC();

			Reduction reductionECParent =
					reductionService.findReductionECParent(referenceCommande, commandeLigne.getReferenceOffre(),
							tarif.getReference());
			// calculer la reduction sur le tarif de ligne.
			calculerReductionECParent(reductionECParent, detailCoutTarif, tva, tarif.getFrequence());
		}

		// trouver les reduction liees aux lignes.
		Reduction reductionLigne =
				reductionService.findReductionLigneDraftSansFrais(referenceCommande, commandeLigne.getReferenceOffre());

		// recuperer les reductions recurrentes liees au draft
		Reduction reductionDraft = reductionService.findReduction(referenceCommande);

		// calculer la reduction de ligne.
		calculerReductionLigne(reductionLigne, coutComptantTTC, tarifTTC, tva, false, tarif.getFrequence());

		// calculer la reduction recurrente du draft sur la ligne.
		calculerReductionLigne(reductionDraft, coutComptantTTC, tarifTTC, tva, true, tarif.getFrequence());

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
		detailCout.setNumero(numero);

		coutRecurentReduitTTC = (tarifTTC - reductionRecurrentTTC) > 0 ? tarifTTC - reductionRecurrentTTC : 0;
		coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitTTC, tva);

		coutComptantReduitTTC =
				(coutComptantTTC - reductionComptantTTC) > 0 ? coutComptantTTC - reductionComptantTTC : 0;
		coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

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
	 * @param tva
	 *            valeur de tva.
	 */
	private void calculerReductionECParent(Reduction reductionECParent, DetailCout detailCoutTarif, double tva,
			Integer frequence) {

		double tarifTTC =
				detailCoutTarif.getCoutRecurrent() != null ? detailCoutTarif.getCoutRecurrent().getNormal()
						.getTarifTTC() : 0d;
		double reduction = 0d;

		reductionHT += detailCoutTarif.getReductionHT();
		reductionTTC += detailCoutTarif.getReductionTTC();

		if (reductionECParent != null && reductionECParent.isreductionRecurrente()) {
			reduction =
					ReductionUtil.calculeReductionRecurrent(tarifTTC - reductionRecurrentTTC, reductionECParent,
							frequence);

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
			double tva, boolean isReductionDraft, Integer frequence) {

		double reduction = 0d;

		if (reductionLigne != null && reductionLigne.isreductionRecurrente() && !isReductionDraft) {

			reduction =
					ReductionUtil.calculeReductionRecurrent(coutRecurrent - reductionRecurrentTTC, reductionLigne,
							frequence);

			reductionTTC += reduction;
			reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

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

			reduction +=
					ReductionUtil.calculeReductionRecurrent(coutRecurrent - reductionRecurrentTTC, reductionLigne,
							frequence);

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
