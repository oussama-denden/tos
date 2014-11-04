package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.opale.enums.TypeFrais;

/**
 * 
 * @author akram-moncer
 * 
 */
public class TypeFraisDeserializer extends JsonDeserializer<TypeFrais> {

	@Override
	public TypeFrais deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		return TypeFrais.fromSting(parser.getText().toUpperCase());
	}

}
