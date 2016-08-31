package net.draconia.jobsemailcollector.ui.listeners;

import java.io.Serializable;

import javax.swing.Action;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class IndividualDetailFieldListSelectionListener implements ListSelectionListener, Serializable
{
	private static final long serialVersionUID = 4717819141921850573L;
	
	private Action mActEdit, mActRemove;
	
	public IndividualDetailFieldListSelectionListener(final Action actEdit, final Action actRemove)
	{
		setEditAction(actEdit);
		setRemoveAction(actRemove);
	}
	
	protected Action getEditAction()
	{
		return(mActEdit);
	}
	
	protected Action getRemoveAction()
	{
		return(mActRemove);
	}
	
	protected void setEditAction(final Action actEdit)
	{
		mActEdit = actEdit;
	}
	
	protected void setRemoveAction(final Action actRemove)
	{
		mActRemove = actRemove;
	}
	
	@SuppressWarnings("unchecked")
	public void valueChanged(final ListSelectionEvent objListSelectionEvent)
	{
		JList<String> lstSource = ((JList<String>)(objListSelectionEvent.getSource()));
		
		getEditAction().setEnabled(lstSource.getSelectedIndices().length == 1);
		getRemoveAction().setEnabled(lstSource.getSelectedIndices().length > 0);
	}
}