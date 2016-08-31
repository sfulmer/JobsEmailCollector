package net.draconia.jobsemailcollector.ui.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.table.TableModel;

import net.draconia.jobsemailcollector.model.ShallowIndividual;

public class IndividualTableModel implements Serializable, TableModel
{
	private static final long serialVersionUID = -4271052497610622215L;
	
	private List<TableModelListener> mLstTableModelListeners;
	private Map<Integer, ShallowIndividual> mMapTableData;
	
	public IndividualTableModel()
	{ }
	
	public IndividualTableModel(final Map<Integer, ShallowIndividual> mapTableData)
	{
		setTableData(mapTableData);
	}
	
	public void addTableModelListener(final TableModelListener objTableModelListener)
	{
		getTableModelListeners().add(objTableModelListener);
	}
	
	public void fireTableChanged()
	{
		for(TableModelListener objListener : getTableModelListeners())
			objListener.tableChanged(new TableModelEvent(this, 0, getRowCount(), TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}
	
	public Class<?> getColumnClass(final int iColumn)
	{
		switch(iColumn)
			{
			case 0:
				return(Integer.class);
			case 1:
				return(String.class);
			case 2:
				return(Date.class);
			default:
				return(null);
			}
	}
	
	public int getColumnCount()
	{
		return(3);
	}
	
	public String getColumnName(final int iColumn)
	{
		switch(iColumn)
			{
			case 0:
				return("Id");
			case 1:
				return("Name");
			case 2:
				return("Last Email Contact");
			default:
				return(null);
			}
	}
	
	protected Map<Integer, ShallowIndividual> getTableData()
	{
		return(mMapTableData);
	}
	
	public int getRowCount()
	{
		return(getTableData().size());
	}
	
	protected List<TableModelListener> getTableModelListeners()
	{
		if(mLstTableModelListeners == null)
			mLstTableModelListeners = new ArrayList<TableModelListener>();
		
		return(mLstTableModelListeners);
	}
	
	public Object getValueAt(final int iRow, final int iColumn)
	{
		ShallowIndividual objDataRow = getTableData().get(iRow);
		
		if(objDataRow != null)
			switch(iColumn)
				{
				case 0:
					return(objDataRow.getId());
				case 1:
					return(objDataRow.getName());
				case 2:
					return(objDataRow.getDateOfLastEmail());
				default:
					return(null);
				}
		else
			return(null);
	}
	
	public boolean isCellEditable(final int iRow, final int iColumn)
	{
		return(false);
	}
	
	public void removeTableModelListener(final TableModelListener objTableModelListener)
	{
		getTableModelListeners().remove(objTableModelListener);
	}
	
	protected void setTableData(final Map<Integer, ShallowIndividual> mapTableData)
	{
		mMapTableData = mapTableData;
	}
	
	public void setValueAt(final Object objValue, final int iRow, final int iColumn)
	{ }
}