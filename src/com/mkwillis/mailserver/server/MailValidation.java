package com.mkwillis.mailserver.server;

import java.util.*;

/**
 * Utility class to capture the result of validating an email.
 * 
 * Immutable class, preventing changes after instantiation
 * 
 * @author Mark Willis
 */
public class MailValidation{
	private List<String> messages;
	private boolean valid;
	
	/**
	 * Constructor
	 * @param valid    true if mail passed muster
	 * @param messages Any messages associated with the validation
	 */
	public MailValidation(boolean valid, List<String> messages){
		this.valid = valid;
		this.messages = messages;
	}
	
	/**
	 * Return messages as an iterator, making them read-only
	 */
	public Iterator<String> getMessages(){
		return messages.iterator();
	}
	
	/**
	 * Allow client to query if validation passed or failed
	 * @return
	 */
	public boolean isValid(){
		return valid;
	}
}