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
	 * {@link Plan}.
	 */
	private CoutRecurrent coutRecurrent;

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
		double tarifHT = 0d;
		double tarifTTC = 0d;
		Integer frequence = null;
		com.nordnet.opale.domain.commande.Tarif tarif = null;
		for (CommandeLigneDetail commandeLigneDetail : commandeLigne.getCommandeLigneDetails()) {
			tarif = commandeLigneDetail.getTarif();
			if (tarif != null) {
				DetailCout detailCoutTarif = calculerCoutTarif(tarif, segmentTVA);
				coutComptantHT += detailCoutTarif.getCoutComptantHT();
				coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
				tarifHT +=
						detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
								.getNormal().getTarifHT() : 0d;
				tarifTTC +=
						detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
								.getNormal().getTarifTTC() : 0d;
				frequence = tarif.getFrequence();
			}
		}

		tarif = commandeLigne.getTarif();
		if (tarif != null) {
			DetailCout detailCoutTarif = calculerCoutTarif(tarif, segmentTVA);
			coutComptantHT += detailCoutTarif.getCoutComptantHT();
			coutComptantTTC += detailCoutTarif.getCoutComptantTTC();
			tarifHT +=
					detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
							.getNormal().getTarifHT() : 0d;
			tarifTTC +=
					detailCoutTarif.getCoutRecurrent().getNormal() != null ? detailCoutTarif.getCoutRecurrent()
							.getNormal().getTarifTTC() : 0d;
			frequence = tarif.getFrequence();
		}

		if (tarifHT > Constants.ZERO) {
			Plan normal = new Plan(tarifHT, tarifTTC);
			this.coutRecurrent = new CoutRecurrent(frequence, normal, null);
		}
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
		double coutComptant = 0d;
		if (tarif.isRecurrent()) {
			Plan normal =
					new Plan(tarif.getPrix(), VatClient.appliquerTVA(tarif.getPrix(), tarif.getTypeTVA(), segmentTVA));
			detailCout.setCoutRecurrent(new CoutRecurrent(tarif.getFrequence(), normal, null));
		} else {
			coutComptant += tarif.getPrix();
		}
		for (com.nordnet.opale.domain.commande.Frais frais : tarif.getFrais()) {
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutComptant += frais.getMontant();
		}
		detailCout.setCoutComptantHT(coutComptant);
		detailCout.setCoutComptantTTC(VatClient.appliquerTVA(coutComptant, tarif.getTypeTVA(), segmentTVA));
		return detailCout;
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
	 * @return {@link #coutComptantHT}
	 */
	public double getCoutComptantHT() {
		return coutComptantHT;
	}

	/**
	 * 
	 * @param coutComptantHT
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
	 * @return {@link #reductionHT}
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

	/**
	 * 
	 * @return {@link #coutRecurrent}
	 */
	public CoutRecurrent getCoutRecurrent() {
		return coutRecurrent;
	}

	/**
	 * 
	 * @param coutRecurrent
	 *            {@link #coutRecurrent}
	 */
	public void setCoutRecurrent(CoutRecurrent coutRecurrent) {
		this.coutRecurrent = coutRecurrent;
	}

}
