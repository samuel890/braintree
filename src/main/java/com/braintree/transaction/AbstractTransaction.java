package com.braintree.transaction;

import java.util.Date;

public abstract class AbstractTransaction {
	/**
	 * Abstract transaction class 
	 * - Account transaction
	 * - Charge transaction
	 * - Credit transaction
	 * 
	 * - Rollback transaction
	 * - Audit transaction
	 */
	
	private int id;
	private String name;
	private int amount;
	private String currency;
	private Date transactionDateTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getTransactionDateTime() {
		if (transactionDateTime == null) {
			transactionDateTime = new Date();
		}
		return transactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	
	@Override
	public String toString() {
		return "Transaction ID: [" + getId() + "], " 
				+ "Name: [" + getName() + "], " 
				+ "Amount: [" + getCurrency() + getAmount() + "], "
				+ "Date: [" + getTransactionDateTime().toString() + "]";
	}
	

}
