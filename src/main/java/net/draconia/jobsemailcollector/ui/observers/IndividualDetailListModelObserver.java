package net.draconia.jobsemailcollector.ui.observers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.draconia.jobsemailcollector.ui.model.IndividualDetailListModel;

public class IndividualDetailListModelObserver implements Observer, Serializable
{
	private static final long serialVersionUID = 6082599177439547022L;
	
	private IndividualDetailListModel mObjListModel;
	private String msField;
	
	public IndividualDetailListModelObserver(final IndividualDetailListModel objListModel, final String sField)
	{
		setListModel(objListModel);
		setField(sField);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected IndividualDetailListModel getListModel()
	{
		return(mObjListModel);
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> getModelList(final Observable objObservable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> clsModel = objObservable.getClass();
		List<String> lstValues;
		Method funcGetter = clsModel.getMethod("get" + getField() + "List", new Class<?>[0]);
		
		if(!funcGetter.isAccessible())
			funcGetter.setAccessible(true);
		
		lstValues = ((List<String>)(funcGetter.invoke(objObservable, new Object[0])));
		
		return(lstValues);
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setListModel(final IndividualDetailListModel objListModel)
	{
		mObjListModel = objListModel;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		List<String> lstModel;
		
		try
			{
			lstModel = getModelList(objObservable);
			}
		catch(IllegalAccessException  | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			
			lstModel = new ArrayList<String>();
			}
		
		if(!lstModel.equals(getListModel().getFieldList()))
			getListModel().fireContentsChanged();
	}
}