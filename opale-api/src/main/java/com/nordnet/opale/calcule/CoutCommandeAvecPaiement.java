package com.nordnet.opale.calcule;

import java.util.List;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.enums.TypePaiement;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.service.paiement.PaiementService;
import com.nordnet.opale.util.Constants;

/**
 * Cette classe calule le cout du commande avec prise en considération du paiement.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutCommandeAvecPaiement extends CalculeCout {

	/**
	 * {@link PaiementService}
	 */
	private PaiementService paiementService;

	/**
	 * {@link CoutCommande}
	 */
	private CoutCommande coutCommande;

	/**
	 * constructeur sans parametres.
	 */
	public CoutCommandeAvecPaiement() {
		this(null, null);
	}

	/**
	 * constructeur pour inialiser les parametres.
	 * 
	 * @param paiementService
	 *            {@link PaiementService}
	 * @param coutCommande
	 *            {@link CoutCommande}
	 */
	public CoutCommandeAvecPaiement(PaiementService paiementService, CoutCommande coutCommande) {
		this.paiementService = paiementService;
		this.coutCommande = coutCommande;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (coutCommande != null || paiementService != null) {
			List<Paiement> paiements =
					paiementService.getPaiementNonAnnulees(coutCommande.getCommande().getReference());
			Paiement paiementCommande = paiements.size() != Constants.ZERO ? paiements.get(Constants.ZERO) : null;
			Cout cout = coutCommande.getCout();

			// changer la trame du cout selon le paiement effectuer par le client
			if (!(paiementCommande == null)) {
				if (paiementCommande.getTypePaiement().equals(TypePaiement.RECURRENT)) {
					cout.setCoutComptantHT(Constants.ZERO);
					cout.setCoutComptantTTC(Constants.ZERO);
					cout.setMontantTva(Constants.ZERO);

					cout.setReductionHT(coutCommande.getReductionRecurrentHT());
					cout.setReductionTTC(coutCommande.getReductionRecurrentTTC());

					for (DetailCout detailCout : cout.getDetails()) {
						detailCout.setCoutComptantHT(Constants.ZERO);
						detailCout.setCoutComptantTTC(Constants.ZERO);
						detailCout.setMontantTva(Constants.ZERO);
					}

				} else if (paiementCommande.getTypePaiement().equals(TypePaiement.COMPTANT)) {
					cout.setCoutRecurrentGlobale(null);

					double coutComptantHT = cout.getCoutComptantHT();
					double coutComptantTTC = cout.getCoutComptantTTC();

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
					cout.setMontantTva(cout.getCoutComptantTTC() >= cout.getCoutComptantHT() ? cout
							.getCoutComptantTTC() - cout.getCoutComptantHT() : 0d);
				}
			} else {
				cout.setMontantTva(cout.getCoutComptantTTC() >= cout.getCoutComptantHT() ? cout.getCoutComptantTTC()
						- cout.getCoutComptantHT() : 0d);
			}

			return cout;
		}
		return null;
	}

	/* Getters ans Setters */

	/**
	 * service paiement
	 * 
	 * @return {@link PaiementService}
	 */
	public PaiementService getPaiementService() {
		return paiementService;
	}

	/**
	 * set les service du paiement.
	 * 
	 * @param paiementService
	 *            {@link #paiementService}
	 */
	public void setPaiementService(PaiementService paiementService) {
		this.paiementService = paiementService;
	}

	/**
	 * cout de la commande sans paiement.
	 * 
	 * @return {@link CoutCommande}
	 */
	public CoutCommande getCoutCommande() {
		return coutCommande;
	}

	/**
	 * set cout commande.
	 * 
	 * @param coutCommande
	 *            {@link CoutCommande}
	 */
	public void setCoutCommande(CoutCommande coutCommande) {
		this.coutCommande = coutCommande;
	}

}
