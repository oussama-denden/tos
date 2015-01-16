package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.reduction.ReductionService;
import com.nordnet.opale.util.Constants;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutCommande extends CalculeCout {

	/**
	 * {@link Commande}
	 */
	private Commande commande;

	/**
	 * {@link ReductionRepository}
	 */
	private ReductionService reductionService;

	/**
	 * constructeur par defaut.
	 */
	public CoutCommande() {

	}

	/**
	 * constructeur avec parametres.
	 * 
	 * @param commande
	 *            {@link Commande}
	 * 
	 * @param reductionRepository
	 *            {@link ReductionRepository}
	 */
	public CoutCommande(Commande commande, ReductionService reductionService) {
		super();
		this.commande = commande;
		this.reductionService = reductionService;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		Cout cout = new Cout();
		double coutComptantHT = 0d;
		double coutComptantTTC = 0d;
		double tva = 0d;
		List<DetailCout> details = new ArrayList<DetailCout>();
		List<CoutRecurrent> coutRecurrentGlobale = new ArrayList<CoutRecurrent>();

		String segmentTVA = commande.getClientAFacturer().getTva();

		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {

			CoutLigneCommande coutLigneCommande =
					new CoutLigneCommande(commande.getReference(), commandeLigne, segmentTVA, reductionService);

			DetailCout detailCout = (DetailCout) coutLigneCommande.getCout();
			coutComptantHT += detailCout.getCoutComptantHT();
			coutComptantTTC += detailCout.getCoutComptantTTC();
			details.add(detailCout);
			ReductionUtil.ajouterCoutRecurrent(coutRecurrentGlobale, detailCout.getCoutRecurrent());

			reductionHT += detailCout.getReductionHT();
			reductionTTC += detailCout.getReductionTTC();

			reductionComptantHT += coutLigneCommande.getReductionComptantHT();
			reductionComptantTTC += coutLigneCommande.getReductionComptantTTC();

			coutRecurentReduitHT += coutLigneCommande.getCoutRecurentReduitTTC();
			tva = coutLigneCommande.getTva();
		}

		// recuperation du reduction du draft.
		Reduction reductionDraft = reductionService.findReduction(commande.getReference());

		reductionTTC += ReductionUtil.calculeReductionComptant(coutComptantTTC - reductionComptantTTC, reductionDraft);
		reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

		coutComptantReduitTTC = coutComptantTTC - reductionComptantTTC;
		coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

		coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitHT, tva);

		cout.setCoutComptantHT(coutComptantHT);
		cout.setCoutComptantTTC(coutComptantTTC);
		cout.setDetails(details);
		cout.setCoutRecurrentGlobale(coutRecurrentGlobale);
		cout.setReductionHT(reductionHT);
		cout.setReductionTTC(reductionTTC);

		return cout;
	}

	/**
	 * ajouter le cout recurrent gloable au trame du cout.
	 * 
	 * @param coutRecurrents
	 *            liste du cout recurrent.
	 * @param coutRecurrent
	 *            cout recurrent a ajoute
	 */
	private void ajouterCoutRecurrent(List<CoutRecurrent> coutRecurrents, CoutRecurrent coutRecurrent) {
		int index = -1;
		if (coutRecurrents != null && coutRecurrents.size() == Constants.ZERO && coutRecurrent != null) {
			coutRecurrents.add(coutRecurrent);
		}

		else {
			index = coutRecurrents.indexOf(coutRecurrent);

			if (index == -1) {
				coutRecurrents.add(coutRecurrent);
			}

			else {

				CoutRecurrent coutRecurrentAdditonne =
						addiotionnerDeuxCoutRecurrent(coutRecurrents.get(index), coutRecurrent);
				coutRecurrents.remove(index);
				coutRecurrents.add(coutRecurrentAdditonne);
			}
		}
	}

	/**
	 * additionner deux cout recurrent.
	 * 
	 * @param coutRecurrentAncient
	 *            cout recurrent deja existant dans la liste des couts recurrents
	 * @param coutRecurrentNouveau
	 *            cout recurrent nouveau
	 */
	private CoutRecurrent addiotionnerDeuxCoutRecurrent(CoutRecurrent coutRecurrentAncient,
			CoutRecurrent coutRecurrentNouveau) {
		if (coutRecurrentAncient.getNormal() != null && coutRecurrentNouveau.getNormal() != null) {
			coutRecurrentAncient.getNormal().setTarifHT(
					coutRecurrentAncient.getNormal().getTarifHT() + coutRecurrentNouveau.getNormal().getTarifHT());
			coutRecurrentAncient.getNormal().setTarifTTC(
					coutRecurrentAncient.getNormal().getTarifTTC() + coutRecurrentNouveau.getNormal().getTarifTTC());
		}

		if (coutRecurrentAncient.getReduit() != null && coutRecurrentNouveau.getReduit() != null) {
			coutRecurrentAncient.getReduit().setTarifHT(
					coutRecurrentAncient.getReduit().getTarifHT() + coutRecurrentNouveau.getReduit().getTarifHT());
			coutRecurrentAncient.getReduit().setTarifTTC(
					coutRecurrentAncient.getReduit().getTarifTTC() + coutRecurrentNouveau.getReduit().getTarifTTC());
		}

		return coutRecurrentAncient;
	}
}
