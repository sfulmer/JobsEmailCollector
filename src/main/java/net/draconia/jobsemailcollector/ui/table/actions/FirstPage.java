package net.draconia.jobsemailcollector.ui.table.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class FirstPage extends AbstractAction
{
	private static final long serialVersionUID = 1884821842716297131L;
	
	private ScrollablePageableModel mObjModel;
	
	public FirstPage(final ScrollablePageableModel objModel)
	{
		super("<<");
		
		setModel(objModel);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getModel().firstPage();
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