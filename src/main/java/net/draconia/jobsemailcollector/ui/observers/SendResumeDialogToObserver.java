package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

import net.draconia.jobsemailcollector.ui.model.SendResumeDialogModel;

public class SendResumeDialogToObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 4287698213145674018L;
	
	private JComboBox<String> mCboTo;
	
	public SendResumeDialogToObserver(final JComboBox<String> cboTo)
	{
		setToField(cboTo);
	}
	
	protected JComboBox<String> getToField()
	{
		return(mCboTo);
	}
	
	protected void setToField(final JComboBox<String> cboTo)
	{
		mCboTo = cboTo;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		SendResumeDialogModel objModel = ((SendResumeDialogModel)(objObservable));
		
		if(!objModel.getTo().equals(getToField().getSelectedItem()))
			getToField().setSelectedItem(objModel.getTo());
	}
}