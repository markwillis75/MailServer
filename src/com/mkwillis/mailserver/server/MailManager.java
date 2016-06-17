package com.mkwillis.mailserver.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.mkwillis.mailserver.beans.IEmail;
import com.mkwillis.mailserver.beans.SendResult;

/**
 * Encapsulation of the functionality necessary to respond to mail requests.
 * 
 * This class is essentially a Facade for the controller, giving the controller a 
 * managed interface to send mail.
 * 
 * Implemented as a Singleton as it implements the bootstrapping of the underlying
 * IMailerHandler instances and we want that to occur only one time.
 * 
 * The class is final to avoid subclassing, which would actually permit someone
 * curious/devious enough to create two instances of the MailManager (one superclass
 * and one subclass)
 * 
 * @author Mark Willis
 *
 */
public final class MailManager {

	private static MailManager _instance;
	private IMailHandler _handler;
	
	/**
	 * Constructor
	 * Bootstrap the MailService, injecting the IMailHandler delegate
	 */
	private MailManager(){
		_handler = new MailService(makeHandler());
	}
	
	/**
	 * Accessor to the single instance of the MailManager class
	 * @return
	 */
	public static synchronized MailManager getInstance(){
		if (_instance == null){
			_instance = new MailManager();
		}
		
		return _instance;
	}
	
	/**
	 * Attempt to send the IEmail instance
	 * @param mail IEmail to transmit
	 * @return SendResult object containing success/error flag and associated messages
	 */
	public SendResult sendMail(IEmail mail){
		// Check to see if the mail passes validation
		// If not, save time by aborting the attempt
		MailValidation validation = MailValidator.validate(mail);
		
		if (!validation.isValid()){
			return new SendResult(false, readMessages(validation.getMessages()));
		}
				
		// Looks like we have a valid email.  Try to send it
		return _handler.sendMail(mail);
	}
	
	/**
	 * Read the mailManagerConfig.properties file and, using reflection, instantiate an 
	 * instance of whichever class is defined in the class property.
	 * 
	 * The class must implement the IMailHandler interface and define a no-args constructor.
	 * 
	 * We then inject this instance into MailService.
	 * 
	 * This pattern allows us to swap out the underlying IMailHandler implementation by simply
	 * writing a new class which implements IMailHandler and changing the value in the
	 * mailManagerConfig.properties file accordingly.
	 *  
	 * @return
	 */
	private IMailHandler makeHandler(){
		
		IMailHandler runtimeHandler = null;
		try{
			
			Properties props = new Properties();
			props.load(MailManager.class.getResourceAsStream("mailManagerConfig.properties"));
		    
		    runtimeHandler = (IMailHandler)Class.forName(props.getProperty("class")).newInstance();
		    runtimeHandler.configure(props.getProperty("config"));
		}
		catch (Exception ioe){
			// Catch Superclass for code brevity.  We could catch specific types and handle accordingly.
			ioe.printStackTrace();
		}
		
		return runtimeHandler;
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
}