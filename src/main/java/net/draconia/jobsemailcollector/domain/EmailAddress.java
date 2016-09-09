package net.draconia.jobsemailcollector.domain;

import java.io.Serializable;

import java.util.Observable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="EmailAddress")
public class EmailAddress extends Observable implements Cloneable, Serializable
{
	private static final long serialVersionUID = -8712782320768935550L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(columnDefinition="Integer", insertable=true, name="Id", nullable=false, unique=true, updatable=true)
	private Integer miId;
	
	@Column(columnDefinition="text", insertable=true, name="EmailAddress", nullable=false, updatable=true)
	private String msEmailAddress;
	
	@JoinColumn(name="Individual")
	private Individual mObjIndividual;
	
	public EmailAddress()
	{ }
	
	public EmailAddress(final Integer iId, final String sEmailAddress)
	{
		setId(iId);
		setEmailAddress(sEmailAddress);
	}
	
	protected Object clone()
	{
		EmailAddress objClone = new EmailAddress();
		
		objClone.setId(getId());
		objClone.setEmailAddress(new String(getEmailAddress()));
		
		return(objClone);
	}
	
	public String getEmailAddress()
	{
		if(msEmailAddress == null)
			msEmailAddress = "";
		
		return(msEmailAddress);
	}
	
	public Integer getId()
	{
		return(miId);
	}
	
	public Individual getIndividual()
	{
		return(mObjIndividual);
	}
	
	public void setEmailAddress(final String sEmailAddress)
	{
		if(sEmailAddress == null)
			msEmailAddress = "";
		else
			msEmailAddress = sEmailAddress;
		
		setChanged();
		notifyObservers();
	}
	
	public void setId(final Integer iId)
	{
		miId = iId;
		
		setChanged();
		notifyObservers();
	}
	
	public void setIndividual(final Individual objIndividual)
	{
		mObjIndividual = objIndividual;
		
		setChanged();
		notifyObservers();
	}
}