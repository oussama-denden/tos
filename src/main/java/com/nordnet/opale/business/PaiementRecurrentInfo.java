package com.nordnet.opale.business;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nordnet.opale.enums.ModePaiement;
import com.nordnet.opale.enums.deserializer.ModePaiementDeserializer;

/**
 * classe pour transferer les info de paiement recurrent.
 * 
 * @author anisselmane.
 * 
 */
public class PaiementRecurrentInfo {

	/**
	 * {@link ModePaiement}.
	 */
	@JsonDeserialize(using = ModePaiementDeserializer.class)
	private ModePaiement modePaiement;

	/**
	 * id paiement.
	 */
	private String idPaiement;

	/**
	 * constructeur par defaut.
	 */
	public PaiementRecurrentInfo() {
	}

	/**
	 * 
	 * @return {@link ModePaiement}.
	 */
	public ModePaiement getModePaiement() {
		return modePaiement;
	}

	/**
	 * 
	 * @param modePaiement
	 *            {@link ModePaiement}.
	 */
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}

	/**
	 * 
	 * @return {@link #idPaiement}
	 */
	public String getIdPaiement() {
		return idPaiement;
	}

	/**
	 * 
	 * @param idPaiement
	 *            {@link #idPaiement}
	 */
	public void setIdPaiement(String idPaiement) {
		this.idPaiement = idPaiement;
	}

}
