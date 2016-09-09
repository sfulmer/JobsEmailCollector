package net.draconia.jobsemailcollector.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.draconia.jobsemailcollector.domain.Email;

@Component
public class EmailDAOImpl implements EmailDAO
{
	private static final long serialVersionUID = -6332121410202485985L;
	
	@Autowired
	private SessionFactory mObjSessionFactory;
	
	public EmailDAOImpl()
	{ }
	
	public EmailDAOImpl(final SessionFactory objSessionFactory)
	{
		setSessionFactory(objSessionFactory);
	}
	
	public void delete(final Email objEmail)
	{
		Session objSession = getSessionFactory().openSession();
		
		objSession.delete(objEmail);
	}
	
	protected SessionFactory getSessionFactory()
	{
		return(mObjSessionFactory);
	}
	
	public void save(final Email objEmail)
	{ }
	
	protected void setSessionFactory(final SessionFactory objSessionFactory)
	{
		mObjSessionFactory = objSessionFactory;
	}
}