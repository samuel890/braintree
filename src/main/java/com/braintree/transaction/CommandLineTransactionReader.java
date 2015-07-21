package com.braintree.transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CommandLineTransactionReader implements TransactionReader {
	/**
	 * Command line transaction reader support following commands
	 *  - Add
	 *  - Charge
	 *  - Credit
	 *  - Process
	 *
	 */
	private final static String terminator = "process";
	
	private final static Set<String> commands = new HashSet<String>(){
		private static final long serialVersionUID = -8010055501192783565L;
		{
			add("add");
			add("charge");
			add("credit");
		}
	};
 	
	
	public List<AbstractTransaction> readTransactions() throws Exception {
		List<AbstractTransaction> transactionList = new ArrayList<AbstractTransaction>();
		Scanner console = new Scanner(System.in);
		String line = null;
		while(console.hasNextLine()) {
			line = console.nextLine().trim();
			// once hit 'process' stop read next line
			if (line.toLowerCase().equals(terminator)) {
				break;
			}
			
			String[] words = line.split("\\s+");
			String command = words[0].toLowerCase();
			if (commands.contains(command)) {
				if (words.length == 4 && command.equals("add")) {
					AccountTransaction transaction = new AccountTransaction();
					String name = words[1];
					String creditCard = words[2];
					String currency = words[3].substring(0, 1);
					int amount = Integer.valueOf(words[3].substring(1));
					
					transaction.setName(name);
					transaction.setCreditCard(creditCard);
					transaction.setCurrency(currency);
					transaction.setAmount(amount);
					
					transactionList.add(transaction);
				} else if (words.length == 3 && (command.equals("charge") || command.equals("credit"))) {
					String action = words[0].toLowerCase();
					String name = words[1];
					String currency = words[2].substring(0, 1);
					int amount = Integer.valueOf(words[2].substring(1));
					
					AbstractTransaction transaction = null;
					if (action.toLowerCase().equals("charge")) {
						transaction = new ChargeTransaction();
					} else {
						transaction = new CreditTransaction();
					}
					
					transaction.setName(name);
					transaction.setCurrency(currency);
					transaction.setAmount(amount);
					
					transactionList.add(transaction);
				}
			}
		}
		
		console.close();
		
		return transactionList;
	}

}
