package net.draconia.jobsemailcollector.ui.model;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import net.draconia.jobsemailcollector.model.Individual;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class SendResumeDialogModel extends Observable implements Serializable
{
	private static final long serialVersionUID = 3692944408440834651L;
	
	private Individual mObjModel;
	private List<Pair<File, Boolean>> mLstFileAttachments;
	private String msBody, msFrom, msSubject, msTo;
	
	public SendResumeDialogModel(final Individual objModel)
	{
		setModel(objModel);
	}
	
	public boolean addAttachment(final File fileAttachment)
	{
		boolean bReturn = getAttachments().add(new MutablePair<File, Boolean>(fileAttachment, false));
		
		setChanged();
		notifyObservers();
		
		return(bReturn);
	}
	
	public boolean addAttachments(final Collection<Pair<File, Boolean>> collFileAttachments)
	{
		boolean bReturn = getAttachments().addAll(collFileAttachments);
		
		setChanged();
		notifyObservers();
		
		return(bReturn);
	}
	
	public List<Pair<File, Boolean>> getAttachmentList()
	{
		return(Collections.unmodifiableList(getAttachments()));
	}
	
	protected List<Pair<File, Boolean>> getAttachments()
	{
		if(mLstFileAttachments == null)
			mLstFileAttachments = Collections.synchronizedList(new ArrayList<Pair<File, Boolean>>());
		
		return(mLstFileAttachments);
	}
	
	public String getBody()
	{
		if(msBody == null)
			msBody = getDefaultBody();
		
		return(msBody);
	}
	
	protected String getDefaultBody()
	{
		return("");
	}
	
	protected String getDefaultTo()
	{
		if(getModel().getEmailAddressList().size() > 0)
			return(getModel().getEmailAddressList().get(0));
		else
			return("");
	}
	
	public String getFrom()
	{
		if(msFrom == null)
			msFrom = "seth.fulmer@gmail.com";
		
		return(msFrom);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	public String getSubject()
	{
		if(msSubject == null)
			msSubject = "";
		
		return(msSubject);
	}
	
	public String getTo()
	{
		if(msTo == null)
			msTo = getDefaultTo();
		
		return(msTo);
	}
	
	public boolean removeAttachment(final File fileAttachment)
	{
		boolean bReturn = getAttachments().remove(fileAttachment);
		
		setChanged();
		notifyObservers();
		
		return(bReturn);
	}
	
	public boolean removeAttachments(final Collection<Pair<File, Boolean>> collFileAttachments)
	{
		boolean bReturn = getAttachments().removeAll(collFileAttachments);
		
		setChanged();
		notifyObservers();
		
		return(bReturn);
	}
	
	public void selectAttachment(final int iAttachment, final boolean bSelect)
	{
		getAttachments().get(iAttachment).setValue(bSelect);
		
		setChanged();
		notifyObservers();
	}
	
	public void selectAttachment(final File fileAttachment, final boolean bSelect)
	{
		for(Pair<File, Boolean> pairAttachment : getAttachments())
			if(pairAttachment.getKey().equals(fileAttachment))
				pairAttachment.setValue(bSelect);
		
		setChanged();
		notifyObservers();
	}
	
	public void selectAttachments(final List<File> lstFileAttachments, final boolean bSelect)
	{
		for(Pair<File, Boolean> pairAttachment : getAttachments())
			for(File fileAttachment : lstFileAttachments)
				if(pairAttachment.getKey().equals(fileAttachment))
					pairAttachment.setValue(bSelect);
		
		setChanged();
		notifyObservers();
	}
	
	public void setBody(final String sBody)
	{
		if(sBody == null)
			msBody = getDefaultBody();
		else
			msBody = sBody;
		
		setChanged();
		notifyObservers();
	}
	
	public void setFrom(final String sFrom)
	{
		if(sFrom == null)
			msFrom = "seth.fulmer@gmail.com";
		else
			msFrom = sFrom;
		
		setChanged();
		notifyObservers();
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	public void setSubject(final String sSubject)
	{
		if(sSubject == null)
			msSubject = "New Resume for your Files";
		else
			msSubject = sSubject;
		
		setChanged();
		notifyObservers();
	}
	
	public void setTo(final String sTo)
	{
		if(sTo == null)
			msTo = getDefaultTo();
		else
			msTo = sTo;
		
		setChanged();
		notifyObservers();
	}
}