package net.draconia.jobsemailcollector.ui.table.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class NextPage extends AbstractAction
{
	private static final long serialVersionUID = 6394254467888783515L;
	
	private ScrollablePageableModel mObjModel;
	
	public NextPage(final ScrollablePageableModel objModel)
	{
		super(">");
		
		setModel(objModel);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getModel().nextPage();
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