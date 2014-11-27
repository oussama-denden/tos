package com.nordnet.opale.finder.contrat.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.nordnet.opale.finder.business.Commande;
import com.nordnet.opale.finder.service.CommandeService;
import com.nordnet.opale.finder.test.GlobalTestCase;
import com.nordnet.opale.finder.test.utils.TopazeMultiSchemaXmlDataSetFactory;

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
	@SpringBean("commandeService")
	CommandeService commandeService;

	/**
	 * Test execute.
	 */
	@Test
	@DataSet(factory = TopazeMultiSchemaXmlDataSetFactory.class, value = "/dataset/findCommandeTest.xml")
	public void testFindAllValide() {

		try {
			Date d1 = new Date();
			List<Commande> commandes = commandeService.findByIdClient("1");
			Date d2 = new Date();
			System.out.println("Find All Contrat in " + (d2.getTime() - d1.getTime()) + "ms");
			assertEquals(3214, commandes.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test execute.
	 */
	@Test
	@DataSet(factory = TopazeMultiSchemaXmlDataSetFactory.class, value = "/dataset/findCommandeTest.xml")
	public void testFindValide() {

		try {
			Date d1 = new Date();
			List<Commande> commandes = commandeService.findByIdClient(2, 50, "1");
			Date d2 = new Date();
			System.out.println("Find All Contrat in " + (d2.getTime() - d1.getTime()) + "ms");
			assertEquals(50, commandes.size());
			assertEquals(commandes.get(0).getReference(), "Cmd-00000051");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}