package test.com.mkwillis.mailserver.beans;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.mkwillis.mailserver.beans.AttachmentImpl;

/**
 * Standard unit test to exercise the construction of AttachmentImpl objects
 * 
 * @author Mark Willis
 */
public class TestAttachmentImpl {

	private static final String _validFileName = "test.png",
			                    _validMimeType = "image/png";
			      
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	/**
	 * Expect valid arguments passed to constructor to generate No exceptions
	 */
	@Test
	public void testValidConstruction(){
		AttachmentImpl attachment = new AttachmentImpl("_validFileName", new byte[]{1}, "image/png");
	}
	
	/**
	 * Expect invalid arguments passed to constructor to generate IllegalArgumentException
	 */
	@Test
	public void testNullConstruction(){
		
		// expect invalid constructor calls to throw IllegalArgumentException
		exception.expect(IllegalArgumentException.class);
	    
		byte[] emptyByte = new byte[0];
		byte[] validByte = new byte[]{1};
		
		AttachmentImpl attachment = null;
		
		// all args invalid
		attachment = new AttachmentImpl(null, null, null);
		attachment = new AttachmentImpl("", null, null);
		attachment = new AttachmentImpl("", null, "");
		attachment = new AttachmentImpl(null, null, "");
		attachment = new AttachmentImpl(null, emptyByte, null);
		attachment = new AttachmentImpl("", emptyByte, null);
		attachment = new AttachmentImpl(null, emptyByte, "");
		attachment = new AttachmentImpl("", emptyByte, "");
		               
		// valid filename
		attachment = new AttachmentImpl(_validFileName, null, null);
		attachment = new AttachmentImpl(_validFileName, null, "");
		attachment = new AttachmentImpl(_validFileName, emptyByte, null);
		attachment = new AttachmentImpl(_validFileName, emptyByte, "");
		
		// valid byte
		attachment = new AttachmentImpl(null, validByte, null);
		attachment = new AttachmentImpl("", validByte, "");
		attachment = new AttachmentImpl("", validByte, null);
		attachment = new AttachmentImpl(null, validByte, "");
		
		// valid mime
		attachment = new AttachmentImpl(null, null, _validMimeType);
		attachment = new AttachmentImpl("", null, _validMimeType);
		attachment = new AttachmentImpl(null, emptyByte, _validMimeType);
		attachment = new AttachmentImpl("", emptyByte, _validMimeType);
		
		// valid filename & valid byte
		attachment = new AttachmentImpl(_validFileName, validByte, null);
		attachment = new AttachmentImpl(_validFileName, validByte, "");
		
		// valid filename and mime
		attachment = new AttachmentImpl(_validFileName, null, _validMimeType);
		attachment = new AttachmentImpl(_validFileName, emptyByte, _validMimeType);
		
		// valid byte and mime
		attachment = new AttachmentImpl(null, validByte, _validMimeType);
		attachment = new AttachmentImpl("", validByte, _validMimeType);		
	}
}