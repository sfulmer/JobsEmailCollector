package net.draconia.jobsemailcollector.domain;

import java.io.Serializable;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Individual")
public class Individual extends Observable implements Cloneable, Observer, Serializable
{
	private static final long serialVersionUID = -1031879838860122322L;
	
	@OneToMany(mappedBy="mObjIndividual")
	private List<Email> mLstEmails;
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer miId;
	
	@OneToMany(mappedBy="mObjIndividual")
	private List<EmailAddress> mLstEmailAddresses;
	
	@OneToMany(mappedBy="mObjIndividual")
	private List<PhoneNumber> mLstPhoneNumbers;
	
	@Column(columnDefinition="text", insertable=true, name="Name", nullable=false, updatable=true)
	private String msName;
	
	public Individual()
	{ }
	
	public boolean addEmailAddress(final EmailAddress objEmailAddress)
	{
		boolean bReturnValue = getEmailAddresses().add(objEmailAddress);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public void addEmailAddress(final int iIndex, final EmailAddress objEmailAddress)
	{
		getEmailAddresses().add(iIndex, objEmailAddress);
		
		setChanged();
		notifyObservers();
	}
	
	public boolean addEmailAddresses(final Collection<EmailAddress> collEmailAddresses)
	{
		boolean bReturnValue = getEmailAddresses().addAll(collEmailAddresses);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addEmailAddresses(final int iIndex, final Collection<EmailAddress> collEmailAddresses)
	{
		boolean bReturnValue = getEmailAddresses().addAll(iIndex, collEmailAddresses);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addPhoneNumber(final PhoneNumber objPhoneNumber)
	{
		boolean bReturnValue = getPhoneNumbers().add(objPhoneNumber);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public void addPhoneNumber(final int iIndex, final PhoneNumber objPhoneNumber)
	{
		getPhoneNumbers().add(iIndex, objPhoneNumber);
		
		setChanged();
		notifyObservers();
	}
	
	public boolean addPhoneNumbers(final Collection<PhoneNumber> collPhoneNumbers)
	{
		boolean bReturnValue = getPhoneNumbers().addAll(collPhoneNumbers);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addPhoneNumbers(final int iIndex, final Collection<PhoneNumber> collPhoneNumbers)
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object clone()
	{
		Individual mObjClone = new Individual();
		
		mObjClone.setId(getId());
		mObjClone.setName(getName());
		
		for(EmailAddress objEmailAddress : getEmailAddresses())
			mObjClone.addEmailAddress((EmailAddress)(objEmailAddress.clone()));
		
		for(PhoneNumber objPhoneNumber : getPhoneNumbers())
			mObjClone.addPhoneNumber((PhoneNumber)(objPhoneNumber.clone()));
		
		List<Email> lstEmails;
		
		try
			{
			Constructor<? extends List> funcConstructor = getEmails().getClass().getDeclaredConstructor(new Class<?>[0]);
			
			if(!funcConstructor.isAccessible())
				funcConstructor.setAccessible(true);
			
			lstEmails = funcConstructor.newInstance(new Object[0]);
			
			for(Email objEmail : getEmails())
				lstEmails.add((Email)(objEmail.clone()));
			}
		catch(IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException objException)
			{
			objException.printStackTrace(System.err);
			
			lstEmails = new ArrayList<Email>();
			}
		
		mObjClone.setEmails(lstEmails);
		
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
	
	public boolean equalsIgnoreId(final Individual objIndividual)
	{
		boolean bEquals = true;
		
		bEquals = bEquals && (getName().equals(objIndividual.getName()));
		
		if((getEmails() != null) && (objIndividual.getEmails() != null))
			if(getEmails().size() == objIndividual.getEmails().size())
				{
				Email[] arrObjEmails = getEmails().toArray(new Email[0]);
				Email[] arrObjOtherEmails = objIndividual.getEmails().toArray(new Email[0]);
				
				for(int iLength = arrObjEmails.length, iLoop = 0; iLoop < iLength; iLoop++)
					if(bEquals)
						bEquals = bEquals && arrObjEmails[iLoop].equalsIgnoreId(arrObjOtherEmails[iLoop]);
				}
		else
			bEquals = false;
		
		if(bEquals)
			bEquals = Objects.deepEquals(getEmailAddresses(), objIndividual.getEmailAddresses());
		if(bEquals)
			bEquals = Objects.deepEquals(getPhoneNumbers(), objIndividual.getPhoneNumbers());
		
		return(bEquals);
	}
	
	public List<Email> getEmails()
	{
		return(mLstEmails);
	}
	
	public List<EmailAddress> getEmailAddressList()
	{
		return(Collections.unmodifiableList(getEmailAddresses()));
	}
	
	protected List<EmailAddress> getEmailAddresses()
	{
		if(mLstEmailAddresses == null)
			mLstEmailAddresses = Collections.synchronizedList(new ArrayList<EmailAddress>());
		
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
	
	public List<PhoneNumber> getPhoneNumberList()
	{
		return(Collections.unmodifiableList(getPhoneNumbers()));
	}
	
	protected List<PhoneNumber> getPhoneNumbers()
	{
		if(mLstPhoneNumbers == null)
			mLstPhoneNumbers = Collections.synchronizedList(new ArrayList<PhoneNumber>());
		
		return(mLstPhoneNumbers);
	}
	
	public EmailAddress removeEmailAddress(final int iIndex)
	{
		EmailAddress sReturnValue = getEmailAddresses().remove(iIndex);
		
		setChanged();
		notifyObservers();
		
		return(sReturnValue);
	}
	
	public boolean removeEmailAddress(final EmailAddress sEmailAddress)
	{
		boolean bReturnValue = getEmailAddresses().remove(sEmailAddress);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean removeEmailAddresses(final Collection<EmailAddress> collEmailAddresses)
	{
		boolean bReturnValue = getEmailAddresses().removeAll(collEmailAddresses);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public PhoneNumber removePhoneNumber(final int iIndex)
	{
		PhoneNumber objReturnValue = getPhoneNumbers().remove(iIndex);
		
		setChanged();
		notifyObservers();
		
		return(objReturnValue);
	}
	
	public boolean removePhoneNumber(final PhoneNumber objPhoneNumber)
	{
		boolean bReturnValue = getPhoneNumbers().remove(objPhoneNumber);
		
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
			for(EmailAddress objEmailAddress : getEmailAddressList())
				addEmailAddress(objEmailAddress);
			
			clearPhoneNumbers();
			for(PhoneNumber objPhoneNumber : getPhoneNumberList())
				addPhoneNumber(objPhoneNumber);
			
			for(Email objEmail : objIndividual.getEmails())
				getEmails().add((Email)(objEmail.clone()));
			}
	}
	
	public void setEmails(final List<Email> lstEmails)
	{
		mLstEmails = lstEmails;
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