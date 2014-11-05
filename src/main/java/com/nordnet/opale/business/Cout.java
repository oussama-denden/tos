package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.commande.Commande;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;

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
	private double coutTotal;

	/**
	 * cout totale du reduction.
	 */
	private double reduction;

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
	 * creation du cout d'un {@link Draft}.
	 * 
	 * @param draft
	 *            {@link Draft}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public Cout(Draft draft, TrameCatalogue trameCatalogue) {
		for (DraftLigne draftLigne : draft.getDraftLignes()) {
			DetailCout detailCout = new DetailCout(draft.getReference(), draftLigne, trameCatalogue);
			coutTotal += detailCout.getCoutTotal();
			addDetail(detailCout);
		}
	}

	/**
	 * creation du cout d'une {@link Commande}.
	 * 
	 * @param commande
	 *            {@link Commande}.
	 */
	public Cout(Commande commande) {
		for (CommandeLigne commandeLigne : commande.getCommandeLignes()) {
			DetailCout detailCout = new DetailCout(commandeLigne);
			coutTotal += detailCout.getCoutTotal();
			addDetail(detailCout);
		}
	}

	/**
	 * 
	 * @return {@link #coutTotal}.
	 */
	public Double getCoutTotal() {
		return coutTotal;
	}

	/**
	 * 
	 * @param coutTotal
	 *            {@link #coutTotal}.
	 */
	public void setCoutTotal(Double coutTotal) {
		this.coutTotal = coutTotal;
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
	 * @return {@link #reduction}
	 */
	public double getReduction() {
		return reduction;
	}

	/**
	 * 
	 * @param reduction
	 *            the new {@link #reduction}
	 */
	public void setReduction(double reduction) {
		this.reduction = reduction;
	}

}
