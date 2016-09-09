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
@Table(name="PhoneNumber")
public class PhoneNumber extends Observable implements Serializable
{
	private static final long serialVersionUID = -8712782320768935550L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(columnDefinition="Integer", insertable=true, name="Id", nullable=false, unique=true, updatable=true)
	private Integer miId;
	
	@Column(columnDefinition="text", insertable=true, name="PhoneNumber", nullable=false, updatable=true)
	private String msPhoneNumber;
	
	@JoinColumn(name="Individual")
	private Individual mObjIndividual;
	
	public PhoneNumber()
	{ }
	
	public PhoneNumber(final Integer iId, final String sPhoneNumber)
	{
		setId(iId);
		setPhoneNumber(sPhoneNumber);
	}
	
	protected Object clone()
	{
		PhoneNumber objClone = new PhoneNumber();
		
		objClone.setId(getId());
		objClone.setPhoneNumber(getPhoneNumber());
		
		return(objClone);
	}
	
	public Integer getId()
	{
		return(miId);
	}
	
	public Individual getIndividual()
	{
		return(mObjIndividual);
	}
	
	public String getPhoneNumber()
	{
		if(msPhoneNumber == null)
			msPhoneNumber = "";
		
		return(msPhoneNumber);
	}
	
	public void setId(final Integer iId)
	{
		miId = iId;
	}
	
	public void setIndividual(final Individual objIndividual)
	{
		mObjIndividual = objIndividual;
		
		setChanged();
		notifyObservers();
	}
	
	public void setPhoneNumber(final String sPhoneNumber)
	{
		if(sPhoneNumber == null)
			msPhoneNumber = "";
		else
			msPhoneNumber = sPhoneNumber;
	}
}