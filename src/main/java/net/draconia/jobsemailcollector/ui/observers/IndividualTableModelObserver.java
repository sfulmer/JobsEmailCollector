package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import net.draconia.jobsemailcollector.model.Model;
import net.draconia.jobsemailcollector.ui.table.model.ScrollablePageableModel;

public class IndividualTableModelObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 9025948665633545964L;
	
	private ScrollablePageableModel mObjModel;
	
	public IndividualTableModelObserver(final ScrollablePageableModel objModel)
	{
		setModel(objModel);
	}
	
	protected ScrollablePageableModel getModel()
	{
		return(mObjModel);
	}
	
	protected void setModel(final ScrollablePageableModel objModel)
	{
		mObjModel = objModel;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		Model objModel = ((Model)(objObservable));
		
		getModel().setList(objModel.getTableData());
	}
}