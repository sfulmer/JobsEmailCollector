package net.draconia.jobsemailcollector.dao;

import net.draconia.jobsemailcollector.domain.EmailAddress;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressDAOImpl implements EmailAddressDAO
{
	private static final long serialVersionUID = -2514117297351164316L;
	
	@Autowired
	private SessionFactory mObjSessionFactory;
	
	public EmailAddressDAOImpl()
	{ }
	
	public EmailAddressDAOImpl(final SessionFactory objSessionFactory)
	{
		setSessionFactory(objSessionFactory);
	}
	
	public void delete(final EmailAddress objEmailAddress)
	{
		Session objSession = getSessionFactory().openSession();
		
		objSession.delete(objEmailAddress);
	}
	
	protected SessionFactory getSessionFactory()
	{
		return(mObjSessionFactory);
	}

	public void save(final EmailAddress objEmailAddress)
	{ }
	
	protected void setSessionFactory(final SessionFactory objSessionFactory)
	{
		mObjSessionFactory = objSessionFactory;
	}
}