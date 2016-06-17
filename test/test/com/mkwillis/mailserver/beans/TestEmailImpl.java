package test.com.mkwillis.mailserver.beans;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.mkwillis.mailserver.beans.EmailImpl;

public class TestEmailImpl {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testNullAttachment(){
		EmailImpl email = new EmailImpl();
		
		exception.expect(IllegalArgumentException.class);
		email.addAttachment(null);
	}
	
	public static void main(String[] args){
		Result result = JUnitCore.runClasses(TestEmailImpl.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	}
}
