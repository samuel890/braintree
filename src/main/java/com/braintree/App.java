package com.braintree;

import java.io.File;
import java.util.List;

import com.braintree.transaction.AbstractTransaction;
import com.braintree.transaction.CommandLineTransactionReader;
import com.braintree.transaction.FileTransactionReader;
import com.braintree.transaction.TransactionProcessor;

public class App {
	public static void main(String[] args) throws Exception {
		// if argument list provide, assume it's file path
		// else treat it as command line credit processor
		List<AbstractTransaction> transactions = null;
		
		if (args.length > 0) {
			try {
				System.out.println("You are in credit card file process mode:");
				String path = args[0];
				File f = new File(path);
				if(!f.exists() || f.isDirectory()) {
					System.out.println(String.format("Can not find file with path %s", path));
					System.exit(-1);
				}
				
				FileTransactionReader ftr = new FileTransactionReader(path);
				transactions = ftr.readTransactions();
			} catch (Exception e) {
				
			}
		} else {
			System.out.println("You are in credit card command line process mode:");
			CommandLineTransactionReader ctr = new CommandLineTransactionReader();
			transactions = ctr.readTransactions();
		}
		
		if (transactions != null) {
			TransactionProcessor processor = new TransactionProcessor();
			processor.process(transactions);
			processor.summarize();
		}
		
		System.exit(0);
	}
}
