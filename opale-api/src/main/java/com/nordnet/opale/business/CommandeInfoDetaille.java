package com.nordnet.opale.business;

import java.util.List;

import com.nordnet.opale.domain.commande.Commande;

/**
 * Classe contient les info detaillees d'une {@link Commande}.
 * 
 * @author akram-moncer
 * 
 */
public class CommandeInfoDetaille extends CommandeInfo {

	/**
	 * {@link PaiementInfo}.
	 */
	private List<PaiementInfo> paiements;

	/**
	 * {@link SignatureInfo}.
	 */
	private SignatureInfo signature;

	/**
	 * constructeur par defaut.
	 */
	public CommandeInfoDetaille() {

	}

	/**
	 * 
	 * @param commandeInfo
	 *            {@link CommandeInfo}.
	 */
	public CommandeInfoDetaille(CommandeInfo commandeInfo) {
		setAuteur(commandeInfo.getAuteur());
		setCodePartenaire(commandeInfo.getCodePartenaire());
		setLignes(commandeInfo.getLignes());
		setAnnule(commandeInfo.isAnnule());
	}

	/**
	 * 
	 * @return {@link #paiements}.
	 */
	public List<PaiementInfo> getPaiements() {
		return paiements;
	}

	/**
	 * 
	 * @param paiements
	 *            {@link #paiements}.
	 */
	public void setPaiements(List<PaiementInfo> paiements) {
		this.paiements = paiements;
	}

	/**
	 * 
	 * @return {@link #signature}.
	 */
	public SignatureInfo getSignature() {
		return signature;
	}

	/**
	 * 
	 * @param signature
	 *            {@link #signature}.
	 */
	public void setSignature(SignatureInfo signature) {
		this.signature = signature;
	}

}
