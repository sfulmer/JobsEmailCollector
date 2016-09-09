package net.draconia.jobsemailcollector.dao;

import java.util.List;

import net.draconia.jobsemailcollector.domain.Individual;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component(value="individualDAO")
public class IndividualDAOImpl implements IndividualDAO
{
	private static final long serialVersionUID = 2575597533203111083L;
	
	@Autowired
	private SessionFactory mObjSessionFactory;
	
	public IndividualDAOImpl()
	{ }
	
	public IndividualDAOImpl(final SessionFactory objSessionFactory)
	{
		setSessionFactory(objSessionFactory);
	}
	
	public void delete(final Individual objIndividual)
	{
		Session objSession = getSessionFactory().openSession();
		
		objSession.delete(objIndividual);
	}
	
	public Individual getIndividualById(final Integer iId)
	{
		return(null);
	}
	
	public Individual getIndividualByName(final String sName)
	{
		return(null);
	}

	public Integer getIndividualCount()
	{
		return(null);
	}
	
	protected SessionFactory getSessionFactory()
	{
		return(mObjSessionFactory);
	}

	@SuppressWarnings("unchecked")
	public List<Individual> list()
	{
		List<Individual> lstIndividuals;
		Session objSession = getSessionFactory().openSession();
		
		lstIndividuals = objSession.createQuery("from Person").list();
		
		objSession.close();
		
		return(lstIndividuals);
	}

	public void save(Individual objIndividual)
	{ }
	
	protected void setSessionFactory(final SessionFactory objSessionFactory)
	{
		mObjSessionFactory = objSessionFactory;
	}
}