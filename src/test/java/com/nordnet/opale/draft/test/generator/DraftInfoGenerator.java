package com.nordnet.opale.draft.test.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordnet.opale.business.Auteur;
import com.nordnet.opale.business.Detail;
import com.nordnet.opale.business.DraftLigneInfo;
import com.nordnet.opale.business.Ip;
import com.nordnet.opale.business.Offre;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.domain.draft.DraftLigne;
import com.nordnet.opale.enums.ModeFacturation;
import com.nordnet.opale.enums.ModePaiement;

/**
 * classe pour generer des info a stocker dans un {@link Draft}/
 * {@link DraftLigne} d'un draft.
 * 
 * @author akram-moncer
 * 
 */
@Component("draftInfoGenerator")
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
		draftLigneInfo.setUser("unit-test-user");
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

	/**
	 * Retourne une {@link DraftLigneInfo} pour la modification d'une ligne.
	 * 
	 * @return {@link DraftLigneInfo}.
	 */
	public static DraftLigneInfo getDraftLigneInfoModifier() {
		DraftLigneInfo draftLigneInfo = getDraftLigneInfo();
		Offre offre = draftLigneInfo.getOffre();
		offre.setModeFacturation(ModeFacturation.PREMIER_MOIS);
		offre.setModePaiement(ModePaiement.SEPA);

		for (Detail detail : offre.getDetails()) {
			detail.setModePaiement(ModePaiement.SEPA);
		}

		Detail detail = new Detail();
		detail.setReference("option");
		detail.setModePaiement(ModePaiement.SEPA);
		detail.setReferenceTarif("achat_kit_mensuel");
		detail.setDependDe("kitsat");
		offre.getDetails().add(detail);
		draftLigneInfo.setUser("unit-test-user");

		return draftLigneInfo;
	}

	/**
	 * Retourne une {@link Draft}.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param valueType
	 *            the value type
	 * @param jsonFilePath
	 *            the json file path
	 * @return {@link DraftLigneInfo}.
	 * @throws JsonParseException
	 *             the json parse exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public <T> T getObjectFromJsonFile(Class<T> valueType, String jsonFilePath) throws JsonParseException,
			JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File json = new File(classLoader.getResource(jsonFilePath).getFile());
		return (T) new ObjectMapper().readValue(json, valueType);
	}

}
