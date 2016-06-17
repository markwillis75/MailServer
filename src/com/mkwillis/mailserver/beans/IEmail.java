package com.mkwillis.mailserver.beans;

import java.util.Collection;

/**
 * Simple interface to define functionality expected of an email object
 * 
 * @author Mark Willis
 */
public interface IEmail {
	
	enum Visibility{
		TO,
		CC,
		BCC
	};
	
	//=========================================================================
	// Sender
	//=========================================================================
	/**
	 * Set Sender
	 * @param sender
	 */
	public void setSender(String sender);
	
	/**
	 * Get Sender
	 * @return
	 */
	public String getSender();
	
	//=========================================================================
	// Recipients
	//=========================================================================
	/**
	 * Add recipents
	 * @param recipients
	 * @param vis One of the Visibility enum values: TO, CC, BCC
	 */
	public void setRecipients(String recipients, Visibility vis);
	
	/**
	 * Get recipients for the specified visibilty
	 * @param vis
	 * @return
	 */
	public String getRecipients(Visibility vis);
	
	//=========================================================================
	// Subject
	//=========================================================================
	/**
	 * Set email subject
	 * @param subject
	 */
	public void setSubject(String subject);
	
	/**
	 * Get email subject
	 * @return
	 */
	public String getSubject();
	
	//=========================================================================
	// Body
	//=========================================================================
	/**
	 * Set email body
	 * @param body
	 */
	public void setBody(String body);
	
	/**
	 * Get email body
	 * @return
	 */
	public String getBody();
	
	//=========================================================================
	// Attachments
	//=========================================================================
	/**
	 * Add an attachment
	 * @param attachment
	 */
	public void addAttachment(IAttachment attachment);
	
	/**
	 * Remove an attachment
	 * @param attachment
	 */
	public void removeAttachment(IAttachment attachment);
	
	/**
	 * Get list of attachments
	 * @return
	 */
	public Collection<IAttachment> getAttachments();
}
