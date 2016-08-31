package net.draconia.jobsemailcollector.ui.model;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.draconia.jobsemailcollector.model.Model;

public class FileListModel implements ListModel<File>, Serializable
{
	private static final long serialVersionUID = 39398596390351756L;
	
	private List<ListDataListener> mLstListDataListeners;
	private Model mObjModel;
	
	public FileListModel(final Model objModel)
	{
		setModel(objModel);
	}
	
	public void addListDataListener(final ListDataListener objListDataListener)
	{
		getListDataListeners().add(objListDataListener);
	}
	
	public void fireContentsChanged()
	{
		for(ListDataListener objListener : getListDataListeners())
			objListener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize()));
	}
	
	public File getElementAt(final int iIndex)
	{
		return(getModel().getFilesToImport().get(iIndex));
	}
	
	public Model getModel()
	{
		return(mObjModel);
	}
	
	protected List<ListDataListener> getListDataListeners()
	{
		if(mLstListDataListeners == null)
			mLstListDataListeners = new ArrayList<ListDataListener>();
		
		return(mLstListDataListeners);
	}
	
	public int getSize()
	{
		return(getModel().getFilesToImport().size());
	}
	
	public void removeListDataListener(final ListDataListener objListDataListener)
	{
		getListDataListeners().remove(objListDataListener);
	}
	
	protected void setModel(final Model objModel)
	{
		mObjModel = objModel;
	}
}