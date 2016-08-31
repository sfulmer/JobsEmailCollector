package net.draconia.jobsemailcollector.model;

import java.io.Serializable;

import java.util.Observable;

public class MimePart extends Observable implements Serializable
{
	private static final long serialVersionUID = 2174462878003640846L;
	
	private String msBoundary, msCharacterSet, msContentTransferEncoding, msContentType, msName, msText;
	
	public MimePart()
	{ }
	
	public String getBoundary()
	{
		if(msBoundary == null)
			msBoundary = "";
		
		return(msBoundary);
	}
	
	public String getCharacterSet()
	{
		if(msCharacterSet == null)
			msCharacterSet = "";
		
		return(msCharacterSet);
	}
	
	public String getContentTransferEncoding()
	{
		if(msContentTransferEncoding == null)
			msContentTransferEncoding = "";
		
		return(msContentTransferEncoding);
	}
	
	public String getContentType()
	{
		if(msContentType == null)
			msContentType = "";
		
		return(msContentType);
	}
	
	public String getName()
	{
		if(msName == null)
			msName = "";
		
		return(msName);
	}
	
	public String getText()
	{
		if(msText == null)
			msText = "";
		
		return(msText);
	}
	
	public void setBoundary(final String sBoundary)
	{
		if(sBoundary == null)
			msBoundary = "";
		else
			msBoundary = sBoundary;
		
		setChanged();
		notifyObservers();
	}
	
	public void setCharacterSet(final String sCharacterSet)
	{
		if(sCharacterSet == null)
			msCharacterSet = "";
		else
			msCharacterSet = sCharacterSet;
		
		setChanged();
		notifyObservers();
	}
	
	public void setContentTransferEncoding(final String sContentTransferEncoding)
	{
		if(sContentTransferEncoding == null)
			msContentTransferEncoding = "";
		else
			msContentTransferEncoding = sContentTransferEncoding;
		
		setChanged();
		notifyObservers();
	}
	
	public void setContentType(final String sContentType)
	{
		if(sContentType == null)
			msContentType = "";
		else
			msContentType = sContentType;
		
		setChanged();
		notifyObservers();
	}
	
	public void setName(final String sName)
	{
		if(sName == null)
			msName = "";
		else
			msName = sName;
		
		setChanged();
		notifyObservers();
	}
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
		
		setChanged();
		notifyObservers();
	}
}