package net.draconia.jobsemailcollector.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class Individual extends Observable implements Cloneable, Observer, Serializable
{
	private static final long serialVersionUID = -1031879838860122322L;
	
	private Email mObjEmail;
	private Integer miId;
	private List<String> mLstEmailAddresses, mLstPhoneNumbers;
	private String msName;
	
	public Individual()
	{ }
	
	public boolean addEmailAddress(final String sEmailAddress)
	{
		boolean bReturnValue = getEmailAddresses().add(sEmailAddress);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public void addEmailAddress(final int iIndex, final String sEmailAddress)
	{
		getEmailAddresses().add(iIndex, sEmailAddress);
		
		setChanged();
		notifyObservers();
	}
	
	public boolean addEmailAddresses(final Collection<String> collEmailAddresses)
	{
		boolean bReturnValue = getEmailAddresses().addAll(collEmailAddresses);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addEmailAddresses(final int iIndex, final Collection<String> collEmailAddresses)
	{
		boolean bReturnValue = getEmailAddresses().addAll(iIndex, collEmailAddresses);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addPhoneNumber(final String sPhoneNumber)
	{
		boolean bReturnValue = getPhoneNumbers().add(sPhoneNumber);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public void addPhoneNumber(final int iIndex, final String sPhoneNumber)
	{
		getPhoneNumbers().add(iIndex, sPhoneNumber);
		
		setChanged();
		notifyObservers();
	}
	
	public boolean addPhoneNumbers(final Collection<String> collPhoneNumbers)
	{
		boolean bReturnValue = getPhoneNumbers().addAll(collPhoneNumbers);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addPhoneNumbers(final int iIndex, final Collection<String> collPhoneNumbers)
	{
		boolean bReturnValue = getPhoneNumbers().addAll(iIndex, collPhoneNumbers);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public void clearEmailAddresses()
	{
		getEmailAddresses().clear();
		
		setChanged();
		notifyObservers();
	}
	
	public void clearPhoneNumbers()
	{
		getPhoneNumbers().clear();
		
		setChanged();
		notifyObservers();
	}
	
	public Object clone()
	{
		Individual mObjClone = new Individual();
		
		mObjClone.setId(getId());
		mObjClone.setName(getName());
		
		for(String sEmailAddress : getEmailAddresses())
			mObjClone.addEmailAddress(new String(sEmailAddress));
		
		for(String sPhoneNumber : getPhoneNumbers())
			mObjClone.addPhoneNumber(new String(sPhoneNumber));
		
		mObjClone.setEmail(((Email)(getEmail().clone())));
		
		return(mObjClone);
	}
	
	public boolean equals(final Object objOther)
	{
		if((objOther instanceof Individual) && (objOther != null))
			{
			boolean bEquals = true;
			Individual objIndividual = ((Individual)(objOther));
			
			bEquals = bEquals && (Integer.valueOf(getId()).equals(objIndividual.getId()));
			
			return(bEquals && equalsIgnoreId(objIndividual));
			}
		else
			return(false);
	}
	
	public boolean equalsIgnoreId(final Individual objOther)
	{
		boolean bEquals = true;
		Individual objIndividual = ((Individual)(objOther));
		
		bEquals = bEquals && (getName().equals(objIndividual.getName()));
		
		if((getEmail() != null) && (objIndividual.getEmail() != null))
			bEquals = bEquals && (getEmail().equalsIgnoreId(objIndividual.getEmail()));
		else
			bEquals = false;
		
		if(bEquals)
			bEquals = Objects.deepEquals(getEmailAddresses(), objIndividual.getEmailAddresses());
		if(bEquals)
			bEquals = Objects.deepEquals(getPhoneNumbers(), objIndividual.getPhoneNumbers());
		
		return(bEquals);
	}
	
	public Date getDateOfLastContact()
	{
		return(getEmail().getDate());
	}
	
	public Email getEmail()
	{
		return(mObjEmail);
	}
	
	public List<String> getEmailAddressList()
	{
		return(Collections.unmodifiableList(getEmailAddresses()));
	}
	
	protected List<String> getEmailAddresses()
	{
		if(mLstEmailAddresses == null)
			mLstEmailAddresses = Collections.synchronizedList(new ArrayList<String>());
		
		return(mLstEmailAddresses);
	}
	
	public int getId()
	{
		if(miId == null)
			miId = 0;
		
		return(miId);
	}
	
	public String getName()
	{
		if(msName == null)
			msName = "";
		
		return(msName);
	}
	
	public List<String> getPhoneNumberList()
	{
		return(Collections.unmodifiableList(getPhoneNumbers()));
	}
	
	protected List<String> getPhoneNumbers()
	{
		if(mLstPhoneNumbers == null)
			mLstPhoneNumbers = Collections.synchronizedList(new ArrayList<String>());
		
		return(mLstPhoneNumbers);
	}
	
	public String removeEmailAddress(final int iIndex)
	{
		String sReturnValue = getEmailAddresses().remove(iIndex);
		
		setChanged();
		notifyObservers();
		
		return(sReturnValue);
	}
	
	public boolean removeEmailAddress(final String sEmailAddress)
	{
		boolean bReturnValue = getEmailAddresses().remove(sEmailAddress);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean removeEmailAddresses(final Collection<String> collEmailAddresses)
	{
		boolean bReturnValue = getEmailAddresses().removeAll(collEmailAddresses);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public String removePhoneNumber(final int iIndex)
	{
		String sReturnValue = getPhoneNumbers().remove(iIndex);
		
		setChanged();
		notifyObservers();
		
		return(sReturnValue);
	}
	
	public boolean removePhoneNumber(final String sPhoneNumber)
	{
		boolean bReturnValue = getPhoneNumbers().remove(sPhoneNumber);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean removePhoneNumbers(final Collection<String> collPhoneNumbers)
	{
		boolean bReturnValue = getPhoneNumbers().removeAll(collPhoneNumbers);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public void set(final Individual objIndividual)
	{
		if(objIndividual != null)
			{
			setId(objIndividual.getId());
			setName(objIndividual.getName());
			
			clearEmailAddresses();
			for(String sEmailAddress : getEmailAddressList())
				addEmailAddress(sEmailAddress);
			
			clearPhoneNumbers();
			for(String sPhoneNumber : getPhoneNumberList())
				addPhoneNumber(sPhoneNumber);
			
			getEmail().set(objIndividual.getEmail());
			}
	}
	
	public void setEmail(final Email objEmail)
	{
		if(mObjEmail != null)
			mObjEmail.deleteObserver(this);
		
		mObjEmail = objEmail;
		
		if(mObjEmail != null)
			{
			mObjEmail.setIndividual(this);
			
			mObjEmail.addObserver(this);
			}
	}
	
	public void setId(final Integer iId)
	{
		if(iId == null)
			miId = 0;
		else
			miId = iId;
		
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
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		if(objObservable instanceof Email)
			{
			setChanged();
			notifyObservers();
			}
	}
}