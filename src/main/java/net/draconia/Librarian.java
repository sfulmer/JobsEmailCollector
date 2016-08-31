package net.draconia;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.draconia.jobsemailcollector.manager.IndividualManager;
import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.model.Model;

public class Librarian implements Runnable, Serializable
{
	private static final long serialVersionUID = 4893441166040743330L;
	
	private IndividualManager mObjIndividualManager;
	private Model mObjModel;
	
	public Librarian(final Model objModel)
	{
		setModel(objModel);
	}
	
	protected IndividualManager getIndividualManager()
	{
		if(mObjIndividualManager == null)
			mObjIndividualManager = new IndividualManager(getModel());
		
		return(mObjIndividualManager);
	}
	
	protected List<Individual> getIndividuals()
	{
		return(getModel().getIndividuals());
	}
	
	protected Model getModel()
	{
		return(mObjModel);
	}
	
	protected void process(final Individual objIndividual) throws Exception
	{
		Individual objDBCopy = getIndividualManager().getIndividualByName(objIndividual.getName());
		
		if(objDBCopy.getId() <= 0)
			getIndividualManager().insertIndividual(objIndividual);
		else if(!objIndividual.equalsIgnoreId(objDBCopy))
			{
			if(!objDBCopy.getName().equals(objIndividual.getName()))
				objDBCopy.setName(objIndividual.getName());
			
			objDBCopy.setEmail(objIndividual.getEmail());
			objDBCopy.addEmailAddresses(objIndividual.getEmailAddressList());
			objDBCopy.addPhoneNumbers(objIndividual.getPhoneNumberList());
			
			getIndividualManager().updateIndividual(objDBCopy);
			}
	}
	
	public void run()
	{
		List<Individual> lstToDelete = Collections.synchronizedList(new ArrayList<Individual>());
		
		while(true)
			{
			while(getModel().getIndividuals().size() <= 0)
				Thread.yield();
			
			boolean bSuccessful = false;
			Individual objIndividual = getModel().getIndividuals().get(0);
				
			do
				{
				try
					{
					process(objIndividual);
					
					bSuccessful = true;
					}
				catch(SQLException objSQLException)
					{
					objSQLException.printStackTrace(System.err);
					}
				catch(Exception objException)
					{ }
				
				Thread.yield();
				}
			while(!bSuccessful);
			
			lstToDelete.add(objIndividual);
			
			getModel().removeIndividuals(lstToDelete);
			
			System.gc();
			
			Thread.yield();
			}
	}
	
	protected void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
}