package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.topaze.ws.enums.TypeValeur;

/**
 * Desrialisation du type de valeur.
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class TypeValeurDeserialiser extends JsonDeserializer<TypeValeur> {

	@Override
	public TypeValeur deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		return TypeValeur.fromString(parser.getText().toUpperCase());
	}

}
