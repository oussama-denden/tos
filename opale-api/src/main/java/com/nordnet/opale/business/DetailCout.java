package com.nordnet.opale.business;

import com.nordnet.opale.domain.commande.CommandeLigne;
import com.nordnet.opale.domain.commande.CommandeLigneDetail;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.vat.client.VatClient;

/**
 * contient les cout en detail pour un profuit.
 * 
 * @author akram-moncer
 * 
 */
public class DetailCout {

	/**
	 * numero detail coincide avec le numero de ligne dans la commande.
	 */
	private String numero;

	/**
	 * label de l'offre.
	 */
	private String label;

	/**
	 * cout total de l'offre.
	 */
	private double coutTotal;

	/**
	 * cout total du commande/draft.
	 */
	private double coutTotalTTC;

	/**
	 * cout totale du reduction.
	 */
	private double reduction;

	/**
	 * {@link Plan}.
	 */
	private Plan plan;

	/**
	 * constructeur par defaut.
	 */
	public DetailCout() {
	}

	/**
	 * creation du cout pour une {@link CommandeLigne}.
	 * 
	 * @param commandeLigne
	 *            {@link CommandeLigne}.
	 * @param segmentTVA
	 *            segment TVA du client.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public DetailCout(CommandeLigne commandeLigne, String segmentTVA) throws OpaleException {
		numero = String.valueOf(commandeLigne.getNumero());
		label = commandeLigne.getReferenceOffre();
		double plan = 0d;
		double planTTC = 0d;
		Integer frequence = null;
		com.nordnet.opale.domain.commande.Tarif tarif = null;
		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			tarif = commandeLigneDetail.getTarif();
			if (tarif != null) {
				DetailCout detailCoutTarif = calculerCoutTarif(tarif, segmentTVA);
				coutTotal += detailCoutTarif.getCoutTotal();
				coutTotalTTC += detailCoutTarif.getCoutTotalTTC();
				plan += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan() : 0d;
				planTTC += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlanTTC() : 0d;
				frequence = tarif.getFrequence();
			}
		}

		tarif = commandeLigne.getTarif();
		if (tarif != null) {
			DetailCout detailCoutTarif = calculerCoutTarif(tarif, segmentTVA);
			coutTotal += detailCoutTarif.getCoutTotal();
			coutTotalTTC += detailCoutTarif.getCoutTotalTTC();
			plan += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlan() : 0d;
			planTTC += detailCoutTarif.getPlan() != null ? detailCoutTarif.getPlan().getPlanTTC() : 0d;
			frequence = tarif.getFrequence();
		}

		if (plan > Constants.ZERO) {
			this.plan = new Plan(frequence, plan, planTTC);
		}
	}

	/**
	 * 
	 * @return {@link #numero}.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 *            {@link #numero}.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * 
	 * @return {@link #label}.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            {@link #label}.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return {@link #coutTotal}.
	 */
	public double getCoutTotal() {
		return coutTotal;
	}

	/**
	 * 
	 * @param coutTotal
	 *            {@link #coutTotal}.
	 */
	public void setCoutTotal(double coutTotal) {
		this.coutTotal = coutTotal;
	}

	/**
	 * 
	 * @return {@link #coutTotalTTC}.
	 */
	public double getCoutTotalTTC() {
		return coutTotalTTC;
	}

	/**
	 * 
	 * @param coutTotalTTC
	 *            {@link #coutTotalTTC}.
	 */
	public void setCoutTotalTTC(double coutTotalTTC) {
		this.coutTotalTTC = coutTotalTTC;
	}

	/**
	 * 
	 * @return {@link Plan}.
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * 
	 * @param plan
	 *            {@link Plan}.
	 */
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	/**
	 * 
	 * @return {@link #reduction}
	 */
	public Double getReduction() {
		return reduction;
	}

	/**
	 * 
	 * @param reduction
	 *            the new {@link #reduction}
	 */
	public void setReduction(Double reduction) {
		this.reduction = reduction;
	}

	/**
	 * calcule du {@link DetailCout} pour un {@link com.nordnet.opale.domain.commande.Tarif}.
	 * 
	 * @param tarif
	 *            {@link com.nordnet.opale.domain.commande.Tarif}.
	 * @param segmentTVA
	 *            segment TVA du client.
	 * @return {@link DetailCout}.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	private DetailCout calculerCoutTarif(com.nordnet.opale.domain.commande.Tarif tarif, String segmentTVA)
			throws OpaleException {
		DetailCout detailCout = new DetailCout();
		double coutTotal = 0d;
		if (tarif.isRecurrent()) {
			detailCout.setPlan(new Plan(tarif.getFrequence(), tarif.getPrix(), VatClient.appliquerTVA(tarif.getPrix(),
					tarif.getTypeTVA(), segmentTVA)));
		} else {
			coutTotal += tarif.getPrix();
		}
		for (com.nordnet.opale.domain.commande.Frais frais : tarif.getFrais()) {
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutTotal += frais.getMontant();
		}
		detailCout.setCoutTotal(coutTotal);
		detailCout.setCoutTotalTTC(VatClient.appliquerTVA(coutTotal, tarif.getTypeTVA(), segmentTVA));
		return detailCout;
	}

}
