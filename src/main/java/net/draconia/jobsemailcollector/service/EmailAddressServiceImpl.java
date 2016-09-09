package net.draconia.jobsemailcollector.service;

import net.draconia.jobsemailcollector.dao.EmailAddressDAO;

import net.draconia.jobsemailcollector.domain.EmailAddress;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class EmailAddressServiceImpl implements EmailAddressService
{
	private static final long serialVersionUID = 1308440032745408898L;
	
	@Autowired
	private EmailAddressDAO mObjEmailAddressDAO;
	
	public EmailAddressServiceImpl()
	{ }
	
	public EmailAddressServiceImpl(final EmailAddressDAO objEmailAddressDAO)
	{
		setEmailAddressDAO(objEmailAddressDAO);
	}
	
	public void delete(final EmailAddress objEmailAddress)
	{
		getEmailAddressDAO().delete(objEmailAddress);
	}
	
	protected EmailAddressDAO getEmailAddressDAO()
	{
		return(mObjEmailAddressDAO);
	}
	
	public void save(final EmailAddress objEmailAddress)
	{
		getEmailAddressDAO().save(objEmailAddress);
	}
	
	protected void setEmailAddressDAO(final EmailAddressDAO objEmailAddressDAO)
	{
		mObjEmailAddressDAO = objEmailAddressDAO;
	}
}