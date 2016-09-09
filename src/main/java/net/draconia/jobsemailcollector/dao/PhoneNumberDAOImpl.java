package net.draconia.jobsemailcollector.dao;

import net.draconia.jobsemailcollector.domain.PhoneNumber;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberDAOImpl implements PhoneNumberDAO
{
	private static final long serialVersionUID = -2514117297351164316L;
	
	@Autowired
	private SessionFactory mObjSessionFactory;
	
	public PhoneNumberDAOImpl()
	{ }
	
	public PhoneNumberDAOImpl(final SessionFactory objSessionFactory)
	{
		setSessionFactory(objSessionFactory);
	}
	
	public void delete(final PhoneNumber objPhoneNumber)
	{
		Session objSession = getSessionFactory().openSession();
		
		objSession.delete(objPhoneNumber);
	}
	
	protected SessionFactory getSessionFactory()
	{
		return(mObjSessionFactory);
	}

	public void save(final PhoneNumber objPhoneNumber)
	{ }
	
	protected void setSessionFactory(final SessionFactory objSessionFactory)
	{
		mObjSessionFactory = objSessionFactory;
	}
}