package com.braintree.transaction;

import com.braintree.transaction.AbstractTransaction;

public class AccountTransaction extends AbstractTransaction {
	private String creditCard;
	
	public AccountTransaction() {
		super();
	}
	
	public String getCreditCard(){
		return creditCard;
	}
	
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
}

