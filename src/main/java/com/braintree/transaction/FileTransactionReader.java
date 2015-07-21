package com.braintree.transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileTransactionReader implements TransactionReader {
	private final String pathToFile;
	
	private final static Set<String> commands = new HashSet<String>(){
		private static final long serialVersionUID = -2092311322539608259L;
		{
			add("add");
			add("charge");
			add("credit");
		}
	};

	public FileTransactionReader(String pathToFile) {
		this.pathToFile = pathToFile;
	}
	
	public String getPathToFile() {
		return pathToFile;
	}

	public List<AbstractTransaction> readTransactions() throws Exception {
		List<AbstractTransaction> transactionList = new ArrayList<AbstractTransaction>();
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
		String line = null;
		try {
			line = br.readLine();

			while(line != null) {
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
						
						// factory pattern
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
				
				line = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			//TODO log exception
		} catch (NumberFormatException e) {
			//TODO log exception
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return transactionList;
	}

}
