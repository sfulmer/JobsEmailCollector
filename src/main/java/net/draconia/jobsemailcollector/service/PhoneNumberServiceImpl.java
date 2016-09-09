package net.draconia.jobsemailcollector.service;

import net.draconia.jobsemailcollector.dao.PhoneNumberDAO;

import net.draconia.jobsemailcollector.domain.PhoneNumber;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService
{
	private static final long serialVersionUID = 1308440032745408898L;
	
	@Autowired
	private PhoneNumberDAO mObjPhoneNumberDAO;
	
	public PhoneNumberServiceImpl()
	{ }
	
	public PhoneNumberServiceImpl(final PhoneNumberDAO objPhoneNumberDAO)
	{
		setPhoneNumberDAO(objPhoneNumberDAO);
	}
	
	public void delete(final PhoneNumber objPhoneNumber)
	{
		getPhoneNumberDAO().delete(objPhoneNumber);
	}
	
	protected PhoneNumberDAO getPhoneNumberDAO()
	{
		return(mObjPhoneNumberDAO);
	}
	
	public void save(final PhoneNumber objPhoneNumber)
	{
		getPhoneNumberDAO().save(objPhoneNumber);
	}
	
	protected void setPhoneNumberDAO(final PhoneNumberDAO objPhoneNumberDAO)
	{
		mObjPhoneNumberDAO = objPhoneNumberDAO;
	}
}