package com.braintree.transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.braintree.creditcard.CreditCard;
import com.braintree.creditcard.CreditCardStatus;
import com.braintree.creditcard.LuhnValidator;

public class TransactionProcessor implements TransactionGateway {
	public TransactionProcessor() {
		
	}
	
	// assume one person hold only one card
	// duplicate add transaction for same card, treat it as update limit

	// key as person name
	private Map<String, CreditCard> cards = new HashMap<String, CreditCard>();
	private List<String> logs = new ArrayList<String>();

	/**
	 * Process a list of transactions
	 */
	public void process(List<AbstractTransaction> transactions) throws Exception {
		for (AbstractTransaction transaction : transactions) {
			process(transaction);
		}
	}

	/**
	 * Process individual transaction
	 * Response code reference 
	 * https://developers.braintreepayments.com/ios+ruby/reference/general/processor-responses/authorization-responses
	 */
	public void process(AbstractTransaction transaction) throws Exception {
		if (transaction == null)
			return;
		if (transaction instanceof AccountTransaction) {
			AccountTransaction t = (AccountTransaction) transaction;
			// new card's balance start from 0
			CreditCard card = new CreditCard.CreditCardBuilder()
								.name(t.getName())
								.number(t.getCreditCard())
								.currency(t.getCurrency())
								.balance(0)
								.limit(t.getAmount())
								.build();
			if (!LuhnValidator.validate(card.getNumber())) {
				card.setStatus(CreditCardStatus.ERROR);
			}
			
			if (!cards.containsKey(card.getName())) {
				cards.put(card.getName(), card);
				logs.add(String.format("[1000, New Account] %s with limit: %s.", card.getName(), card.getLimit()));
			} else {
				// duplicate add transaction for same card, treat it as update limit
				CreditCard oldCard = cards.get(card.getName());
				oldCard.setLimit(card.getLimit());
				cards.put(card.getName(), oldCard);
				logs.add(String.format("[1000, Update Account] %s with balance: %s.", card.getName(), oldCard.getBalance()));
			}
		} else if (transaction instanceof ChargeTransaction){
			
			CreditCard card = cards.get(transaction.getName());
			if (card == null) {
				logs.add(String.format("[2007, No Account] Can not find credit card with name %s.", transaction.getName()));
			} else if (card.getStatus() == CreditCardStatus.ERROR) {
				logs.add(String.format("[2005, Invalid Card Number] %s's credit card is invalid.",card.getName()));
			} else if (card.getBalance() + transaction.getAmount() > card.getLimit()) {
				logs.add(String.format("[2002, Limit Exceeded] %s's charge transaction with %s declined as credit exceeded limit.", card.getName(), transaction.getAmount()));
			} else {
				// update balance
				int newBalance = card.getBalance() + transaction.getAmount();
				card.setBalance(newBalance);
				cards.put(card.getName(), card);
				logs.add(String.format("[1000, Approved] %s's charge transaction with amount: %s processed.",card.getName(), transaction.getAmount()));
			}
		} else if (transaction instanceof CreditTransaction) {
			CreditCard card = cards.get(transaction.getName());
			if (card == null) {
				logs.add(String.format("[2007, No Account] Can not find credit card with name %s.", transaction.getName()));
			} else if (card.getStatus() == CreditCardStatus.ERROR) {
				logs.add(String.format("[2005, Invalid Card Number] %s's credit card is invalid.",card.getName()));
			} else {
				int newBalance = card.getBalance() - transaction.getAmount();
				card.setBalance(newBalance);
				cards.put(card.getName(), card);
				logs.add(String.format("[1000, Approved] %s's credit transaction with amount: %s processed.", card.getName(), transaction.getAmount()));
			}
		}
	}
	
	/**
	 * Summarize current credit card account
	 */
	public void summarize() {
		printSummary(getSummary());
		printLog(logs);
	}
	
	public TreeMap<String, CreditCard> getSummary() {
		TreeMap<String, CreditCard> sorted = new TreeMap<String, CreditCard>(new Comparator<String>() {
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		});
		sorted.putAll(cards);
		return sorted;
	}
	
	public List<String> getLogs() {
		return logs;
	}
	
	/**
	 * Print a list of log message
	 */
	private void printLog(List<String> logs) {
		System.out.println("================ Credit Card Transaction Log ================");
		for(String log: logs) {
			System.out.println(log);
		}
	}
	
	/**
	 * Print helper method
	 */
	private void printSummary(Map<String, CreditCard> map) {
		System.out.println("================ Credit Card Summary ================");
		for(String name: map.keySet()) {
			CreditCard card = map.get(name);
			if (card.getStatus() != CreditCardStatus.ERROR) {
				System.out.println(String.format("%s : %s%s", name, card.getCurrency(), card.getBalance()));
			} else {
				System.out.println(String.format("%s : %s", name, card.getStatus().toString()));
			}
		}
	}
	

}
