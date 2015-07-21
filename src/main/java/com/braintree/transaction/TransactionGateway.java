package com.braintree.transaction;

import java.util.List;


public interface TransactionGateway {
	
//	CreditCard updateCreditCard(CreditCard card) throws Exception;
//	void removeCreditCard() throws Exception;
//	AbstractTransaction processTransaction(CreditCard card, AbstractTransaction transaction) throws Exception;
	
	void process(List<AbstractTransaction> transactions) throws Exception;
	void process(AbstractTransaction transaction) throws Exception;
	void summarize();
}
