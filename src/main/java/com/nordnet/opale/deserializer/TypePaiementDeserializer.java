package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.opale.enums.TypePaiement;

/**
 * Definir notre propre logique de deserialisation.
 * 
 * @author akram-moncer
 * 
 */
public class TypePaiementDeserializer extends JsonDeserializer<TypePaiement> {

	@Override
	public TypePaiement deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return TypePaiement.fromString(parser.getText().toUpperCase());
	}

}
