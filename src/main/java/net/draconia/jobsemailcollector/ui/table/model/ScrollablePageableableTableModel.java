package net.draconia.jobsemailcollector.ui.table.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TableModelListener;

import javax.swing.table.TableModel;

public class ScrollablePageableableTableModel implements Serializable, TableModel
{
	private static final long serialVersionUID = 8917840399498288212L;
	
	private List<TableModelListener> mLstTableModelListeners;
	private ScrollablePageableModel mObjModel;
	
	public ScrollablePageableableTableModel(final ScrollablePageableModel objModel)
	{
		setModel(objModel);
	}
	
	public void addTableModelListener(final TableModelListener objTableModelListener)
	{
		getTableModelListeners().add(objTableModelListener);
	}
	
	public Class<?> getColumnClass(final int iColumnIndex)
	{
		return(getModel().getColumns().get(iColumnIndex).getType());
	}
	
	public int getColumnCount()
	{
		return(getModel().getColumns().size());
	}
	
	public String getColumnName(final int iColumnIndex)
	{	
		return(getModel().getColumns().get(iColumnIndex).getName());
	}
	
	protected ScrollablePageableModel getModel()
	{
		return(mObjModel);
	}

	public int getRowCount()
	{
		return(getModel().getPageRowCount(getModel().getCurrentPage()));
	}
	
	protected List<TableModelListener> getTableModelListeners()
	{
		if(mLstTableModelListeners == null)
			mLstTableModelListeners = Collections.synchronizedList(new ArrayList<TableModelListener>());
		
		return(mLstTableModelListeners);
	}
	
	public Object getValueAt(final int iRowIndex, final int iColumnIndex)
	{
		try
			{
			Object objRow = getModel().getRow(iRowIndex);
			
			return(getModel().getColumns().get(iColumnIndex).getValue(objRow));
			}
		catch(IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			
			return(null);
			}
	}

	public boolean isCellEditable(final int iRowIndex, final int iColumnIndex)
	{
		return(getModel().getColumns().get(iColumnIndex).isEditable());
	}

	public void removeTableModelListener(final TableModelListener objTableModelListener)
	{
		getTableModelListeners().remove(objTableModelListener);
	}
	
	protected void setModel(final ScrollablePageableModel objModel)
	{
		mObjModel = objModel;
	}
	
	public void setValueAt(final Object objValue, final int iRowIndex, final int iColumnIndex)
	{
		try
			{
			getModel().getColumns().get(iColumnIndex).setValue(getModel().getRow(iRowIndex), objValue);
			}
		catch(IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
	}
}