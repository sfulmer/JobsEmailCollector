package net.draconia.jobsemailcollector.service;

import net.draconia.jobsemailcollector.dao.EmailDAO;

import net.draconia.jobsemailcollector.domain.Email;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService
{
	private static final long serialVersionUID = 4301320153662954351L;
	
	@Autowired
	private EmailDAO mObjEmailDAO;
	
	public EmailServiceImpl()
	{ }
	
	public EmailServiceImpl(final EmailDAO objEmailDAO)
	{
		setEmailDAO(objEmailDAO);
	}
	
	public void delete(final Email objEmail)
	{
		getEmailDAO().delete(objEmail);
	}
	
	protected EmailDAO getEmailDAO()
	{
		return(mObjEmailDAO);
	}

	public void save(final Email objEmail)
	{
		getEmailDAO().save(objEmail);
	}
	
	protected void setEmailDAO(final EmailDAO objEmailDAO)
	{
		mObjEmailDAO = objEmailDAO;
	}
}