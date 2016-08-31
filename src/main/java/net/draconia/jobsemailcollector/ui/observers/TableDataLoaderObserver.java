package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import net.draconia.jobsemailcollector.ui.model.IndividualTableModel;

public class TableDataLoaderObserver implements Observer, Serializable
{
	private static final long serialVersionUID = -8374133270888762877L;
	
	private IndividualTableModel mObjTableModel;
	
	public TableDataLoaderObserver(final IndividualTableModel objTableModel)
	{
		setTableModel(objTableModel);
	}
	
	protected IndividualTableModel getTableModel()
	{
		return(mObjTableModel);
	}
	
	protected void setTableModel(final IndividualTableModel objTableModel)
	{
		mObjTableModel = objTableModel;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		getTableModel().fireTableChanged();
	}
}