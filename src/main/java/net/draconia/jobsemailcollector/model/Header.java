package net.draconia.jobsemailcollector.model;

import java.io.Serializable;
import java.util.Observable;

public class Header extends Observable implements Serializable
{
	private static final long serialVersionUID = -7443992081536326L;
	
	private String msBoundary, msCharacterSet, msContentTransferEncoding, msContentType, msDate, msFrom, msName, msSubject, msTo;
	
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
	
	public String getDate()
	{
		if(msDate == null)
			msDate = "";
		
		return(msDate);
	}
	
	public String getFrom()
	{
		if(msFrom == null)
			msFrom = "";
		
		return(msFrom);
	}
	
	public String getName()
	{
		if(msName == null)
			msName = "";
		
		return(msName);
	}
	
	public String getSubject()
	{
		if(msSubject == null)
			msSubject = "";
		
		return(msSubject);
	}
	
	public String getTo()
	{
		if(msTo == null)
			msTo = "";
		
		return(msTo);
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
	
	public void setDate(final String sDate)
	{
		if(sDate == null)
			msDate = "";
		else
			msDate = sDate;
		
		setChanged();
		notifyObservers();
	}
	
	public void setFrom(final String sFrom)
	{
		if(sFrom == null)
			msFrom = "";
		else
			msFrom = sFrom;
		
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
	
	public void setSubject(final String sSubject)
	{
		if(sSubject == null)
			msSubject = "";
		else
			msSubject = sSubject;
		
		setChanged();
		notifyObservers();
	}
	
	public void setTo(final String sTo)
	{
		if(sTo == null)
			msTo = "";
		else
			msTo = sTo;
		
		setChanged();
		notifyObservers();
	}
}