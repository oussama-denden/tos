package com.nordnet.opale.draft.test.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * classe pour generer des info a stocker dans un {@link Draft}/ {@link DraftLigne} d'un draft.
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
		List<Detail> details = new ArrayList<>();
		Detail detail = new Detail();
		detail.setReferenceSelection("kitsat");
		detail.setReferenceChoix("trafic10g");
		detail.setReferenceTarif("achat_kit_mensuel");
		details.add(detail);

		detail = new Detail();
		detail.setReferenceSelection("jet");
		detail.setReferenceChoix("trafic10g");
		detail.setReferenceTarif("achat_kit_mensuel");
		detail.setDependDe("kitsat");
		details.add(detail);

		detail = new Detail();
		detail.setReferenceSelection("tlf");
		detail.setReferenceChoix("trafic10g");
		detail.setReferenceTarif("achat_kit_mensuel");
		detail.setDependDe("kitsat");
		details.add(detail);

		offre.setDetails(details);

		Auteur auteur = new Auteur();
		auteur.setCanal("Welcome");
		auteur.setQui("GRC.WEL-JOHNDOE");
		Ip ip = new Ip();
		ip.setIp("127.0.0.1");
		ip.setTs(new Date());
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

		Detail detail = new Detail();
		detail.setReferenceSelection("option");
		detail.setReferenceChoix("trafic10g");
		detail.setReferenceTarif("achat_kit_mensuel");
		detail.setDependDe("kitsat");
		offre.getDetails().add(detail);

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
	public <T> T getObjectFromJsonFile(Class<T> valueType, String jsonFilePath)
			throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File json = new File(classLoader.getResource(jsonFilePath).getFile());
		return new ObjectMapper().readValue(json, valueType);
	}

}
