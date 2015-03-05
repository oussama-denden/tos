package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.opale.controller.DraftController;
import com.nordnet.opale.domain.draft.Draft;
import com.nordnet.opale.enums.Geste;

/**
 * Definir notre propre logique de deserialisation que sera utiliser par jackson
 * lors de auto-population de {@link Geste}. Cette classe sera utiliser
 * notamment par les controlleur {@link DraftController} pour l'auto-population
 * de {@link Draft} a partir des parametres de requete HttpServletRequest.
 * 
 * @author Denden-OUSSAMA
 * 
 */
public class GesteDeserializer extends JsonDeserializer<Geste> {

	@Override
	public Geste deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		return Geste.fromString(parser.getText().toUpperCase());
	}

}
