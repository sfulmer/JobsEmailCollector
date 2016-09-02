package net.draconia.jobsemailcollector.ui.table.listeners;

import java.io.Serializable;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class ScrollablePageableCurrentPageChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = -8037573015036453977L;
	
	private ScrollablePageableModel mObjModel;
	
	public ScrollablePageableCurrentPageChangeListener(final ScrollablePageableModel objModel)
	{
		setModel(objModel);
	}
	
	protected ScrollablePageableModel getModel()
	{
		return(mObjModel);
	}
	
	protected void setModel(final ScrollablePageableModel objModel)
	{
		mObjModel = objModel;
	}
	
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		SpinnerNumberModel objSpinnerModel = ((SpinnerNumberModel)(objChangeEvent.getSource()));
		
		if(!getModel().getCurrentPage().equals(objSpinnerModel.getValue()))
			getModel().setCurrentPage((Integer)(objSpinnerModel.getValue()));
	}
}