package com.mkwillis.mailserver.beans;

/**
 * Simple interface for defining a mail attachment
 * 
 * @author Mark Willis
 */
public interface IAttachment {
	
	//=========================================================================
	// Name
	//=========================================================================
	/**
	 * Set the name of the attachment
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Get the name of the attachment
	 * @return
	 */
	public String getName();
	
	//=========================================================================
	// Mime type
	//=========================================================================
	/**
	 * Specify the MIME type for this attachment
	 * @param mime
	 */
	public void setMimeType(String mime);
	
	/**
	 * Return the MIME type for this attachment
	 * @return
	 */
	public String getMimeType();
	
	//=========================================================================
	// Data
	//=========================================================================
	/**
	 * Set the raw data
	 * @param data
	 */
	public void setData(byte[] data);
	
	/**
	 * Get the raw data
	 * @return
	 */
	public byte[] getData();
}
