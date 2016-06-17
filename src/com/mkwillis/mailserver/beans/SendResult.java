package com.mkwillis.mailserver.beans;

import java.util.Arrays;
import java.util.List;

/**
 * Class to encapsulate the success/failure of sending a mail
 * 
 * Overloaded constructors to allow construction from multiple
 * situations
 * 
 * @author Mark Willis
 */
public class SendResult {
	
	private boolean success;
	private List<String> messages;
		
	public SendResult(boolean success, String message){
		this(success, Arrays.asList(new String[]{message}));
	}
	
	public SendResult(boolean success, List<String> messages){
		this.success = success;
		this.messages = messages;
	}
		
	public boolean isSuccess(){
		return success;
	}
	
	public List<String> getMessages(){
		return messages;
	}
}