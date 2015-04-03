package com.nordnet.opale.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.context.MessageSource;

import com.nordnet.opale.exception.OpaleException;
import com.nordnet.opale.util.spring.ApplicationContextHolder;

/**
 * Singleton to handel topaze.proerties file.
 * 
 * @author anisselmane.
 * 
 */
public class PropertiesUtil {

	/**
	 * Instance unique de la classe.
	 */
	private static PropertiesUtil instance;

	/**
	 * Rest URL properties file.
	 */
	private final Properties opaleExceptionsProperties = ApplicationContextHolder.getBean("opaleExceptionsProperties");

	/**
	 * Dynamic properties file.
	 */
	private final MessageSource dynamicProperties = ApplicationContextHolder.getBean("dynamicProperties");

	/**
	 * private constructor.
	 */
	private PropertiesUtil() {

	}

	/**
	 * @return a single instance of the {@link PropertiesUtil}.
	 */
	public static PropertiesUtil getInstance() {
		if (instance == null)
			instance = new PropertiesUtil();
		return instance;
	}

	/**
	 * retourner un message d'erreur.
	 * 
	 * @param errorMessageKey
	 *            cle du message d'erreur.
	 * @param parameters
	 *            paramerers du message
	 * @return message d'erreur.
	 */
	public String getErrorMessage(String errorMessageKey, Object... parameters) {
		return String.format(opaleExceptionsProperties.getProperty(errorMessageKey), parameters);
	}

	/**
	 * @return LocalDateTime de jour definit dans la fichier env.properties.
	 * @throws OpaleException
	 *             {@link OpaleException}.
	 */
	public Date getDateDuJour() throws OpaleException {
		String dateDuJourString =
				dynamicProperties.getMessage(Constants.DATE_DU_JOUR_PROPERTY, null,
						Constants.DEFAULT_DATE_FORMAT.format(new Date()), null);
		if (Utils.isStringNullOrEmpty(dateDuJourString) || dateDuJourString.equals(Constants.NOW)
				|| System.getProperty(Constants.ENV_PROPERTY).equals(Constants.PROD_ENV)) {
			return new LocalDateTime().toDate();
		}
		try {
			SimpleDateFormat formatter = Constants.DEFAULT_DATE_WITHOUT_TIME_FORMAT;
			Date dateDuJour = formatter.parse(dateDuJourString);
			LocalDateTime date = new LocalDateTime(dateDuJour);
			LocalTime time = new LocalTime();
			return date.withTime(time.hourOfDay().get(), time.minuteOfHour().get(), time.secondOfMinute().get(),
					time.millisOfSecond().get()).toDate();

		} catch (Exception e) {
			throw new OpaleException("erreur lors de la recuperation de la date du jour", e);
		}
	}

	/**
	 * Recuperer la duree inactive.
	 * 
	 * @return {@link Integer}
	 */
	public Integer getDureeInactive() {
		Integer delaiInactive = Integer.valueOf(dynamicProperties.getMessage(Constants.DELAI_INACTIVE, null, null));
		return delaiInactive;
	}

}
