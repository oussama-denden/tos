package com.nordnet.opale.draft.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

import com.nordnet.opale.util.spring.ApplicationContextHolder;

/**
 * Abstract class for Test classes.
 * <p>
 * All test classes must inherit this class.
 * 
 * @author anisselmane.
 * 
 */
@SpringApplicationContext(value = { "applicationContextTest.xml" })
public abstract class GlobalTestCase extends UnitilsJUnit4 {

	/**
	 * Logger de la classe.
	 */
	private static final Log LOGGER = LogFactory.getLog(GlobalTestCase.class);

	/**
	 * Constructeur par défaut.
	 */
	public GlobalTestCase() {
		super();
		init();
	}

	/**
	 * Spring context.
	 */
	public static void beforeClass() {

		LOGGER.info("Spring context : " + ApplicationContextHolder.getContext());
	}

	/**
	 * Memory dump.
	 */
	public static void afterClass() {

		LOGGER.info("Free memory  : " + Runtime.getRuntime().freeMemory() + " bytes");
		LOGGER.info("Max memory   : " + Runtime.getRuntime().maxMemory() + " bytes");
		LOGGER.info("Total memory : " + Runtime.getRuntime().totalMemory() + " bytes");
	}

	/**
	 * init methode.
	 */
	public void init() {
		System.setProperty("references.option.non.envoyer.packager", "option.plus.50g,option.plus.20g");
		System.setProperty("reference.voip", "voip");
		System.setProperty("reference.option.plus.20g", "option.plus.20g");
		System.setProperty("reference.option.plus.50g", "option.plus.50g");
		System.setProperty(com.nordnet.opale.util.Constants.ENV_PROPERTY, "dev");
		System.setProperty("ws.netEquipment.useMock", "true");
	}

}