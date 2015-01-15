package com.nordnet.opale.calcule;

import org.springframework.stereotype.Component;

import com.nordnet.opale.business.Cout;
import com.nordnet.opale.exception.OpaleException;

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
	 * {@link CalculeCout}
	 * 
	 * @param calculeCout
	 */
	public void setCalculeCout(CalculeCout calculeCout) {
		this.calculeCout = calculeCout;
	}

}
