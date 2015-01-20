package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.repository.reduction.ReductionRepository;
import com.nordnet.opale.service.paiement.PaiementService;
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
	 * {@link PaiementService}
	 */
	private PaiementService paiementService;

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
	public CoutCommande(Commande commande, ReductionService reductionService, PaiementService paiementService) {
		super();
		this.commande = commande;
		this.reductionService = reductionService;
		this.paiementService = paiementService;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		List<Paiement> paiements = paiementService.getPaiementByReferenceCommande(commande.getReference());
		Paiement paiementCommande = paiements.size() != Constants.ZERO ? paiements.get(Constants.ZERO) : null;

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

		// changer la trame du cout selon le paiement effectuer par le client
		if (!(paiementCommande == null)) {
			if (paiementCommande.getModePaiement().isModePaiementRecurrent()) {
				cout.setCoutComptantHT(Constants.ZERO);
				cout.setCoutComptantTTC(Constants.ZERO);

				for (DetailCout detailCout : cout.getDetails()) {
					detailCout.setCoutComptantHT(Constants.ZERO);
					detailCout.setCoutComptantTTC(Constants.ZERO);
				}

			} else if (paiementCommande.getModePaiement().isModePaimentComptant()) {
				cout.setCoutRecurrentGlobale(null);

				for (DetailCout detailCout : cout.getDetails()) {
					coutComptantHT += detailCout.getCoutRecurrent().getNormal().getTarifHT();
					coutComptantTTC += detailCout.getCoutRecurrent().getNormal().getTarifTTC();
					detailCout.setCoutRecurrent(null);
				}

				cout.setCoutComptantHT(coutComptantHT);
				cout.setCoutComptantTTC(coutComptantTTC);

			}
		}

		return cout;
	}

}
