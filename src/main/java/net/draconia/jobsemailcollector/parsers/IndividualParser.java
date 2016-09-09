package net.draconia.jobsemailcollector.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.draconia.jobsemailcollector.domain.Email;
import net.draconia.jobsemailcollector.domain.Individual;
import net.draconia.jobsemailcollector.model.Model;

public class IndividualParser implements Parser
{
	private static final long serialVersionUID = 1722762801232520744L;
	
	private static final String[] mssArrAvoidEmailList = new String[]
														{	"seth.fulmer@gmail.com"
														, 	"angel4hire00@yahoo.com"
														,	"dennislloydjr@gmail.com"
														,	"spmcginnis@gmail.com"
														,	"lisay23@hotmail.com"
														};
	
	private Individual mObjIndividual;
	private Model mObjModel;
	
	public List<String> collectEmails(final String sEmail)
	{
		List<String> lstEmails = Collections.synchronizedList(new ArrayList<String>());
		Matcher objMatcher;
		Pattern objPattern = Pattern.compile("([\\w\\.]+)@([\\w\\.]+)", Pattern.MULTILINE);
		
		objMatcher = objPattern.matcher(sEmail);
		
		if(objMatcher.find())
			do
				{
				String sEmailAddress = sEmail.substring(objMatcher.start(), objMatcher.end());
				
				if((!isEmailToBeAvoided(sEmailAddress)) && (!lstEmails.contains(sEmailAddress)))
					lstEmails.add(sEmailAddress);
				}
			while(objMatcher.find(objMatcher.end()));
		
		return(lstEmails);
	}
	
	public String filterOutEmailAddress(final String sEmail)
	{
		Matcher objMatcher;
		Pattern objPattern = Pattern.compile("([\\w\\.]+)@([\\w\\.]+)", Pattern.MULTILINE);
		
		objMatcher = objPattern.matcher(sEmail);
		
		if(objMatcher.find())
			return(sEmail.substring(objMatcher.start(), objMatcher.end()));
		else
			return(sEmail);
	}
	
	public List<String> getEmailAddresses(final Email objEmail)
	{
		List<String> lstAddresses = Collections.synchronizedList(new ArrayList<String>());
		List<String> lstToProcess = collectEmails(objEmail.getBody());
		
		lstToProcess.addAll(collectEmails(objEmail.getFrom()));
		lstToProcess.addAll(collectEmails(objEmail.getTo()));
		
		for(int iLength = lstToProcess.size(), iLoop = 0; iLoop < iLength; iLoop++)
			lstToProcess.set(iLoop, lstToProcess.get(iLoop).toLowerCase());
				
		for(String sAddress : lstToProcess)
			if((!ArrayUtils.contains(mssArrAvoidEmailList, sAddress)) && (!ArrayUtils.contains(lstAddresses.toArray(new String[0]), sAddress)))
				lstAddresses.add(sAddress);
		
		return(lstAddresses);
	}
	
	public Individual getIndividual()
	{
		if(mObjIndividual == null)
			mObjIndividual = new Individual();
		
		return(mObjIndividual);
	}
	
	public Model getModel()
	{
		return(mObjModel);
	}
	
	public String getName(final List<String> lstEmailAddresses)
	{
		for(String sEmailAddress : lstEmailAddresses)
			if(!isEmailToBeAvoided(filterOutEmailAddress(sEmailAddress)))
				{
				String sName = StringUtils.removePattern(StringUtils.removePattern(sEmailAddress, "<.*>"), "\"");
				
				if(!sName.isEmpty())
					return(sName);
				}
		
		return("");
	}
	
	public List<String> getPhoneNumbers(final Email objEmail)
	{
		List<String> lstPhoneNumbers = Collections.synchronizedList(new ArrayList<String>());
		List<String> lstToProcess = Collections.synchronizedList(new ArrayList<String>());
		Matcher objMatcher;
		Pattern objPattern = Pattern.compile("\\b(\\(?)(\\d{3})(\\)?)[-\\s](\\d{3})[-\\s](\\d{4})", Pattern.MULTILINE);
		String sEmailBody = objEmail.getBody();
		
		objMatcher = objPattern.matcher(sEmailBody);
		
		if(objMatcher.find())
			do
				{
				lstToProcess.add(StringUtils.removePattern(sEmailBody.substring(objMatcher.start(), objMatcher.end()), "\\s|-"));
				}
			while(objMatcher.find(objMatcher.end()));
		
		for(String sPhoneNumber : lstToProcess)
			if(!lstPhoneNumbers.contains(sPhoneNumber))
				lstPhoneNumbers.add(sPhoneNumber);
		
		return(lstPhoneNumbers);
	}
	
	public boolean isEmailToBeAvoided(final String sEmailAddress)
	{
		return(ArrayUtils.contains(mssArrAvoidEmailList, sEmailAddress));
	}
	
	public void parse() throws Exception
	{
		List<Email> lstEmailsToRemove = Collections.synchronizedList(new ArrayList<Email>());
		List<Individual> lstIndividuals = Collections.synchronizedList(new ArrayList<Individual>());
		
		resetIndividual();
		
		for(Email objEmail : getModel().getEmails())
			{
			if(!isEmailToBeAvoided(filterOutEmailAddress(objEmail.getFrom())))
				{
				getIndividual().setName(getName(Arrays.asList(new String[] {objEmail.getFrom(), objEmail.getTo()})));
//				getIndividual().addEmailAddresses(getEmailAddresses(objEmail));
//				getIndividual().addPhoneNumbers(getPhoneNumbers(objEmail));
				//getIndividual().addEmail(objEmail);  //TODO: Must fix!
				
				lstIndividuals.add(getIndividual());
				}
			
			lstEmailsToRemove.add(objEmail);
			}
		
		if(!lstEmailsToRemove.isEmpty())
			getModel().removeEmails(lstEmailsToRemove);
		if(!lstIndividuals.isEmpty())
			getModel().addIndividuals(lstIndividuals);
	}
	
	public void resetIndividual()
	{
		mObjIndividual = null;
	}
	
	public void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
}