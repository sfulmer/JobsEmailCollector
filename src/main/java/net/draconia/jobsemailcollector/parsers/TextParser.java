package net.draconia.jobsemailcollector.parsers;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.draconia.jobsemailcollector.model.Email;
import net.draconia.jobsemailcollector.model.Model;

public class TextParser implements Parser
{
	private static final long serialVersionUID = -1641441220729689817L;
	
	private Model mObjModel;
	private String msText;
	private String[] msArrEmails;
	
	public String[] getEmails()
	{
		if(msArrEmails == null)
			msArrEmails = new String[0];
		
		return(msArrEmails);
	}
	
	public int getIndexOfNextFrom(final String sText, final int iCurrentIndex)
	{
		int iNextFrom;
		
		if(iCurrentIndex >= 0)
			iNextFrom = sText.indexOf("From ", iCurrentIndex + 1);
		else
			iNextFrom = sText.indexOf("From ");
		
		if(iNextFrom >= 0)
			if(!isValidEmailStart(sText, iNextFrom))
				return(getIndexOfNextFrom(sText, iNextFrom));
		
		return(iNextFrom);
	}
	
	public Model getModel()
	{
		return(mObjModel);
	}
	
	public String getText()
	{
		if(msText == null)
			msText = "";
		
		return(msText);
	}
	
	public boolean isValidEmailStart(final String sText, final int iIndex)
	{
		char cCharacterBefore;
		
		if(iIndex >= 0)
			if(iIndex > 0)
				{
				cCharacterBefore = sText.charAt(iIndex - 1);
				
				return((cCharacterBefore == '\n') || (cCharacterBefore == '\r'));
				}
			else
				return(true);
		else
			return(false);
	}
	
	public void parse() throws Exception
	{
		String[] sArrEmails = getEmails();
		
		for(int iLength = sArrEmails.length, iLoop = 0; iLoop < iLength - ((iLength > 1) ? 1 : 0); iLoop++)
			spawnEmailParser(sArrEmails[iLoop]);
	}
	
	public void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
	
	public void setText(final String sText)
	{
		if(sText == null)
			msText = "";
		else
			msText = sText;
		
		msArrEmails = splitToEmails(sText);
		
		msText = msArrEmails[msArrEmails.length - 1];
	}
	
	public void spawnEmailParser(final String sEmail) throws Exception
	{
		ExecutorService objExecutorService = Executors.newFixedThreadPool(10);
		Future<Email> objEmail = objExecutorService.submit(new EmailParser(sEmail));
		
		getModel().addEmail(objEmail.get());
	}
	
	public String[] splitToEmails(final String sText)
	{
		int iIndexToUse = 1;
		int[] iArrFromIndices = new int[] {0, -1};
		List<String> lstEmails = new ArrayList<String>();
		
		if((iArrFromIndices[iIndexToUse] = getIndexOfNextFrom(sText, -1)) > 0)
			lstEmails.add(sText.substring(iArrFromIndices[0], iArrFromIndices[1]));
		
		while(iArrFromIndices[1] >= 0) 
			{
			iArrFromIndices[(iIndexToUse == 1) ? 0 : 1] = iArrFromIndices[iIndexToUse];
			
			iArrFromIndices[iIndexToUse] = getIndexOfNextFrom(sText, iArrFromIndices[(iIndexToUse == 1) ? 0 : 1]);
			
			if(iArrFromIndices[iIndexToUse] >= 0)
				lstEmails.add(sText.substring(iArrFromIndices[0], iArrFromIndices[1]));
			}
		
		lstEmails.add(sText.substring(iArrFromIndices[0]));
					
		return(lstEmails.toArray(new String[0]));
	}
}