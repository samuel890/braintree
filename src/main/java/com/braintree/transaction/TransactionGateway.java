package com.braintree.transaction;

import java.util.List;


public interface TransactionGateway {
	
	void process(List<AbstractTransaction> transactions) throws Exception;
	void process(AbstractTransaction transaction) throws Exception;
	void summarize();
}
