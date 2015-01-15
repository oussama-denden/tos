package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.exception.OpaleException;

/**
 * contient les couts d'une commande.
 * 
 * @author akram-moncer
 * 
 */
public class Cout {

	/**
	 * cout total du commande/draft.
	 */
	private double coutComptantHT;

	/**
	 * cout total du commande/draft.
	 */
	private double coutComptantTTC;

	/**
	 * cout totale du reduction.
	 */
	private double reductionHT;

	/**
	 * cout totale du reduction.
	 */
	private double reductionTTC;

	/**
	 * liste des {@link DetailCout}.
	 */
	List<DetailCout> details = new ArrayList<DetailCout>();

	/**
	 * constructeur par defaut.
	 */
	public Cout() {
	}

	/**
	 * creation du cout d'une {@link Commande}.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Cout(Commande commande) throws OpaleException {
		String segmentTVA = commande.getClientAFacturer().getTva();
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			DetailCout detailCout = new DetailCout(commandeLigne, segmentTVA);
			coutComptantHT += detailCout.getCoutComptantHT();
			coutComptantTTC += detailCout.getCoutComptantTTC();
			addDetail(detailCout);
		}
	}

	/**
	 * 
	 * @return {@link #details}.
	 */
	public List<DetailCout> getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details
	 *            {@link #details}.
	 */
	public void setDetails(List<DetailCout> details) {
		this.details = details;
	}

	/**
	 * ajout d'un {@link DetailCout}.
	 * 
	 * @param detailCout
	 *            {@link DetailCout}.
	 */
	public void addDetail(DetailCout detailCout) {
		details.add(detailCout);
	}

	/**
	 * 
	 * @return {@link #coutComptantHT}
	 */
	public double getCoutComptantHT() {
		return coutComptantHT;
	}

	/**
	 * 
	 * @param coutComptant
	 *            {@link #coutComptantHT}
	 */
	public void setCoutComptantHT(double coutComptantHT) {
		this.coutComptantHT = coutComptantHT;
	}

	/**
	 * 
	 * @return {@link #coutComptantTTC}
	 */
	public double getCoutComptantTTC() {
		return coutComptantTTC;
	}

	/**
	 * 
	 * @param coutComptantTTC
	 *            {@link #coutComptantTTC}
	 */
	public void setCoutComptantTTC(double coutComptantTTC) {
		this.coutComptantTTC = coutComptantTTC;
	}

	/**
	 * 
	 * @return {@link Cout#reductionHT}
	 */
	public double getReductionHT() {
		return reductionHT;
	}

	/**
	 * 
	 * @param reductionHT
	 *            {@link #reductionHT}
	 */
	public void setReductionHT(double reductionHT) {
		this.reductionHT = reductionHT;
	}

	/**
	 * 
	 * @return {@link #reductionTTC}
	 */
	public double getReductionTTC() {
		return reductionTTC;
	}

	/**
	 * 
	 * @param reductionTTC
	 *            {@link #reductionTTC}
	 */
	public void setReductionTTC(double reductionTTC) {
		this.reductionTTC = reductionTTC;
	}

}
