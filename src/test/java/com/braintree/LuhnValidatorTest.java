package com.braintree;

import org.junit.Test;

import com.braintree.creditcard.LuhnValidator;

import junit.framework.TestCase;

public class LuhnValidatorTest extends TestCase {
	
	
	@Test 
	public void testInvalid() {
		String nullString = null;
		String emptyString ="";
		String zeroString ="0000000000000000";
		String longString = "4111111111111111411111111111";
		String numberWithCharacterString = "411111111111111a";
		String invalidString = "1234567890123456";
		
		
		assertTrue("Null is invalid credit card.", LuhnValidator.validate(nullString) == false);
		assertTrue("Empty is invalid credit card.", LuhnValidator.validate(emptyString) == false);
		assertTrue("All zero number is invalid credit card.", LuhnValidator.validate(zeroString) == false);
		assertTrue("Long number is invalid credit card.", LuhnValidator.validate(longString) == false);
		assertTrue("Number with non-digit character.", LuhnValidator.validate(numberWithCharacterString) == false);
		assertTrue("Invalid number.", LuhnValidator.validate(invalidString) == false);
	}
	
	@Test
	public void testValid() {
		String validString1 = "4111111111111111";
		String validString2 = "5454545454545454";
		
		assertTrue("Valid number1.", LuhnValidator.validate(validString1)== true);
		assertTrue("Valid number2.", LuhnValidator.validate(validString2)== true);
	}
	

}
