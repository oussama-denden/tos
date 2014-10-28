package com.nordnet.opale.business;


/**
 * Cette classe regroupe les informations qui definissent un {@link Auteur}.
 * 
 * @author anisselmane.
 * 
 */
public class Auteur {

	/**
	 * code auteur.
	 */
	private String codePartenaire;

	/**
	 * nom de l auteur.
	 */
	private String qui;

	/**
	 * Le canal (exemple : Welcome).
	 */
	private String canal;

	/**
	 * l'ip de l auteur.
	 */
	private Ip ip;

	/**
	 * constructeur par defaut.
	 */
	public Auteur() {

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


	/**
	 * 
	 * @return {@link Auteur#qui}.
	 */
	public String getQui() {
		return qui;
	}

	/**
	 * 
	 * @param qui
	 *            {@link Auteur#qui}.
	 */
	public void setQui(String qui) {
		this.qui = qui;
	}

	/**
	 * 
	 * @return {@link #canal}.
	 */
	public String getCanal() {
		return canal;
	}

	/**
	 * 
	 * @param canal
	 *            {@link #canal}.
	 */
	public void setCanal(String canal) {
		this.canal = canal;
	}

	/**
	 * 
	 * @return {@link #ip}.
	 */
	public Ip getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            {@link #ip}.
	 */
	public void setIp(Ip ip) {
		this.ip = ip;
	}

	/**
	 * convertir un {@link Auteur} en {@link com.nordnet.opale.domain.Auteur}.
	 * 
	 * @return {@link com.nordnet.opale.domain.Auteur}.
	 */
	public com.nordnet.opale.domain.Auteur toDomain() {
		com.nordnet.opale.domain.Auteur auteur = new com.nordnet.opale.domain.Auteur();
		auteur.setCanal(canal);
		auteur.setCodePartenaire(codePartenaire);
		auteur.setIp(ip.getIp());
		auteur.setQui(qui);
		auteur.setTimestamp(ip.getTs());

		return auteur;
	}

}
