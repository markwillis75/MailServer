package com.mkwillis.mailserver.server;

import java.util.*;

import com.mkwillis.mailserver.beans.IEmail;

/**
 * Simple email validator.
 * 
 * Implemented as a static class.  It has no state, so we don't need an instance
 * and don't need to implement it as a singleton
 * 
 * Nothing more than checking that the email has a sender and that at least 
 * one of the recipient fields has a value.
 * 
 * We're not going to get bogged down in performing regex expressions against
 * the recipient fields - it could be done very easily, but this is just 
 * proof-of-concept code
 * 
 * @author Mark Willis
 *
 */
public final class MailValidator {
	
	/**
	 * Validate the email, checking necessary fields are populated
	 * @param email
	 * @return
	 */
	public static MailValidation validate(IEmail email){
		List<String> messages = new ArrayList<String>();
		boolean valid = true;
		
		if (email == null){
			messages.add("Null email");
			valid = false;
		}
		else{
			if (email.getSender() == null || "".equals(email.getSender())){
				messages.add("No sender defined");
				valid = false;
			}
			if (!validateRecipients(email)){
				messages.add("No recipients defined");
				valid = false;
			}
		}
		
		MailValidation val = new MailValidation(valid, messages);
		
		return val;
	}
	
	/**
	 * Does at least one of the recipient fields have a value
	 * @param email
	 * @return
	 */
	private static boolean validateRecipients(IEmail email){
		boolean valid = false;
		
		for (IEmail.Visibility vv : IEmail.Visibility.values()){
			String recipients = email.getRecipients(vv);
			
			if (recipients != null && !"".equals(recipients)){
				valid = true;
				break;
			}
		}
		
		return valid;
	}
}