package com.nordnet.opale.business;

/**
 * Cette classe regroupe les informations qui definissent un {@link CodePartenaireInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class CodePartenaireInfo {

	/**
	 * le code partenaire.
	 */
	private String codePartenaire;

	/**
	 * constructeur par defaut.
	 */
	public CodePartenaireInfo() {

	}

	/**
	 * the code partenaire.
	 * 
	 * @return {@link #codePartenaire}
	 */
	public String getCodePartenaire() {
		return codePartenaire;
	}

	/**
	 * set the code partenaire.
	 * 
	 * @param codePartenaire
	 *            the new {@link #codePartenaire}
	 */
	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
	}

}
