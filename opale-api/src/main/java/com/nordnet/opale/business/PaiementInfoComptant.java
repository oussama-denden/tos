package com.nordnet.opale.business;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * classe pour transferer les info de paiement.
 * 
 * @author akram-moncer
 * 
 */
@JsonInclude(Include.NON_NULL)
public class PaiementInfoComptant extends PaiementInfo {

	/**
	 * RUM.
	 */
	private String rum;

	/**
	 * reference mode paiement.
	 */
	private String referenceModePaiement;

	/**
	 * constructeur par defaut.
	 */
	public PaiementInfoComptant() {
	}

	/**
	 * constructeur parametre.
	 * 
	 * @param modePaiement
	 *            {@link #modePaiement}
	 * @param montant
	 *            {@link #montant}
	 * @param timestampIntention
	 *            {@link #timestampIntention}
	 * @param timestampPaiement
	 *            {@link #timestampPaiement}
	 * @param referenceModePaiement
	 *            {@link #referenceModePaiement}
	 */
	public PaiementInfoComptant(ModePaiement modePaiement, Double montant, Date timestampIntention,
			Date timestampPaiement, String rum, String referenceModePaiement) {
		super(modePaiement, montant, timestampIntention, timestampPaiement);
		this.referenceModePaiement = referenceModePaiement;
		this.rum = rum;

	}

	/**
	 * 
	 * @return {@link #referenceModePaiement }
	 */
	public String getReferenceModePaiement() {
		return referenceModePaiement;
	}

	/**
	 * 
	 * @param referenceModePaiement
	 *            {@link #referenceModePaiement }
	 */
	public void setReferenceModePaiement(String referenceModePaiement) {
		this.referenceModePaiement = referenceModePaiement;
	}

	/**
	 * @return {@link #rum}.
	 */
	public String getRum() {
		return rum;
	}

	/**
	 * @param rum
	 *            {@link #rum}.
	 */
	public void setRum(String rum) {
		this.rum = rum;
	}

}
