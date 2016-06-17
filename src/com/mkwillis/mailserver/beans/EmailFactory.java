package com.mkwillis.mailserver.beans;

/**
 * Factory to encapsulate the creation of an IEMail instance.
 * 
 * Allows us to further divorce the controller from the model
 * 
 * @author Mark Willis
 *
 */
public class EmailFactory {
	public static IEmail getEmail(){
		return new EmailImpl();
	}
	
	public static void setSender(IEmail mail, String sender){
		testMail(mail);
		
		mail.setSender(sender);
	}
	
	public static void setRecipients(IEmail mail, String recipients, IEmail.Visibility vv){
		testMail(mail);
		
		mail.setRecipients(recipients, vv);
	}
	
	public static void setSubject(IEmail mail, String subject){
		testMail(mail);
		
		mail.setSubject(subject);
	}
	
	public static void setBody(IEmail mail, String body){
		testMail(mail);
		
		mail.setBody(body);
	}
	
	public static void addAttachment (IEmail mail, String filename, byte[] data, String mimeType){
		testMail(mail);
		
		IAttachment att = new AttachmentImpl(filename, data, mimeType);
		mail.addAttachment(att);
	}
	
	private static void testMail(IEmail mail){
		if (mail == null)
			throw new IllegalArgumentException("Cannot set property on null email");
	}
}
