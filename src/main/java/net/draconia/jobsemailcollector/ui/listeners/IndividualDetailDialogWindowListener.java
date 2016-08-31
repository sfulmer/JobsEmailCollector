package net.draconia.jobsemailcollector.ui.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Observer;

import net.draconia.jobsemailcollector.model.Individual;

public class IndividualDetailDialogWindowListener extends WindowAdapter implements Serializable
{
	private static final long serialVersionUID = 6642022918852184825L;
	
	private Individual mObjModel;
	private Observer mObjSaveApplyObserver;
	
	public IndividualDetailDialogWindowListener(final Individual objModel, final Observer objObserver)
	{
		setModel(objModel);
		setObserver(objObserver);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	protected Observer getObserver()
	{
		return(mObjSaveApplyObserver);
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setObserver(final Observer objObserver)
	{
		mObjSaveApplyObserver = objObserver;
	}
	
	public void windowClosed(final WindowEvent objWindowEvent)
	{
		getModel().deleteObserver(getObserver());
	}
	
	public void windowOpened(final WindowEvent objWindowEvent)
	{
		getModel().addObserver(getObserver());
	}
}