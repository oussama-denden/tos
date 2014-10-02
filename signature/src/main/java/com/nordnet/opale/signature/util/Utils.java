package com.nordnet.opale.signature.util;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;

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
	 * @return <code>true</code> if the string is null or empty,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isStringNullOrEmpty(String str) {
		return (str == null) ? true : str.trim().length() == 0;
	}

	/**
	 * Tests if a list is null or empty.
	 * 
	 * @param list
	 *            the list of objects
	 * @return <code>false</code> if the list is null or empty,
	 *         <code>true</code> otherwise.
	 */
	public static boolean isListNullOrEmpty(List<?> list) {
		return (list == null) ? true : list.size() == 0;
	}

	/**
	 * Comparer deux date.
	 * 
	 * @param firstDate
	 * @param seconDate
	 * @return int (-1 first date is less then second date, 0 equals , first
	 *         date is great then second date)
	 */
	public static int compareDate(Date firstDate, Date seconDate) {
		LocalDate firstLocalDate = new LocalDate(firstDate);
		LocalDate seconLocalDate = new LocalDate(seconDate);
		return firstLocalDate.compareTo(seconLocalDate);
	}

	/**
	 * Check string contain whitespace.
	 * 
	 * @param str
	 *            the string.
	 * @return <code>true</code> if the string contain whitespace,
	 *         <code>false</code> otherwise.
	 */
	public static boolean containsWhiteSpace(final String str) {
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(str.trim());
		return matcher.find();
	}

	public static Date dateMinusMonth(Date date, int i) {
		if (date != null) {
			return LocalDate.fromDateFields(date).minusMonths(i).toDate();
		}
		return null;
	}

}