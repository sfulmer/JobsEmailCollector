package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.ui.model.IndividualDetailFieldListAddDialogModel;

public class IndividualDetailFieldListAddChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = 2751700584257894428L;
	
	private Action mActSave;
	private IndividualDetailFieldListAddDialogModel mObjModel;
	
	public IndividualDetailFieldListAddChangeListener(final IndividualDetailFieldListAddDialogModel objModel, final Action actSave)
	{
		setSaveAction(actSave);
		setModel(objModel);
	}
	
	protected IndividualDetailFieldListAddDialogModel getModel()
	{
		return(mObjModel);
	}
	
	protected Action getSaveAction()
	{
		return(mActSave);
	}
	
	protected void setModel(final IndividualDetailFieldListAddDialogModel objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setSaveAction(final Action actSave)
	{
		mActSave = actSave;
	}
	
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		JTextComponent txtSource = ((JTextComponent)(objChangeEvent.getSource()));
		String sControlValue = txtSource.getText(), sModelValue = getModel().getValue();
		
		if(!sControlValue.equals(sModelValue))
			getModel().setValue(sControlValue);
		
		getSaveAction().setEnabled(getModel().isDirty());
	}
}