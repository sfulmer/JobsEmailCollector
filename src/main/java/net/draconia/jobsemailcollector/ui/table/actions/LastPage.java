package net.draconia.jobsemailcollector.ui.table.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class LastPage extends AbstractAction
{
	private static final long serialVersionUID = 6394254467888783515L;
	
	private ScrollablePageableModel mObjModel;
	
	public LastPage(final ScrollablePageableModel objModel)
	{
		super(">>");
		
		setModel(objModel);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getModel().lastPage();
	}
	
	protected ScrollablePageableModel getModel()
	{
		return(mObjModel);
	}
	
	protected void setModel(final ScrollablePageableModel objModel)
	{
		mObjModel = objModel;
	}
}