package test.com.mkwillis.mailserver.server.smtp;

import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mkwillis.mailserver.beans.EmailImpl;
import com.mkwillis.mailserver.beans.IEmail;
import com.mkwillis.mailserver.server.smtp.SMTPMailHandler;

public class TestSMTPMailHandler {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	@Test
	public void testNullConfiguration(){
		expected.expect(IllegalArgumentException.class);
		new SMTPMailHandler().configure(null);
	}
	
	@Test
	public void testNoConfiguration(){
		expected.expect(IllegalArgumentException.class);
		
		EmailImpl email = new EmailImpl();
		email.setSender("joe.bloggs@gmail.com");
		email.setRecipients("jane.doe@hotmail.com", IEmail.Visibility.TO);
		new SMTPMailHandler().sendMail(email);
	}
	
	@Test
	public void testUnexpectedObject(){
		expected.expect(IllegalArgumentException.class);
		new SMTPMailHandler().configure(new Integer(100));
	}
	
	@Test
	public void testPropertiesObject(){
		Properties props = new Properties();
		props.put("hello", "world");
		new SMTPMailHandler().configure(props);
	}
}
