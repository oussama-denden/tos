package com.nordnet.opale.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nordnet.common.valueObject.constants.VatType;
import com.nordnet.topaze.ws.entity.Produit;

/**
 * Definir notre propre logique de deserialisation que sera utiliser par jackson lors de auto-population de
 * {@link TypePrix}. Cette classe sera utiliser notamment par les controlleur {@link ProduitController} pour
 * l'auto-population de {@link Produit} a partir des parametres de requete HttpServletRequest.
 * 
 * @author Denden-OUSSAMA
 * 
 */
public class VATTypeDeserializer extends JsonDeserializer<VatType> {

	@Override
	public VatType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		switch (parser.getText().toUpperCase()) {
		case "S":
			return VatType.S;
		case "R":
			return VatType.R;
		case "SR":
			return VatType.SR;
		case "P":
			return VatType.P;

		}
		return null;
	}

}
