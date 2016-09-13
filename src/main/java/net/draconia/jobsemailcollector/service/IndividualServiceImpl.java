package net.draconia.jobsemailcollector.service;

import java.util.List;

import net.draconia.jobsemailcollector.dao.IndividualDAO;

import net.draconia.jobsemailcollector.domain.Individual;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service(value="individualService")
public class IndividualServiceImpl implements IndividualService
{
	private static final long serialVersionUID = 9197887556573721242L;
	
	@Autowired
	private IndividualDAO mObjIndividualDAO; 
	
	public IndividualServiceImpl()
	{ }
	
	public IndividualServiceImpl(final IndividualDAO objIndividualDAO)
	{
		setIndividualDAO(objIndividualDAO);
	}
	
	public void delete(final Individual objIndividual)
	{
		getIndividualDAO().delete(objIndividual);
	}

	public Individual getIndividualById(final Integer iId)
	{
		return(getIndividualDAO().getIndividualById(iId));
	}
	
	public Individual getIndividualByName(final String sName)
	{
		return(getIndividualDAO().getIndividualByName(sName));
	}
	
	public Long getIndividualCount()
	{
		return(getIndividualDAO().getIndividualCount());
	}
	
	protected IndividualDAO getIndividualDAO()
	{
		return(mObjIndividualDAO);
	}
	
	public List<Individual> list()
	{
		return(getIndividualDAO().list());
	}
	
	public void save(Individual objIndividual)
	{ }
	
	protected void setIndividualDAO(final IndividualDAO objIndividualDAO)
	{
		mObjIndividualDAO = objIndividualDAO;
	}
}