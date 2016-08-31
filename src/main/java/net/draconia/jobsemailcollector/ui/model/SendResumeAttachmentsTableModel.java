package net.draconia.jobsemailcollector.ui.model;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.table.TableModel;

import org.apache.commons.lang3.tuple.Pair;

public class SendResumeAttachmentsTableModel implements Serializable, TableModel
{
	private static final long serialVersionUID = 6202515874523931468L;
	
	private List<TableModelListener> mLstTableModelListeners;
	private SendResumeDialogModel mObjModel;
	
	public SendResumeAttachmentsTableModel(final SendResumeDialogModel objModel)
	{
		setModel(objModel);
	}
	
	public void addTableModelListener(final TableModelListener objTableModelListener)
	{
		getTableModelListeners().add(objTableModelListener);
	}
	
	public void fireTableChanged()
	{
		for(TableModelListener objTableModelListener : getTableModelListeners())
			objTableModelListener.tableChanged(new TableModelEvent(this));
	}
	
	public Class<?> getColumnClass(final int iColumnIndex)
	{
		switch(iColumnIndex)
			{
			case 0:
				return(File.class);
			case 1:
				return(Boolean.class);
			default:
				return(null);
			}
	}
	
	public int getColumnCount()
	{
		return(2);
	}
	
	public String getColumnName(final int iColumnIndex)
	{
		switch(iColumnIndex)
			{
			case 0:
				return("File");
			case 1:
				return("Select");
			default:
				return(null);
			}
	}
	
	protected SendResumeDialogModel getModel()
	{
		return(mObjModel);
	}
	
	public int getRowCount()
	{
		return(getModel().getAttachmentList().size());
	}
	
	protected List<TableModelListener> getTableModelListeners()
	{
		if(mLstTableModelListeners == null)
			mLstTableModelListeners = new ArrayList<TableModelListener>();
		
		return(mLstTableModelListeners);
	}
	
	public Object getValueAt(final int iRowIndex, final int iColumnIndex)
	{
		Pair<File, Boolean> pairFileValue = getModel().getAttachmentList().get(iRowIndex);
		
		switch(iColumnIndex)
			{
			case 0:
				return(pairFileValue.getKey());
			case 1:
				return(pairFileValue.getValue());
			default:
				return(null);
			}
	}
	
	public boolean isCellEditable(final int iRowIndex, final int iColumnIndex)
	{
		return(iColumnIndex == 1);
	}
	
	public void removeTableModelListener(final TableModelListener objTableModelListener)
	{
		getTableModelListeners().remove(objTableModelListener);
	}
	
	protected void setModel(final SendResumeDialogModel objModel)
	{
		mObjModel = objModel;
	}
	
	public void setValueAt(final Object objValue, final int iRowIndex, final int iColumnIndex)
	{
		if(iColumnIndex == 1)
			if(objValue instanceof Boolean)
				getModel().selectAttachment(iRowIndex, (Boolean)(objValue));
	}
}