package com.nordnet.opale.finder.cout;

import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.finder.business.Cout;
import com.nordnet.opale.finder.business.CoutRecurrent;
import com.nordnet.opale.finder.business.DetailCout;
import com.nordnet.opale.finder.business.Frais;
import com.nordnet.opale.finder.business.Reduction;
import com.nordnet.opale.finder.business.Tarif;
import com.nordnet.opale.finder.business.TypeFrais;
import com.nordnet.opale.finder.dao.ReductionDao;
import com.nordnet.opale.finder.exception.OpaleException;
import com.nordnet.opale.finder.util.ReductionUtil;
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
	 * detaille du ligne draft.
	 */
	private String referenceDraftLigneDetail;

	/**
	 * ligne du draft.
	 */
	private String referenceDraftLigne;

	/**
	 * reference commande.
	 */
	private String referenceDraft;

	/**
	 * indique si on va calculer le tarif d'une ligne.
	 */
	private boolean isLigne;

	/**
	 * indique si on va calculer le tarif d'un detaille ligne.
	 * 
	 */
	private boolean isDetail;

	/**
	 * {@link ReductionRepository}.
	 */
	@Autowired
	private ReductionDao reductionDao;

	/**
	 * 
	 * @param tarif
	 *            {@link Tarif}
	 * @param segmentTVA
	 *            segmant tva
	 * @param referenceDraftLigneDetail
	 *            reference Detail Ligne commande
	 * @param referenceDraftLigne
	 *            reference Ligne commande
	 * @param referenceDraft
	 *            reference commande
	 * @param isLigne
	 *            si c est une ligne
	 * @param isDetail
	 *            si c est une detail ligne
	 * @param reductionDao
	 *            {@link ReductionDao}
	 */
	public CoutTarif(Tarif tarif, String segmentTVA, String referenceDraftLigneDetail, String referenceDraftLigne,
			String referenceDraft, boolean isLigne, boolean isDetail, ReductionDao reductionDao) {
		this.tarif = tarif;
		this.segmentTVA = segmentTVA;
		this.referenceDraftLigneDetail = referenceDraftLigneDetail;
		this.referenceDraftLigne = referenceDraftLigne;
		this.referenceDraft = referenceDraft;
		this.isLigne = isLigne;
		this.isDetail = isDetail;
		this.reductionDao = reductionDao;
	}

	/**
	 * Constructeur par defaut.
	 */
	public CoutTarif() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws OpaleException
	 */
	@Override
	public Cout getCout() throws OpaleException {

		if (referenceDraft == null || tarif == null || reductionDao == null || (isLigne && referenceDraftLigne == null)
				|| (isDetail && referenceDraftLigneDetail == null)) {
			return null;
		}
		double coutComptantHT = 0.0;
		DetailCout detailCout = new DetailCout();
		double coutComptantTTC = 0d;
		if (tarif.isRecurrent()) {

			coutRecurrentTTC = VatClient.appliquerTVA(tarif.getPrix(), tarif.getTypeTVA(), segmentTVA);
		}

		else

		{

			coutComptantHT += tarif.getPrix();
		}

		if (tarif.getFrais() != null) {
			for (Frais frais : tarif.getFrais()) {

				if (frais != null && frais.getType().equals(TypeFrais.CREATION.name())) {

					coutComptantHT += frais.getMontant();

					// application du reduction.
					calculerReductionTarif(frais);
				}
			}
		}

		coutComptantTTC = VatClient.appliquerTVA(coutComptantHT, tarif.getTypeTVA(), segmentTVA);
		coutComptantReduitTTC = coutComptantTTC - reductionTTC;

		detailCout.setCoutComptantTTC(coutComptantReduitTTC);
		// detailCout.setReductionTTC(reductionTTC);
		CoutRecurrent coutRecurrent = new CoutRecurrent();
		coutRecurrent.setEngagement(tarif.getEngagement());
		coutRecurrent.setFrais(tarif.getFrais());
		coutRecurrent.setFrequence(tarif.getFrequence());
		coutRecurrent.setPrix(coutRecurrentTTC);
		coutRecurrent.setTVA(VatClient.getValeurTVA(tarif.getTypeTVA(), segmentTVA));
		detailCout.setCoutRecurrent(coutRecurrent);

		return detailCout;
	}

	/**
	 * calculer la reduction associee a un tarif.
	 * 
	 * @param frais
	 *            frais creation du tarif.
	 * @throws OpaleException
	 *             {@link OpaleException}
	 */
	private void calculerReductionTarif(Frais frais) throws OpaleException {
		Reduction reduction = null;
		if (isLigne) {
			reduction =
					reductionDao.findReductionLigneFrais(referenceDraft, referenceDraftLigne, frais.getReference(),
							tarif.getReference());
		} else if (isDetail) {
			reduction =
					reductionDao.findReductionLigneDetailleFrais(referenceDraft, referenceDraftLigne,
							referenceDraftLigneDetail, frais.getReference(), tarif.getReference());

		}
		reductionTTC =
				ReductionUtil.calculeReductionComptant(
						VatClient.appliquerTVA(frais.getMontant(), tarif.getTypeTVA(), segmentTVA), reduction);

		reductionComptantTTC = reductionTTC;
	}

}
