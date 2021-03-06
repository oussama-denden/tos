package com.nordnet.opale.finder.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.logging.LogFactory;

/**
 * Utils class.
 * 
 * @author anisselmane.
 * 
 */
public final class Utils {

	/**
	 * Logger of the service.
	 */
	private static final org.apache.commons.logging.Log LOGGER = LogFactory.getLog(Utils.class);

	/**
	 * Default constructor.
	 */
	private Utils() {
		LOGGER.info("Default constructor.");
	}

	/**
	 * Check string is null or empty.
	 * 
	 * @param str
	 *            the string.
	 * @return <code>true</code> if the string is null or empty, <code>false</code> otherwise.
	 */
	public static boolean isStringNullOrEmpty(String str) {
		return (str == null) ? true : str.trim().length() == 0;
	}

	/**
	 * Tests if a list is null or empty.
	 * 
	 * @param list
	 *            the list of objects
	 * @return <code>false</code> if the list is null or empty, <code>true</code> otherwise.
	 */
	public static boolean isListNullOrEmpty(List<?> list) {
		return (list == null) ? true : list.size() == 0;
	}

	/**
	 * Arrondir a deux chiffres apres la virgule.
	 * 
	 * @param value
	 *            double value.
	 * @return rounded value.
	 */
	public static double arroundiNombre(double value) {

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		bd = bd.setScale(Constants.DEUX, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
