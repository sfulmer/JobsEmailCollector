package net.draconia.jobsemailcollector.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import net.draconia.jobsemailcollector.model.Model;

import net.draconia.jobsemailcollector.parsers.IndividualParser;

public class EmailObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -4456932933426293416L;
	
	private IndividualParser mObjIndividualParser;
	
	public IndividualParser getIndividualParser()
	{
		if(mObjIndividualParser == null)
			mObjIndividualParser = new IndividualParser();
		
		return(mObjIndividualParser);
	}
	
	public void setIndividualParser(final IndividualParser objIndividualParser)
	{
		if(objIndividualParser == null)
			mObjIndividualParser = new IndividualParser();
		else
			mObjIndividualParser = objIndividualParser;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		Model objModel = ((Model)(objObservable));
		
		if(!objModel.getEmails().isEmpty())
			try
				{
				getIndividualParser().setModel(objModel);
				
				getIndividualParser().parse();
				}
			catch(Exception objException)
				{
				objException.printStackTrace(System.err);
				}
	}
}