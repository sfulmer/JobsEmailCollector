package net.draconia.jobsemailcollector.ui.observers;

import java.io.File;
import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;

import org.apache.commons.lang3.tuple.Pair;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResumeAttachmentsRemoveObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -576032222654397440L;
	
	private Action mActRemove;
	
	public SendResumeAttachmentsRemoveObserver(final Action actRemove)
	{
		setRemoveAction(actRemove);
	}
	
	protected Action getRemoveAction()
	{
		return(mActRemove);
	}
	
	protected void setRemoveAction(final Action actRemove)
	{
		mActRemove = actRemove;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		int iSelectedCount = 0;
		SendResumeDialogModel objModel = ((SendResumeDialogModel)(objObservable));
		
		for(Pair<File, Boolean> pairFile : objModel.getAttachmentList())
			if(pairFile.getValue())
				iSelectedCount++;
		
		getRemoveAction().setEnabled(iSelectedCount > 0);
	}
}