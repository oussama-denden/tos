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
public class PaiementInfoRecurrent extends PaiementInfo {

	/**
	 * RUM.
	 */
	private String rum;

	/**
	 * constructeur par defaut.
	 */
	public PaiementInfoRecurrent() {
	}

	/**
	 * constructeur parametre.
	 * 
	 * @param modePaiement
	 *            {@link #modePaiement}
	 * @param montant
	 *            {@link #montant}
	 * @param infoPaiement
	 *            {@link #infoPaiement}
	 * @param timestampIntention
	 *            {@link #timestampIntention}
	 * @param timestampPaiement
	 *            {@link #timestampPaiement}
	 * @param rum
	 *            {@link #rum}
	 */
	public PaiementInfoRecurrent(ModePaiement modePaiement, Double montant, String infoPaiement,
			Date timestampIntention, Date timestampPaiement, String rum) {
		super(modePaiement, montant, infoPaiement, timestampIntention, timestampPaiement);
		this.rum = rum;
	}

	/**
	 * 
	 * @return {@link #rum}
	 */
	public String getRum() {
		return rum;
	}

	/**
	 * 
	 * @param rum
	 *            {@link #rum}
	 */
	public void setRum(String rum) {
		this.rum = rum;
	}

}
