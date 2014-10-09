package com.nordnet.opale.draft.test.generator;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.Ip;
import com.nordnet.opale.business.Offre;
import com.nordnet.opale.domain.Draft;
import com.nordnet.opale.domain.DraftLigne;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;

/**
 * classe pour generer des info a stocker dans un {@link Draft}/ {@link DraftLigne} d'un draft.
 * 
 * @author akram-moncer
 * 
 */
public class DraftInfoGenerator {

	/**
	 * Gerer un {@link DraftLigneInfo}.
	 * 
	 * @return {@link DraftLigneInfo}.
	 */
	public static DraftLigneInfo getDraftLigneInfo() {
		DraftLigneInfo draftLigneInfo = new DraftLigneInfo();
		Offre offre = new Offre();
		offre.setReferenceOffre("mensuel_jet10_base");
		offre.setReferenceTarif("jet_surf10");
		offre.setModePaiement(ModePaiement.CB);
		offre.setModeFacturation(ModeFacturation.DATE_ANNIVERSAIRE);
		List<Detail> details = new ArrayList<Detail>();
		Detail detail = new Detail();
		detail.setReference("kitsat");
		detail.setModePaiement(ModePaiement.CB);
		detail.setReferenceTarif("achat_kit_mensuel");
		details.add(detail);

		detail = new Detail();
		detail.setReference("jet");
		detail.setModePaiement(ModePaiement.CB);
		detail.setReferenceTarif("achat_kit_mensuel");
		detail.setDependDe("kitsat");
		details.add(detail);

		detail = new Detail();
		detail.setReference("tlf");
		detail.setModePaiement(ModePaiement.CB);
		detail.setReferenceTarif("achat_kit_mensuel");
		detail.setDependDe("kitsat");
		details.add(detail);

		offre.setDetails(details);

		Auteur auteur = new Auteur();
		auteur.setCode("part_123456");
		auteur.setCanal("Welcome");
		auteur.setQui("GRC.WEL-JOHNDOE");
		Ip ip = new Ip();
		ip.setIp("127.0.0.1");
		ip.setTs(1411654933);
		auteur.setIp(ip);

		draftLigneInfo.setOffre(offre);
		draftLigneInfo.setAuteur(auteur);

		return draftLigneInfo;

	}

}
