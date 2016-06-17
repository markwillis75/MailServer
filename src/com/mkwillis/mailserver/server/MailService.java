package com.mkwillis.mailserver.server;

import java.util.*;

import com.mkwillis.mailserver.beans.*;

/**
 * Implementation of the IMailHandler interface which acts solely as a container
 * into which we will inject a delegate capable of satisfying the requests to
 * the IMailHandler methods
 * 
 * The {@link #configure(Object)} method has no implementation as this is the 
 * instance exposed to the  controller via MailManger.getMailHandler() and we 
 * want to avoid clumsy/devious clients from inadvertently configuring this class.
 * 
 * @author Mark Willis
 *
 */
public class MailService implements IMailHandler{
	
	private IMailHandler _delegate;
	
	/**
	 * Constructor
	 * @param delegate
	 */
	public MailService(IMailHandler delegate){
		if (delegate == null)
			throw new IllegalArgumentException(
					"Null delegate received in mail service");
					
		_delegate = delegate;
	}
	
	/**
	 * @see IMailHandler#sendMail(IEmail)
	 */
	public SendResult sendMail(IEmail email){
		
		// Check to see if the mail passes validation
		// If not, save time by aborting the attempt
		MailValidation validation = MailValidator.validate(email);
		
		if (!validation.isValid()){
			return new SendResult(false, readMessages(validation.getMessages()));
		}
		
		// Looks like we have a valid email.  Try to send it
		return _delegate.sendMail(email);
	}
	
	/**
	 * Read messages from Iterator and return them as a List of Strings
	 * @param iter
	 * @return
	 */
	private List<String> readMessages(Iterator<String> iter){
		List<String> msgs = new ArrayList<>();
		while(iter.hasNext()){
			msgs.add(iter.next());
		}
		
		return msgs;
	}
	
	/**
	 * @see IMailHandler#configure(Object)
	 */
	public void configure(Object o){
		// no implementation
	}
}