package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;

import net.draconia.jobsemailcollector.ui.model.IndividualDetailFieldListAddDialogModel;

public class IndividualDetailFieldListToAddSaveObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 6010595607027885139L;
	
	private Action mActSave;
	
	public IndividualDetailFieldListToAddSaveObserver(final Action actSave)
	{
		setSaveAction(actSave);
	}
	
	protected Action getSaveAction()
	{
		return(mActSave);
	}
	
	protected void setSaveAction(final Action actSave)
	{
		mActSave = actSave;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		IndividualDetailFieldListAddDialogModel objModel = ((IndividualDetailFieldListAddDialogModel)(objObservable));
		
		getSaveAction().setEnabled(objModel.getValue().length() > 0);
	}
}