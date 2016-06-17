package com.mkwillis.mailserver.server;

import com.mkwillis.mailserver.beans.IEmail;
import com.mkwillis.mailserver.beans.SendResult;

/**
 * Simple interface for classes which want to declare themselves capable of 
 * handling requests to send an email
 * 
 * @author Mark Willis
 */
public interface IMailHandler {
	
	/**
	 * Handle the request to send the email
	 * @param email
	 * @return 
	 */
	public SendResult sendMail(IEmail email);
	
	/**
	 * Method to allow injection of arguments necessary to configure the handler
	 * @param o
	 */
	public void configure(Object o);
}
