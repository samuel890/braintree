package com.braintree.transaction;

import java.util.List;

public interface TransactionReader {
	
	/**
	 * Transaction reader can have multiple sources
	 * - Command line transaction reader
	 * - File transaction reader
	 * 
	 * @return
	 * @throws Exception
	 */
	
	List<AbstractTransaction> readTransactions() throws Exception;
}
