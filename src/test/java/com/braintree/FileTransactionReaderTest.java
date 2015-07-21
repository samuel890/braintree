package com.braintree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintree.transaction.AbstractTransaction;
import com.braintree.transaction.FileTransactionReader;

public class FileTransactionReaderTest {
	
	private static String basePath = "";
	@BeforeClass
	public static void onceExecuteBeforeAll() {
		File currentDirectory = new File(".");
		String currentPath = currentDirectory.getAbsolutePath();
		basePath = currentPath.substring(0, currentPath.length() - 1);
	} 
	
	
	@Test(expected = FileNotFoundException.class)
	public void testWithFileNotExsit() throws Exception {
		String path = "";
		FileTransactionReader ftr = new FileTransactionReader(path);
		ftr.readTransactions();
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testWithDirectoryPath() throws Exception {
		String path = "/usr/bin";
		FileTransactionReader ftr = new FileTransactionReader(path);
		ftr.readTransactions();
	}
	
	@Test
	public void testWithUnsupportedCommand() throws Exception {
		String path = basePath + "resource/file_transaction_with_unsupported_command.txt";
		FileTransactionReader ftr = new FileTransactionReader(path);
		List<AbstractTransaction> transactions = ftr.readTransactions();
		Assert.assertTrue(transactions.size() == 3);
	}
	
	@Test
	public void testWithInvalidMoneyNumber() throws Exception {
		String path = basePath + "resource/file_transaction_with_invalid_money_number.txt";
		FileTransactionReader ftr = new FileTransactionReader(path);
		List<AbstractTransaction> transactions = ftr.readTransactions();
		Assert.assertEquals(0, transactions.size());
	}
	
	
	@Test
	public void testWithSuccessCase() throws Exception{
		String path = basePath + "resource/file_transaction_with_success.txt";
		FileTransactionReader ftr = new FileTransactionReader(path);
		List<AbstractTransaction> transactions = ftr.readTransactions();
		Assert.assertEquals(8, transactions.size());
	}
	
	

}
