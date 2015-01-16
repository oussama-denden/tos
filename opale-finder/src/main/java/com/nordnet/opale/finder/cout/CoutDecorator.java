package com.nordnet.opale.finder.cout;

import org.springframework.stereotype.Component;

import com.nordnet.opale.finder.business.Cout;
import com.nordnet.opale.finder.exception.OpaleException;

/**
 * 
 * @author mahjoub-MARZOUGUI
 * 
 */
@Component("coutDecorator")
public class CoutDecorator extends CalculeCout {

	/**
	 * interface du DP decorator.
	 */
	private CalculeCout calculeCout;

	/**
	 * @throws OpaleException
	 *             {@link OpaleException}
	 * @return {@link Cout}
	 * 
	 */
	@Override
	public Cout getCout() throws OpaleException {
		return calculeCout.getCout();

	}

	/**
	 * 
	 * @return {@link CalculeCout}
	 */
	public CalculeCout getCalculeCout() {
		return calculeCout;
	}

	/**
	 * 
	 * @param calculeCout
	 *            {@link CalculeCout}
	 */
	public void setCalculeCout(CalculeCout calculeCout) {
		this.calculeCout = calculeCout;
	}

}
