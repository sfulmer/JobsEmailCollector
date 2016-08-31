package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import net.draconia.jobsemailcollector.model.Individual;

public class IndividualNameChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = -7083214791590517515L;
	
	private Individual mObjModel;
	
	public IndividualNameChangeListener(final Individual objModel)
	{
		setModel(objModel);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		JTextComponent txtName = ((JTextComponent)(objChangeEvent.getSource()));
		String sControlValue = txtName.getText();
		
		if(!sControlValue.equals(getModel().getName()))
			getModel().setName(sControlValue);
	}
}