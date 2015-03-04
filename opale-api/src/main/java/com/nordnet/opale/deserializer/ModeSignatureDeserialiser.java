package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.opale.enums.ModeSignature;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
public class ModeSignatureDeserialiser extends JsonDeserializer<ModeSignature> {

	@Override
	public ModeSignature deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		return ModeSignature.fromSting(parser.getText().toUpperCase());
	}

}
