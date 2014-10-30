package com.nordnet.opale.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.business.catalogue.TrameCatalogue;
import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.domain.draft.DraftLigneDetail;
import com.nordnet.opale.enums.TypeFrais;

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
	 * liste des {@link DetailCout}.
	 */
	List<DetailCout> details = new ArrayList<DetailCout>();

	/**
	 * constructeur par defaut.
	 */
	public Cout() {
	}

	/**
	 * creation du cout a partir du {@link DraftLigne} et {@link TrameCatalogue}.
	 * 
	 * @param draftLigne
	 *            {@link DraftLigne}.
	 * @param trameCatalogue
	 *            {@link TrameCatalogue}.
	 */
	public Cout(DraftLigne draftLigne, TrameCatalogue trameCatalogue) {
		Map<String, Tarif> tarifMap = trameCatalogue.getTarifsMap();
		Tarif tarif = tarifMap.get(draftLigne.getReferenceTarif());
		coutTotal += tarif.getPrix();

		Map<String, Frais> fraisMap = trameCatalogue.getFraisMap();
		for (String refFrais : tarif.getFrais()) {
			Frais frais = fraisMap.get(refFrais);
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}

		for (DraftLigneDetail draftLigneDetail : draftLigne.getDraftLigneDetails()) {
			DetailCout detailCout = new DetailCout(draftLigneDetail, trameCatalogue);
			coutTotal += detailCout.getCoutTotal();
			addDetail(detailCout);
		}
	}

	/**
	 * Creation du {@link Cout} a partir de la {@link CommandeLigne}.
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 */
	public Cout(CommandeLigne commandeLigne) {
		com.nordnet.opale.domain.commande.Tarif tarif = commandeLigne.getTarif();
		coutTotal += tarif.getPrix();
		for (com.nordnet.opale.domain.commande.Frais frais : tarif.getFrais()) {
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}

		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			DetailCout detailCout = new DetailCout(commandeLigneDetail);
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

}
