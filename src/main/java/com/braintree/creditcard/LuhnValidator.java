package com.braintree.creditcard;

public class LuhnValidator {
	/**
	 * Validate credit card number by Luhn's algorithm
	 * 
	 * @param number
	 * @return boolean
	 */
	public static boolean validate(String number) {
		if (number == null || number.length() == 0 || number.length() > 19)
			return false;
		boolean ret = false;
		try {
			int sum = calculateSum(number);
			if (sum == 0)
				return false;
			ret = calculateSum(number) % 10 == 0;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	private static int calculateSum(String number) throws Exception{
		int sum = 0;
		boolean even = false;
		try {
			for (int i = number.length() - 1; i >= 0; i--) {
				int digit = Integer.parseInt(String.valueOf(number.charAt(i)));
				if (even) {
					digit = digit * 2;
					if (digit / 10 != 0)
						digit = digit / 10 + digit % 10;
				}
				even = !even;
				sum += digit;
			}
		} catch (Exception e) {
			throw new Exception("[2005, Invalid Credit Card Number]");
		}
		
		return sum;
	}
	
	
}
