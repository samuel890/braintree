package com.braintree;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintree.creditcard.CreditCard;
import com.braintree.creditcard.CreditCardStatus;
import com.braintree.transaction.AbstractTransaction;
import com.braintree.transaction.FileTransactionReader;
import com.braintree.transaction.TransactionProcessor;

public class AppTest {
	
	public static String basePath = "";
	
	@BeforeClass
	public static void onceExecuteBeforeAll() {
		File currentDirectory = new File(".");
		String currentPath = currentDirectory.getAbsolutePath();
		basePath = currentPath.substring(0, currentPath.length() - 1);
	}
	
	@Test 
	public void testWithSuccess() throws Exception{
		String path = basePath + "resource/file_transaction_with_success.txt";
		FileTransactionReader ftr = new FileTransactionReader(path);
		List<AbstractTransaction> transactions = ftr.readTransactions();
		TransactionProcessor processor = new TransactionProcessor();
		processor.process(transactions);
		TreeMap<String, CreditCard> cards = processor.getSummary();

		// total number of transaction
		Assert.assertEquals(8, transactions.size());
		// total number of customer
		Assert.assertEquals(3, cards.size());

		// Tom's balance 500
		Assert.assertEquals(500, cards.get("Tom").getBalance());
		// Quincy's credit card is invalid
		Assert.assertEquals(CreditCardStatus.ERROR, cards.get("Quincy").getStatus());
	}
	
	@Test
	public void testWithLimitExceed() throws Exception {
		String path =  basePath + "resource/transaction_with_limit_exceed.txt";
		FileTransactionReader ftr = new FileTransactionReader(path);
		List<AbstractTransaction> transactions = ftr.readTransactions();
		TransactionProcessor processor = new TransactionProcessor();
		processor.process(transactions);
		List<String> logs = processor.getLogs();
		String expectedMessage = "[2002, Limit Exceeded] Tom's charge transaction with 800 declined as credit exceeded limit.";
		Assert.assertTrue(logs.contains(expectedMessage));
	}
	
	@Test
	public void testWithNegativeBalance() throws Exception {
		String path = basePath + "resource/transaction_with_limit_exceed.txt";
		FileTransactionReader ftr = new FileTransactionReader(path);
		List<AbstractTransaction> transactions = ftr.readTransactions();
		TransactionProcessor processor = new TransactionProcessor();
		processor.process(transactions);
		TreeMap<String, CreditCard> cards = processor.getSummary();
		Assert.assertEquals(-93, cards.get("Lisa").getBalance());
	}
	
}
