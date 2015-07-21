package com.braintree.creditcard;


public class CreditCard {
	private String number;
	private String name;
	private int balance;
	private int limit;
	private String currency;
	// default as active
	private CreditCardStatus status = CreditCardStatus.ACTIVE;
	
//	private String securityCode;
//	private Date expirationDate;
//	private String zipCode;
	
	public CreditCard() {
		
	}
	
	public CreditCard(CreditCardBuilder builder) {
		this.number = builder.number;
		this.name = builder.name;
		this.currency = builder.currency;
		this.balance = builder.balance;
		this.limit = builder.limit;
		this.status = builder.status;
	}
 	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public CreditCardStatus getStatus() {
		return status;
	}

	public void setStatus(CreditCardStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int ret = 1;
		ret = prime * ret + ((name == null) ? 0 : name.hashCode());
		ret = prime * ret + ((number == null) ? 0 : number.hashCode());
		ret = prime * ret + balance;
		ret = prime * ret + limit;
		ret = prime * ret + ((status == null) ? 0 : status.hashCode());
		return ret;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		CreditCard other = (CreditCard) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number)) {
			return false;
		}
		
		if (balance != other.balance) {
			return false;
		}
		
		if (limit != other.limit) {
			return false;
		}

		if (status != other.status)
			return false;

		return true;
		
	}
	
	public static class CreditCardBuilder {
		private String name;
		private String number;
		private String currency;
		private int balance;
		private int limit;
		private CreditCardStatus status;
		
		public CreditCardBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public CreditCardBuilder number(String number) {
			this.number = number;
			return this;
		}
		
		public CreditCardBuilder currency(String currency) {
			this.currency = currency;
			return this;
		}
		
		public CreditCardBuilder balance(int balance) {
			this.balance = balance;
			return this;
		}
		
		public CreditCardBuilder limit(int limit) {
			this.limit = limit;
			return this;
		}
		
		public CreditCardBuilder status(CreditCardStatus status) {
			this.status = status;
			return this;
		}
		
		public CreditCard build() {
			return new CreditCard(this);
		}
	}
	
}
