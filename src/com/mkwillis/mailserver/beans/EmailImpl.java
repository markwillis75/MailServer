package com.mkwillis.mailserver.beans;

import java.util.*;

/**
 * Concrete implementation of the IEmail interface
 * 
 * @author Mark Willis
 */
public class EmailImpl implements IEmail {

	private String _sender;
	private String _subject;
	private String _body;
	private List<IAttachment> _attachments;
	private HashMap <IEmail.Visibility, String> _recipients;
	
	//===========================================================================
	// Constructors
	//===========================================================================
	/**
	 * Default constructor
	 */
	public EmailImpl() {
		
		_attachments = new ArrayList<>();
		
		_recipients  = new HashMap<>();
		for (Visibility vv : Visibility.values())
			_recipients.put(vv, "");
	}
	
	//===========================================================================
	// Sender
	//===========================================================================
	/**
	 * @see IEmail#setSender(java.lang.String)
	 */
	@Override
	public void setSender(String sender) {
		_sender = sender;
	}

	/**
	 * @see IEmail#getSender()
	 */
	@Override
	public String getSender() {
		return _sender;
	}

	//===========================================================================
	// Recipients
    //===========================================================================
	/**
	 * @see IEmail#setRecipients(java.lang.String, IEmail.Visibilty)
	 */
	@Override
	public void setRecipients(String recipients, Visibility vis) {
		_recipients.put(vis, recipients);
	}
	
	public String getRecipients(Visibility vis){
		return _recipients.get(vis);
	}

	//===========================================================================
	// Subject
	//===========================================================================
	/**
	 * @see IEmail#setSubject(java.lang.String)
	 */
	@Override
	public void setSubject(String title) {
		_subject = title;
	}

	/**
	 * @see IEmail#getSubject()
	 */
	@Override
	public String getSubject() {
		return _subject;
	}

	//===========================================================================
	// Body
    //===========================================================================
	/**
	 * @see IEmail#setBody(java.lang.String)
	 */
	@Override
	public void setBody(String body) {
		_body = body;
	}

	/**
	 * @see IEmail#getBody()
	 */
	@Override
	public String getBody() {
		return _body;
	}

	//===========================================================================
	// Attachments
	//===========================================================================
	/**
	 * @see IEmail#addAttachment(IAttachment)
	 */
	@Override
	public void addAttachment(IAttachment attachment) {
		if (attachment == null)
			throw new IllegalArgumentException("Attempting to attach a null attachment");
		
		_attachments.add(attachment);
	}

	/**
	 * @see IEmail#removeAttachment(IAttachment)
	 */
	@Override
	public void removeAttachment(IAttachment attachment) {
		_attachments.remove(attachment);

	}

	/**
	 * @see IEmail#getAttachments()
	 */
	@Override
	public Collection<IAttachment> getAttachments() {
		return _attachments;
	}
	
	//===========================================================================
	// Standard Object overrides
	//===========================================================================
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		builder.append("sender: ").append(getSender())
		       .append("\tto: ").append(getRecipients(Visibility.TO))
		       .append("\tcc: ").append(getRecipients(Visibility.CC))
		       .append("\tbcc: " ).append(getRecipients(Visibility.BCC))
		       .append("\ttitle: ").append(getSubject())
		       .append("\tbody: ").append(getBody());  // This is actually impractical as the body could be large
		
		int num = 1;
		if (getAttachments() != null){
			for(IAttachment attachment:getAttachments()){
				builder.append("\tatt[").append(num).append("]: ").append(attachment.getName());
			}
		}
		
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_attachments == null) ? 0 : _attachments.hashCode());
		result = prime * result + ((_body == null) ? 0 : _body.hashCode());
		result = prime * result + ((_recipients == null) ? 0 : _recipients.hashCode());
		result = prime * result + ((_sender == null) ? 0 : _sender.hashCode());
		result = prime * result + ((_subject == null) ? 0 : _subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailImpl other = (EmailImpl) obj;
		if (_attachments == null) {
			if (other._attachments != null)
				return false;
		} else if (!_attachments.equals(other._attachments))
			return false;
		if (_body == null) {
			if (other._body != null)
				return false;
		} else if (!_body.equals(other._body))
			return false;
		if (_recipients == null) {
			if (other._recipients != null)
				return false;
		} else if (!_recipients.equals(other._recipients))
			return false;
		if (_sender == null) {
			if (other._sender != null)
				return false;
		} else if (!_sender.equals(other._sender))
			return false;
		if (_subject == null) {
			if (other._subject != null)
				return false;
		} else if (!_subject.equals(other._subject))
			return false;
		return true;
	}
}