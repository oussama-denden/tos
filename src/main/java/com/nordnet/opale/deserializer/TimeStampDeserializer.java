package com.nordnet.opale.deserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.Constants;
import com.nordnet.opale.util.PropertiesUtil;
import com.nordnet.opale.util.Utils;

/**
 * desrialisation du timestamp.
 * 
 * @author akram-moncer
 * 
 */
public class TimeStampDeserializer extends JsonDeserializer<Date> {

	/**
	 * Declaration du log.
	 */
	private final static Logger LOGGER = Logger.getLogger(TimeStampDeserializer.class);

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		String timeStamp = parser.getText();
		Date date = null;
		if (Utils.isStringNullOrEmpty(timeStamp)) {
			try {
				date = PropertiesUtil.getInstance().getDateDuJour();
			} catch (OpaleException e) {
				LOGGER.error("erreur lors de la recuperation de la date du jour", e);
			}
		} else {
			try {
				date = new Date(Long.parseLong(timeStamp));
			} catch (NumberFormatException e) {
				try {
					date = Constants.DEFAULT_DATE_WITHOUT_TIME_FORMAT.parse(timeStamp);
				} catch (ParseException e1) {
					LOGGER.error(e.getMessage());
					LOGGER.error(e1.getMessage());
					throw new JsonMappingException(e1.getLocalizedMessage());
				}
			}
		}
		return date;
	}

}
