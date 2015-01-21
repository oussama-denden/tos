package com.nordnet.opale.finder.cout;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.business.CommandeLigne;
import com.nordnet.opale.finder.business.Cout;
import com.nordnet.opale.finder.business.DetailCout;
import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.dao.ReductionDao;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.util.ReductionUtil;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutCommande extends CalculeCout {

	/**
	 * {@link Commande}.
	 */
	private Commande commande;

	/**
	 * {@link ReductionDao}.
	 */
	private ReductionDao reductionDao;

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
	 * @param reductionDao
	 *            {@link ReductionDao}
	 */
	public CoutCommande(Commande commande, ReductionDao reductionDao) {
		super();
		this.commande = commande;
		this.reductionDao = reductionDao;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Cout getCout() throws OpaleException {

		Cout cout = new Cout();
		double coutComptantTTC = 0d;
		List<DetailCout> details = new ArrayList<DetailCout>();

		String segmentTVA = commande.getClientAFacturer().getTva();

		for (CommandeLigne commandeLigne : commande.getLignes()) {

			CoutLigneCommande coutLigneCommande =
					new CoutLigneCommande(commande.getReference(), commandeLigne, segmentTVA, reductionDao);

			DetailCout detailCout = (DetailCout) coutLigneCommande.getCout();

			commandeLigne.setCoutComptant(detailCout.getCoutComptantTTC());

			commandeLigne.setCoutRecurrent(detailCout.getCoutRecurrent());
			coutComptantTTC += detailCout.getCoutComptantTTC();
			details.add(detailCout);

		}

		// recuperation du reduction du commande.
		Reduction reductionDraft = reductionDao.findReduction(commande.getReference());

		reductionComptantTTC = ReductionUtil.calculeReductionComptant(coutComptantTTC, reductionDraft);

		coutComptantReduitTTC = coutComptantTTC - reductionComptantTTC;

		cout.setCoutComptantTTC(coutComptantReduitTTC);
		cout.setDetails(details);
		commande.setCoutComptant(coutComptantReduitTTC);
		return cout;
	}

	/**
	 * Recuperer commande avec cout.
	 * 
	 * @return {@link Commande}
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	public Commande getCommande() throws OpaleException {
		getCout();
		return this.commande;
	}
}
