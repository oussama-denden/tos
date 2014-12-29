package com.nordnet.opale.business;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nordnet.opale.deserializer.ModePaiementDeserializer;
import com.nordnet.opale.domain.paiement.Paiement;
import com.nordnet.opale.serializer.DateSerializer;
import com.nordnet.topaze.ws.enums.ModePaiement;

/**
 * classe pour transferer les info de paiement recurrent.
 * 
 * @author anisselmane.
 * 
 */
public class PaiementRecurrentInfo {

	/**
	 * reference paiement.
	 */
	private String reference;
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
	 * date de paiement.
	 */
	@JsonSerialize(using = DateSerializer.class)
	private Date timestamp;

	/**
	 * constructeur par defaut.
	 * 
	 */
	public PaiementRecurrentInfo() {
	}

	/**
	 * constructeur parametre.
	 * 
	 * @param paiement
	 *            {@link Paiement}
	 */
	public PaiementRecurrentInfo(Paiement paiement) {
		this.idPaiement = paiement.getIdPaiement();
		this.modePaiement = paiement.getModePaiement();
		this.reference = paiement.getReference();
		this.timestamp = paiement.getTimestampPaiement();
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

	/**
	 * 
	 * @return {@link #reference}
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            {@link #reference}
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * 
	 * @return {@link #timestamp}
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @param timestamp
	 *            {@link #timestamp}
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
