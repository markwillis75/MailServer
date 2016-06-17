package com.mkwillis.mailserver.beans;

/**
 * Concrete instance of the IAttachment interface
 * 
 * @author Mark Willis
 */
public class AttachmentImpl implements IAttachment {

	private String name;
	private byte[] data;
	private String mimeType;
	
	//=========================================================================
	// Constructors
	//=========================================================================
	/**
	 * 
	 * @param name     Filename of the attachment
	 * @param data     Raw data of the attachment
	 * @param mimeType
	 */
	public AttachmentImpl(String name, byte[] data, String mimeType) {
		setName(name);
		setData(data);
		setMimeType(mimeType);
	}

	//=========================================================================
	// Name
	//=========================================================================
	/**
	 * @see IAttachment#setName(String)
	 */
	@Override
	public void setName(String name) {
		if (name == null || "".equals(name))
			throw new IllegalArgumentException("Null name for attachment");
		this.name = name;

	}

	/**
	 * @see IAttachment#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	//=========================================================================
	// Data
	//=========================================================================
	/**
	 * @see IAttachment#setData()
	 */
	@Override
	public void setData(byte[] data) {
		if (data == null || data.length == 0)
			throw new IllegalArgumentException();
		
		this.data = data;

	}

	/**
	 * @see IAttachment#getData()
	 */
	@Override
	public byte[] getData() {
		return data;
	}
	
	//=========================================================================
	// Mime Type
	//=========================================================================
	/**
	 * @see IAttachment#setMimeType()
	 */
	@Override
	public void setMimeType(String mime) {
		if (mime == null || "".equals(mime))
			throw new IllegalArgumentException();
		
		this.mimeType = mime;
		
	}

	/**
	 * @see IAttachment#getMimeType()
	 */
	@Override
	public String getMimeType() {
		return mimeType;
	}
}