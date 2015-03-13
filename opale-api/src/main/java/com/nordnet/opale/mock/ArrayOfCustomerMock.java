package com.nordnet.opale.mock;

import java.util.ArrayList;
import java.util.List;

import com.nordnet.mandatelibrary.ws.types.ArrayOfCustomer;
import com.nordnet.mandatelibrary.ws.types.Customer;

public class ArrayOfCustomerMock extends ArrayOfCustomer {

	@Override
	public List<Customer> getCustomer() {
		Customer customer = new Customer();
		customer.setCustomerKey("customerKey");
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		return customers;
	}

}
