package com.nordnet.opale.calcule;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.InfosReductionPourBonCommande;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.domain.reduction.Reduction;
import com.nordnet.opale.enums.TypePaiement;
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
	 * {@link InfosReductionPourBonCommande}
	 */
	private InfosReductionPourBonCommande infosReductionPourBonCommande;

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

		List<Paiement> paiements = paiementService.getPaiementNonAnnulees(commande.getReference());
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

			reductionRecurrentHT += coutLigneCommande.getReductionRecurrentHT();
			reductionRecurrentTTC += coutLigneCommande.getReductionRecurrentTTC();

			coutRecurentReduitHT += coutLigneCommande.getCoutRecurentReduitTTC();
			tva = coutLigneCommande.getTva();
		}

		// recuperation du reduction du draft.
		Reduction reductionDraft = reductionService.findReduction(commande.getReference());
		double coutReduction =
				ReductionUtil.calculeReductionComptant(coutComptantTTC - reductionComptantTTC, reductionDraft);
		reductionTTC += coutReduction;
		reductionHT = ReductionUtil.caculerReductionHT(reductionTTC, tva);

		coutComptantReduitTTC = coutComptantTTC - reductionComptantTTC;
		coutComptantReduitHT = ReductionUtil.caculerCoutReduitHT(coutComptantReduitTTC, tva);

		coutRecurentReduitHT = ReductionUtil.caculerCoutReduitHT(coutRecurentReduitHT, tva);
		creerInfosReductionBonCommande(reductionDraft, tva, coutReduction);

		if (infosReductionPourBonCommande != null) {
			for (DetailCout detailCout : details) {
				detailCout.getInfosReductionPourBonCommande().add(infosReductionPourBonCommande);
			}
		}

		cout.setCoutComptantHT(coutComptantHT);
		cout.setCoutComptantTTC(coutComptantTTC);
		cout.setDetails(details);
		cout.setCoutRecurrentGlobale(coutRecurrentGlobale);
		cout.setReductionHT(reductionHT);
		cout.setReductionTTC(reductionTTC);
		cout.setTva(tva);

		// changer la trame du cout selon le paiement effectuer par le client
		if (!(paiementCommande == null)) {
			if (paiementCommande.getTypePaiement().equals(TypePaiement.RECURRENT)) {
				cout.setCoutComptantHT(Constants.ZERO);
				cout.setCoutComptantTTC(Constants.ZERO);
				cout.setMontantTva(Constants.ZERO);

				cout.setReductionHT(reductionRecurrentHT);
				cout.setReductionTTC(reductionRecurrentTTC);

				for (DetailCout detailCout : cout.getDetails()) {
					detailCout.setCoutComptantHT(Constants.ZERO);
					detailCout.setCoutComptantTTC(Constants.ZERO);
					detailCout.setMontantTva(Constants.ZERO);
				}

			} else if (paiementCommande.getTypePaiement().equals(TypePaiement.COMPTANT)) {
				cout.setCoutRecurrentGlobale(null);

				for (DetailCout detailCout : cout.getDetails()) {

					detailCout.setCoutComptantHT(detailCout.getCoutRecurrent().getNormal().getTarifHT()
							+ detailCout.getCoutComptantHT());
					detailCout.setCoutComptantTTC(detailCout.getCoutRecurrent().getNormal().getTarifTTC()
							+ detailCout.getCoutComptantTTC());
					detailCout.setMontantTva(detailCout.getCoutComptantTTC() > detailCout.getCoutComptantHT()
							? detailCout.getCoutComptantTTC() - detailCout.getCoutComptantHT() : 0d);

					coutComptantHT += detailCout.getCoutRecurrent().getNormal().getTarifHT();
					coutComptantTTC += detailCout.getCoutRecurrent().getNormal().getTarifTTC();
					detailCout.setCoutRecurrent(null);
				}

				cout.setCoutComptantHT(coutComptantHT);
				cout.setCoutComptantTTC(coutComptantTTC);
				cout.setMontantTva(coutComptantTTC >= coutComptantHT ? coutComptantTTC - coutComptantHT : 0d);

			}
		} else {
			cout.setMontantTva(coutComptantTTC >= coutComptantHT ? coutComptantTTC - coutComptantHT : 0d);
		}

		return cout;
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
		if (reduction != null && reduction.isreductionComptant()) {
			infosReductionPourBonCommande = new InfosReductionPourBonCommande();
			infosReductionPourBonCommande.setReference(reduction.getReference());
			infosReductionPourBonCommande.setLabel(reduction.getLabel());
			infosReductionPourBonCommande.setReferenceCatalogue(reduction.getCodeCatalogueReduction());
			infosReductionPourBonCommande.setPrixTTC(prixHT);
			infosReductionPourBonCommande.setPrixHT(ReductionUtil.caculerReductionHT(prixHT, tva));
		}
	}

	/**
	 * 
	 * @return {@link Commande}
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * 
	 * @param commande
	 *            {@link Commande}
	 */
	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	/**
	 * 
	 * @return {@link ReductionService}
	 */
	public ReductionService getReductionService() {
		return reductionService;
	}

	/**
	 * 
	 * @param reductionService
	 *            {@link ReductionService}
	 */
	public void setReductionService(ReductionService reductionService) {
		this.reductionService = reductionService;
	}

	/**
	 * 
	 * @return {@link PaiementService}
	 */
	public PaiementService getPaiementService() {
		return paiementService;
	}

	/**
	 * 
	 * @param paiementService
	 *            {@link PaiementService}
	 */
	public void setPaiementService(PaiementService paiementService) {
		this.paiementService = paiementService;
	}

	/**
	 * 
	 * @return {@link #infosReductionPourBonCommande}
	 */
	public InfosReductionPourBonCommande getInfosReductionPourBonCommande() {
		return infosReductionPourBonCommande;
	}

	/**
	 * 
	 * @param infosReductionPourBonCommande
	 *            {@link #infosReductionPourBonCommande}
	 */
	public void setInfosReductionPourBonCommande(InfosReductionPourBonCommande infosReductionPourBonCommande) {
		this.infosReductionPourBonCommande = infosReductionPourBonCommande;
	}

}
