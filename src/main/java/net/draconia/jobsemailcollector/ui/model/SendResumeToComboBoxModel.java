package net.draconia.jobsemailcollector.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import net.draconia.jobsemailcollector.model.Individual;

public class SendResumeToComboBoxModel implements ComboBoxModel<String>, Serializable
{
	private static final long serialVersionUID = 319427693766580414L;
	
	private Individual mObjModel;
	private List<ListDataListener> mLstListDataListeners;
	private String msSelectedItem;
	
	public SendResumeToComboBoxModel(final Individual objModel)
	{
		setModel(objModel);
	}
	
	public void addListDataListener(final ListDataListener objListDataListener)
	{
		getListDataListeners().add(objListDataListener);
	}
	
	public String getElementAt(final int iIndex)
	{
		return(getModel().getEmailAddressList().get(iIndex));
	}
	
	protected List<ListDataListener> getListDataListeners()
	{
		if(mLstListDataListeners == null)
			mLstListDataListeners = new ArrayList<ListDataListener>();
		
		return(mLstListDataListeners);
	}
	
	protected Individual getModel()
	{
		return(mObjModel);
	}
	
	public Object getSelectedItem()
	{
		if(msSelectedItem == null)
			if(getSize() > 0)
				msSelectedItem = getElementAt(0);
			else
				msSelectedItem = "";
		
		return(msSelectedItem);
	}
	
	public int getSize()
	{
		return(getModel().getEmailAddressList().size());
	}
	
	public void removeListDataListener(final ListDataListener objListDataListener)
	{
		getListDataListeners().remove(objListDataListener);
	}
	
	protected void setModel(final Individual objModel)
	{
		mObjModel = objModel;
	}
	
	public void setSelectedItem(final Object objItem)
	{
		if(objItem instanceof String)
			msSelectedItem = ((String)(objItem));
	}
}