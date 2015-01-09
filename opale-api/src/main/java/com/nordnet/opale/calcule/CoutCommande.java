package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.reduction.ReductionService;

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

		String segmentTVA = commande.getClientAFacturer().getTva();

		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {

			CoutLigneCommande coutLigneCommande =
					new CoutLigneCommande(commande.getReference(), commandeLigne, segmentTVA, reductionService);

			DetailCout detailCout = (DetailCout) coutLigneCommande.getCout();
			coutComptantHT += detailCout.getCoutComptantHT();
			coutComptantTTC += detailCout.getCoutComptantTTC();
			details.add(detailCout);

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
		cout.setReductionHT(reductionHT);
		cout.setReductionTTC(reductionTTC);

		return cout;
	}
}
