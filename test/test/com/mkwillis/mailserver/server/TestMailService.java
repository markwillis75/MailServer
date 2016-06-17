package test.com.mkwillis.mailserver.server;

import org.junit.*;
import org.junit.rules.ExpectedException;

import com.mkwillis.mailserver.beans.*;
import com.mkwillis.mailserver.server.*;

public class TestMailService {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void tesNullInjection(){
		exception.expect(IllegalArgumentException.class);
		
		new MailService(null);
	}
	
	@Test
	public void testSuccessfulSend(){
		IMailHandler service = getMailService(true);
		
		Assert.assertTrue(service.sendMail(getEmail()).isSuccess());
	}
	
	public void testUnsuccessfulSend(){
        IMailHandler service = getMailService(false);
		
		Assert.assertFalse(service.sendMail(getEmail()).isSuccess());
	}
	
	/**
	 * Build a valid email
	 * @return
	 */
	private IEmail getEmail(){
		EmailImpl email = new EmailImpl();
		
		email.setSender("joe.bloggs@hotmail.com");
		email.setRecipients("john.doe@hotmail.com", IEmail.Visibility.TO);
		
		return email;
	}
	
	/**
	 * Build a MailService instance and inject a mock handler into it.
	 * The handler will return a success or false depending on the value
	 * passed to this method
	 * @param success
	 * @return
	 */
	private IMailHandler getMailService (boolean success){
		MockHandler mock = new MockHandler();
		mock.configure(Boolean.valueOf(success));
		
		MailService service = new MailService(mock);
		
		return service;
	}
	
	/**
	 * Mock mail handler.  Will return successful or unsuccessful send result
	 * depending on value of Boolean object passed to the configure method
	 */
	private class MockHandler implements IMailHandler{
		boolean success = false;
		
		public SendResult sendMail(IEmail email){
			return new SendResult(success, "returning " + success);
		}
		
		public void configure(Object o){
			success = ((Boolean)o).booleanValue();
		}
	}
}
