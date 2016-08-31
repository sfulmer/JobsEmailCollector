package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import net.draconia.jobsemailcollector.ui.model.SendResumeAttachmentsTableModel;

public class SendResumeAttachmentTableModelObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -8956405997529557837L;
	
	private SendResumeAttachmentsTableModel mObjTableModel;
	
	public SendResumeAttachmentTableModelObserver(final SendResumeAttachmentsTableModel objTableModel)
	{
		setTableModel(objTableModel);
	}
	
	protected SendResumeAttachmentsTableModel getTableModel()
	{
		return(mObjTableModel);
	}
	
	protected void setTableModel(final SendResumeAttachmentsTableModel objTableModel)
	{
		mObjTableModel = objTableModel;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		getTableModel().fireTableChanged();
	}
}