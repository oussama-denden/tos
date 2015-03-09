package com.nordnet.opale.calcule;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.exception.OpaleException;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Component("coutDecorator")
public class CoutDecorator extends CalculeCout {

	/**
	 * interface du DP decorator.
	 */
	private CalculeCout calculeCout;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Cout getCout() throws OpaleException {
		if (calculeCout instanceof CoutCommande) {
			CoutCommande coutCommande = (CoutCommande) calculeCout;
			calculeCout = new CoutCommandeAvecPaiement(coutCommande.getPaiementService(), coutCommande);
		}
		return calculeCout.getCout();

	}

	/**
	 * Cout sans paiement pour le bon de commande.
	 * 
	 * @return cout sans tenir compte le paiement.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Cout getCoutPourBonDeCommande() throws OpaleException {
		return calculeCout.getCout();

	}

	/**
	 * 
	 * @return {@link CalculeCout}
	 */
	public CalculeCout getCalculeCout() {
		return calculeCout;
	}

	/**
	 * {@link CalculeCout}
	 * 
	 * @param calculeCout
	 */
	public void setCalculeCout(CalculeCout calculeCout) {
		this.calculeCout = calculeCout;
	}

}
