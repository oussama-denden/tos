package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.opale.enums.TypeTVA;

/**
 * Definir notre propre logique de deserialisation que sera utiliser par jackson lors de auto-population de
 * {@link TypePrix}. Cette classe sera utiliser notamment par les controlleur {@link ProduitController} pour
 * l'auto-population de {@link Produit} a partir des parametres de requete HttpServletRequest.
 * 
 * @author Denden-OUSSAMA
 * 
 */
public class TypeTVADeserializer extends JsonDeserializer<TypeTVA> {

	@Override
	public TypeTVA deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		return TypeTVA.fromString(parser.getText().toUpperCase());
	}

}
