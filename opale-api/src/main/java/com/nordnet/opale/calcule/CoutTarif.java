package com.nordnet.opale.calcule;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.business.CoutRecurrent;
import com.nordnet.opale.business.DetailCout;
import com.nordnet.opale.business.Plan;
import com.nordnet.opale.business.catalogue.Frais;
import com.nordnet.opale.business.catalogue.Tarif;
import com.nordnet.opale.enums.TypeFrais;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.vat.client.VatClient;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CoutTarif extends CalculeCout {

	/**
	 * tarif.
	 */
	private Tarif tarif;

	/**
	 * segement TVA.
	 */
	private String segmentTVA;

	/**
	 * constructeur par defaut.
	 */
	public CoutTarif() {
	}

	/**
	 * constructeur avec param.
	 * 
	 * @param tarif
	 *            {@link #tarif}
	 * 
	 * @param segmentTVA
	 *            {@link #segmentTVA}
	 */
	public CoutTarif(Tarif tarif, String segmentTVA) {
		this.tarif = tarif;
		this.segmentTVA = segmentTVA;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {
		DetailCout detailCout = new DetailCout();
		double coutComptant = 0d;
		if (tarif.isRecurrent()) {
			Plan normal =
					new Plan(tarif.getPrix(), VatClient.appliquerTVA(tarif.getPrix(), tarif.getTva(), segmentTVA));
			detailCout.setCoutRecurrent(new CoutRecurrent(tarif.getFrequence(), normal, null));
			// detailCout.setPlan(new Plan(tarif.getFrequence(), tarif.getPrix(),
			// VatClient.appliquerTVA(tarif.getPrix(),
			// tarif.getTva(), segmentTVA)));
		} else {
			coutComptant += tarif.getPrix();
		}
		for (Frais frais : tarif.getFrais()) {
			if (frais.getTypeFrais() == TypeFrais.CREATION)
				coutComptant += frais.getMontant();
		}
		detailCout.setCoutComptantHT(coutComptant);
		detailCout.setCoutComptantTTC(VatClient.appliquerTVA(coutComptant, tarif.getTva(), segmentTVA));

		return detailCout;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoutTTC() throws OpaleException {
		return getCout().getCoutComptantTTC();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getCoutHT() throws OpaleException {
		return getCout().getCoutComptantHT();
	}
}
