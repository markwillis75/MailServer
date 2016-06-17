package com.mkwillis.mailserver.server.smtp;

import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.util.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;


import com.mkwillis.mailserver.beans.*;
import com.mkwillis.mailserver.server.IMailHandler;

/**
 * Implementation of the IMailHandler interface capable of sending mail requests
 * via SMTP
 * 
 * @author Mark Willis
 */
public class SMTPMailHandler implements IMailHandler{
		
	private Properties props;
	
	private final String str = "Attempting to send with %s";
	
	public SMTPMailHandler(){
	}

	/**
	 * @see IMailHandler#configure(Object)
	 */
	public void configure(Object o){
		if (o == null){
			throw new IllegalArgumentException();
		}
		
		// Allow for configuration directly with a Properties object - useful for unit testing
		// But also allow for configuration with a String, the name of which we expect to be
		// a properties file on disk
		if (o instanceof Properties){
			props = (Properties)o;
		}
		else if (o instanceof String){
			try
			{
				Properties props = new Properties();
				props.load(SMTPMailHandler.class.getResourceAsStream(o.toString()));
				
				this.props = props;
			}
			catch (Exception ioe){
				ioe.printStackTrace();
				throw new IllegalArgumentException(ioe.getMessage());
			}
		}
		else{
			throw new IllegalArgumentException("configuration object must be String or Properties object");
		}
	}
	
	/**
	 * @see IMailHandler#sendMail(IEmail)
	 */
	public SendResult sendMail(IEmail email){
		if (props == null){
			throw new IllegalArgumentException("Class has not been configured.  Props object is null");
		}
		
		SendResult result = null;
				
		List<String> msgs = new ArrayList<>();
		boolean success = false;
		
		Transport transport = null;
		try{
			msgs.add(String.format(str, props.getProperty("mail.smtp.host")));
			
			// build the email
			MimeMessage msg = getMimeMessage(email);
				    
		    // send the email
		    transport = msg.getSession().getTransport();
		    transport.connect();
		    transport.sendMessage(msg, msg.getAllRecipients());
		    
		    msgs.add("success");
		    success = true;
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			msgs.add("failure: " + e.getMessage());
			success = false;
		}
		finally{
			try{
			    transport.close();}
			catch (Exception e){}
		}
		
		result = new SendResult(success, msgs);
		return result;
	}
	
	/**
	 * Build the MimeMessage stub
	 * @param server
	 * @return
	 */
	private MimeMessage getMimeMessage(IEmail email) throws MessagingException{
		Authenticator auth = new SMTPAuthenticator(props.getProperty("mail.smtp.user"),  props.getProperty("mail.smtp.password"));
		Session mailSession = Session.getDefaultInstance(props, auth);
		
	    MimeMessage msg = new MimeMessage(mailSession);
	    msg.setFrom(email.getSender());
	    setRecipients(msg, email);
	    msg.setSubject(email.getSubject());   
	    BodyPart body = new MimeBodyPart();
	    body.setText(email.getBody());
	    Multipart multipart = new MimeMultipart("alternative");
	    multipart.addBodyPart(body);
	    List<BodyPart> attachments = getAttachments(email);
	    for (BodyPart part: attachments){
	    	multipart.addBodyPart(part);
	    }
	    msg.setContent(multipart);
	    
	    return msg;
	}
	
	/**
	 * Add the recipients to the MimeMessage
	 * @param email
	 * @param msg
	 * @throws MessagingException
	 */
	private void setRecipients(MimeMessage msg, IEmail email) throws MessagingException{
	    String recipients = email.getRecipients(IEmail.Visibility.TO);
        if (recipients != null && !"".equals(recipients))
            msg.addRecipient(RecipientType.TO,  new InternetAddress(recipients));
        
        recipients = email.getRecipients(IEmail.Visibility.CC);
        if (recipients != null && !"".equals(recipients))
            msg.addRecipient(RecipientType.CC,  new InternetAddress(email.getRecipients(IEmail.Visibility.CC)));
        
        recipients = email.getRecipients(IEmail.Visibility.BCC);
        if (recipients != null && !"".equals(recipients))
	        msg.addRecipient(RecipientType.BCC, new InternetAddress(email.getRecipients(IEmail.Visibility.BCC)));
	}
	
	/**
	 * Add attachments to the MimeMessage
	 * @param email
	 * @return
	 * @throws MessagingException
	 */
	private List<BodyPart> getAttachments(IEmail email) throws MessagingException{
		List<BodyPart> parts = new ArrayList<>();
		
		if (!email.getAttachments().isEmpty()){
	    	Iterator<IAttachment> iter = email.getAttachments().iterator();
	    	while (iter.hasNext()){
	    		IAttachment attachment = iter.next();
			    BodyPart part = new MimeBodyPart();
			    DataSource source = new ByteArrayDataSource(attachment.getData(), attachment.getMimeType());
		        part.setDataHandler(new DataHandler(source));
		        part.setFileName(attachment.getName());
		        parts.add(part);
	    	}
	    }
		
		return parts;
	}
	
	private class SMTPAuthenticator extends Authenticator{
		private String _username,
		               _password;
		
		public SMTPAuthenticator(String username, String password){
			_username = username;
			_password = password;
		}
		
		public PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(_username, _password);
		}
	}
}