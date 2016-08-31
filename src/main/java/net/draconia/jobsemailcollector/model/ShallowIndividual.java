package net.draconia.jobsemailcollector.model;

import java.io.Serializable;

import java.util.Date;

public class ShallowIndividual implements Serializable
{
	private static final long serialVersionUID = -1244724544654420465L;
	
	private Date mDtLastEmail;
	private Integer miId;
	private String msName;
	
	public ShallowIndividual()
	{ }
	
	public ShallowIndividual(final Integer iId, final String sName, final Date dtLastEmail)
	{
		setId(iId);
		setName(sName);
		setDateOfLastEmail(dtLastEmail);
	}
	
	public Date getDateOfLastEmail()
	{
		return(mDtLastEmail);
	}
	
	public Integer getId()
	{
		return(miId);
	}
	
	public String getName()
	{
		return(msName);
	}
	
	public void setDateOfLastEmail(final Date dtLastEmail)
	{
		mDtLastEmail = dtLastEmail;
	}
	
	public void setId(final Integer iId)
	{
		miId = iId;
	}
	
	public void setName(final String sName)
	{
		msName = sName;
	}
}