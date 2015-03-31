package com.nordnet.opale.finder.contrat.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.service.CommandeService;
import com.nordnet.opale.finder.test.GlobalTestCase;

/**
 * class tests {@link CommandeService#findByIdClient(String)}.
 * 
 * @author anisselmane.
 * 
 */
public class FindCommandeTest extends GlobalTestCase {

	/**
	 * Commande service.
	 */
	@Autowired
	CommandeService commandeService;

	/**
	 * Test execute.
	 */
	@Test
	public void testFindAllValide() {

		try {
			System.setProperty("ws.nordNetVat.useMock", "true");
			Date d1 = new Date();
			List<Commande> commandes = commandeService.findByIdClient("000003");
			Date d2 = new Date();
			System.out.println("Find All Contrat in " + (d2.getTime() - d1.getTime()) + "ms");
			assertEquals(6, commandes.size());

		} catch (Exception e) {
			Assert.fail("Unexpected Error");
		}

	}

}
