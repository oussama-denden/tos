package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.opale.business.catalogue.DetailCatalogue;
import com.nordnet.opale.enums.TypeProduit;

/**
 * Definir notre propre logique de deserialisation que sera utiliser par jackson
 * lors de auto-population de {@link TypeProduit}. Cette classe sera utiliser
 * notamment par les controlleur pour l'auto-population de
 * {@link DetailCatalogue} a partir des parametres de requete
 * HttpServletRequest.
 * 
 * @author anisselmane.
 * 
 */
public class TypeProduitDeserializer extends JsonDeserializer<TypeProduit> {

	@Override
	public TypeProduit deserialize(JsonParser parser, DeserializationContext context) throws IOException,
			JsonProcessingException {
		return TypeProduit.fromString(parser.getText().toUpperCase());
	}

}
