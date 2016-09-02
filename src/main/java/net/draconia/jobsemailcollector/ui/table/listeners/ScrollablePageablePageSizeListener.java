package net.draconia.jobsemailcollector.ui.table.listeners;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class ScrollablePageablePageSizeListener extends AbstractAction
{
	private static final long serialVersionUID = 6210450269297020248L;
	
	private ScrollablePageableModel mObjModel;
	
	public ScrollablePageablePageSizeListener(final ScrollablePageableModel objModel)
	{
		setModel(objModel);
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		Integer iControlPageSize, iModelPageSize;
		JComboBox<Integer> cboPageSize = ((JComboBox<Integer>)(objActionEvent.getSource()));
		
		iControlPageSize = ((Integer)(cboPageSize.getSelectedItem()));
		iModelPageSize = getModel().getPageSize();
		
		if(!iControlPageSize.equals(iModelPageSize))
			{
			if(!getModel().getPageSizes().contains(iControlPageSize))
				getModel().addPageSize(iControlPageSize);
			
			getModel().setPageSize(iControlPageSize);
			}
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