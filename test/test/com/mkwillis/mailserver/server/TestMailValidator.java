package test.com.mkwillis.mailserver.server;

import org.junit.*;

import com.mkwillis.mailserver.beans.*;
import com.mkwillis.mailserver.server.*;

public class TestMailValidator {

	@Test
	public void testValidEmail(){
		Assert.assertTrue(validate(getEmail()).isValid());
	}
	
	@Test
	public void testNull(){		
		Assert.assertFalse(validate(null).isValid());
	}
	
	@Test
	public void testNoSender(){
		IEmail email = getEmail();
		
		email.setSender(null);		
		Assert.assertFalse(validate(email).isValid());
		
		email.setSender("");
		Assert.assertFalse(validate(email).isValid());
	}
	
	@Test
	public void testRecipients(){
		IEmail email = getEmail();
		
		// Wipe out the TO field
		// We still have CC and BCC, so should be valid
		email.setRecipients(null, IEmail.Visibility.TO);
		Assert.assertTrue(validate(email).isValid());
		
		email.setRecipients("", IEmail.Visibility.TO);
		Assert.assertTrue(validate(email).isValid());
		
		// Wipe out the CC field
		// We still have the BCC field, should be valid
		email.setRecipients(null, IEmail.Visibility.CC);
		Assert.assertTrue(validate(email).isValid());
		
		email.setRecipients("", IEmail.Visibility.CC);
		Assert.assertTrue(validate(email).isValid());
		
		// Wipe out the BCC field
		// We have no recipients, so should be invalid
		email.setRecipients(null, IEmail.Visibility.BCC);
		Assert.assertFalse(validate(email).isValid());
		
		email.setRecipients(null, IEmail.Visibility.BCC);
		Assert.assertFalse(validate(email).isValid());
	}
	
	/**
	 * Handle the validation
	 * @param email
	 * @return
	 */
	private MailValidation validate(IEmail email){
		return MailValidator.validate(email);
	}
	
	/**
	 * Build a mock email with all fields populated
	 * @return
	 */
	private IEmail getEmail(){
		EmailImpl email = new EmailImpl();
		
		email.setSender("joe.bloggs@hotmail.com");
		email.setSubject("Test test test");
		email.setBody("body body body");
		email.setRecipients("john.doe@hotmail.com", IEmail.Visibility.TO);
		email.setRecipients("jane.doe@hotmail.com", IEmail.Visibility.CC);
		email.setRecipients("jack.doe@hotmail.com", IEmail.Visibility.BCC);
		email.addAttachment(new AttachmentImpl("test.png", new byte[]{1}, "image/png"));
		
		return email;
	}
}
