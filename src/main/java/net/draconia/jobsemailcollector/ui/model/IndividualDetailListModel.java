package net.draconia.jobsemailcollector.ui.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.draconia.jobsemailcollector.model.Individual;
import net.draconia.jobsemailcollector.ui.observers.IndividualDetailListModelObserver;

public class IndividualDetailListModel implements ListModel<String>, Serializable
{
	private static final long serialVersionUID = 2558900451077305342L;
	
	private Individual mObjModel;
	private List<ListDataListener> mLstListDataListeners;
	private List<String> mLstValues;
	private Observer mObjObserver;
	private String msField;
	
	public IndividualDetailListModel(final Individual objModel, final String sField)
	{
		setField(sField);
		setModel(objModel);
	}
	
	public void addListDataListener(final ListDataListener objListDataListener)
	{
		getListDataListeners().add(objListDataListener);
	}
	
	public void fireContentsChanged()
	{
		for(ListDataListener objListDataListener : getListDataListeners())
			objListDataListener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize()));
	}
	
	public String getElementAt(final int iIndex)
	{
		return(getFieldList().get(iIndex));
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getFieldList()
	{
		if(mLstValues == null)
			try
				{
				Class<?> clsModel = getModel().getClass();
				Method objMethod = clsModel.getDeclaredMethod("get" + getField() + "List", new Class<?>[0]);
				
				mLstValues = ((List<String>)(objMethod.invoke(getModel(), new Object[0])));
				}
			catch(Exception objException)
				{
				mLstValues = new ArrayList<String>();
				}
		
		return(mLstValues);
	}
	
	protected String getField()
	{
		return(msField);
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
	
	protected Observer getObserver()
	{
		if(mObjObserver == null)
			mObjObserver = new IndividualDetailListModelObserver(this, getField());
		
		return(mObjObserver);
	}
	
	public int getSize()
	{
		return(getFieldList().size());
	}
	
	public void removeListDataListener(final ListDataListener objListDataListener)
	{
		getListDataListeners().remove(objListDataListener);
	}
	
	public void refresh()
	{
		mLstValues = null;
		
		fireContentsChanged();
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setModel(final Individual objModel)
	{
		if(mObjModel != null)
			mObjModel.deleteObserver(getObserver());
		
		mObjModel = objModel;
		
		if(objModel != null)
			mObjModel.addObserver(getObserver());
	}
}