package com.nordnet.opale.draft.test.generator;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.AjoutSignatureInfo;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.AuteurInfo;
import com.nordnet.opale.business.Ip;
import com.nordnet.opale.business.SignatureInfo;
import com.nordnet.opale.enums.ModeSignature;

/**
 * classe pour generer des {@link SignatureInfo} et {@link AjoutSignatureInfo}.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Component("signatureInfoGenerator")
public class SignatureInfoGenerator {

	/**
	 * generer un ajout signature info.
	 * 
	 * @return {@link AjoutSignatureInfo}
	 */
	public static AjoutSignatureInfo getAjoutSignatureInfo() {
		AjoutSignatureInfo ajoutSignatureInfo = new AjoutSignatureInfo();
		ajoutSignatureInfo.setMode(ModeSignature.OPEN_TRUST);
		ajoutSignatureInfo.setAuteur(getAuteur());

		return ajoutSignatureInfo;

	}

	/**
	 * generer un signature info.
	 * 
	 * @return {@link SignatureInfo}
	 */
	public static SignatureInfo getSignatureInfo() {
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setAuteur(getAuteur());
		signatureInfo.setMode(ModeSignature.OPEN_TRUST);
		signatureInfo.setIdSignature("opentrust_dfr2225tr3555");
		signatureInfo.setTimestamp(new Date());
		signatureInfo.setFootprint("e2680542f77606f42f1cb06d6");
		return signatureInfo;

	}

	/**
	 * generer un auteur info.
	 * 
	 * 
	 * @return {@link AuteurInfo}
	 */
	private static Auteur getAuteur() {
		Auteur auteur = new Auteur();
		Ip ip = new Ip();
		ip.setIp("127.0.0.1");
		ip.setTs(1411654933);
		auteur.setCanal("welcome");
		auteur.setIp(ip);
		auteur.setQui("GRC.WEL-JOHNDOE");

		return auteur;

	}

}
